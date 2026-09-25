# Showcases

`showcases/` is a non-Gradle grouping directory for
journey-oriented, runnable KlumAST material. Each
`showcases/<immutable-kebab-case-journey-slug>/` directory is a non-Gradle
journey container. Its actual project roots are role-specific leaves such as
`schema/` and `model/`, with `domain-api/` or `client/` only when the journey
needs them. Each leaf owns its settings, build, wrapper, sources, and README.

## Available journeys

- [`domain-first-smart-home`](domain-first-smart-home/README.md) — a Layer 3
  Domain API, concrete `CityFlat` Schema, configured Model, and API-only client
  ported from the immutable KlumAST onboarding fixture.
- [`helm-target-contract`](helm-target-contract/README.md) — a direct-Schema
  Podinfo backend/Redis and frontend/ingress journey with a binary Schema
  handoff plus offline semantic values and rendered-manifest checks.

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
