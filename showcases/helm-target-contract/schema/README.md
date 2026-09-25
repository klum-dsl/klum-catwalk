# Helm target-contract Schema leaf

This independent Gradle project owns the direct `ServiceRelease` authoring
contract. It derives image repositories, public hosts, and default resource
limits; expands one public-ingress convenience into the pinned values shape;
and validates image tags, ports, host requirements, resources, CPU, and memory.
A differing memory limit remains a non-fatal warning, preserving the upstream
journey behavior.

Run its normal command:

```shell
./gradlew clean check
```

The project applies public
`com.blackbuild.klum-ast-schema:com.blackbuild.klum-ast-schema.gradle.plugin:4.0.1`,
uses Groovy 3 on Java 17, and supplies its own Spock tests. Catwalk orchestration
may additionally export the normal `schema-1.0.0.jar` using the external helper
described in the [journey README](../README.md); no handoff task is embedded in
this build.

See the journey README for the immutable source, `acme-service:3.2.1` target
pin, complete coordinate/tool list, handoff command, assertions, boundaries,
governing links, and later stable link target.
