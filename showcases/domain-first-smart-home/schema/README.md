# Smart-home Schema leaf

This independent Gradle project realizes the Domain API as one concrete
`CityFlat`: named rooms and windows, required thermostat/smoke-detector slots,
and Schema-owned display-name defaults.

First produce or download `../artifacts/domain-api-1.0.0.jar`, then run:

```shell
./gradlew clean check exportArtifact
```

The build fails before compilation when the Domain API handoff is absent.
`exportArtifact` verifies that the Schema JAR contains `CityFlat` without
repackaging Domain API classes, then copies `schema-1.0.0.jar` to
`../artifacts/`. It applies the public `com.blackbuild.klum-ast-schema` plugin
at `4.0.1` on Groovy 3.

See the [journey README](../README.md) for the immutable upstream source,
complete handoff order, coordinates, and governing issues.
