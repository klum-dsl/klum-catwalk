# Helm target-contract values leaf

This independent Gradle project consumes only the compiled
`../artifacts/schema-1.0.0.jar`. `CatalogValues` and `BillingValues` are its two
representative authoring inputs. Its documentary test renders both through the
Schema-owned Helm mapping and performs stable semantic comparisons against the
pinned target snapshots and separate golden outputs.

After providing the Schema handoff, run its normal command:

```shell
./gradlew clean check
```

Generated files are written to `build/generated-values/`. Assertions compare
parsed YAML trees, not incidental formatting or key order. The project applies
the public Groovy convention plugin `4.0.1`, consumes
`com.blackbuild.klum.ast:klum-ast-runtime:4.0.1`, Groovy `3.0.25`, and Jackson
YAML `2.14.2`, and resolves Spock `2.4-groovy-3.0`, all from ordinary public
repositories. It has no Schema source, project dependency, included build, or
`mavenLocal()` repository.

See the [journey README](../README.md) for immutable source and chart/target
pins, the complete tool list, handoff order, contract assertions, boundaries,
governing links, and later stable link target.
