package onboarding.helm.schema

import com.blackbuild.klum.ast.DSL
import com.blackbuild.klum.ast.Required

/** Single top-level Model entry point containing both cooperating Podinfo releases. */
@DSL
class PodinfoStack {

    /** Backend release with the in-chart Redis deployment. */
    @Required PodinfoRelease backend

    /** Public frontend release connected to {@link #backend}. */
    @Required PodinfoRelease frontend
}
