# Direct Schema validation

This independent Gradle project contains the SC-1 direct Schema consumer and
its completed-model test. It verifies KlumAST through the published 4.0.0
plugin marker and modules selected by the repository-level
[`coordinates.json`](../../coordinates.json).

The project owns its settings, build, wrapper, fixture sources, tests, and
generated verification report. Run the passing public-coordinate checks from
this directory, preferably with a fresh Gradle user home:

```shell
GRADLE_USER_HOME="$(mktemp -d)" ./gradlew test check verifyResolvedConsumerEvidence
```

`./gradlew verifyDirectPublicBaseline` retains the full clean-to-evidence task
graph. At the historical 4.0.0 pin it is expected to stop at AnnoDocimal #99
while projecting `KlumFactory.BuilderFactoryProvider`; this prevents Javadoc
from starting. The recorded result is preserved in
[`validation/2026-09-14-public-4.0.0.md`](../../validation/2026-09-14-public-4.0.0.md).

## Boundaries

- Dependencies resolve only from Maven Central and the Gradle Plugin Portal.
- No KlumAST checkout, composite build, project/file dependency, `mavenLocal()`,
  local fallback, or workaround is used.
- The generated `*_DSL` source mirror is IDE metadata and must not become a
  compilation, Javadoc, packaging, publication, or runtime input.
- Candidate staging and rebinding belong to SC-2. Layer 3 contracts, showcase
  journeys, CI, source-level composition, and product changes are out of scope.
- Repository CI and project guidance remain governed by open
  [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) and
  [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2). No governing
  ADR is currently present in this repository.
