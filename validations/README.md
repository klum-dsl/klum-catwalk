# Validations

`validations/` groups independent, feature-oriented proof projects. It is not a
Gradle project: it has no settings, build, or wrapper, and it does not aggregate
its children.

- [`direct-schema`](direct-schema/README.md) contains the executable SC-1
  published-consumer proof.
- [`layer3-contracts`](layer3-contracts/README.md) contains the independent SC-3
  Layer 3 API + Schema contract validation.

Invoke each child only through that project's own Gradle wrapper.
