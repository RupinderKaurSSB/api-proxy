package io.descoped.client.api.test;

import io.descoped.client.api.builder.impl.HttpBinOperation;
import org.junit.Test;

public class HttpBinOperationTest {

    @Test
    public void testMe() throws Exception {
        HttpBinOperation operation = new HttpBinOperation();
        operation.get();
    }
}
