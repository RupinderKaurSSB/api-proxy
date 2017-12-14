package io.descoped.client.http.internal;

import io.descoped.client.http.HttpResponse;

import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.function.Function;

public class ResponseProcessor {

    abstract static class AbstractProcessor<T> implements HttpResponse.BodyProcessor<T> {
    }

    public static class ByteArrayProcessor<T> implements HttpResponse.BodyProcessor<T> {
        private final Function<byte[], T> finisher;

        public ByteArrayProcessor(Function<byte[],T> finisher) {
            this.finisher = finisher;
        }
    }

    public static class PathProcessor extends AbstractProcessor<Path> {

        public PathProcessor(Path file, OpenOption[] openOptions) {
            super();
        }

        public PathProcessor(Path file, StandardOpenOption create, StandardOpenOption write) {
            super();
        }
    }


    public static class NullProcessor<T> extends AbstractProcessor<T> {
        private final Optional<T> result;

        public NullProcessor(Optional<T> result) {
            this.result = result;
        }
    }
}
