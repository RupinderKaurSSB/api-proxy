package io.descoped.client.api.test.op;

import io.descoped.client.api.test.storage.RequestStorage;
import io.descoped.client.api.test.storage.Variables;
import io.descoped.client.http.*;
import io.descoped.client.http.internal.HeadersImpl;
import io.descoped.client.http.internal.RequestBuilderImpl;

import java.net.URI;
import java.util.Map;

/*
    add verb for OpHandler and multiplicity for outcome + callback
 */
abstract public class OpHandler<T> {

    private String id;
    private final URI uri;
    private final ResponseHandler<T> handler;
    private Variables requestVariables;
    private RequestStorage requestStorage;

    public OpHandler(String id, URI uri, ResponseHandler<T> handler) {
        this.id = id;
        this.uri = uri;
        this.handler = handler;
        requestVariables = new Variables();
        requestStorage = new RequestStorage();
    }

    public String getId() {
        return id;
    }

    public void param(String name, String value) {
        requestStorage.param(name, value);
    }

    public Map<String, String> params() {
        return requestStorage.params();
    }

    public void header(String name, String value) {
        requestStorage.header(name, value);
    }

    public Map<String, String> headers() {
        return requestStorage.headers();
    }

    public void requestBody(RequestProcessor requestProcessor) {
        requestStorage.body(new String(requestProcessor.body()));
    }

    public String requestBody() {
        return requestStorage.body();
    }

    abstract public OutHandler<T> run();

    public OutHandler<T> execute() {
        RequestBuilder requestBuilder = Request.builder(uri);

        HeadersImpl internalRequestHeaders = (HeadersImpl) ((RequestBuilderImpl) requestBuilder).getHeaders();
        for(Map.Entry<String,String> entry : requestStorage.headers().entrySet()) {

            boolean exists = requestVariables.containsKey(entry.getKey());

            internalRequestHeaders.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.GET().build();
        Response<T> response = Client.create().send​(request, handler);
        response.body();

//        OutHandler<T> outHandler = new OutHandler<>(this, response);
//        outHandler.consume();

//        return outHandler;

        run();
        return null;
    }

}
