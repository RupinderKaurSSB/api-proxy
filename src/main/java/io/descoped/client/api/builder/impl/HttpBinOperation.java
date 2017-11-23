package io.descoped.client.api.builder.impl;

import io.descoped.client.api.builder.Operation;

import java.util.List;
import java.util.Map;

public class HttpBinOperation implements Operation<HttpBinOutcome> {

    @Override
    public String getURL() {
        return null;
    }

    @Override
    public void requestHeaders(Map<String, List<String>> headers) {

    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public HttpBinOutcome get() {
        return null;
    }

}
