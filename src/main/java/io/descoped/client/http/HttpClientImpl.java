package io.descoped.client.http;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.builder.intf.OutcomeHandler;

import java.net.URL;
import java.util.Objects;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public class HttpClientImpl implements HttpClient {

    private HttpRequest req;

    public HttpClientImpl() {
    }

    @Override
    public HttpGet get(CharSequence url) {
//        req = HttpRequest.get(url);
//        return this;
        return null;
    }

    @Override
    public HttpGet get(URL url) {
        Objects.requireNonNull(url);
//        req = HttpRequest.get(url);
//        return this;
        return null;
    }

    @Override
    public HttpClient post(CharSequence url) {
        req = HttpRequest.post(url);
        return this;
    }

    @Override
    public HttpClient post(URL url) {
        req = HttpRequest.post(url);
        return this;
    }

    @Override
    public HttpClient put(CharSequence url) {
        req = HttpRequest.put(url);
        return this;
    }

    @Override
    public HttpClient put(URL url) {
        req = HttpRequest.put(url);
        return this;
    }

    @Override
    public HttpClient delete(CharSequence url) {
        req = HttpRequest.delete(url);
        return this;
    }

    @Override
    public HttpClient delete(URL url) {
        req = HttpRequest.delete(url);
        return this;
    }

    @Override
    public OutcomeHandler getOutcome() {
        return OutcomeHandler.create(req.code(), req.bytes(), req.contentLength(), req.headers());
    }
}
