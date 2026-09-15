# klum-catwalk

`klum-catwalk` is a documentation and orchestration container for isolated
KlumAST consumer validations and curated runnable showcases. It is not a
Gradle build and intentionally has no root build files or Gradle wrapper.

## Repository navigation

- [`validations/direct-schema`](validations/direct-schema/README.md) — the SC-1
  direct published-consumer proof.
- [`validations/layer3-contracts`](validations/layer3-contracts/README.md) — the
  reserved independent SC-3 validation project.
- [`showcases`](showcases/README.md) — the non-Gradle grouping directory for
  future independent, named showcase projects.

Each Gradle project is invoked through its own wrapper. Projects do not include,
compose, or build one another, and there is no root aggregate build. CI and
repository agent setup remain tracked by [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1)
and [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2), respectively.
