package onboarding.helm.schema

import com.blackbuild.klum.ast.DSL
import com.blackbuild.klum.ast.Default
import com.blackbuild.klum.ast.Key
import com.blackbuild.klum.ast.Required
import com.blackbuild.klum.ast.Validate
import com.blackbuild.klum.ast.layer3.AutoCreate

@DSL
class ServiceRelease {

    @Key String name

    String imageRegistry = 'ghcr.io/acme'

    @Default(code = { "$imageRegistry/$name" })
    String imageRepository

    String imageTag
    int replicaCount = 2
    int containerPort = 8080
    boolean publiclyReachable

    @Default(code = { publiclyReachable ? "${name}.example.test" : null })
    String hostname

    @Required('resources with requests and limits are required')
    ResourceRequirements resources

    /** Expands the concise authoring model into the pinned Helm values contract. */
    Map<String, Object> toHelmValues() {
        Map<String, Object> values = new LinkedHashMap<>()
        values.replicaCount = replicaCount
        values.image = [repository: imageRepository, tag: imageTag]
        values.service = [type: 'ClusterIP', port: containerPort, targetPort: containerPort]
        values.resources = resources.toHelmValues()
        Map<String, Object> ingress = [enabled: publiclyReachable]
        if (publiclyReachable) {
            ingress.put('hosts', [[host: hostname, paths: [[path: '/', pathType: 'Prefix']]]])
        }
        values.ingress = ingress
        values
    }

    @Validate
    void requiresSemanticImageTag() {
        assert imageTag ==~ /\d+\.\d+\.\d+/ : 'imageTag must be a semantic version such as 1.4.0'
    }

    @Validate
    void requiresValidContainerPort() {
        assert containerPort in 1..65535 : 'containerPort must be between 1 and 65535'
    }

    @Validate
    void requiresHostnameForPublicIngress() {
        assert !publiclyReachable || hostname : 'publicly reachable releases need a hostname'
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
