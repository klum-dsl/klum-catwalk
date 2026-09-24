# Smart-home Schema leaf

This independent Gradle project realizes the Domain API as one concrete
`CityFlat`: named rooms and windows, required thermostat/smoke-detector slots,
and Schema-owned display-name defaults.

After providing `../artifacts/domain-api-1.0.0.jar`, run the copyable project's
normal command:

```shell
./gradlew clean check
```

It applies the public `com.blackbuild.klum-ast-schema` plugin at `4.0.1` on
Groovy 3. The Domain API supplies the Groovy `DisplayName` annotation; this
Schema owns only the concrete labels it assigns to its room and window types.
Because the annotation arrives in the already-compiled Domain API JAR, public
4.0.1 can validate its `@DefaultValues` contract without special source sets or
build tasks.

Catwalk journey orchestration copies the normal `schema-1.0.0.jar` with the
external fixture helper described in the [journey README](../README.md). No
export or verification plumbing lives in this build.

See the [journey README](../README.md) for the immutable upstream source,
complete handoff order, coordinates, and governing issues.
