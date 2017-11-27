package io.descoped.client.http;

import io.descoped.client.api.builder.intf.OutcomeHandler;

import java.net.URL;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 24/11/2017
 */
public interface HttpConsumer {

    static HttpConsumer create() {
        return new HttpConsumerImpl();
    }

    HttpConsumer get(CharSequence url);

    HttpConsumer get(URL url);

    HttpConsumer post(CharSequence url);

    HttpConsumer post(URL url);

    HttpConsumer put(CharSequence url);

    HttpConsumer put(URL url);

    HttpConsumer delete(CharSequence url);

    HttpConsumer delete(URL url);

    OutcomeHandler getOutcome();
}
