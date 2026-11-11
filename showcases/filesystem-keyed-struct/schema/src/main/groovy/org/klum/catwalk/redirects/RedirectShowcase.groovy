package org.klum.catwalk.redirects

import java.nio.file.Path

/** Prints the redirects constructed from the two input forms. */
class RedirectShowcase {
    static void main(String[] args) {
        Path examples = Path.of(args[0])

        println 'Standalone document'
        printRule(RedirectInputs.loadStandalone(examples))

        println 'Directory catalog'
        RedirectInputs.loadDirectory(examples).values().each { printRule(it) }
    }

    private static void printRule(RedirectRule rule) {
        println "${rule.sourcePath} -> ${rule.targetPath}"
    }
}
