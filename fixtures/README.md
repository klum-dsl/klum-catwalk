# Catwalk fixture plumbing

Files in this directory support Catwalk's repository-level verification and CI
topology. They are not part of a showcase's adopter-facing Gradle setup.

[`showcase-artifact-handoff.init.gradle`](showcase-artifact-handoff.init.gradle)
adds one opt-in `exportCatwalkArtifact` task to an otherwise standalone Java or
Groovy project. Catwalk uses it to copy the project's normal JAR into a journey
handoff directory. A copied showcase project can run `./gradlew check` without
this script.
