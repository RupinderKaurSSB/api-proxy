package io.descoped.client.http;

import io.descoped.client.http.internal.HttpConsumeBuilderImpl;
import io.descoped.client.http.internal.RequestProcessors;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface HttpConsume {

    static HttpConsumeBuilder builder(URI uri) {
        return new HttpConsumeBuilderImpl(uri);
    }

    interface BodyProcessor<T> {
        static BodyProcessor fromString(String body) {
            return fromString(body, StandardCharsets.UTF_8);
        }

        static BodyProcessor fromString(String body, Charset charset) {
            return new RequestProcessors.StringProcessor(body, charset);
        }

        static BodyProcessor fromByteArray(byte[] bytes) {
            return new RequestProcessors.ByteArrayProcessor(bytes);
        }

        static BodyProcessor fromInputStream(InputStream inputStream) {
            return new RequestProcessors.InputStreamProcessor(inputStream);
        }

        static BodyProcessor fromFile(String name) {
            return new RequestProcessors.FileProcessor(name);
        }

        long contentLength();
    }

}
