package io.descoped.client.http.internal;

import io.descoped.client.http.HttpConsume;

public class HttpConsumeImpl implements HttpConsume {

    private HttpConsumeBuilderImpl builder;

    public HttpConsumeImpl(HttpConsumeBuilderImpl builder) {
        this.builder = builder;
    }

}
