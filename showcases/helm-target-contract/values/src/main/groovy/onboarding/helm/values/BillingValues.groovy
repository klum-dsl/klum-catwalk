package onboarding.helm.values

import onboarding.helm.schema.ServiceRelease

/** Concise authoring input for the internal billing service. */
class BillingValues {

    static ServiceRelease create() {
        ServiceRelease.Create.With('billing') {
            imageTag '2.1.3'
            replicaCount 1
            containerPort 8081
            resources {
                requests { cpu '250m'; memory '256Mi' }
                limits { cpu '500m'; memory '512Mi' }
            }
        }
    }
}
