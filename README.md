# klum-catwalk

`klum-catwalk` is a documentation and orchestration container for isolated
KlumAST consumer validations and curated runnable showcases. It is not a
Gradle build and intentionally has no root build files or Gradle wrapper.

## Repository navigation

- [`validations/direct-schema`](validations/direct-schema/README.md) — the SC-1
  direct published-consumer proof.
- [`validations/layer3-contracts`](validations/layer3-contracts/README.md) — the
  independent SC-3 Layer 3 API + Schema contract validation.
- [`showcases/domain-first-smart-home`](showcases/domain-first-smart-home/README.md)
  — the four-leaf smart-home Layer 3 onboarding journey with explicit binary
  artifact handoffs.

Each Gradle project is invoked through its own wrapper. Projects do not include,
compose, or build one another, and there is no root aggregate build.

The [CI workflow](.github/workflows/ci.yml) runs the documented project-local
commands in explicit per-project jobs for both validations and each actual
smart-home role leaf. It resolves only public coordinates and does not perform
candidate, release, publish, or deploy work. Showcase JARs move between jobs as
explicit workflow artifacts; CI has no root aggregate, directory scan,
placeholder job, or dormant showcase command. The opt-in handoff task comes
from [`fixtures/`](fixtures/README.md), keeping Catwalk-only plumbing out of the
copyable showcase builds.
Repository agent setup remains tracked by
[issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2).
