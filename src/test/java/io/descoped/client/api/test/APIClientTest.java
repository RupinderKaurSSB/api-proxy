package io.descoped.client.api.test;

import io.descoped.client.api.builder.APIClient;
import io.descoped.client.api.builder.intf.OutcomeHandler;
import io.descoped.client.api.test.impl.HttpBinOperation;
import io.descoped.client.api.test.impl.HttpBinOutcome;
import io.descoped.client.http.HttpConsumer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class APIClientTest {

    private static final Logger log = LoggerFactory.getLogger(APIClientTest.class);

//    @Test
    public void testBuilder() throws Exception {
        APIClient.builder()
                .worker("postHttpBin")
                    .operation(HttpBinOperation.class)
                    .outcome(HttpBinOutcome.class)
                    .consume("http://httpbin.org/get/$1", "$param")
                    .consume((j,p) -> j) // new ConsumerJob("http://httpbin.org/get/$1", "$param")
                    .done()
                .worker("getHttpBin")
                    .operation(HttpBinOperation.class)
                    .outcome(HttpBinOutcome.class)
                    .done()
                .execute()
        ;
    }

    /*
        todo - tracking key data for request/response -headers, -content

        1)
     */

    @Test
    public void testHttpConsumer() throws Exception {
        HttpConsumer consumer = HttpConsumer.create().get("http://httpbin.org/get");

        OutcomeHandler outcome = consumer.getOutcome(); // outcome should handle success and failure
        log.trace("{}", outcome);
    }

}
