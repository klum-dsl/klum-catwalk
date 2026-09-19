# Direct Schema validation

This independent Gradle project contains the SC-1 direct Schema consumer and
its completed-model test. It verifies KlumAST through the published 4.0.1
plugin marker and modules. The canonical plugin ID and module coordinates are
declared directly in the build so this project also serves as a conventional
KlumAST setup example.

The project owns its settings, build, wrapper, selected version, fixture
sources, tests, baseline metadata, and evidence. Run the passing
public-coordinate checks from this directory, preferably with a fresh Gradle
user home:

```shell
GRADLE_USER_HOME="$(mktemp -d)" ./gradlew verifyDirectPublicBaseline
```

[`gradle.properties`](gradle.properties) selects KlumAST 4.0.1 by default. To
smoke-test the unchanged fixture against another compatible public version,
override only the version for `test` and `check`:

```shell
./gradlew -PklumAstVersion=VERSION test check
```

The evidence tasks deliberately reject non-4.0.1 versions until SC-2
introduces an explicit release-line or candidate product manifest.

`./gradlew verifyDirectPublicBaseline` retains the full clean-to-evidence task
graph. The retained public 4.0.1 pin completes source-mirror generation,
Javadoc, tests, and resolved-evidence verification against the intended
AnnoDocimal 1.0.1 graph. The current baseline and recorded result are in
[`baselines/4.0.json`](baselines/4.0.json) and the linked evidence record below;
the original public 4.0.0 failure record remains historical evidence.

## Evidence

- [`2026-09-19 public 4.0.1`](evidence/2026-09-19-public-4.0.1.md)
- [`2026-09-14 public 4.0.0`](evidence/2026-09-14-public-4.0.0.md) (historical)

## Boundaries

- Dependencies resolve only from Maven Central and the Gradle Plugin Portal.
- No KlumAST checkout, composite build, project/file dependency, `mavenLocal()`,
  local fallback, or workaround is used.
- The generated `*_DSL` source mirror is IDE metadata and must not become a
  compilation, Javadoc, packaging, publication, or runtime input.
- Candidate staging and rebinding remain future SC-2 gates. Layer 3 contracts,
  showcase journeys, CI, source-level composition, and product changes are out
  of scope.

## Governing records

- Cross-repository consumer/showcase verification is governed by KlumAST
  [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  and [issue #484](https://github.com/klum-dsl/klum-ast/issues/484).
- Catwalk-local CI and project guidance remain tracked by
  [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) and
  [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2).
