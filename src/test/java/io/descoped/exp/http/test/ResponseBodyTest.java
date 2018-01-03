package io.descoped.exp.http.test;

import io.descoped.exp.http.internal.ResponseProcessors;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ResponseBodyTest {

    private static Logger log = LoggerFactory.getLogger(ResponseBodyTest.class);

    @Test
    public void test() {
        byte[] a1 = "Foo".getBytes();
        byte[] a2 = "Bar".getBytes();
        List<ByteBuffer> byteBuffers = new ArrayList<>();
        byteBuffers.add(ByteBuffer.wrap(a1));
        byteBuffers.add(ByteBuffer.wrap(a2));
        log.trace("size: {}", ResponseProcessors.remaining(byteBuffers));
    }

    @Test
    public void testBodyProcessor() {
        byte[] a1 = "Foo".getBytes();
        byte[] a2 = "Bar".getBytes();
//        ResponseProcessors.ByteArrayProcessor;
    }
}
