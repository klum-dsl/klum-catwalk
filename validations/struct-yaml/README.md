# Struct/YAML validation

This independent Gradle project is Catwalk's retained 4.2 Struct/YAML consumer
baseline. It proves the boundary between caller-owned YAML parsing and
KlumAST-owned conversion of an already parsed Java structure.

The fixture parses one YAML document into an insertion-ordered String-keyed
`Map` with nested `Map`, `List`, and scalar values. SnakeYAML rejects duplicate
keys before KlumAST receives the structure. The caller then invokes
`ReleaseManifest.Create.FromStruct(document, 'release-manifest.yaml')`; the
keyed root takes `release` from its own input member, while keyed service
children take their identities from the outer `services` map keys. Service
bodies deliberately do not repeat those keys.

The Schema plugin generates a formatted Draft 2020-12 JSON Schema for
`ReleaseManifest`. The test reads the generated file and uses a JSON Schema
validator to accept the caller-owned document and reject a string in a nested
integer port list. Schema validation and completed Model validation are
separate steps.

## Status and normal command

The retained candidate is public KlumAST `4.2.0-rc.4`. Run the public
coordinate check from this directory with a fresh Gradle user home:

```shell
GRADLE_USER_HOME="$(mktemp -d)" ./gradlew verifyStructYamlPublicBaseline
```

The retained task runs source-mirror generation, JSON Schema generation,
Javadoc, and tests. It records the wrapper task invocation, test counts,
resolved compile and runtime coordinates, and mirror checks in
`build/verification/struct-yaml-public-evidence.json`. It rejects other
KlumAST versions and requires a passing test report.

## Evidence

- [`2026-11-10 public 4.2.0-rc.4`](evidence/2026-11-10-public-4.2.0-rc.4.md)
- [`2026-11-08 public 4.2.0-rc.1`](evidence/2026-11-08-public-4.2.0-rc.1.md) (historical)

## Boundaries

- The existing `validations/direct-schema` and `validations/layer3-contracts`
  baselines remain unchanged.
- This project uses the normal Schema plugin marker and a matching KlumAST BOM,
  AST, and runtime graph; it introduces no new plugin.
- Dependencies resolve only through Maven Central and the Gradle Plugin Portal.
  Formal evidence uses no `mavenLocal()`, composite/source build, file/project
  dependency, local artifact, or substitution.
- The project does not recreate Schelm, own YAML parser behavior beyond its
  explicit caller policy, or imply that KlumAST parses YAML.
- Templates, overlays, and caller-pre-keyed Builder bodies are intentionally
  outside this first baseline.

## Governing records

- Catwalk [issue #12](https://github.com/klum-dsl/klum-catwalk/issues/12)
  owns this baseline.
- Catwalk [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) and
  [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2) govern CI and
  repository guidance.
- KlumAST [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  and [issue #484](https://github.com/klum-dsl/klum-ast/issues/484) govern
  cross-repository consumer verification.
