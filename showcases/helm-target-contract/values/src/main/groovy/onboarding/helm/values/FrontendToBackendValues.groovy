package onboarding.helm.values

import onboarding.helm.schema.PodinfoRelease

/** Public Podinfo frontend authoring input connected to the backend release. */
class FrontendToBackendValues {

    static PodinfoRelease create() {
        PodinfoRelease.Create.With('frontend') {
            uiMessage 'Frontend to backend'
            backendRelease 'backend'
            ingressEnabled true
            resources {
                requests { cpu '50m'; memory '64Mi' }
            }
        }
    }
}
