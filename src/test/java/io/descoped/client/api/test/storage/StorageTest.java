package io.descoped.client.api.test.storage;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StorageTest {

    Variables variables = new Variables();
    RequestStorage requestStorage = new RequestStorage(variables);
    ResponseStorage<String> responseStorage = new ResponseStorage<>();

    @Before
    public void setUp() throws Exception {
        variables.add("foo", "bar");
        variables.add("bar", "baz");
    }

    @Test
    public void testRequestStorage() {
        requestStorage.param("foo", "${bar}");
        assertEquals(requestStorage.params().get("foo"), "bar");
        assertEquals(requestStorage.params().eval("${foo}"), "bar");  // should keys be evaled or only value?
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

    @Test
    public void shouldEvalValueExpression() {
        responseStorage.param("foo", "${foo}");
        String foo = responseStorage.params().get("foo");
        assertEquals(foo, "${foo}");
        assertEquals(variables.evaluateExpression(foo), "bar");
    }
}
