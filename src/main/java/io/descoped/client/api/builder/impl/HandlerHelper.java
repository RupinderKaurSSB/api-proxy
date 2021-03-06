package io.descoped.client.api.builder.impl;

import io.descoped.client.http.Headers;
import io.descoped.client.http.internal.ResponseProcessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HandlerHelper {

    private static final Logger log = LoggerFactory.getLogger(HandlerHelper.class);

    public static class ErrorProcessor<T> extends ResponseProcessors.ByteArrayProcessor<T> {
        public ErrorProcessor(int statusCode, Headers responseHeaders) {
            super(ErrorProcessor::apply);
            open();
            write(Integer.valueOf(statusCode).toString().getBytes());
        }

        public static <T> T apply(byte[] bytes) {
            return (T) new byte[0];
        }
    }

    public static void x() {
    }

    public static <T> T handle(int statusCode, Headers responseHeaders, T byteArrayProcessor) {
        log.trace("--> statusCode: {}", statusCode);
        return (T) new ErrorProcessor<T>(statusCode, responseHeaders);
    }

//    public static <T> ResponseHandler<T> handler2(Class<T> byteArrayProcessorClass, ResponseHandler<T> func) {
//        return func.apply(st);
//    }
}
