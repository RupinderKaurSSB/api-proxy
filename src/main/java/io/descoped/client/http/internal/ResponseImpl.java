package io.descoped.client.http.internal;

import io.descoped.client.http.Headers;
import io.descoped.client.http.Request;
import io.descoped.client.http.Response;
import io.descoped.client.http.internal.httpRequest.HttpRequestExchange;

import java.net.URI;

public class ResponseImpl<T> implements Response<T> {

    final int responseCode;
    final HttpRequestExchange<T> exchange;
    final RequestImpl initialRequest;
    final Headers headers;
    final URI uri;
    final T body;

    public ResponseImpl(Request initialRequest,
                            int statusCode,
                        Headers responseHeaders,
                            T body, HttpRequestExchange<T> exch) {
        this.responseCode = statusCode;
        this.exchange = exch;
        this.initialRequest = (RequestImpl) initialRequest;
        this.headers = responseHeaders;
        this.uri = this.initialRequest.getUri();
        this.body = body;
    }

    @Override
    public int statusCode() {
        return responseCode;
    }

    @Override
    public Request request() {
        return initialRequest;
    }

    @Override
    public Headers headers() {
        return headers;
    }

    @Override
    public T body() {
        return body;
    }

    @Override
    public URI uri() {
        return uri;
    }
}
