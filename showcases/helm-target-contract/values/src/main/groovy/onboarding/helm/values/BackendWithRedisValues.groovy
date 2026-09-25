package onboarding.helm.values

import onboarding.helm.schema.PodinfoRelease

/** Podinfo backend authoring input with an in-chart Redis deployment. */
class BackendWithRedisValues {

    static PodinfoRelease create() {
        PodinfoRelease.Create.With('backend') {
            uiMessage 'Backend with Redis'
            redisEnabled true
            resources {
                requests { cpu '100m'; memory '64Mi' }
                limits { cpu '200m'; memory '128Mi' }
            }
        }
    }
}
