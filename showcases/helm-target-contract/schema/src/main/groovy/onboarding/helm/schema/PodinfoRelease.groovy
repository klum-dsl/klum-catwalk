package onboarding.helm.schema

import com.blackbuild.klum.ast.DSL
import com.blackbuild.klum.ast.Default
import com.blackbuild.klum.ast.Key
import com.blackbuild.klum.ast.Required
import com.blackbuild.klum.ast.Validate
import com.blackbuild.klum.ast.layer3.AutoCreate

/**
 * Concise direct-Schema authoring model for the pinned Podinfo 6.15.0 values contract.
 */
@DSL
class PodinfoRelease {

    @Key String name

    String imageRepository = 'ghcr.io/stefanprodan/podinfo'
    String imageTag = '6.15.0'
    int replicaCount = 2
    String uiMessage
    boolean redisEnabled
    String backendRelease
    boolean ingressEnabled
    String ingressClassName = 'nginx'

    @Default(code = { ingressEnabled ? "${name}.example.test" : null })
    String hostname

    @Required('resources with requests and limits are required')
    ResourceRequirements resources

    /** Expands the authoring conveniences into Podinfo chart values. */
    Map<String, Object> toHelmValues() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.replicaCount = replicaCount
        values.image = [repository: imageRepository, tag: imageTag]
        if (uiMessage) {
            values.ui = [message: uiMessage]
        }
        if (backendRelease) {
            values.backend = "http://${backendRelease}-podinfo:9898/echo".toString()
        }
        values.redis = [enabled: redisEnabled]
        values.resources = resources.toHelmValues()
        Map<String, Object> ingress = [enabled: ingressEnabled]
        if (ingressEnabled) {
            ingress.className = ingressClassName
            ingress.hosts = [[
                    host : hostname,
                    paths: [[path: '/', pathType: 'Prefix']]
            ]]
        }
        values.ingress = ingress
        values
    }

    @Validate
    void requiresSemanticImageTag() {
        assert imageTag ==~ /\d+\.\d+\.\d+/ : 'imageTag must be a semantic version such as 6.15.0'
    }

    @Validate
    void requiresDnsSafeBackendRelease() {
        assert !backendRelease || backendRelease ==~ /[a-z0-9]([-a-z0-9]*[a-z0-9])?/ :
                'backendRelease must be a DNS label'
    }

    @Validate
    void requiresHostnameForIngress() {
        assert !ingressEnabled || hostname : 'ingress-enabled releases need a hostname'
    }
}

@DSL
class ResourceRequirements {

    @Required('resource requests are required')
    ResourceValues requests

    ResourceValues limits

    @AutoCreate
    void defaultsLimitsFromRequests() {
        if (requests && !limits) {
            def requested = requests
            limits {
                copyFrom requested
            }
        }
    }

    Map<String, Object> toHelmValues() {
        [requests: requests.toHelmValues(), limits: limits.toHelmValues()]
    }

    @Validate(level = Validate.Level.WARNING)
    void memoryLimitsShouldMatchRequests() {
        assert !requests || !limits || requests.memory == limits.memory :
                'memory limit differs from memory request'
    }
}

@DSL
class ResourceValues {

    @Required('resource CPU is required')
    String cpu

    @Required('resource memory is required')
    String memory

    Map<String, String> toHelmValues() {
        [cpu: cpu, memory: memory]
    }
}
