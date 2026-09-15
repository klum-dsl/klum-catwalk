package org.klum.catwalk.schema

import org.klum.catwalk.api.contract.EnvironmentContract

final class CustomerEnvironmentContractSpec extends EnvironmentContract<CustomerEnvironment> {

    @Override
    protected CustomerEnvironment environment() {
        CustomerEnvironment.Create.With {
            primaryApplication {
                name 'shipping'
            }
        }
    }
}
