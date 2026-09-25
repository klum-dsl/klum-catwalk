package onboarding.helm.values

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import onboarding.helm.schema.ServiceRelease
import spock.lang.Issue
import spock.lang.See
import spock.lang.Specification
import spock.lang.Tag
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Path

@Issue('https://github.com/klum-dsl/klum-ast/issues/469')
@Tag('documentary')
@See('https://github.com/klum-dsl/klum-ast/tree/d4fdbc75c4f52a5f61a821626a77b370b36203da/agent-skills/fixtures/helm-target-contract')
class HelmValuesContractSpec extends Specification {

    private final ObjectMapper yaml = new ObjectMapper(new YAMLFactory())

    @Unroll
    def 'renders #release.name values with the pinned target semantics'() {
        when:
        String generated = yaml.writeValueAsString(release.toHelmValues())
        Path generatedValues = Path.of('build', 'generated-values', "${release.name}.values.yaml")
        Files.createDirectories(generatedValues.parent)
        Files.writeString(generatedValues, generated)

        def targetContract = yaml.readTree(resourceText("helm/target-contract/${release.name}.values.yaml"))
        def goldenOutput = yaml.readTree(resourceText("helm/golden/${release.name}.values.yaml"))
        def actual = yaml.readTree(generated)

        then: 'formatting is readable, while compatibility is intentionally semantic'
        generated.contains('image:')
        generated.contains('resources:')
        targetContract == goldenOutput
        actual == goldenOutput

        where:
        release << [CatalogValues.create(), BillingValues.create()]
    }

    private String resourceText(String resourcePath) {
        getClass().classLoader.getResource(resourcePath).text
    }
}
