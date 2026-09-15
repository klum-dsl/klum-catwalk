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
  showcases project.
- Adding Layer 3 behavior, copying paused worktree content, or changing KlumAST
  or AnnoDocimal is outside this skeleton's scope.
- Repository CI and project guidance remain governed by open
  [issue #1](https://github.com/klum-dsl/klum-catwalk/issues/1) and
  [issue #2](https://github.com/klum-dsl/klum-catwalk/issues/2). No governing
  ADR is currently present in this repository.
