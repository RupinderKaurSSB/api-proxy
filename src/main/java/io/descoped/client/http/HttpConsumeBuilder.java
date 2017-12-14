package io.descoped.client.http;

public interface HttpConsumeBuilder {

    HttpConsumeBuilder GET();

    HttpConsumeBuilder POST(HttpResponse.BodyProcessor body);

    HttpConsumeBuilder DELETE(HttpResponse.BodyProcessor body);

    HttpConsumeBuilder PUT(HttpResponse.BodyProcessor body);

    HttpConsume build();

}
