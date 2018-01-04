package io.descoped.client.http.internal;

import io.descoped.client.http.Request;
import io.descoped.client.http.RequestBodyProcessor;

import java.net.URI;
import java.util.Locale;

public class RequestImpl implements Request {

    private final String method;
    private final URI uri;
    private final boolean secure;
    private final RequestBodyProcessor requestProcessor;
    private RequestBuilderImpl builder;

    public RequestImpl(RequestBuilderImpl builder) {
        this.builder = builder;

        this.method = builder.getMethod();
//        this.userHeaders = ImmutableHeaders.of(builder.headers().map(), ALLOWED_HEADERS);
//        this.systemHeaders = new HttpHeadersImpl();
        this.uri = builder.getUri();
        this.secure = uri.getScheme().toLowerCase(Locale.US).equals("https");
        if (builder.getBody() == null) {
            this.requestProcessor = new RequestProcessors.EmptyProcessor();
        } else {
            this.requestProcessor = builder.getBody();
        }
    }

    public String getMethod() {
        return method;
    }

    public URI getUri() {
        return uri;
    }

    public boolean isSecure() {
        return secure;
    }

    public RequestBodyProcessor getRequestProcessor() {
        return requestProcessor;
    }

}
