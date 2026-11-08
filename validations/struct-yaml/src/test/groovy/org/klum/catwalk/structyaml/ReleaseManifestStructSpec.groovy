package org.klum.catwalk.structyaml

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.SpecVersion
import org.yaml.snakeyaml.constructor.DuplicateKeyException
import spock.lang.Specification

class ReleaseManifestStructSpec extends Specification {

    private static final String DOCUMENT = '''
release: production
region: europe-west
zones:
  - eu-west-1a
  - eu-west-1b
services:
  gateway:
    image: registry.example/gateway:1.2.3
    ports: [8080, 8443]
    labels:
      tier: edge
  worker:
    image: registry.example/worker:1.2.3
    ports: [9000]
    labels:
      queue: orders
'''

    def 'caller-owned ordered YAML creates a completed keyed nested Model'() {
        given:
        Map<String, Object> document = CallerYaml.parse(DOCUMENT)

        when:
        ReleaseManifest manifest = ReleaseManifest.Create.FromStruct(document, 'release-manifest.yaml')

        then:
        document.keySet().toList() == ['release', 'region', 'zones', 'services']
        manifest.release == 'production'
        manifest.region == 'europe-west'
        manifest.zones == ['eu-west-1a', 'eu-west-1b']
        manifest.services.keySet().toList() == ['gateway', 'worker']
        manifest.services.gateway.name == 'gateway'
        manifest.services.gateway.image == 'registry.example/gateway:1.2.3'
        manifest.services.gateway.ports == [8080, 8443]
        manifest.services.worker.name == 'worker'
        manifest.services.worker.labels == [queue: 'orders']
    }

    def 'caller rejects duplicate YAML keys before Struct conversion'() {
        when:
        CallerYaml.parse('release: production\nrelease: duplicate\n')

        then:
        thrown(DuplicateKeyException)
    }

    def 'the generated JSON Schema validates the same caller-owned document'() {
        given:
        Map<String, Object> document = CallerYaml.parse(DOCUMENT)
        ObjectMapper mapper = new ObjectMapper()
        File schemaFile = new File(
                System.getProperty('catwalk.generatedSchema',
                        'build/generated/klum-json-schema/org.klum.catwalk.structyaml.ReleaseManifest.schema.json')
        )
        JsonNode schemaDocument = mapper.readTree(schemaFile)
        def schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012).getSchema(schemaDocument)

        expect:
        schema.validate(mapper.valueToTree(document)).empty
    }
}
