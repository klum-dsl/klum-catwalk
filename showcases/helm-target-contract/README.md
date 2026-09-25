# Helm target-contract direct-Schema journey

This Catwalk showcase ports KlumAST's existing catalog-and-billing Helm journey
into two independently runnable consumer projects. A direct `ServiceRelease`
Schema provides compact, validated authoring; two configured inputs render
human-readable values files whose parsed meaning is compared with a pinned
target contract and checked-in golden output.

The exact upstream authority is
[`agent-skills/fixtures/helm-target-contract`](https://github.com/klum-dsl/klum-ast/tree/d4fdbc75c4f52a5f61a821626a77b370b36203da/agent-skills/fixtures/helm-target-contract)
at KlumAST merge commit
[`d4fdbc75c4f52a5f61a821626a77b370b36203da`](https://github.com/klum-dsl/klum-ast/commit/d4fdbc75c4f52a5f61a821626a77b370b36203da),
delivered by [PR #530](https://github.com/klum-dsl/klum-ast/pull/530).
The service keys, defaults, validation, convenience mapping, catalog and
billing configurations, target examples, golden values, and semantic
comparison are preserved. Catwalk adapts the old checkout-composite fixture to
the canonical public 4.0.1 packages and separates Schema and values authoring
with an explicit binary handoff.

## Target pin and assertions

The target is the fixture-owned `acme-service` Helm values contract at chart
version `3.2.1`. That illustrative chart is not a separately published chart
archive: its immutable authority is the two upstream representative values
files at the source commit above. Catwalk snapshots those exact contracts in
[`values/src/test/resources/helm/target-contract/`](values/src/test/resources/helm/target-contract/).
This name, version, source commit, and path are the complete target pin.

[`CatalogValues.groovy`](values/src/main/groovy/onboarding/helm/values/CatalogValues.groovy)
and [`BillingValues.groovy`](values/src/main/groovy/onboarding/helm/values/BillingValues.groovy)
are the two concise authoring inputs. The contract test renders each to
`values/build/generated-values/<service>.values.yaml`, parses target, golden,
and actual YAML, and compares their data trees. Comments, quoting, whitespace,
and key order are intentionally not asserted; service/image/resources/ingress
meaning is. The target snapshot and golden output remain separate so a future
contract change cannot silently promote itself by overwriting an expectation.

## Roles and handoff

- [`schema/`](schema/README.md) owns the direct `ServiceRelease`, nested
  resources, derived image/host/limits defaults, target mapping, and validation.
- [`values/`](values/README.md) receives only `schema-1.0.0.jar`, owns the two
  authoring inputs, renders values, and verifies semantic goldens.

This directory is intentionally not a Gradle project. Each role leaf has its
own settings, build, wrapper, source, tests, and README. There is no repository
or journey aggregate, project dependency, included/composite build, source
composition, or `mavenLocal()` repository. [`artifacts/`](artifacts/README.md)
is the ignored handoff location. Catwalk-only export plumbing stays in the
top-level [`fixtures/`](../../fixtures/README.md) directory.

## Run the journey

Each leaf's normal command is `./gradlew clean check`. Run the workflow-equivalent
journey in handoff order from this directory:

```shell
(cd schema && ./gradlew clean check exportCatwalkArtifact -I ../../../fixtures/showcase-artifact-handoff.init.gradle -PcatwalkArtifactDirectory=../artifacts)
(cd values && ./gradlew clean check)
```

CI executes these commands in explicit jobs, uploads the Schema JAR, downloads
it into the values job, and retains the generated YAML for inspection.

## Pinned coordinates and tools

All external dependencies resolve from Maven Central and the Gradle Plugin
Portal. The reproducible identities are:

- KlumAST release `4.0.1`, tag target commit
  [`4d85ec2ed7e0a737d71b421af2c0cf597f6830e4`](https://github.com/klum-dsl/klum-ast/commit/4d85ec2ed7e0a737d71b421af2c0cf597f6830e4).
- Schema plugin marker
  `com.blackbuild.klum-ast-schema:com.blackbuild.klum-ast-schema.gradle.plugin:4.0.1`.
- Groovy convention plugin marker
  `com.blackbuild.convention.groovy:com.blackbuild.convention.groovy.gradle.plugin:4.0.1`.
- Values runtime `com.blackbuild.klum.ast:klum-ast-runtime:4.0.1`.
- Groovy `org.codehaus.groovy:groovy:3.0.25` and Spock
  `org.spockframework:spock-core:2.4-groovy-3.0`.
- YAML parser/writer
  `com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.2`.
- Gradle wrapper `8.14.4` and Java toolchain `17`.
- Journey artifacts `org.klum.catwalk.showcase.helm:{schema,values}:1.0.0`;
  these are copied between jobs, not published.
- Target contract `acme-service` chart identity `3.2.1`, pinned by the immutable
  source commit and path above.

## Boundaries and governing records

This is a target-specific authoring seam, not arbitrary YAML import/export, a
general Helm adapter, a `build-target-contract-schema` skill, or feature-advisor
work. It does not read defaults from resources, compose layered overrides,
deploy with Helm, publish artifacts, promote a retained baseline, or act as a
release/candidate gate.

- KlumAST [issue #469](https://github.com/klum-dsl/klum-ast/issues/469) remains
  the open 4.1 onboarding umbrella. This Catwalk implementation candidate does
  not close it.
- KlumAST [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  governs cross-repository showcase verification.
- Historical [issue #472](https://github.com/klum-dsl/klum-ast/issues/472) and
  [PR #530](https://github.com/klum-dsl/klum-ast/pull/530) own the upstream
  journey; Catwalk owns this executable public-coordinate port.
- KlumAST issues [#79](https://github.com/klum-dsl/klum-ast/issues/79) and
  [#304](https://github.com/klum-dsl/klum-ast/issues/304) remain outside this
  showcase's resource-default and composition boundaries.

After merge, the exact stable link recommended for later KlumAST tutorial and
portable-skill follow-up is
`https://github.com/klum-dsl/klum-catwalk/tree/main/showcases/helm-target-contract`.
