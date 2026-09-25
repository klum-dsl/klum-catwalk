# Podinfo target-contract values leaf

This independent Gradle project consumes only the compiled
`../artifacts/schema-1.0.0.jar`. `PodinfoStackModel` is one concise executable
Model script, not a wrapper class, and is registered as the `PodinfoStack`
classpath entry point. `PodinfoValuesClient` loads that completed stack and
delegates each release's actual YAML output to the Schema's `HelmValuesWriter`.
The documentary test has a separate entry-point read-and-validation case and a
verbatim frontend YAML assertion, then verifies the vendored Podinfo `6.15.0`
archive and renders both scenarios with Helm `4.3.0` entirely from local inputs.

After providing the Schema handoff and Helm `4.3.0` on `PATH`, run its normal
command:

```shell
./gradlew clean check
```

To run the production client explicitly and write both values files:

```shell
./gradlew generateHelmValues
```

Generated values are written to `build/generated-values/`; rendered Kubernetes
documents are written to `build/rendered-manifests/`. The test compares parsed
values with semantic values goldens, and compares a deliberately selected
summary of rendered workloads, services, images, Redis/backend wiring, and
ingress routing with semantic render goldens. It pins Kubernetes capabilities
to `1.34.0` and skips chart tests. It performs no chart download, dependency
update, cluster access, install, or deployment during rendering.

The project applies the public KlumAST Model plugin `4.0.1`, consumes
`com.blackbuild.klum.ast:klum-ast-runtime:4.0.1`, Groovy `3.0.25`, Jackson YAML
`2.14.2`, and Spock `2.4-groovy-3.0` from ordinary public repositories. It has
no Schema source, project dependency, included build, or `mavenLocal()`
repository.

See the [journey README](../README.md) for immutable Podinfo source/license and
chart pins, the complete tool list, handoff order, offline-render contract,
boundaries, governing links, and later stable link target.
