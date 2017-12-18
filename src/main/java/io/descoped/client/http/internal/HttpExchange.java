package io.descoped.client.http.internal;

import io.descoped.client.http.HttpConsume;
import io.descoped.client.http.HttpResponse;

public class HttpExchange {

    private final HttpConsume req;
    private final HttpResponse.BodyProcessor responseBodyHandler;

    public HttpExchange(HttpConsume req, HttpResponse.BodyProcessor responseBodyHandler) {
        this.req = req;
        this.responseBodyHandler = responseBodyHandler;
    }

    public HttpResponse response() {

        return null;
    }

}
