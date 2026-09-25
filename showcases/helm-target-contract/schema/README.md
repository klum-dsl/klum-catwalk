# Podinfo target-contract Schema leaf

This independent Gradle project owns the single top-level `PodinfoStack` and
its `PodinfoRelease` contract for Podinfo chart `6.15.0`. The stack contains the
backend and frontend releases so a Model library publishes one registered entry
point. Each release's fields follow the nested `values.yaml` output directly:
image, UI, backend, Redis, resources, and ingress. Typed converter methods make
those fields fluent on input (`ui 'message'`, `backend 'release'`,
`ingress 'host'`, and `resources '50m', '64Mi'`) without introducing a separate
translation-oriented model. `ResourceRequirements.fromValues` is the focused
custom converter example; limits default from requests, while explicitly
different memory limits remain a non-fatal warning.

`PodinfoRelease` implements the documented `HelmValues` interface and returns
its already values-shaped instance. The production `HelmValuesWriter` uses
Jackson to serialize that object directly to a requested path. Public value
types and their relevant fields carry API documentation, and bare `@Required`
annotations use KlumAST's standard diagnostics.

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
