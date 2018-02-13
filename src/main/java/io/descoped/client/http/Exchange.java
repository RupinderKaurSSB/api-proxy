package io.descoped.client.http;

import io.descoped.client.http.internal.httpRequest.HttpRequestExchange;

public interface Exchange<T> {

    Response<T> response();

    static <T> Exchange<T> createHttpRequestExchange(Request consumer, ResponseHandler<T> responseHandler) {
        return new HttpRequestExchange<>(consumer, responseHandler);
    }

}
