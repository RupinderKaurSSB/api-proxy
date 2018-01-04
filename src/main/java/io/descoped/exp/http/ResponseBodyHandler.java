package io.descoped.exp.http;

import io.descoped.exp.http.internal.ResponseProcessors;

import java.nio.charset.StandardCharsets;

@FunctionalInterface
public interface ResponseBodyHandler<T> {

    ResponseBodyProcessor<T> apply(int statusCode, Headers responseHeaders);

    static ResponseBodyHandler<byte[]> asBytes() {
        ResponseBodyHandler<byte[]> handler = (statusCode, responseHeaders) -> new ResponseProcessors.ByteArrayProcessor<>(bytes -> bytes);
        return handler;
    }

    static ResponseBodyHandler<String> asString() {
        return (statusCode, responseHeaders) -> new ResponseProcessors.ByteArrayProcessor<>(bytes-> new String(bytes, StandardCharsets.UTF_8));
    }

}
