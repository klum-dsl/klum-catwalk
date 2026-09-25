package onboarding.helm.values

import onboarding.helm.schema.ServiceRelease

/** Concise authoring input for the public catalog service. */
class CatalogValues {

    static ServiceRelease create() {
        ServiceRelease.Create.With('catalog') {
            imageTag '1.4.0'
            publiclyReachable true
            resources {
                requests { cpu '250m'; memory '256Mi' }
            }
        }
    }
}
