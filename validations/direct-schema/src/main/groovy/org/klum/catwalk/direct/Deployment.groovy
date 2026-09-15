package org.klum.catwalk.direct

import com.blackbuild.klum.ast.DSL

/**
 * Small direct-consumer schema used for public-coordinate verification.
 *
 * <p>This deliberately retains the legacy paragraph and tag shape traversed
 * by AnnoDocimal’s Javadoc path.</p>
 *
 * @since 4.0.0
 */
@DSL
class Deployment {
    /** The polymorphic endpoint generates a signature using an external nested runtime type. */
    Endpoint endpoint
}

@DSL
abstract class Endpoint {
    String host
}

@DSL
class HttpEndpoint extends Endpoint {
    int port
}
