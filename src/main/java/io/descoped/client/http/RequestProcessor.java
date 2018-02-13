package io.descoped.client.http;

import io.descoped.client.http.internal.RequestProcessors;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public interface RequestProcessor {

    byte[] body();

    long contentLength();

    static RequestProcessor noBody() {
        return new RequestProcessors.EmptyProcessor();
    }

    static RequestProcessor fromString(String body) {
        return fromString(body, StandardCharsets.UTF_8);
    }

    static RequestProcessor fromString(String body, Charset charset) {
        return new RequestProcessors.StringProcessor(body, charset);
    }

    static RequestProcessor fromByteArray(byte[] bytes) {
        return new RequestProcessors.ByteArrayProcessor(bytes);
    }

    static RequestProcessor fromInputStream(InputStream inputStream) {
        return new RequestProcessors.InputStreamProcessor(inputStream);
    }

    static RequestProcessor fromFile(Path name) {
        return new RequestProcessors.FileProcessor(name);
    }

}
