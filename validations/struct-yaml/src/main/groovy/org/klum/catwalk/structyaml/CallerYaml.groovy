package org.klum.catwalk.structyaml

import org.yaml.snakeyaml.LoaderOptions
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.SafeConstructor

/**
 * Consumer-owned YAML entry point. KlumAST receives the resulting ordered
 * Java structure only; parsing and duplicate-key policy remain outside its
 * Struct conversion contract.
 */
final class CallerYaml {
    private CallerYaml() {}

    static Map<String, Object> parse(String yaml) {
        LoaderOptions options = new LoaderOptions()
        options.setAllowDuplicateKeys(false)
        Object parsed = new Yaml(new SafeConstructor(options)).load(yaml)
        if (!(parsed instanceof Map)) {
            throw new IllegalArgumentException('The YAML document root must be a mapping')
        }
        normalizeMap(parsed as Map<?, ?>)
    }

    private static Map<String, Object> normalizeMap(Map<?, ?> source) {
        Map<String, Object> result = new LinkedHashMap<>()
        source.each { key, value ->
            if (!(key instanceof String)) {
                throw new IllegalArgumentException("YAML mapping keys must be Strings: ${key}")
            }
            result[key] = normalize(value)
        }
        result
    }

    private static Object normalize(Object value) {
        if (value instanceof Map) return normalizeMap(value as Map<?, ?>)
        if (value instanceof List) return (value as List<?>).collect { normalize(it) }
        if (value == null || value instanceof String || value instanceof Number || value instanceof Boolean) return value
        throw new IllegalArgumentException("Unsupported YAML scalar type: ${value.class.name}")
    }
}
