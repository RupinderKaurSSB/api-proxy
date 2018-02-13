package io.descoped.client.http;

import java.net.URI;
import java.time.Duration;

public interface RequestBuilder {

    RequestBuilder uri(URI uri);

    RequestBuilder header(String name, String value);

    RequestBuilder timeout(Duration duration);

    RequestBuilder GET();

    RequestBuilder POST(RequestProcessor body);

    RequestBuilder DELETE(RequestProcessor body);

    RequestBuilder PUT(RequestProcessor body);

    Request build();

}
