package org.klum.catwalk.redirects

import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.SpecVersion
import org.yaml.snakeyaml.constructor.DuplicateKeyException
import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class RedirectShowcaseSpec extends Specification {
    private static final Path EXAMPLES = Path.of('examples')
    private static final Path STANDALONE = EXAMPLES.resolve('standalone/redirect.yaml')
    private static final Path CLI_BODY = EXAMPLES.resolve('redirects/cli/install/redirect.yaml')
    private static final Path GUIDES_BODY = EXAMPLES.resolve('redirects/guides/install/redirect.yaml')

    @TempDir Path temp

    def 'the standalone document and directory catalog produce the expected redirects'() {
        when:
        RedirectRule standalone = RedirectInputs.loadStandalone(EXAMPLES)
        Map<String, RedirectRule> catalog = RedirectInputs.loadDirectory(EXAMPLES)

        then:
        catalog.keySet().toList() == ['/cli/install', '/guides/install']
        catalog['/cli/install'].sourcePath == '/cli/install'
        catalog['/cli/install'].targetPath == '/reference/cli/setup'
        standalone.sourcePath == '/guides/install'
        standalone.targetPath == '/getting-started/setup'
        catalog['/guides/install'].sourcePath == standalone.sourcePath
        catalog['/guides/install'].targetPath == standalone.targetPath
    }

    def 'discovery only selects redirect.yaml in a directory below the catalog root'() {
        given:
        Path root = Files.createDirectories(temp.resolve('redirects'))
        Path ruleDirectory = Files.createDirectories(root.resolve('guides/install'))
        Files.writeString(root.resolve('redirect.yaml'), 'invalid root fixture')
        Files.writeString(ruleDirectory.resolve('notes.yaml'), 'invalid sibling fixture')
        Files.copy(GUIDES_BODY, ruleDirectory.resolve('redirect.yaml'))

        expect:
        RedirectInputs.loadDirectory(temp).keySet().toList() == ['/guides/install']
    }

    def 'the external validator accepts each document only with its associated generated schema'() {
        given:
        def mapper = new ObjectMapper()
        def validator = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012)
        Path schemaDirectory = Path.of(System.getProperty('catwalk.redirectSchemaDirectory'))
        def rootSchema = validator.getSchema(mapper.readTree(
                schemaDirectory.resolve("${RedirectRule.name}.schema.json").toFile()))
        def bodySchema = validator.getSchema(mapper.readTree(
                schemaDirectory.resolve("${RedirectRule.name}.builder-body.schema.json").toFile()))
        def standalone = mapper.valueToTree(RedirectInputs.parse(STANDALONE))
        def bodies = [CLI_BODY, GUIDES_BODY].collect { mapper.valueToTree(RedirectInputs.parse(it)) }

        expect:
        rootSchema.validate(standalone).empty
        !bodySchema.validate(standalone).empty
        bodies.every { bodySchema.validate(it).empty && !rootSchema.validate(it).empty }
    }

    def 'duplicate YAML keys fail in the caller before the keyed Factory route'() {
        given:
        Path ruleDirectory = Files.createDirectories(temp.resolve('redirects/guides/install'))
        Files.writeString(ruleDirectory.resolve('redirect.yaml'), '''\
targetPath: /getting-started/setup
targetPath: /reference/cli/setup
''')

        when:
        RedirectInputs.loadDirectory(temp)

        then:
        thrown(DuplicateKeyException)
    }
}
