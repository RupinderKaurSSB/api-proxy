package io.descoped.client.api.test;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.descoped.client.http.*;
import io.descoped.client.http.internal.ResponseImpl;
import io.descoped.client.http.internal.httpRequest.HttpRequestExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class GetHystrixCommand<R> extends HystrixCommand<Response<R>> {

    private static final Logger log = LoggerFactory.getLogger(GetHystrixCommand.class);


    private final URI uri;
    private final RequestBuilder builder;
    private final ResponseBodyHandler<R> handler;
    private Response<R> response = null;

    protected GetHystrixCommand(URI uri, ResponseBodyHandler<R> handler) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("OperationCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(300000))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(100)
                                .withQueueSizeRejectionThreshold(100)
                                .withCoreSize(4)));
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
        HttpRequestExchange exch = ((ResponseImpl) response).getExchange();
        log.error("Error: [{}] {}\n{}", response.statusCode(), exch.getErrorMessage(), exch.getErrorBody() );
//        ResponseBodyProcessor<R> r = handler.apply(response.statusCode(), response.headers());
        return response;
    }
}
