# Layer 3 contracts validation

This independent Gradle project is the Catwalk Layer 3 API + Schema contract
consumer. It implements ADR 0018's CT-1 and CT-2 topology against the published
KlumAST Schema plugin:

- `:domain-api` applies `java-library` and `java-test-fixtures`, owns the generic
  `EnvironmentContract`, and keeps that contract out of its main artifact.
- `:schema` applies the published `com.blackbuild.klum-ast-schema` plugin,
  depends explicitly on `testFixtures(project(':domain-api'))`, and supplies a
  concrete `CustomerEnvironmentContractSpec` realization.
- [`gradle.properties`](gradle.properties) selects one Groovy/Spock pair for
  both modules. `verifyLayer3ContractEvidence` checks the resolved pair,
  fixture artifact boundary, API-only contract source, successful inherited
  Spock test report, and an isolated wiring harness. The harness proves the
  wired control compiles and that removing only the test-fixtures dependency
  makes `:schema:compileTestGroovy` fail on the missing contract while retaining
  the Domain API production dependency.

Run the normal project-local verification from this directory:

```shell
./gradlew clean check
```

For the focused contract path, run
`./gradlew :schema:test verifyLayer3ContractEvidence`. The generated evidence
is written below `build/verification/`; build output is not retained in Git.

## Boundaries

- `validations/` is only a grouping directory. This project owns its settings,
  build, subprojects, dependencies, and wrapper; it neither includes nor builds
  `validations/direct-schema` or anything under `showcases/`.
- The reusable contract names Domain API types only. Schema classes, generated
  `*_DSL` types, Builders, Cluster helpers, and KlumAST runtime internals remain
  outside its surface.
- The optional Model module is deliberately absent.
- Dependencies use ordinary binary project wiring and public repositories.
  There is no source composition, composite substitution, KlumAST checkout,
  `mavenLocal()`, or deliberately broken negative-build fixture.
- This validation changes no KlumAST or AnnoDocimal product code and adds no
  shared convention, repository-root build, or CI policy.

## Governing records

- Cross-repository consumer/showcase verification is governed by KlumAST
  [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  and [issue #484](https://github.com/klum-dsl/klum-ast/issues/484).
- Reusable Layer 3 Domain API contract packaging is governed by KlumAST
  [ADR 0018](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0018-domain-api-contract-test-packaging.md)
  and [issue #755](https://github.com/klum-dsl/klum-ast/issues/755).
- This is a Catwalk implementation candidate related to open KlumAST issue
  #755; it does not claim to close that upstream issue.
- Catwalk-local CI and project guidance remain tracked by
  [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) and
  [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2).
