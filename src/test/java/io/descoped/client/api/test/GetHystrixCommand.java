package io.descoped.client.api.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.descoped.client.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class GetHystrixCommand<R> extends HystrixCommand<Response<R>> {

    private static final Logger log = LoggerFactory.getLogger(GetHystrixCommand.class);


    private final URI uri;
    private final RequestBuilder builder;
    private final ResponseHandler<R> handler;
    private Response<R> response = null;

    protected GetHystrixCommand(URI uri, ResponseHandler<R> handler) {
        super(HystrixSetter.setterFallback());
        this.uri = uri;
        this.builder = Request.builder(uri).GET();
        this.handler = handler;
        HystrixRequestContext.initializeContext();
    }

    public RequestBuilder getBuilder() {
        return builder;
    }

    @Override
    protected Response<R> run() throws Exception {
        Request request = builder.build();
        response = Client.create().send​(request, handler);
        if (response.isError()) throw response.getError();
        return response;
    }

    @Override
    protected Response<R> getFallback() {
//        HttpRequestExchange exch = ((ResponseImpl) response).getExchange();
        log.error("Hystrix Fallback Error: [{}] {}", response.statusCode(), (response.getError() != null ? response.getError().getMessage() : ""));
        return response;
    }
}
