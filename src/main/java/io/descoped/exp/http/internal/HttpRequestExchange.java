package io.descoped.exp.http.internal;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.exp.http.Request;
import io.descoped.exp.http.Response;
import io.descoped.exp.http.ResponseBodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class HttpRequestExchange {

    private static Logger log = LoggerFactory.getLogger(HttpRequestExchange.class);

    private final Request consumer;
    private final ResponseBodyHandler responseBodyHandler;

    public HttpRequestExchange(Request consumer, ResponseBodyHandler responseBodyHandler) {
        this.consumer = consumer;
        this.responseBodyHandler = responseBodyHandler;
    }

    public Response response() {
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

        int statusCode = req.code();
        byte[] bytes = req.bytes();

        try {
            log.info("---> {}", new String(bytes, StandardCharsets.UTF_8.name()) );

//            responseBodyHandler.foo(req.bytes());


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
