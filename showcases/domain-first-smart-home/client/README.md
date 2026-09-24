# Smart-home API-only client leaf

This independent Gradle project owns a small Java client that walks generic
`Home`/`Room`/`Window` Cluster projections and delegates live state lookup to a
`WindowStateService`. It knows no concrete room, Schema, Model, provider,
Builder, or generated DSL type.

First produce or download `../artifacts/domain-api-1.0.0.jar`, then run:

```shell
./gradlew clean check exportArtifact
```

`verifyApiOnlyBoundary`, wired into `check`, rejects Schema/Model imports and
proves that `domain-api-1.0.0.jar` is the only local journey artifact on the
compile classpath. Evidence is written to
`build/verification/api-only-client.json`. `exportArtifact` copies
`client-1.0.0.jar` to `../artifacts/` for the Model's documentary test. The
public `com.blackbuild.convention.groovy` plugin and
`com.blackbuild.klum.ast:klum-ast-runtime` at `4.0.1` supply the language and
runtime API exposed by the flat-file Domain API handoff without granting access
to Schema types. The otherwise-lost language metadata is restored explicitly as
`org.codehaus.groovy:groovy:3.0.25`.

See the [journey README](../README.md) for the immutable upstream source,
complete handoff order, coordinates, and governing issues.
