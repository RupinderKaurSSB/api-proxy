package io.descoped.client.api.test.storage;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StorageTest {

    RequestStorage requestStorage = new RequestStorage();
    ResponseStorage<String> responseStorage = new ResponseStorage<>();

    @Test
    public void testRequestStorage() {
        requestStorage.param("foo", "bar");
        assertEquals(requestStorage.params().get("foo"), "bar");
        requestStorage.header("foo", "bar");
        assertEquals(requestStorage.headers().get("foo"), "bar");
        requestStorage.body("foobar");
        assertEquals(requestStorage.body(), "foobar");
    }

    @Test
    public void testResponseStorage() {
        responseStorage.param("foo", "bar");
        assertEquals(responseStorage.params().get("foo"), "bar");
        responseStorage.header("foo", "bar");
        assertEquals(responseStorage.headers().get("foo"), "bar");
        responseStorage.outcome("foo", "bar");
        assertEquals(responseStorage.outcome().get("foo"), "bar");
    }
}
