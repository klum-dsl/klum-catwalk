# Validations

`validations/` groups independent, feature-oriented proof projects. It is not a
Gradle project: it has no settings, build, or wrapper, and it does not aggregate
its children.

- [`direct-schema`](direct-schema/README.md) contains the executable SC-1
  published-consumer proof.
- [`layer3-contracts`](layer3-contracts/README.md) contains the independent SC-3
  Layer 3 API + Schema contract validation.
- [`struct-yaml`](struct-yaml/README.md) contains the pending 4.2 Struct/YAML
  consumer baseline. Its source fixture is intentionally not runnable until
  KlumAST publishes the exact public RC coordinate.

Invoke each child only through that project's own Gradle wrapper.
