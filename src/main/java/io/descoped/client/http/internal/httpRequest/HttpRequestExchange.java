package io.descoped.client.http.internal.httpRequest;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.http.*;
import io.descoped.client.http.internal.HeadersImpl;
import io.descoped.client.http.internal.RequestImpl;
import io.descoped.client.http.internal.ResponseImpl;
import io.descoped.client.http.internal.ResponseProcessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class HttpRequestExchange<T> implements Exchange<T> {

    private static Logger log = LoggerFactory.getLogger(HttpRequestExchange.class);

    private final Request request;
    private final ResponseBodyHandler<T> responseBodyHandler;

    public HttpRequestExchange(Request request, ResponseBodyHandler<T> responseBodyHandler) {
        this.request = request;
        this.responseBodyHandler = responseBodyHandler;
    }

    public Response<T> response() {
        RequestImpl requestImpl = (RequestImpl) request;

        HttpRequest httpRequest;

        if ("GET".equals(requestImpl.getMethod())) {
            httpRequest = HttpRequest.get(requestImpl.getUri().toString());

        } else if ("POST".equals(requestImpl.getMethod())) {
            httpRequest = HttpRequest.post(requestImpl.getUri().toString());

        } else if ("PUT".equals(requestImpl.getMethod())) {
            httpRequest = HttpRequest.put(requestImpl.getUri().toString());

        } else if ("DELETE".equals(requestImpl.getMethod())) {
            httpRequest = HttpRequest.delete(requestImpl.getUri().toString());

        } else {
            throw new UnsupportedOperationException("Method not supported!");
        }

        // copy to HttpRequest userHeaders (request)
        Map<String,String> userHeaders = HeadersImpl.asFlatMap(requestImpl.headers());
        httpRequest.headers(userHeaders);
        log.info("----> {}", userHeaders);


        int statusCode = httpRequest.code();

        // copy from HttpRequest userHeaders (response)
        Map<String, List<String>> responseHeadersMap = httpRequest.headers();
        log.info("===> {}", responseHeadersMap);

        // obtain payload

        Headers responseHeaders = new HeadersImpl(responseHeadersMap);
        ResponseBodyProcessor<T> result = responseBodyHandler.apply(statusCode, responseHeaders);
        if (result != null) {
            ResponseProcessors.AbstractProcessor abstractProcessor = (ResponseProcessors.AbstractProcessor) result;
            abstractProcessor.open();
            abstractProcessor.write(httpRequest.bytes());
            abstractProcessor.complete();
            ResponseImpl<T> response = new ResponseImpl<T>(requestImpl, statusCode, responseHeaders, result.getBody(), this);
            return response;
        }
        return null;
    }

}
