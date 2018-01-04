package io.descoped.exp.http;

public interface RequestBuilder {

    RequestBuilder GET();

    RequestBuilder POST(RequestBodyProcessor body);

    RequestBuilder DELETE(RequestBodyProcessor body);

    RequestBuilder PUT(RequestBodyProcessor body);

    Request build();

}
