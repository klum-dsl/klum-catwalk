# Journey artifact handoff

This directory is the explicit binary seam between the four independent Gradle
projects. Generated JARs are ignored by Git. Catwalk CI and local journey runs
apply the repository's clearly separated
[`showcase-artifact-handoff.init.gradle`](../../../fixtures/showcase-artifact-handoff.init.gradle)
fixture helper to copy each leaf's normal JAR here. The helper is orchestration
plumbing, not part of any copyable showcase project.

Expected files:

- `domain-api-1.0.0.jar`, exported by `domain-api/` and consumed by `schema/`,
  `client/`, and `model/`.
- `schema-1.0.0.jar`, exported by `schema/` and consumed by `model/`.
- `client-1.0.0.jar`, exported by `client/` and consumed by `model/` tests.
- `model-1.0.0.jar`, exported by `model/` as the final configured-model
  artifact.

No leaf includes another build, composes another leaf's sources, declares a
Gradle project dependency, or defines a Catwalk-specific export task.
