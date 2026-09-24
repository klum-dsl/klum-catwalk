# Smart-home configured Model leaf

This independent Gradle project records provider-specific thermostats and
sensors, durable identifiers, target temperatures, and generic devices for one
`CityFlat`. The Klum Model plugin registers `CityFlatModel` so the end-to-end
test loads it through `CityFlat.Create.FromClasspath()`.

Produce or download all three prior handoffs in `../artifacts/`, then run:

```shell
./gradlew clean check exportArtifact
```

Required inputs are `domain-api-1.0.0.jar`, `schema-1.0.0.jar`, and
`client-1.0.0.jar`. The Schema JAR is declared through the Model plugin's
`schemas` dependency configuration; no project or source dependency is used.
The test preserves the upstream story assertions for Schema defaults,
provider-polymorphic configuration, generic-client window states, and rejection
of a fixed heated room without its required thermostat. `exportArtifact`
packages the tested Model as `model-1.0.0.jar`.

The project applies the public `com.blackbuild.klum-ast-model` plugin and uses
`com.blackbuild.klum.ast:klum-ast-runtime`, both at `4.0.1`, on Groovy 3. See
the [journey README](../README.md) for the immutable upstream source, complete
handoff order, coordinates, and governing issues.
