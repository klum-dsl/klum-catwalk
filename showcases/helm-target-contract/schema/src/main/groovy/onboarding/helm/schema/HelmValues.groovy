package onboarding.helm.schema

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

import java.nio.file.Files
import java.nio.file.Path

/** Contract implemented by completed output models that can be emitted as one Helm values file. */
interface HelmValues<T> {

    /**
     * Returns the values-shaped object consumed by the YAML writer.
     *
     * <p>A schema that already mirrors {@code values.yaml} can simply return itself.</p>
     */
    T toHelmValues()
}

/** Small production client that turns one completed output model into a values file. */
final class HelmValuesWriter {
    private final ObjectMapper yaml = new ObjectMapper(new YAMLFactory())
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

    /** Writes {@code values} to {@code outputFile} and returns the created path. */
    Path write(HelmValues<?> values, Path outputFile) {
        Path parent = outputFile.parent
        if (parent) {
            Files.createDirectories(parent)
        }
        yaml.writeValue(outputFile.toFile(), values.toHelmValues())
        outputFile
    }
}
