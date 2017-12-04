package io.descoped.client.http;

import io.descoped.client.api.builder.intf.OutcomeHandler;

import java.net.URL;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public interface HttpClient {

    static HttpClient create() {
        return new HttpClientImpl();
    }

    HttpGet get(CharSequence url);

    HttpGet get(URL url);

    HttpClient post(CharSequence url);

    HttpClient post(URL url);

    HttpClient put(CharSequence url);

    HttpClient put(URL url);

    HttpClient delete(CharSequence url);

    HttpClient delete(URL url);

    OutcomeHandler getOutcome();
}
