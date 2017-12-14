package io.descoped.client.http;

import io.descoped.client.http.internal.HttpClientImpl;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public interface HttpClient<T> {

    static HttpClient create() {
        return new HttpClientImpl();
    }

    <T> HttpResponse<T> send​(HttpConsume req, HttpResponse.BodyProcessor<T> responseBodyHandler);

}
