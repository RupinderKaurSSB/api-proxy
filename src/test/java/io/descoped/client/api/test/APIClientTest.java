package io.descoped.client.api.test;

import io.descoped.client.api.builder.APIBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class APIClientTest {

    private static final Logger log = LoggerFactory.getLogger(APIClientTest.class);

    @Test
    public void testBuilder() throws Exception {
        APIBuilder.builder().group("getBin");
    }
}
