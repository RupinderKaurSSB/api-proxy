package io.descoped.client.http;

import io.descoped.client.http.internal.ResponseProcessors;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * todo: status code is not used. need to determine use in context of exchange
 *
 * @param <T>
 */

@FunctionalInterface
public interface ResponseHandler<T> {

    ResponseProcessor<T> apply(int statusCode, Headers responseHeaders);

    static ResponseHandler<byte[]> asBytes() {
        ResponseHandler<byte[]> handler = (statusCode, responseHeaders) -> {
            return new ResponseProcessors.ByteArrayProcessor<>(bytes -> bytes);
        };
        return handler;
    }

    static ResponseHandler<String> asString() {
        return (statusCode, responseHeaders) -> new ResponseProcessors.ByteArrayProcessor<>(bytes -> new String(bytes, ResponseProcessors.charsetFrom(Optional.ofNullable(responseHeaders))));
    }

    static ResponseHandler<String> asString(Charset charset) {
        return (statusCode, responseHeaders) -> {
            if (charset != null) {
                return new ResponseProcessors.ByteArrayProcessor<>(bytes -> new String(bytes, charset));
            }
            return new ResponseProcessors.ByteArrayProcessor<>(bytes -> new String(bytes, ResponseProcessors.charsetFrom(Optional.ofNullable(responseHeaders))));
        };
    }

}
