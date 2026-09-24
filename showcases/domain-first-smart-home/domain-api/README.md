# Smart-home Domain API leaf

This independent Gradle project owns the abstract, backend-neutral smart-home
DSL: homes, rooms, windows, devices, and provider-specific device variants. It
also owns the reusable `DisplayName` annotation because `displayName` is a
Domain API property; concrete Schemas still choose the actual labels. The
project contains no floorplan Schema, configured Model, or client code.

Run the copyable project's normal command from this directory:

```shell
./gradlew clean check
```

The project applies the public
`com.blackbuild.klum-ast-schema` plugin at `4.0.1` on Groovy 3 because the
abstract API types are themselves Klum DSL types.

Catwalk journey orchestration applies the external fixture helper; it is not
part of this build:

```shell
./gradlew exportCatwalkArtifact \
  -I ../../../fixtures/showcase-artifact-handoff.init.gradle \
  -PcatwalkArtifactDirectory=../artifacts
```

This copies the normal `domain-api-1.0.0.jar` for the Schema and Client leaves.

See the [journey README](../README.md) for the immutable upstream source,
complete handoff order, coordinates, and governing issues.
