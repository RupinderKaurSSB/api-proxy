package io.descoped.client.api.test.op;

import io.descoped.client.api.test.storage.Variables;

import java.net.URI;

public class UriTemplate {

    private final String uri;

    private UriTemplate(String uri) {
        this.uri = uri;
    }

    public URI expand(Variables variables) {
        return URI.create(variables.evaluate(uri));
    }

    public static UriTemplate create(String uri) {
        return new UriTemplate(uri);
    }

}
