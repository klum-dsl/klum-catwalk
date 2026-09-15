package org.klum.catwalk.api.contract

import org.klum.catwalk.api.Application
import org.klum.catwalk.api.Environment
import spock.lang.Specification

abstract class EnvironmentContract<T extends Environment> extends Specification {

    protected abstract T environment()

    def 'exposes generic primary application'() {
        given:
        Environment configuredEnvironment = environment()

        when:
        Application primaryApplication = configuredEnvironment.primaryApplication

        then:
        primaryApplication.name == 'shipping'
    }
}
