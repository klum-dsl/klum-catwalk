package onboarding.helm.schema

import com.blackbuild.klum.ast.DSL
import com.blackbuild.klum.ast.Key
import com.blackbuild.klum.ast.Required
import com.blackbuild.klum.ast.Validate
import com.blackbuild.klum.ast.layer3.AutoCreate
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue

/**
 * Podinfo 6.15.0 Helm values.
 *
 * <p>The fields deliberately follow the chart's {@code values.yaml} shape. Compact authoring
 * is supplied by converters on the value types rather than by a second, translation-oriented
 * object model.</p>
 */
@DSL
class PodinfoRelease implements HelmValues<PodinfoRelease> {

    /** Helm release name; used for filenames and {@code helm template}, not emitted as a value. */
    @Key @JsonIgnore String name

    /** Number of Podinfo replicas. */
    int replicaCount = 2

    /** Container image coordinates. */
    ImageValues image

    /** Optional Podinfo UI settings. */
    UiValues ui

    /** Optional URL of another Podinfo release used as the echo backend. */
    BackendUrl backend

    /** In-chart Redis settings. */
    RedisValues redis

    /** Container resource requests and limits. */
    @Required ResourceRequirements resources

    /** Ingress settings and routes. */
    IngressValues ingress

    /** Materializes chart defaults that must still appear in the emitted YAML. */
    @AutoCreate
    void createChartDefaults() {
        if (!image) {
            image {
                repository 'ghcr.io/stefanprodan/podinfo'
                tag '6.15.0'
            }
        }
        if (!redis) {
            redis { }
        }
        if (!ingress) {
            ingress { }
        }
    }

    /** Returns this values-shaped model for direct Jackson serialization. */
    @Override
    PodinfoRelease toHelmValues() {
        this
    }
}

/** Container image values accepted by the Podinfo chart. */
@DSL
class ImageValues {
    /** OCI image repository. */
    @Required String repository

    /** Semantic Podinfo image tag. */
    @Required String tag

    @Validate
    void requiresSemanticTag() {
        assert tag ==~ /\d+\.\d+\.\d+/ : 'image tag must be a semantic version such as 6.15.0'
    }
}

/** Optional text rendered by the Podinfo UI. */
@DSL
class UiValues {
    /** Message shown on the Podinfo page. */
    @Required String message

    /** Allows the Model script to use {@code ui 'A message'}. */
    static UiValues fromMessage(String message) {
        UiValues.Create.With(message: message)
    }
}

/** URL value serialized as the scalar expected by {@code values.yaml}. */
final class BackendUrl {
    private final String value

    private BackendUrl(String value) {
        this.value = value
    }

    /** Allows the Model script to use a release name instead of spelling out the service URL. */
    static BackendUrl fromRelease(String releaseName) {
        assert releaseName ==~ /[a-z0-9]([-a-z0-9]*[a-z0-9])?/ :
                'backend release must be a DNS label'
        new BackendUrl("http://${releaseName}-podinfo:9898/echo".toString())
    }

    /** Emits the URL as a YAML scalar. */
    @JsonValue
    String asString() {
        value
    }
}

/** Redis sub-chart values. */
@DSL
class RedisValues {
    /** Whether the bundled Redis deployment is enabled. */
    boolean enabled

    /** Allows the Model script to use {@code redis true}. */
    static RedisValues fromEnabled(boolean value) {
        RedisValues.Create.With(enabled: value)
    }
}

/** Resource requests and limits in the shape expected by Kubernetes. */
@DSL
class ResourceRequirements {

    /** Minimum CPU and memory requested by the Podinfo container. */
    @Required ResourceValues requests

    /** Maximum CPU and memory available to the Podinfo container. */
    ResourceValues limits

    /** Converts a compact CPU/memory pair into requests; limits default from those requests. */
    static ResourceRequirements fromValues(String cpu, String memory) {
        ResourceRequirements.Create.With(requests: ResourceValues.fromValues(cpu, memory))
    }

    /** Converts compact request and limit pairs into the nested Helm values shape. */
    static ResourceRequirements fromValues(
            String requestCpu,
            String requestMemory,
            String limitCpu,
            String limitMemory
    ) {
        ResourceRequirements.Create.With(
                requests: ResourceValues.fromValues(requestCpu, requestMemory),
                limits: ResourceValues.fromValues(limitCpu, limitMemory)
        )
    }

    /** Copies requests into limits when the Model omits an explicit limit pair. */
    @AutoCreate
    void defaultsLimitsFromRequests() {
        if (requests && !limits) {
            def requested = requests
            limits {
                copyFrom requested
            }
        }
    }

    /** Records intentional memory overcommit as a warning without rejecting the Model. */
    @Validate(level = Validate.Level.WARNING)
    void memoryLimitsShouldMatchRequests() {
        assert !requests || !limits || requests.memory == limits.memory :
                'memory limit differs from memory request'
    }
}

/** CPU and memory quantity pair used for requests and limits. */
@DSL
class ResourceValues {

    /** Kubernetes CPU quantity, for example {@code 100m}. */
    @Required String cpu

    /** Kubernetes memory quantity, for example {@code 64Mi}. */
    @Required String memory

    /** Creates the nested pair used by the {@link ResourceRequirements} converters. */
    static ResourceValues fromValues(String cpu, String memory) {
        ResourceValues.Create.With(cpu: cpu, memory: memory)
    }
}

/** Podinfo ingress values. */
@DSL
class IngressValues {
    private static final String DNS_HOSTNAME = /(?=.{1,253}$)[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?(?:\.[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?)*/

    /** Whether an Ingress resource is rendered. */
    boolean enabled

    /** Kubernetes ingress class name. */
    String className

    /** Host and path routes. */
    List<IngressHost> hosts

    /** Allows the Model script to enable the standard nginx route with one hostname. */
    static IngressValues fromHostname(String hostname) {
        if (!(hostname ==~ DNS_HOSTNAME)) {
            throw new IllegalArgumentException('ingress hostname must be a lowercase DNS hostname')
        }
        IngressValues.Create.With(
                enabled: true,
                className: 'nginx',
                hosts: [IngressHost.fromHostname(hostname)]
        )
    }
}

/** One ingress hostname and its paths. */
class IngressHost {
    /** DNS hostname emitted into the Ingress rule. */
    String host

    /** Routes for this hostname. */
    List<IngressPath> paths

    /** Creates the standard root route for one hostname. */
    static IngressHost fromHostname(String hostname) {
        new IngressHost(host: hostname, paths: [IngressPath.ofRoot()])
    }
}

/** One HTTP ingress path. */
class IngressPath {
    /** URL path matched by the rule. */
    String path = '/'

    /** Kubernetes path matching strategy. */
    String pathType = 'Prefix'

    /** Creates the Podinfo chart's standard root prefix path. */
    static IngressPath ofRoot() {
        new IngressPath()
    }
}
