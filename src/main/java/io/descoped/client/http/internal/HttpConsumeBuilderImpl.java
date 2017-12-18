package io.descoped.client.http.internal;

import io.descoped.client.http.HttpConsume;
import io.descoped.client.http.HttpConsumeBuilder;
import io.descoped.client.http.HttpHeaders;

import java.net.URI;

import static java.util.Objects.requireNonNull;

public class HttpConsumeBuilderImpl implements HttpConsumeBuilder {

    private final HttpHeaders httpHeaders;
    private final URI uri;
    private String method;
    private HttpConsume.BodyProcessor body;

    public HttpConsumeBuilderImpl(URI uri) {
        httpHeaders = new HttpHeadersImpl();
        this.uri = uri;
    }

    public HttpHeaders getHeaders() {
        return httpHeaders;
    }

    public URI getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public HttpConsume.BodyProcessor getBody() {
        return body;
    }

    @Override
    public HttpConsumeBuilder GET() {
        return method("GET", null);
    }

    @Override
    public HttpConsumeBuilder POST(HttpConsume.BodyProcessor body) {
        return method("POST", body);
    }

    @Override
    public HttpConsumeBuilder DELETE(HttpConsume.BodyProcessor body) {
        return method("DELETE", body);
    }

    @Override
    public HttpConsumeBuilder PUT(HttpConsume.BodyProcessor body) {
        return method("PUT", body);
    }

    private HttpConsumeBuilder method(String method, HttpConsume.BodyProcessor body) {
        this.method = requireNonNull(method);
        this.body = body;
        return this;
    }

    @Override
    public HttpConsume build() {
        return new HttpConsumeImpl(this);
    }

}
