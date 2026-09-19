# klum-catwalk

`klum-catwalk` is a documentation and orchestration container for isolated
KlumAST consumer validations and curated runnable showcases. It is not a
Gradle build and intentionally has no root build files or Gradle wrapper.

## Repository navigation

- [`validations/direct-schema`](validations/direct-schema/README.md) — the SC-1
  direct published-consumer proof.
- [`validations/layer3-contracts`](validations/layer3-contracts/README.md) — the
  independent SC-3 Layer 3 API + Schema contract validation.
- [`showcases`](showcases/README.md) — the non-Gradle grouping directory for
  future independent, named showcase projects.

Each Gradle project is invoked through its own wrapper. Projects do not include,
compose, or build one another, and there is no root aggregate build.

The [CI workflow](.github/workflows/ci.yml) runs the documented project-local
commands in explicit per-project jobs: `verifyDirectPublicBaseline` in
`validations/direct-schema` and `clean check` in
`validations/layer3-contracts`. It resolves only public coordinates and does
not perform candidate, release, publish, or deploy work. Future showcases are
added as reviewed per-project jobs only when their projects exist; CI has no
placeholder job, directory scan, or dormant showcase command.
Repository agent setup remains tracked by
[issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2).
