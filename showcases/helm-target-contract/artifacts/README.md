# Journey artifact handoff

Catwalk copies `schema-1.0.0.jar` here after the Schema leaf passes. The values
leaf consumes that ordinary binary and has no access to Schema sources or a
Gradle project dependency.

The opt-in [`showcase-artifact-handoff.init.gradle`](../../../fixtures/showcase-artifact-handoff.init.gradle)
helper performs the local copy. CI uses `actions/upload-artifact@v4` and
`actions/download-artifact@v4` for the equivalent job boundary. JARs in this
directory are ignored; they are reproducible build outputs, not checked-in or
published dependencies.
