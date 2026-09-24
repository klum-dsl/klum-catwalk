# Domain-first smart-home Layer 3 journey

This Catwalk showcase ports the existing KlumAST smart-home Layer 3 fixture into
four independently runnable consumer projects. It starts with a backend-neutral
home Domain API, realizes a `CityFlat` Schema, records one configured Model, and
lets a Java client inspect window state through the Domain API alone.

The exact upstream authority is
[`agent-skills/fixtures/domain-first-smart-home`](https://github.com/klum-dsl/klum-ast/tree/e17085870ec05ff8f0becdd75f033cd7abb29aac/agent-skills/fixtures/domain-first-smart-home)
at KlumAST commit
[`e17085870ec05ff8f0becdd75f033cd7abb29aac`](https://github.com/klum-dsl/klum-ast/commit/e17085870ec05ff8f0becdd75f033cd7abb29aac).
The domain types, floorplan, configured values, generic client, and documentary
story assertions are preserved. The annotation imports are adapted to the
canonical public 4.0.1 packages established by S1, and the `DisplayName`
annotation is compiled as Java before the Schema Groovy sources to satisfy the
released compiler's annotation-validation order. Catwalk otherwise changes the
delivery topology: upstream's aggregate fixture becomes independent leaf
projects with explicit binary handoffs.

## Roles and boundaries

- [`domain-api/`](domain-api/README.md) owns abstract `Home`, `Room`, `Window`,
  and device DSL types. Its bounded `windows` Cluster makes the window context
  explicit in Model scripts.
- [`schema/`](schema/README.md) consumes that JAR and owns the concrete
  `CityFlat` floorplan.
- [`client/`](client/README.md) consumes only the Domain API JAR. Its boundary
  is enforced by the dependency graph and ordinary compilation: no Schema or
  Model artifact is available to import.
- [`model/`](model/README.md) consumes the three prior JARs, registers the
  configured Model, and runs the end-to-end story and validation assertions.

This directory is intentionally not a Gradle project. Every leaf owns its own
settings, build, wrapper, README, and sources. There are no project
dependencies, included/composite builds, source-set composition, or
`mavenLocal()` repositories. [`artifacts/`](artifacts/README.md) is the ignored
binary handoff directory. The leaf builds contain no Catwalk-specific
verification or export tasks; repository orchestration is isolated in the
top-level [`fixtures/`](../../fixtures/README.md) directory.

## Run the journey

Each copied leaf uses the ordinary project command `./gradlew clean check`.
Within Catwalk, the following workflow-equivalent commands additionally apply
the opt-in fixture helper to perform the required binary handoff:

```shell
(cd domain-api && ./gradlew clean check exportCatwalkArtifact -I ../../../fixtures/showcase-artifact-handoff.init.gradle -PcatwalkArtifactDirectory=../artifacts)
(cd schema && ./gradlew clean check exportCatwalkArtifact -I ../../../fixtures/showcase-artifact-handoff.init.gradle -PcatwalkArtifactDirectory=../artifacts)
(cd client && ./gradlew clean check exportCatwalkArtifact -I ../../../fixtures/showcase-artifact-handoff.init.gradle -PcatwalkArtifactDirectory=../artifacts)
(cd model && ./gradlew clean check exportCatwalkArtifact -I ../../../fixtures/showcase-artifact-handoff.init.gradle -PcatwalkArtifactDirectory=../artifacts)
```

CI executes the same four commands in explicit project-leaf jobs. The external
fixture helper moves normal project JARs between jobs with workflow artifacts;
it does not alter the showcase build model.

## Selected public coordinates

All leaves resolve from Maven Central and the Gradle Plugin Portal. The pinned
KlumAST release is `4.0.1` on the baseline Groovy 3 line:

- `com.blackbuild.klum-ast-schema:com.blackbuild.klum-ast-schema.gradle.plugin:4.0.1`
  in `domain-api/` and `schema/`.
- `com.blackbuild.klum-ast-model:com.blackbuild.klum-ast-model.gradle.plugin:4.0.1`
  in `model/`.
- `com.blackbuild.convention.groovy:com.blackbuild.convention.groovy.gradle.plugin:4.0.1`
  in `client/`.
- `com.blackbuild.klum.ast:klum-ast-runtime:4.0.1` in the Client and Model
  leaves as the explicit replacement for runtime metadata lost at flat-file
  artifact handoffs.
- `org.codehaus.groovy:groovy:3.0.25` in the Java Client leaf because the
  compiled Groovy Domain API exposes `GroovyObject` in its type hierarchy.

The journey artifacts use the local showcase coordinate namespace
`org.klum.catwalk.showcase.smarthome` and version `1.0.0`; they are copied, not
published.

## Governing records

- KlumAST [issue #469](https://github.com/klum-dsl/klum-ast/issues/469) remains
  the open 4.1 onboarding umbrella. This Catwalk implementation candidate does
  not close it.
- KlumAST [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  governs cross-repository showcase verification.
- Catwalk [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) governs
  project-local CI conventions.

After this showcase merges, the stable link recommended for later KlumAST
tutorial prose and portable-skill references is
`https://github.com/klum-dsl/klum-catwalk/tree/main/showcases/domain-first-smart-home`.
