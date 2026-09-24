# Journey artifact handoff

This directory is the explicit binary seam between the four independent Gradle
projects. Generated JARs are ignored by Git. Build the exporting leaf, then
place or download its JAR here before running a consuming leaf.

Expected files:

- `domain-api-1.0.0.jar`, exported by `domain-api/` and consumed by `schema/`,
  `client/`, and `model/`.
- `schema-1.0.0.jar`, exported by `schema/` and consumed by `model/`.
- `client-1.0.0.jar`, exported by `client/` and consumed by `model/` tests.
- `model-1.0.0.jar`, exported by `model/` as the final configured-model
  artifact.

No leaf includes another build, composes another leaf's sources, or declares a
Gradle project dependency.
