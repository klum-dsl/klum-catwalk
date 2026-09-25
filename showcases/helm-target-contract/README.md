# Podinfo Helm target-contract direct-Schema journey

This Catwalk showcase authors and verifies values for the real Podinfo Helm
chart through two independently runnable consumer projects. A direct
`PodinfoRelease` Schema provides concise defaults and target-specific
conveniences; the values leaf renders a backend with Redis and an
ingress-enabled frontend connected to that backend.

## Immutable target and license

The target is [Podinfo](https://github.com/stefanprodan/podinfo), licensed
under Apache-2.0 by Stefan Prodan and contributors:

- chart and application version/tag: [`6.15.0`](https://github.com/stefanprodan/podinfo/releases/tag/6.15.0),
  annotated tag object `a9b846405af7697629de8a1da335aaf639a09f34`
- source commit: [`dd507173b7b75b2312a36cabe0de5f09c1ce69c8`](https://github.com/stefanprodan/podinfo/commit/dd507173b7b75b2312a36cabe0de5f09c1ce69c8)
- source path: [`charts/podinfo`](https://github.com/stefanprodan/podinfo/tree/dd507173b7b75b2312a36cabe0de5f09c1ce69c8/charts/podinfo)
- official archive: [`podinfo-6.15.0.tgz`](https://stefanprodan.github.io/podinfo/podinfo-6.15.0.tgz)
- archive SHA-256: `5ca7896889b539e04cdad4df2093ff1ff7576295e7e3ef90a1e1845e3a334d75`
- license at the pinned source:
  [`charts/podinfo/LICENSE`](https://github.com/stefanprodan/podinfo/blob/dd507173b7b75b2312a36cabe0de5f09c1ce69c8/charts/podinfo/LICENSE)

The unmodified official archive is checked in beside a machine-readable
[`pin.properties`](values/src/test/resources/helm/chart/podinfo-6.15.0.pin.properties)
record. The archive contains Podinfo's full Apache-2.0 license; repository-level
attribution is recorded in [`THIRD_PARTY_NOTICES.md`](THIRD_PARTY_NOTICES.md).
Tests verify the archive digest and its `name`, `version`, and `appVersion`
metadata before rendering.

KlumAST's historical [target-contract journey](https://github.com/klum-dsl/klum-ast/tree/d4fdbc75c4f52a5f61a821626a77b370b36203da/agent-skills/fixtures/helm-target-contract)
established the direct-Schema, two-input, semantic-golden shape. This Catwalk
implementation keeps that onboarding shape while replacing its illustrative
contract with the immutable Podinfo target above.

## Inputs and semantic evidence

- [`BackendWithRedisValues.groovy`](values/src/main/groovy/onboarding/helm/values/BackendWithRedisValues.groovy)
  enables Podinfo's in-chart Redis deployment and supplies explicit resource
  requests and limits.
- [`FrontendToBackendValues.groovy`](values/src/main/groovy/onboarding/helm/values/FrontendToBackendValues.groovy)
  names the backend release; the Schema derives Podinfo's backend URL, enables
  ingress, derives the host, and defaults limits from requests.

The values tests compare parsed generated YAML with checked-in semantic values
goldens. They then run `helm template` from the vendored chart and generated
files only, parse the rendered Kubernetes documents, and compare intentional
semantic summaries. The summaries prove workload/service names, images,
replicas, Redis wiring, frontend backend URL, and ingress routing without
coupling the test to YAML comments, quoting, order, Helm source comments,
checksums, or unrelated chart defaults.

Rendering uses Helm `4.3.0`, namespace `podinfo`, Kubernetes capability version
`1.34.0`, and `--skip-tests`. It performs no dependency update, repository
lookup, schema download, cluster validation, installation, or deployment.
Once the pinned Helm executable is present, rendering is offline and
deterministic from the checked-in archive, authored inputs, and fixed flags.

## Roles and handoff

- [`schema/`](schema/README.md) owns the direct `PodinfoRelease`, resource
  defaults, Podinfo values mapping, and validation.
- [`values/`](values/README.md) receives only `schema-1.0.0.jar`, owns the two
  authoring inputs, verifies the target pin, renders offline, and checks values
  plus manifest semantics.

This directory is intentionally not a Gradle project. Each role leaf has its
own settings, build, wrapper, source, tests, and README. There is no repository
or journey aggregate, project dependency, included/composite build, source
composition, or `mavenLocal()` repository. [`artifacts/`](artifacts/README.md)
is the ignored binary handoff location. Catwalk-only export plumbing stays in
the top-level [`fixtures/`](../../fixtures/README.md) directory.

## Run the journey

Install Helm `4.3.0` on `PATH`, then run the workflow-equivalent commands in
handoff order from this directory:

```shell
(cd schema && ./gradlew clean check exportCatwalkArtifact -I ../../../fixtures/showcase-artifact-handoff.init.gradle -PcatwalkArtifactDirectory=../artifacts)
(cd values && ./gradlew clean check)
```

Each leaf's normal command is `./gradlew clean check`; the values leaf requires
the documented Schema JAR input. CI installs the exact Helm version, executes
the same commands in explicit jobs, moves the Schema JAR as a workflow
artifact, and retains generated values and rendered manifests for inspection.

## Pinned coordinates and tools

All Gradle dependencies resolve from Maven Central and the Gradle Plugin
Portal. The reproducible identities are:

- KlumAST `4.0.1`, tag target commit
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
- Gradle wrapper `8.14.4`, Java toolchain `17`, Helm `4.3.0`, and pinned
  Kubernetes template capability `1.34.0`.
- Journey artifacts `org.klum.catwalk.showcase.helm:{schema,values}:1.0.0`;
  these are copied between jobs, not published.
- Podinfo chart/application `6.15.0` with the commit and archive digest above.

## Boundaries and governing records

This is a Podinfo-specific authoring seam, not arbitrary YAML import/export, a
general Helm adapter, a portable skill, or feature-advisor work. It does not
introduce KlumAST Template/layering support, read live chart defaults, compose
ordered overlays, contact a cluster, deploy Helm, publish artifacts, promote a
retained baseline, or act as a release/candidate gate.

- KlumAST [issue #469](https://github.com/klum-dsl/klum-ast/issues/469) remains
  the open 4.1 onboarding umbrella. This Catwalk implementation candidate does
  not close it.
- KlumAST [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  governs cross-repository showcase verification.
- Historical [issue #472](https://github.com/klum-dsl/klum-ast/issues/472) and
  [PR #530](https://github.com/klum-dsl/klum-ast/pull/530) own the original
  target-contract journey shape; Catwalk owns this real-target executable port.
- KlumAST issue [#304](https://github.com/klum-dsl/klum-ast/issues/304)
  continues to own stackable configuration and overwrite composition.

After merge, the exact stable link recommended for later KlumAST tutorial and
portable-skill follow-up is
`https://github.com/klum-dsl/klum-catwalk/tree/main/showcases/helm-target-contract`.
