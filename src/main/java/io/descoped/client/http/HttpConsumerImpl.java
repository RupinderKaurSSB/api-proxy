package io.descoped.client.http;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.builder.intf.OutcomeHandler;

import java.net.URL;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public class HttpConsumerImpl implements HttpConsumer {

    private HttpRequest req;

    public HttpConsumerImpl() {
    }

    @Override
    public HttpConsumer get(CharSequence url) {
        req = HttpRequest.get(url);
        return this;
    }

    @Override
    public HttpConsumer get(URL url) {
        req = HttpRequest.get(url);
        return this;
    }

    @Override
    public HttpConsumer post(CharSequence url) {
        req = HttpRequest.post(url);
        return this;
    }

    @Override
    public HttpConsumer post(URL url) {
        req = HttpRequest.post(url);
        return this;
    }

    @Override
    public HttpConsumer put(CharSequence url) {
        req = HttpRequest.put(url);
        return this;
    }

    @Override
    public HttpConsumer put(URL url) {
        req = HttpRequest.put(url);
        return this;
    }

    @Override
    public HttpConsumer delete(CharSequence url) {
        req = HttpRequest.delete(url);
        return this;
    }

    @Override
    public HttpConsumer delete(URL url) {
        req = HttpRequest.delete(url);
        return this;
    }

    @Override
    public OutcomeHandler getOutcome() {
        return OutcomeHandler.create(req.code(), req.bytes(), req.contentLength(), req.headers());
    }
}
