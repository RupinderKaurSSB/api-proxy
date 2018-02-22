package io.descoped.client.api.test.op;

import io.descoped.client.api.test.storage.ELMap;
import io.descoped.client.api.test.storage.Variables;
import io.descoped.client.http.RequestProcessor;
import io.descoped.client.http.ResponseHandler;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Op {

    private URI uri;
    private List<ResponseHandler<?>> handlers;

    private Op() {
        handlers = new ArrayList<>();
    }

    public URI uri() {
        return uri;
    }

    public static Builder newBuilder(UriTemplate uriTemplate, Variables variables) {
        return new Builder(uriTemplate, variables);
    }

    public Op handler(ResponseHandler<?> responseHandler) {
        handlers.add(responseHandler);
        return this;
    }

    public Out execute() {
        return new Out();
    }

    public static class Builder {
        private final UriTemplate uriTemplate;
        private final Variables variables;
        private final ELMap<String,String> headers;
        private String method;
        private RequestProcessor requestProcessor;

        public Builder(UriTemplate uri, Variables variables) {
            this.uriTemplate = uri;
            this.variables = variables;
            this.headers = new ELMap(variables);
        }

        public UriTemplate getUriTemplate1() {
            return uriTemplate;
        }

        public String getMethod() {
            return method;
        }

        public RequestProcessor getRequestProcessor() {
            return requestProcessor;
        }

        public Op build() {
            Op operation = new Op();
            operation.uri = uriTemplate.expand(variables);
            return operation;
        }

        private Builder method(String method, RequestProcessor requestProcessor) {
            this.method = requireNonNull(method);
            this.requestProcessor = requestProcessor;
            return this;
        }

        public Builder GET() {
            return method("GET", null);
        }

        public Builder POST(RequestProcessor body) {
            return method("POST", body);
        }

        public Builder DELETE(RequestProcessor body) {
            return method("DELETE", body);
        }

        public Builder PUT(RequestProcessor body) {
            return method("PUT", body);
        }

        public void addHeaderValue(String name, String value) {
            headers.put(name, value);
        }
    }
}
