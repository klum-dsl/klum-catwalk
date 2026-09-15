package org.klum.catwalk.schema

import spock.lang.Specification

class GroovySpockAlignmentSpec extends Specification {

    def 'uses the centrally selected Groovy and Spock pair'() {
        given:
        String expectedSpock = System.getProperty('catwalk.expectedSpockVersion')

        expect:
        GroovySystem.version == System.getProperty('catwalk.expectedGroovyVersion')
        Specification.package.implementationVersion == expectedSpock.takeBefore('-')
        Specification.protectionDomain.codeSource.location.path.contains("spock-core-${expectedSpock}.jar")
    }
}
