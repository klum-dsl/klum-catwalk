# Validations

`validations/` groups independent, feature-oriented proof projects. It is not a
Gradle project: it has no settings, build, or wrapper, and it does not aggregate
its children.

- [`direct-schema`](direct-schema/README.md) contains the executable SC-1
  published-consumer proof.
- [`layer3-contracts`](layer3-contracts/README.md) reserves the independent SC-3
  contract-validation boundary.

Invoke each child only through that project's own Gradle wrapper.
