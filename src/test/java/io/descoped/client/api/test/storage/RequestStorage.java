package io.descoped.client.api.test.storage;

import java.util.LinkedHashMap;
import java.util.Map;

public class RequestStorage {

    private Map<String,String> params;
    private Map<String,String> headers;
    private String body;

    public RequestStorage() {
        params = new LinkedHashMap<>();
        headers = new LinkedHashMap<>();
    }

    public void param(String name, String value) {
        params.put(name, value);
    }

    public Map<String, String> params() {
        return params;
    }

    public void header(String name, String value) {
        headers.put(name, value);
    }

    public Map<String, String> headers() {
        return headers;
    }

    public void body(String requestData) {
        this.body = requestData;
    }

    public String body() {
        return body;
    }

}
