package io.descoped.client.http;

public interface HttpConsumeBuilder {

    HttpConsumeBuilder GET();

    HttpConsumeBuilder POST(HttpConsume.BodyProcessor body);

    HttpConsumeBuilder DELETE(HttpConsume.BodyProcessor body);

    HttpConsumeBuilder PUT(HttpConsume.BodyProcessor body);

    HttpConsume build();

}
