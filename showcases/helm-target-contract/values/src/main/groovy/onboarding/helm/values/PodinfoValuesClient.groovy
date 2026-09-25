package onboarding.helm.values

import groovy.lang.Script
import onboarding.helm.schema.HelmValuesWriter
import onboarding.helm.schema.PodinfoRelease

import java.nio.file.Path

/** Loads the two top-level Models and emits the actual Podinfo values files. */
final class PodinfoValuesClient {
    private static final List<Class<? extends Script>> VALUE_SCRIPTS = [
            BackendWithRedisValues,
            FrontendToBackendValues
    ]

    private final HelmValuesWriter writer = new HelmValuesWriter()

    /** Reads and validates every configured top-level release. */
    List<PodinfoRelease> readModels() {
        VALUE_SCRIPTS.collect { script -> PodinfoRelease.Create.From(script) }
    }

    /** Writes one {@code <release>.values.yaml} file per configured release. */
    List<Path> writeValues(Path outputDirectory) {
        readModels().collect { release ->
            writer.write(release, outputDirectory.resolve("${release.name}.values.yaml"))
        }
    }

    /** Command-line entry point used by the {@code generateHelmValues} task. */
    static void main(String[] args) {
        Path outputDirectory = Path.of(args ? args.first() : 'build/generated-values')
        new PodinfoValuesClient().writeValues(outputDirectory)
    }
}
