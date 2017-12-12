package io.descoped.client.http;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.builder.intf.OutcomeHandler;

//public class HttpConsumeImpl implements HttpConsume {
public class HttpConsumeImpl {

    private HttpRequest req;

//    @Override
    public HttpConsume get() {
//        req = HttpRequest.get(url);
//        return this;
        return null;
    }


//    @Override
    public OutcomeHandler getOutcome() {
        return OutcomeHandler.create(req.code(), req.bytes(), req.contentLength(), req.headers());
    }

}
