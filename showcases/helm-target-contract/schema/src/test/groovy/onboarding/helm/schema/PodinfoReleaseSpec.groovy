package onboarding.helm.schema

import com.blackbuild.klum.ast.Validate
import com.blackbuild.klum.ast.runtime.KlumObjectSupport
import com.blackbuild.klum.ast.runtime.validation.KlumValidationException
import spock.lang.Specification

class PodinfoReleaseSpec extends Specification {

    def 'converter-backed fields keep authoring compact while retaining the Helm values shape'() {
        when:
        PodinfoRelease frontend = PodinfoRelease.Create.With('frontend') {
            ui 'Frontend to backend'
            backend 'backend'
            ingress 'frontend.example.test'
            resources '50m', '64Mi'
        }

        then:
        frontend.image.repository == 'ghcr.io/stefanprodan/podinfo'
        frontend.image.tag == '6.15.0'
        frontend.ui.message == 'Frontend to backend'
        frontend.backend.asString() == 'http://backend-podinfo:9898/echo'
        !frontend.redis.enabled
        frontend.resources.requests.cpu == '50m'
        frontend.resources.requests.memory == '64Mi'
        frontend.resources.limits.cpu == '50m'
        frontend.resources.limits.memory == '64Mi'
        frontend.ingress.enabled
        frontend.ingress.className == 'nginx'
        frontend.ingress.hosts*.host == ['frontend.example.test']
        frontend.ingress.hosts.first().paths*.path == ['/']
        frontend.ingress.hosts.first().paths*.pathType == ['Prefix']
        frontend.toHelmValues().is(frontend)
    }

    def 'retains explicit resource limits and records a non-fatal memory warning'() {
        when:
        PodinfoRelease backend = PodinfoRelease.Create.With('backend') {
            redis true
            resources '100m', '64Mi', '200m', '128Mi'
        }
        def issues = KlumObjectSupport.of(backend.resources).validation.result.issues

        then:
        backend.redis.enabled
        backend.resources.limits.memory == '128Mi'
        issues.any { issue ->
            issue.level == Validate.Level.WARNING &&
                    issue.message.contains('memory limit differs from memory request')
        }
    }

    def 'rejects an ingress hostname outside the supported lowercase DNS subset'() {
        when:
        IngressValues.fromHostname('Frontend_example.test')

        then:
        IllegalArgumentException error = thrown()
        error.message.contains('lowercase DNS hostname')
    }

    def 'rejects an invalid image tag and missing resources'() {
        when:
        PodinfoRelease.Create.With('frontend') {
            image {
                repository 'ghcr.io/stefanprodan/podinfo'
                tag 'latest'
            }
        }

        then:
        KlumValidationException error = thrown()
        error.message.contains('image tag must be a semantic version')
        error.message.contains('resources')
    }

    def 'uses the default required-value diagnostic for ResourceValues'() {
        when:
        PodinfoRelease.Create.With('backend') {
            resources {
                requests { cpu '100m' }
            }
        }

        then:
        KlumValidationException error = thrown()
        error.message.contains('memory')
    }
}
