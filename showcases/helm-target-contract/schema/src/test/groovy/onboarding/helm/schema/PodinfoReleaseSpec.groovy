package onboarding.helm.schema

import com.blackbuild.klum.ast.Validate
import com.blackbuild.klum.ast.runtime.KlumObjectSupport
import com.blackbuild.klum.ast.runtime.validation.KlumValidationException
import spock.lang.Specification

class PodinfoReleaseSpec extends Specification {

    def 'derives Podinfo image, backend, ingress, and resource defaults'() {
        when:
        PodinfoRelease frontend = PodinfoRelease.Create.With('frontend') {
            backendRelease 'backend'
            ingressEnabled true
            resources {
                requests { cpu '50m'; memory '64Mi' }
            }
        }

        then:
        frontend.imageRepository == 'ghcr.io/stefanprodan/podinfo'
        frontend.imageTag == '6.15.0'
        frontend.hostname == 'frontend.example.test'
        frontend.resources.limits.cpu == '50m'
        frontend.resources.limits.memory == '64Mi'
        frontend.toHelmValues().backend == 'http://backend-podinfo:9898/echo'
        frontend.toHelmValues().ingress == [
                enabled  : true,
                className: 'nginx',
                hosts    : [[host: 'frontend.example.test', paths: [[path: '/', pathType: 'Prefix']]]]
        ]
    }

    def 'retains explicit limits and records a non-fatal memory warning'() {
        when:
        PodinfoRelease backend = PodinfoRelease.Create.With('backend') {
            redisEnabled true
            resources {
                requests { cpu '100m'; memory '64Mi' }
                limits { cpu '200m'; memory '128Mi' }
            }
        }
        def issues = KlumObjectSupport.of(backend.resources).validation.result.issues

        then:
        backend.resources.limits.memory == '128Mi'
        issues.any { issue ->
            issue.level == Validate.Level.WARNING &&
                    issue.message.contains('memory limit differs from memory request')
        }
    }

    def 'rejects an invalid image tag, backend release, and missing resources'() {
        when:
        PodinfoRelease.Create.With('frontend') {
            imageTag 'latest'
            backendRelease 'Backend Service'
        }

        then:
        KlumValidationException error = thrown()
        error.message.contains('imageTag must be a semantic version')
        error.message.contains('backendRelease must be a DNS label')
        error.message.contains('resources with requests and limits are required')
    }

    def 'requires memory on each resource value'() {
        when:
        PodinfoRelease.Create.With('backend') {
            resources {
                requests { cpu '100m' }
            }
        }

        then:
        KlumValidationException error = thrown()
        error.message.contains('resource memory is required')
    }
}
