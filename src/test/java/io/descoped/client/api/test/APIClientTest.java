package io.descoped.client.api.test;

import io.descoped.client.api.builder.APIClient;
import io.descoped.client.api.test.impl.HttpBinOperation;
import io.descoped.client.api.test.impl.HttpBinOutcome;
import io.descoped.client.http.HttpClient;
import io.descoped.client.http.HttpGet;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class APIClientTest {

    private static final Logger log = LoggerFactory.getLogger(APIClientTest.class);

    /*
        todo - tracking key data for request/response -headers, -content

        1)
     */

//    @Test
    public void testBuilder() throws Exception {
        APIClient.builder()
                .worker("postHttpBin")
                    .operation(HttpBinOperation.class)
                    .outcome(HttpBinOutcome.class)
                    .done()
                .worker("getHttpBin")
                    .operation(HttpBinOperation.class)
                    .outcome(HttpBinOutcome.class)
                    .done()
                .execute()
        ;
    }

//    @Test
    public void testBuilder2() throws Exception {
        APIClient.builder()
                .worker("postHttpBin")
                    .consume("http://httpbin.org/get/$1", Stream.of("$foo"))
                    .consume((j,p) -> j) // new ConsumerJob("http://httpbin.org/get/$1", "$param")
                    .produce()
                    .header()
                    .options()
                    .delete()
                    .done()
                .execute()
        ;
    }

    @Test
    public void testHttpConsumer() throws Exception {
        HttpClient client = HttpClient.create();
        HttpGet consumer = client.get("http://httpbin.org/get");


//        OutcomeHandler outcome = consumer.getOutcome(); // outcome should handle success and failure
//        log.trace("{}", outcome);
    }

}
