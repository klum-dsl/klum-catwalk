package org.klum.catwalk.direct

import spock.lang.Specification

class DeploymentSpec extends Specification {

    def 'a completed model is built through the published Schema plugin surface'() {
        when:
        def deployment = Deployment.Create.With {
            endpoint(HttpEndpoint.Create) {
                host 'catwalk.example'
                port 443
            }
        }

        then:
        deployment.endpoint instanceof HttpEndpoint
        deployment.endpoint.host == 'catwalk.example'
        deployment.endpoint.port == 443
    }
}
