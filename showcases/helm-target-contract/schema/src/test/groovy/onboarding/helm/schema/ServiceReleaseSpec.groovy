package onboarding.helm.schema

import com.blackbuild.klum.ast.Validate
import com.blackbuild.klum.ast.runtime.KlumObjectSupport
import com.blackbuild.klum.ast.runtime.validation.KlumValidationException
import spock.lang.Specification

class ServiceReleaseSpec extends Specification {

    def 'derives target-specific defaults and ingress values'() {
        when:
        ServiceRelease catalog = ServiceRelease.Create.With('catalog') {
            imageTag '1.4.0'
            publiclyReachable true
            resources {
                requests { cpu '250m'; memory '256Mi' }
            }
        }

        then:
        catalog.imageRepository == 'ghcr.io/acme/catalog'
        catalog.hostname == 'catalog.example.test'
        catalog.resources.limits.cpu == '250m'
        catalog.resources.limits.memory == '256Mi'
        catalog.toHelmValues().ingress == [
                enabled: true,
                hosts  : [[host: 'catalog.example.test', paths: [[path: '/', pathType: 'Prefix']]]]
        ]
    }

    def 'retains a completed model and records a non-fatal memory warning'() {
        when:
        ServiceRelease billing = ServiceRelease.Create.With('billing') {
            imageTag '2.1.3'
            resources {
                requests { cpu '250m'; memory '256Mi' }
                limits { cpu '500m'; memory '512Mi' }
            }
        }
        def issues = KlumObjectSupport.of(billing.resources).validation.result.issues

        then:
        billing.resources.limits.memory == '512Mi'
        issues.any { issue ->
            issue.level == Validate.Level.WARNING &&
                    issue.message.contains('memory limit differs from memory request')
        }
    }

    def 'rejects values that cannot satisfy the Helm contract'() {
        when:
        ServiceRelease.Create.With('catalog') {
            imageTag 'latest'
            containerPort 0
        }

        then:
        KlumValidationException error = thrown()
        error.message.contains('imageTag must be a semantic version')
        error.message.contains('containerPort must be between 1 and 65535')
        error.message.contains('resources with requests and limits are required')
    }

    def 'requires memory on each resource value'() {
        when:
        ServiceRelease.Create.With('catalog') {
            imageTag '1.4.0'
            resources {
                requests { cpu '250m' }
            }
        }

        then:
        KlumValidationException error = thrown()
        error.message.contains('resource memory is required')
    }
}
