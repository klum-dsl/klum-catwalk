package org.klum.catwalk.structyaml

import com.blackbuild.klum.ast.DSL
import com.blackbuild.klum.ast.Field
import com.blackbuild.klum.ast.Key
import com.blackbuild.klum.ast.Required

/**
 * One caller-supplied release description. Its key is part of the input
 * document, while the keys of {@code services} are supplied by their outer
 * YAML map entries rather than repeated inside each service body.
 */
@DSL
class ReleaseManifest {
    @Key String release

    @Required String region

    List<String> zones

    @Field(members = 'service')
    Map<String, ServiceDefinition> services
}

/** A keyed service body nested in a release manifest. */
@DSL
class ServiceDefinition {
    @Key String name

    @Required String image

    List<Integer> ports

    Map<String, String> labels
}
