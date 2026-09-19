# Showcases

`showcases/` is a non-Gradle grouping directory reserved for future
journey-oriented, runnable KlumAST material. Each future project lives directly
at `showcases/<immutable-kebab-case-journey-slug>/` as an independent Gradle
root with its own settings, build, wrapper, sources, and README. No showcase has
been selected or created yet.

## Boundaries

- Showcases are runnable journeys, not feature-oriented validation proofs.
- This directory has no build and does not aggregate its future children or
  either project under `validations/`.
- A future showcase must be named and scoped before its standalone project is
  added; this grouping directory is not a placeholder build.
- Each added showcase receives an explicit reviewed CI job that runs its own
  documented normal check and evidence commands from the project directory.

## Governing records

- Cross-repository consumer/showcase verification is governed by KlumAST
  [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  and [issue #484](https://github.com/klum-dsl/klum-ast/issues/484).
- Catwalk-local CI and project guidance remain tracked by
  [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) and
  [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2).
