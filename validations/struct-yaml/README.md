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

The Schema plugin also generates a JSON Schema for `ReleaseManifest`. The test
loads that artifact and validates the same caller-owned parsed document through
a normal JSON Schema validator. Schema validation is explicit consumer work: it
does not replace Struct conversion or full Model lifecycle validation.

## Status and normal command

The source fixture is ready for review, but no 4.2 public candidate is
available yet. `gradle.properties` therefore contains the deliberately
unresolvable placeholder `4.2.0-rc.1-PENDING-PUBLICATION`. Do not run Gradle
against it and do not substitute a local or guessed version.

After KlumAST Hive announces the exact public `4.2.0-rc.1` coordinate, replace
that placeholder only through the approved baseline update and run from this
directory with a fresh Gradle user home:

```shell
GRADLE_USER_HOME="$(mktemp -d)" ./gradlew verifyStructYamlPublicBaseline
```

The retained task will run source-mirror generation, JSON Schema generation,
Javadoc, tests, normal checks, and public-coordinate evidence recording. It
will reject any version other than the formal retained baseline.

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
