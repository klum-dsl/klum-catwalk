package org.klum.catwalk.api

import spock.lang.Specification

abstract class EnvironmentContract<T extends Environment<? extends Application>> extends Specification {

    protected abstract T environment()

    def 'exposes its application through the Domain API'() {
        expect:
        environment().application.name == 'shipping'
    }
}
