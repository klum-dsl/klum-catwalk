# Smart-home Domain API leaf

This independent Gradle project owns the abstract, backend-neutral smart-home
DSL: homes, rooms, windows, devices, and provider-specific device variants. It
contains no floorplan Schema, configured Model, or client code.

Run its normal command from this directory:

```shell
./gradlew clean check exportArtifact
```

`exportArtifact` verifies the role boundary and copies
`domain-api-1.0.0.jar` to `../artifacts/`. That JAR is the only journey input
allowed in the Schema and Client leaves. The project applies the public
`com.blackbuild.klum-ast-schema` plugin at `4.0.1` on Groovy 3 because the
abstract API types are themselves Klum DSL types.

See the [journey README](../README.md) for the immutable upstream source,
complete handoff order, coordinates, and governing issues.
