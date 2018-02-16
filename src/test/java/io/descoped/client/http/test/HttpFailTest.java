package io.descoped.client.http.test;

import io.descoped.client.exception.APIClientException;
import io.descoped.client.http.*;
import io.descoped.server.http.LoopbackRoute;
import io.descoped.server.http.TestWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpFailTest {

    private static final Logger log = LoggerFactory.getLogger(HttpFailTest.class);

    private TestWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new TestWebServer();
        server.addRoute("/echo", new LoopbackRoute());
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void should_fail() {
        Request request = Request.builder(server.baseURL("/echo")).GET().build();
        try {
            Response<String> response = Client.create().send​(request, HttpFailTest::apply); // <== fails here
            log.trace("{}", response.body().get());
        } catch (Exception e) {
            log.error("Error was catched: {}", e.getMessage());
        }
    }

    private static ResponseProcessor<String> apply(int statusCode, Headers headers) {
        throw new APIClientException("Error");
    }

}
