package org.klum.catwalk.schema

import org.klum.catwalk.api.EnvironmentContract

final class CustomerEnvironmentContractSpec extends EnvironmentContract<CustomerEnvironment> {

    @Override
    protected CustomerEnvironment environment() {
        CustomerEnvironment.Create.With {
            application(CustomerApplication.Create) {
                name 'shipping'
            }
        }
    }
}
