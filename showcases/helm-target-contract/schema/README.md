# Podinfo target-contract Schema leaf

This independent Gradle project owns the direct `PodinfoRelease` authoring
contract for Podinfo chart `6.15.0`. It defaults the pinned Podinfo image and
replica count, derives an in-cluster backend URL from a backend release name,
derives an ingress host, defaults resource limits from requests, expands the
conveniences into the chart's values keys, and validates target-specific
constraints. Explicitly different memory limits remain a non-fatal warning.

Run its normal command:

```shell
./gradlew clean check
```

The project applies public
`com.blackbuild.klum-ast-schema:com.blackbuild.klum-ast-schema.gradle.plugin:4.0.1`,
uses Groovy 3 on Java 17, and supplies focused Spock tests. Catwalk orchestration
may additionally export the normal `schema-1.0.0.jar` with the external helper
described in the [journey README](../README.md); no handoff task is embedded in
this build.

See the journey README for immutable Podinfo source/license evidence, the chart
archive digest, complete coordinates/tools, handoff commands, semantic
assertions, boundaries, governing links, and later stable link target.
