# Smart-home configured Model leaf

This independent Gradle project records provider-specific thermostats and
sensors, durable identifiers, target temperatures, and generic devices for one
`CityFlat`. The Klum Model plugin registers `CityFlatModel` so the end-to-end
test loads it through `CityFlat.Create.FromClasspath()`.

After providing all three prior handoffs in `../artifacts/`, run the copyable
project's normal command:

```shell
./gradlew clean check
```

Required inputs are `domain-api-1.0.0.jar`, `schema-1.0.0.jar`, and
`client-1.0.0.jar`. The Schema JAR is declared through the Model plugin's
`schemas` dependency configuration; no project or source dependency is used.
The test preserves the upstream story assertions for Schema defaults,
provider-polymorphic configuration, generic-client window states, and rejection
of a fixed heated room without its required thermostat. The bounded `windows`
Cluster makes `windows { street { ... } }` and `windows { garden { ... } }`
explicit in the Model rather than relying on an unexplained bare room member.

The project applies the public `com.blackbuild.klum-ast-model` plugin and uses
`com.blackbuild.klum.ast:klum-ast-runtime`, both at `4.0.1`, on Groovy 3. See
the [journey README](../README.md) for the immutable upstream source, complete
handoff order, coordinates, and governing issues.

Catwalk journey orchestration copies the normal `model-1.0.0.jar` with the
external fixture helper described there. No export or verification plumbing
lives in this build.
