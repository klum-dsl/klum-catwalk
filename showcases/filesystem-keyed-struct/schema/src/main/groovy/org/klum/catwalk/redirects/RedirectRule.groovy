package org.klum.catwalk.redirects

import com.blackbuild.klum.ast.DSL
import com.blackbuild.klum.ast.Key
import com.blackbuild.klum.ast.Required
import com.blackbuild.klum.ast.Struct

/** A documentation redirect indexed by its old URL path. */
@DSL
class RedirectRule {
    /** The old URL path. */
    @Key String sourcePath

    /** The destination URL path. */
    @Required @Struct.Required String targetPath
}
