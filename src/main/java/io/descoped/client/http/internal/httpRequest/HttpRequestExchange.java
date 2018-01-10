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

    private final Request consumer;
    private final ResponseBodyHandler<T> responseBodyHandler;

    public HttpRequestExchange(Request consumer, ResponseBodyHandler<T> responseBodyHandler) {
        this.consumer = consumer;
        this.responseBodyHandler = responseBodyHandler;
    }

    public Response<T> response() {
        RequestImpl consumeImpl = (RequestImpl) consumer;

        HttpRequest req;

        if ("GET".equals(consumeImpl.getMethod())) {
            req = HttpRequest.get(consumeImpl.getUri().toString());

        } else if ("POST".equals(consumeImpl.getMethod())) {
            req = HttpRequest.post(consumeImpl.getUri().toString());

        } else if ("PUT".equals(consumeImpl.getMethod())) {
            req = HttpRequest.put(consumeImpl.getUri().toString());

        } else if ("DELETE".equals(consumeImpl.getMethod())) {
            req = HttpRequest.delete(consumeImpl.getUri().toString());

        } else {
            throw new UnsupportedOperationException("Method not supported!");
        }

        // obtain payload

        int statusCode = req.code();
        Map<String, List<String>> responseHeadersMap = req.headers();
        log.info("===> {}", responseHeadersMap);

        Headers responseHeaders = new HeadersImpl(responseHeadersMap);
        ResponseBodyProcessor<T> result = responseBodyHandler.apply(statusCode, responseHeaders);
        if (result != null) {
            ResponseProcessors.AbstractProcessor abstractProcessor = (ResponseProcessors.AbstractProcessor) result;
            abstractProcessor.open();
            abstractProcessor.write(req.bytes());
            abstractProcessor.complete();
            ResponseImpl<T> response = new ResponseImpl<T>(consumeImpl, statusCode, responseHeaders, result.getBody(), this);
            return response;
        }
        return null;
    }

}
