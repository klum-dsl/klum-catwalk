package onboarding.helm.values

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import onboarding.helm.schema.PodinfoRelease
import spock.lang.Issue
import spock.lang.See
import spock.lang.Specification
import spock.lang.Tag
import spock.lang.Unroll

import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest

@Issue('https://github.com/klum-dsl/klum-ast/issues/469')
@Tag('documentary')
@See('https://github.com/stefanprodan/podinfo/tree/dd507173b7b75b2312a36cabe0de5f09c1ce69c8/charts/podinfo')
class PodinfoHelmContractSpec extends Specification {

    private final ObjectMapper yaml = new ObjectMapper(new YAMLFactory())
    private final Properties pin = loadPin()
    private final String chartVersion = pin.getProperty('chartVersion')
    private final Path chart = Path.of('src', 'test', 'resources', 'helm', 'chart', "podinfo-${chartVersion}.tgz")

    def 'uses the pinned Podinfo chart archive and Helm renderer'() {
        expect:
        pin.getProperty('sourceTag') == '6.15.0'
        pin.getProperty('sourceTagObject') == 'a9b846405af7697629de8a1da335aaf639a09f34'
        pin.getProperty('sourceCommit') == 'dd507173b7b75b2312a36cabe0de5f09c1ce69c8'
        pin.getProperty('license') == 'Apache-2.0'
        sha256(chart) == pin.getProperty('archiveSha256')
        command('helm', 'version', '--template', '{{.Version}}').trim() == "v${System.getProperty('helmVersion')}"

        when:
        Map metadata = yaml.readValue(command('helm', 'show', 'chart', chart.toString()), Map)

        then:
        metadata.name == 'podinfo'
        metadata.version == chartVersion
        metadata.appVersion == chartVersion
    }

    @Unroll
    def 'renders #release.name values and manifests with pinned Podinfo semantics'() {
        given:
        Path generatedDirectory = Path.of('build', 'generated-values')
        Path renderedDirectory = Path.of('build', 'rendered-manifests')
        Files.createDirectories(generatedDirectory)
        Files.createDirectories(renderedDirectory)

        when:
        String generatedValues = yaml.writeValueAsString(release.toHelmValues())
        Path generatedValuesFile = generatedDirectory.resolve("${release.name}.values.yaml")
        Files.writeString(generatedValuesFile, generatedValues)
        Map expectedValues = yaml.readValue(resourceText("helm/golden-values/${release.name}.values.yaml"), Map)

        String rendered = command(
                'helm', 'template', release.name, chart.toString(),
                '--namespace', 'podinfo',
                '--kube-version', System.getProperty('kubernetesVersion'),
                '--skip-tests',
                '--values', generatedValuesFile.toString()
        )
        Files.writeString(renderedDirectory.resolve("${release.name}.yaml"), rendered)
        Map actualSummary = semanticSummary(release.name, readDocuments(rendered))
        Map expectedSummary = yaml.readValue(resourceText("helm/golden-render/${release.name}.summary.yaml"), Map)

        then: 'values and rendered manifests match intentional semantic goldens'
        yaml.readValue(generatedValues, Map) == expectedValues
        actualSummary == expectedSummary

        where:
        release << [BackendWithRedisValues.create(), FrontendToBackendValues.create()]
    }

    private List<Map<String, Object>> readDocuments(String rendered) {
        yaml.readerFor(Map).readValues(rendered).readAll().findAll { it }
    }

    private static Map<String, Object> semanticSummary(String releaseName, List<Map<String, Object>> documents) {
        Map deployment = findResource(documents, 'Deployment', "${releaseName}-podinfo")
        Map container = deployment.spec.template.spec.containers.find { it.name == 'podinfo' }
        Map redisDeployment = findResource(documents, 'Deployment', "${releaseName}-podinfo-redis", false)
        Map ingress = findResource(documents, 'Ingress', "${releaseName}-podinfo", false)
        List<String> serviceNames = documents.findAll { it.kind == 'Service' }*.metadata*.name.flatten().sort()

        [
                release    : releaseName,
                deployments: documents.findAll { it.kind == 'Deployment' }*.metadata*.name.flatten().sort(),
                services   : serviceNames,
                podinfo    : [
                        replicas   : deployment.spec.replicas,
                        image      : container.image,
                        cacheServer: container.command.find { it.startsWith('--cache-server=') },
                        backendUrl : container.env.find { it.name == 'PODINFO_BACKEND_URL' }?.value
                ],
                redis      : redisDeployment == null ? null : [
                        image: redisDeployment.spec.template.spec.containers.find { it.name == 'redis' }.image
                ],
                ingress    : ingress == null ? null : [
                        className: ingress.spec.ingressClassName,
                        hosts    : ingress.spec.rules*.host,
                        services : ingress.spec.rules*.http*.paths.flatten()*.backend*.service*.name.flatten()
                ]
        ]
    }

    private static Map findResource(List<Map<String, Object>> documents, String kind, String name, boolean required = true) {
        Map resource = documents.find { it.kind == kind && it.metadata.name == name }
        if (required && resource == null) {
            throw new AssertionError("Missing ${kind}/${name}")
        }
        resource
    }

    private static String command(String... command) {
        Process process = new ProcessBuilder(command).redirectErrorStream(true).start()
        String output = process.inputStream.getText('UTF-8')
        int status = process.waitFor()
        if (status != 0) {
            throw new AssertionError("Command failed (${status}): ${command.join(' ')}\n${output}")
        }
        output
    }

    private static String sha256(Path file) {
        MessageDigest digest = MessageDigest.getInstance('SHA-256')
        Files.newInputStream(file).withCloseable { input ->
            byte[] buffer = new byte[8192]
            for (int read = input.read(buffer); read != -1; read = input.read(buffer)) {
                digest.update(buffer, 0, read)
            }
        }
        digest.digest().encodeHex().toString()
    }

    private String resourceText(String resourcePath) {
        getClass().classLoader.getResource(resourcePath).text
    }

    private Properties loadPin() {
        Properties properties = new Properties()
        getClass().classLoader.getResourceAsStream('helm/chart/podinfo-6.15.0.pin.properties').withCloseable {
            properties.load(it)
        }
        properties
    }
}
