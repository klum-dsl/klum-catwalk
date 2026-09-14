package org.klum.catwalk.api;

public interface Environment<A extends Application> {
    A getApplication();
}
