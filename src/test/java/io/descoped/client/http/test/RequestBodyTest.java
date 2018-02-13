package io.descoped.client.http.test;

import io.descoped.client.http.RequestProcessor;
import io.descoped.client.util.CommonUtil;
import io.descoped.client.util.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class RequestBodyTest {

    private static Logger log = LoggerFactory.getLogger(RequestBodyTest.class);

    @Test
    public void testFromString() {
        RequestProcessor processor = RequestProcessor.fromString("FooBar");
        log.trace("{} => {}", processor.body(), "FooBar".getBytes());
        assertEquals(Arrays.toString("FooBar".getBytes()), Arrays.toString(processor.body()));
    }


    @Test
    public void testFromStringCharset() {
        RequestProcessor processor = RequestProcessor.fromString("FooBar", StandardCharsets.UTF_8);
        log.trace("{} => {}", processor.body(), "FooBar".getBytes());
        assertEquals(new String("FooBar".getBytes(), StandardCharsets.UTF_8), new String(processor.body(), StandardCharsets.UTF_8));
    }

    @Test
    public void testFromByteArray() {
        RequestProcessor processor = RequestProcessor.fromByteArray("FooBar".getBytes());
        log.trace("{} => {}", processor.body(), "FooBar".getBytes());
        assertEquals(Arrays.toString("FooBar".getBytes()), Arrays.toString(processor.body()));
    }

    @Test
    public void testFromInputStream() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("FooBar".getBytes());
        RequestProcessor processor = RequestProcessor.fromInputStream(in);
        log.trace("{} => {}", processor.body(), "FooBar".getBytes());
        assertEquals(Arrays.toString("FooBar".getBytes()), Arrays.toString(processor.body()));
    }

    @Test
    public void testFromFile() throws Exception {
        Path tempFile = Files.createTempFile("tempFile", "tmp");
        OutputStream out = CommonUtil.newOutputStream();
        out.write("FooBar".getBytes());
        FileUtils.writeTo(out, tempFile);
        log.trace("file: {}", tempFile.toString());

        RequestProcessor processor = RequestProcessor.fromFile(tempFile);
        log.trace("{} => {}", processor.body(), "FooBar".getBytes());
        assertEquals(Arrays.toString("FooBar".getBytes()), Arrays.toString(processor.body()));
    }

}
