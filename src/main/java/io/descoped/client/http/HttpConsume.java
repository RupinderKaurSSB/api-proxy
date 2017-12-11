package io.descoped.client.http;

public interface HttpConsume {

    static HttpConsume create() {
        return new HttpConsumeImpl();
    }

    HttpConsume get();

}
