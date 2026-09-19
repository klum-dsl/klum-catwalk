# Showcases

`showcases/` is a non-Gradle grouping directory reserved for future
journey-oriented, runnable KlumAST material. Each
`showcases/<immutable-kebab-case-journey-slug>/` directory is a non-Gradle
journey container. Its actual project roots are role-specific leaves such as
`schema/` and `model/`, with `api/` or `client/` only when the journey needs
them. Each leaf owns its settings, build, wrapper, sources, and README. No
showcase has been selected or created yet.

## Boundaries

- Showcases are runnable journeys, not feature-oriented validation proofs.
- Neither this directory nor a journey container has a build or aggregates
  project leaves or either project under `validations/`.
- A future showcase must be named and scoped before its project leaves are
  added; journey containers are not placeholder builds.
- Each added project leaf receives an explicit reviewed CI job that runs its
  own documented normal check and evidence commands from the leaf directory.

## Governing records

- Cross-repository consumer/showcase verification is governed by KlumAST
  [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  and [issue #484](https://github.com/klum-dsl/klum-ast/issues/484).
- Catwalk-local CI and project guidance remain tracked by
  [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) and
  [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2).
