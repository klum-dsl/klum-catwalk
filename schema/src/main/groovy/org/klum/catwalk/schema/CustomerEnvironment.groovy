package org.klum.catwalk.schema

import com.blackbuild.klum.ast.DSL
import org.klum.catwalk.api.Application
import org.klum.catwalk.api.Environment

@DSL
class CustomerApplication implements Application {
    String name
}

@DSL
class CustomerEnvironment implements Environment<CustomerApplication> {
    CustomerApplication application
}
