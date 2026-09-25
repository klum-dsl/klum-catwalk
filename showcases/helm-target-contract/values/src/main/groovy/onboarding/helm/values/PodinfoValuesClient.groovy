package onboarding.helm.values

import onboarding.helm.schema.HelmValuesWriter
import onboarding.helm.schema.PodinfoRelease
import onboarding.helm.schema.PodinfoStack

import java.nio.file.Path

/** Loads the single top-level stack Model and emits its two Podinfo values files. */
final class PodinfoValuesClient {
    private final HelmValuesWriter writer = new HelmValuesWriter()

    /** Reads and validates the registered top-level stack Model. */
    PodinfoStack readModel() {
        PodinfoStack.Create.FromClasspath()
    }

    /** Writes both releases from a previously loaded stack. */
    List<Path> writeValues(PodinfoStack stack, Path outputDirectory) {
        [stack.backend, stack.frontend].collect { PodinfoRelease release ->
            writer.write(release, outputDirectory.resolve("${release.name}.values.yaml"))
        }
    }

    /** Loads the registered Model and writes one {@code <release>.values.yaml} file per release. */
    List<Path> writeValues(Path outputDirectory) {
        writeValues(readModel(), outputDirectory)
    }

    /** Command-line entry point used by the {@code generateHelmValues} task. */
    static void main(String[] args) {
        Path outputDirectory = Path.of(args ? args.first() : 'build/generated-values')
        new PodinfoValuesClient().writeValues(outputDirectory)
    }
}
