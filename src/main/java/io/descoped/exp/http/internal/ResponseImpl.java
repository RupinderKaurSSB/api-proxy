package io.descoped.exp.http.internal;

import io.descoped.exp.http.Headers;
import io.descoped.exp.http.Request;
import io.descoped.exp.http.Response;

import java.net.URI;

public class ResponseImpl implements Response {

    @Override
    public int statusCode() {
        return 0;
    }

    @Override
    public Request request() {
        return null;
    }

    @Override
    public Headers headers() {
        return null;
    }

    @Override
    public Object body() {
        return null;
    }

    @Override
    public URI uri() {
        return null;
    }
}
