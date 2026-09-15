package org.klum.catwalk.api;

/** Consumer-facing view of an environment, independent of its Schema realization. */
public interface Environment {
    Application getPrimaryApplication();
}
