package io.descoped.client.http;

import io.descoped.client.http.internal.ResponseProcessor;

import java.nio.charset.Charset;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.function.Function;

public interface HttpResponse<T> {

    interface BodyProcessor<T> {

        static HttpResponse.BodyProcessor<String> asString(Charset charset) {
            return new ResponseProcessor.ByteArrayProcessor<>(
                    bytes -> new String(bytes, charset)
            );
        }

        static BodyProcessor<byte[]> asByteArray() {
            return new ResponseProcessor.ByteArrayProcessor<>(
                    Function.identity() // no conversion
            );
        }

        static BodyProcessor<Path> asFile(Path file, OpenOption... openOptions) {
            return new ResponseProcessor.PathProcessor(file, openOptions);
        }

        public static BodyProcessor<Path> asFile(Path file) {
            return new ResponseProcessor.PathProcessor(
                    file,
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        }

        public static <U> BodyProcessor<U> discard(U value) {
            return new ResponseProcessor.NullProcessor<>(Optional.ofNullable(value));
        }

    }

    @FunctionalInterface
    interface BodyHandler<T> {
        BodyProcessor<T> apply(int statusCode, HttpHeaders responseHeaders);

        public static <U> BodyHandler<U> discard(U value) {
            return (status, headers) -> BodyProcessor.discard(value);
        }

    }
}

