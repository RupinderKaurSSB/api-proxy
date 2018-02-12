package io.descoped.client.api.test.op;

import io.descoped.client.api.test.storage.RequestStorage;
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
    private final ResponseBodyHandler<T> handler;
    private RequestStorage requestStorage;

    public OpHandler(String id, URI uri, ResponseBodyHandler<T> handler) {
        this.id = id;
        this.uri = uri;
        this.handler = handler;
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

    public void requestBody(RequestBodyProcessor requestBodyProcessor) {
        requestStorage.body(new String(requestBodyProcessor.body()));
    }

    public String requestBody() {
        return requestStorage.body();
    }

    abstract public OutHandler<T> run();

    public OutHandler<T> execute() {
        RequestBuilder requestBuilder = Request.builder(uri);

        HeadersImpl internalRequestHeaders = (HeadersImpl) ((RequestBuilderImpl) requestBuilder).getHeaders();
        for(Map.Entry<String,String> entry : requestStorage.headers().entrySet()) {
            internalRequestHeaders.addHeader(entry.getKey(), entry.getValue());
        }

        Request request = requestBuilder.GET().build();
        Response<T> response = Client.create().send​(request, handler);

//        OutHandler<T> outHandler = new OutHandler<>(this, response);
//        outHandler.consume();

//        return outHandler;
        return null;
    }

}
