package io.descoped.client.http.test;

import io.descoped.client.exception.APIClientException;
import io.descoped.client.http.*;
import io.descoped.client.http.internal.ResponseProcessors;
import io.descoped.server.http.LoopbackRoute;
import io.descoped.server.http.TestWebServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.net.HttpURLConnection.HTTP_OK;

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
    public void should_fail_due_to_exception() {
        Request request = Request.builder(server.baseURL("/echo")).GET().build();
        try {
            Response<byte[]> response = Client.create().send​(request, HttpFailTest::handle); // <== fails here
            log.trace("Response: {}", response.body().get());
        } catch (Exception e) {
            log.error("Error was catched: {}", e.getMessage());
        }
    }

    @Test
    public void should_succeed_on_resource_not_found() {
        Request request = Request.builder(server.baseURL("/echo2")).GET().build();
        Response<byte[]> response = Client.create().send​(request, HttpFailTest::handle); // <== does NOT fail here! statusCode = 404
        log.trace("Response: {}", response.body().get());
    }

    private static ResponseProcessor<byte[]> handle(int statusCode, Headers headers) {
        if (statusCode != HTTP_OK) {
            return new ResponseProcessors.ByteArrayProcessor<>(HttpFailTest::empty);
        }
        return new ResponseProcessors.ByteArrayProcessor<>(HttpFailTest::process);
    }

    private static byte[] empty(byte[] bytes) {
        return new byte[0];
    }

    private static byte[] process(byte[] bytes) {
        throw new APIClientException("Error");
    }

}
