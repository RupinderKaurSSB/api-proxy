package io.descoped.client.api.test.op;

import io.descoped.client.http.ResponseHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class OpTest {

    private static Logger log = LoggerFactory.getLogger(OpTest.class);

    @Test
    public void testOpHandler() {
        OpHandler<String> operation = new OpHandler<String>("op0", URI.create("http://httpbin.org/get"), ResponseHandler.asString()) {
            @Override
            public OutHandler<String> run() {
                log.trace("requestBody(): {}", requestBody());
                return null;
            }
        };
    }
}
