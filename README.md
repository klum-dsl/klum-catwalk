# klum-catwalk

`klum-catwalk` is the separate home for KlumAST published-consumer verification
and, later, curated runnable showcases.  Its first retained baseline is a
small direct Schema consumer for the released KlumAST 4.0.0 line.

Run the independently passing public-coordinate checks from a fresh Gradle user
home:

```shell
GRADLE_USER_HOME="$(mktemp -d)" ./gradlew :direct-schema:test :direct-schema:check verifyResolvedConsumerEvidence
```

`verifyDirectPublicBaseline` also executes the retained full task graph.  With
the historical 4.0.0 pin it is currently expected to stop at AnnoDocimal #99
while projecting the generated signature containing
`KlumFactory.BuilderFactoryProvider`.  Consequently Javadoc cannot start.
That is the intentionally recorded public integration result, not a reason to
modify the fixture, swap coordinates, or add a local fallback.  See
[`validation/2026-09-14-public-4.0.0.md`](validation/2026-09-14-public-4.0.0.md).

The fixture resolves only from Maven Central and the Gradle Plugin Portal.  It
does not use a KlumAST checkout, composite substitution, project or file
dependencies to KlumAST, or `mavenLocal()`.  The generated `*_DSL` source
mirror is deliberately IDE metadata: the verification task fails if it leaks
into compilation, Javadoc, packaging, publication, or runtime inputs.

`coordinates.json` is the immutable coordinate selection for this repository
revision.  `baselines/4.0.json` retains the historical public contract and
records what a future candidate-maintenance run may rebind.  Candidate staging
and rebinding are SC-2 work; they are intentionally absent here.

## Boundaries recorded by SC-1

- KlumAST 4.0.0 is the confirmed public pin.  Its released product set does
  not include `klum-ast-test-support`; SC-1a owns the separate resolver
  correction needed before 4.0.x admission evidence can be claimed.
- AnnoDocimal #99 and #100 remain upstream integration seams.  This fixture
  makes their nested-signature/source-projection and Javadoc paths executable;
  it carries no override, patch, or compatibility workaround.
- The Layer 3 topology, source-level Schema composition, workflows, remote
  bootstrap, and all candidate repository logic remain out of scope.
