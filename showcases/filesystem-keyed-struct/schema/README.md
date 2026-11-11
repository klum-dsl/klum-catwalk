# Redirect Rule Schema leaf

`RedirectRule` uses the old URL path as its construction key (`sourcePath`).
The value `targetPath` is the destination. Run the two example inputs and the
project-local verification with JDK 17.

```shell
./gradlew run
./gradlew clean check
```

The standalone input at `examples/standalone/redirect.yaml` supplies
`sourcePath: /guides/install` and `targetPath: /getting-started/setup`. The caller
passes the parsed mapping to `RedirectRule.Create.FromStruct(document, sourceName)`.

For the directory catalog, the caller walks `examples/redirects/` without
following symlinks and selects regular files named `redirect.yaml` in descendant
directories. It sorts files by their slash-separated relative paths and derives
each key by joining the parent directory segments with `/` and adding a leading
`/`. The file `examples/redirects/guides/install/redirect.yaml` supplies only
`targetPath: /getting-started/setup`. The caller passes its directory-derived
`/guides/install` key and the parsed body to
`RedirectRule.Create.FromStruct(key, body, sourceName)`. In both calls,
`sourceName` is a diagnostic label relative to `examples/`.

The caller reads UTF-8 and uses SnakeYAML's `SafeConstructor` to parse a single
mapping, reject duplicate keys, and pass String-keyed data to the Factory.
Filesystem discovery, key derivation, YAML parsing, and schema association belong
to the caller. KlumAST generates the artifacts and constructs Models from parsed
data. The tests use an external JSON Schema validator.

## Schema association

The leaf uses the public KlumAST Schema plugin at `4.2.0-rc.5`. It registers
`RedirectRule` in both `rootTypes` and `bodyRootTypes`. Run
`./gradlew generateKlumJsonSchema` to generate the artifacts under
`build/generated/klum-json-schema/`. Select the matching file explicitly in an
editor or external validator.

| Input | Artifact |
| --- | --- |
| `examples/standalone/redirect.yaml` | `org.klum.catwalk.redirects.RedirectRule.schema.json` |
| `examples/redirects/**/redirect.yaml` below the catalog root | `org.klum.catwalk.redirects.RedirectRule.builder-body.schema.json` |

The check verifies deterministic discovery, equivalent construction of the two
`/guides/install` examples, acceptance of each YAML shape by its matching
schema, and rejection of swapped shapes and duplicate YAML keys. Catwalk CI runs
the leaf's check and executable demo. The showcase is separate from the retained
Struct/YAML validation and KlumAST release qualification.

See [Catwalk issue #14](https://github.com/klum-dsl/klum-catwalk/issues/14) and
[KlumAST ADR 0032](https://github.com/klum-dsl/klum-ast/blob/v4.2.0-rc.5/docs/adr/0032-builder-oriented-structured-input.md).
