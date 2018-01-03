package io.descoped.exp.http;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface RequestBodyProcessor {

    static RequestBodyProcessor fromString(String body) {
        return fromString(body, StandardCharsets.UTF_8);
    }

    static RequestBodyProcessor fromString(String body, Charset charset) {
//        return new RequestProcessors.StringProcessor(body, charset);
        return null;
    }

    static RequestBodyProcessor fromByteArray(byte[] bytes) {
//        return new RequestProcessors.ByteArrayProcessor(bytes);
        return null;
    }

    static RequestBodyProcessor fromInputStream(InputStream inputStream) {
//        return new RequestProcessors.InputStreamProcessor(inputStream);
        return null;
    }

    static RequestBodyProcessor fromFile(String name) {
//        return new RequestProcessors.FileProcessor(name);
        return null;
    }

    long contentLength();

}
