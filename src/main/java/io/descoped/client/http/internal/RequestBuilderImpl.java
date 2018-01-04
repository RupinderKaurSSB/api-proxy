package io.descoped.client.http.internal;

import io.descoped.client.http.Headers;
import io.descoped.client.http.Request;
import io.descoped.client.http.RequestBodyProcessor;
import io.descoped.client.http.RequestBuilder;

import java.net.URI;

import static java.util.Objects.requireNonNull;

public class RequestBuilderImpl implements RequestBuilder {

    private final Headers httpHeaders;
    private final URI uri;
    private String method;
    private RequestBodyProcessor body;

    public RequestBuilderImpl(URI uri) {
        httpHeaders = new HeadersImpl();
        this.uri = uri;
    }

    public Headers getHeaders() {
        return httpHeaders;
    }

    public URI getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public RequestBodyProcessor getBody() {
        return body;
    }

    @Override
    public RequestBuilder GET() {
        return method("GET", null);
    }

    @Override
    public RequestBuilder POST(RequestBodyProcessor body) {
        return method("POST", body);
    }

    @Override
    public RequestBuilder DELETE(RequestBodyProcessor body) {
        return method("DELETE", body);
    }

    @Override
    public RequestBuilder PUT(RequestBodyProcessor body) {
        return method("PUT", body);
    }

    private RequestBuilder method(String method, RequestBodyProcessor body) {
        this.method = requireNonNull(method);
        this.body = body;
        return this;
    }

    @Override
    public Request build() {
        return new RequestImpl(this);
    }


}
