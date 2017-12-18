package io.descoped.client.http.internal;

import io.descoped.client.http.HttpConsume;

import java.net.URI;
import java.util.Locale;

public class HttpConsumeImpl implements HttpConsume {

    private final String method;
    private final URI uri;
    private final boolean secure;
    private final BodyProcessor requestProcessor;
    private HttpConsumeBuilderImpl builder;

    public HttpConsumeImpl(HttpConsumeBuilderImpl builder) {
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

}
