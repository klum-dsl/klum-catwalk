# Layer 3 contracts validation

This is the reserved independent Gradle project for future SC-3 Layer 3
contract validation. It currently contains only project-local Gradle lifecycle
wiring and no contract fixtures, source-level composition, or CT-1/CT-2/CT-3
implementation.

Run the empty project boundary check from this directory:

```shell
./gradlew check
```

## Boundaries

- This project does not include or build the direct Schema validation or the
  future projects grouped under `showcases/`.
- Adding Layer 3 behavior, copying paused worktree content, or changing KlumAST
  or AnnoDocimal is outside this skeleton's scope.

## Governing records

- Cross-repository consumer/showcase verification is governed by KlumAST
  [ADR 0019](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0019-published-showcase-consumer-verification.md)
  and [issue #484](https://github.com/klum-dsl/klum-ast/issues/484).
- Reusable Layer 3 Domain API contract packaging is governed by KlumAST
  [ADR 0018](https://github.com/klum-dsl/klum-ast/blob/master/docs/adr/0018-domain-api-contract-test-packaging.md)
  and [issue #755](https://github.com/klum-dsl/klum-ast/issues/755).
- Catwalk-local CI and project guidance remain tracked by
  [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) and
  [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2).
