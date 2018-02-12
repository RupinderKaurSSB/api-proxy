package io.descoped.client.api.test.storage;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseStorage<T> {

    private Map<String,String> params;
    private Map<String,String> headers;
    private Map<String,T> outcome;

    public ResponseStorage() {
        params = new LinkedHashMap<>();
        headers = new LinkedHashMap<>();
        outcome = new LinkedHashMap<>();
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

    public void outcome(String name, T value) {
        outcome.put(name, value);
    }

    public Map<String, T> outcome() {
        return outcome;
    }

}
