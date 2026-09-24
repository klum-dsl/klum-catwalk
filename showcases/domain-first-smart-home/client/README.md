# Smart-home API-only client leaf

This independent Gradle project owns a small Java client that walks generic
`Home`/`Room`/`Window` Cluster projections and delegates live state lookup to a
`WindowStateService`. It knows no concrete room, Schema, Model, provider,
Builder, or generated DSL type.

After providing `../artifacts/domain-api-1.0.0.jar`, run the copyable project's
normal command:

```shell
./gradlew clean check
```

The build declares only `domain-api-1.0.0.jar` as a local journey dependency,
so ordinary Java compilation enforces the API-only boundary: Schema and Model
types are unavailable. The public `com.blackbuild.convention.groovy` plugin and
`com.blackbuild.klum.ast:klum-ast-runtime` at `4.0.1` supply the language and
runtime API exposed by the flat-file Domain API handoff without granting access
to Schema types. The otherwise-lost language metadata is restored explicitly as
`org.codehaus.groovy:groovy:3.0.25`.

Catwalk journey orchestration copies the normal `client-1.0.0.jar` with the
external fixture helper described in the [journey README](../README.md). No
export or verification plumbing lives in this build.

See the [journey README](../README.md) for the immutable upstream source,
complete handoff order, coordinates, and governing issues.
