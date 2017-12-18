package io.descoped.client.http.internal;

import io.descoped.client.http.HttpClient;
import io.descoped.client.http.HttpConsume;
import io.descoped.client.http.HttpResponse;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public class HttpClientImpl implements HttpClient {

    public HttpClientImpl() {
    }

    @Override
    public HttpResponse send​(HttpConsume req, HttpResponse.BodyProcessor responseBodyHandler) {
        HttpExchange exchange = new HttpExchange(req, responseBodyHandler);
        return exchange.response();
    }

}
