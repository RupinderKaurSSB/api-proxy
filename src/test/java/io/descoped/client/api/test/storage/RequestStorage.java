package io.descoped.client.api.test.storage;

import java.util.Objects;

public class RequestStorage {

    private final Variables variables;
    private final ELMap<String,String> params;
    private final ELMap<String,String> headers;
    private String body;

    public RequestStorage(Variables variables) {
        Objects.requireNonNull(variables);
        this.variables = variables;
        params = new ELMap<>(this.variables);
        headers = new ELMap<>(this.variables);
    }

    public void param(String name, String value) {
        params.put(name, value);
    }

    public ELMap<String, String> params() {
        return params;
    }

    public void header(String name, String value) {
        headers.put(name, value);
    }

    public ELMap<String, String> headers() {
        return headers;
    }

    public void body(String requestData) {
        this.body = requestData;
    }

    public String body() {
        return body;
    }

}
