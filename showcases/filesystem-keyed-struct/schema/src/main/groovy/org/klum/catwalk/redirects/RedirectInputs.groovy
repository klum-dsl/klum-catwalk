package org.klum.catwalk.redirects

import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.SafeConstructor

import java.nio.file.Files
import java.nio.file.Path

import static java.nio.file.LinkOption.NOFOLLOW_LINKS

/** Owns the showcase's file layout and YAML parsing. */
final class RedirectInputs {
    private RedirectInputs() {}

    static RedirectRule loadStandalone(Path examples) {
        String sourceName = 'standalone/redirect.yaml'
        Map<String, Object> document = parse(examples.resolve(sourceName))
        RedirectRule.Create.FromStruct(document, sourceName)
    }

    static Map<String, RedirectRule> loadDirectory(Path examples) {
        Path root = examples.resolve('redirects')
        List<Path> files = Files.walk(root).withCloseable { paths ->
            paths.filter { path ->
                path.fileName?.toString() == 'redirect.yaml' &&
                        path.parent != root && Files.isRegularFile(path, NOFOLLOW_LINKS)
            }.toList()
        }

        Map<String, RedirectRule> rules = new LinkedHashMap<>()
        files.sort(false) { slashPath(root.relativize(it)) }.each { file ->
            String key = '/' + slashPath(root.relativize(file.parent))
            String sourceName = slashPath(examples.relativize(file))
            Map<String, Object> body = parse(file)
            rules[key] = RedirectRule.Create.FromStruct(key, body, sourceName)
        }
        rules
    }

    static Map<String, Object> parse(Path file) {
        LoaderOptions options = new LoaderOptions()
        options.setAllowDuplicateKeys(false)
        Object parsed = new Yaml(new SafeConstructor(options)).load(Files.readString(file))
        if (!(parsed instanceof Map)) {
            throw new IllegalArgumentException("Expected one YAML mapping in ${file}")
        }
        normalizeMap(parsed)
    }

    private static Map<String, Object> normalizeMap(Map<?, ?> source) {
        Map<String, Object> result = new LinkedHashMap<>()
        source.each { key, value ->
            if (!(key instanceof String)) {
                throw new IllegalArgumentException("YAML mapping keys must be strings: ${key}")
            }
            result[key] = normalize(value)
        }
        result
    }

    private static Object normalize(Object value) {
        if (value instanceof Map) return normalizeMap(value)
        if (value instanceof List) return value.collect { normalize(it) }
        if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean) return value
        throw new IllegalArgumentException("Unsupported YAML scalar type: ${value.class.name}")
    }

    private static String slashPath(Path relativePath) {
        relativePath.collect { it.toString() }.join('/')
    }
}
