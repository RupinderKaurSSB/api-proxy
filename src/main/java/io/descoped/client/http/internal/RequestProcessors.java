package io.descoped.client.http.internal;

import io.descoped.client.http.HttpConsume;

import java.io.InputStream;
import java.nio.charset.Charset;

public class RequestProcessors {

    static class ProcessorBase {
    }

    public static class EmptyProcessor extends ProcessorBase implements HttpConsume.BodyProcessor {
        @Override
        public long contentLength() {
            return 0;
        }
    }

    public static class ByteArrayProcessor extends ProcessorBase implements HttpConsume.BodyProcessor {
        public ByteArrayProcessor(byte[] bytes) {

        }

        @Override
        public long contentLength() {
            return 0;
        }
    }

    public static class StringProcessor extends ByteArrayProcessor {
        public StringProcessor(String body, Charset charset) {
            super(body.getBytes(charset));
        }

        @Override
        public long contentLength() {
            return 0;
        }
    }

    public static class InputStreamProcessor extends ProcessorBase implements HttpConsume.BodyProcessor {
        public InputStreamProcessor(InputStream inputStream) {
        }

        @Override
        public long contentLength() {
            return 0;
        }
    }

    public static class FileProcessor extends ProcessorBase implements HttpConsume.BodyProcessor {
        public FileProcessor(String name) {
        }

        @Override
        public long contentLength() {
            return 0;
        }
    }
}
