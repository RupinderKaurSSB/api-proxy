package io.descoped.exp.http;

import io.descoped.exp.http.internal.RequestBuilderImpl;

import java.net.URI;

public interface Request {

    static RequestBuilder builder(URI uri) {
        return new RequestBuilderImpl(uri);
    }

}
