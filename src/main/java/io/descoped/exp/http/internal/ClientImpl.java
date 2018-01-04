package io.descoped.exp.http.internal;

import io.descoped.exp.http.Client;
import io.descoped.exp.http.Request;
import io.descoped.exp.http.Response;
import io.descoped.exp.http.ResponseBodyHandler;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public class ClientImpl implements Client {

    public ClientImpl() {
    }

    @Override
    public Response send​(Request req, ResponseBodyHandler responseBodyHandler) {
        HttpRequestExchange exchange = new HttpRequestExchange(req, responseBodyHandler);
        return exchange.response();
    }

}
