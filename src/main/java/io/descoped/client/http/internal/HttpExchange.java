package io.descoped.client.http.internal;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.http.HttpConsume;
import io.descoped.client.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class HttpExchange {

    private static Logger log = LoggerFactory.getLogger(HttpExchange.class);

    private final HttpConsume consumer;
    private final HttpResponse.BodyProcessor responseBodyHandler;

    public HttpExchange(HttpConsume consumer, HttpResponse.BodyProcessor responseBodyHandler) {
        this.consumer = consumer;
        this.responseBodyHandler = responseBodyHandler;
    }

    public HttpResponse response() {
        HttpConsumeImpl consumeImpl = (HttpConsumeImpl) consumer;

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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

}
