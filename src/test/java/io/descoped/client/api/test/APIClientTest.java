package io.descoped.client.api.test;

import io.descoped.client.api.builder.APIClient;
import io.descoped.client.api.test.impl.HttpBinOperation;
import io.descoped.client.api.test.impl.HttpBinOutcome;
import io.descoped.client.http.HttpClient;
import io.descoped.client.http.HttpConsume;
import io.descoped.client.http.HttpResponse;
import io.descoped.client.http.internal.HttpExchange;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
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
                    .consume("http://httpbin.org/GET/$1", Stream.of("$handler"))
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
        HttpConsume consume = HttpConsume.builder(URI.create("http://httpbin.org/get")).GET().build();

        HttpResponse.BodyProcessor<byte[]> handler = HttpResponse.BodyProcessor.asByteArray();
        HttpExchange exchange = new HttpExchange(consume, handler);
        HttpResponse response = exchange.response();

        /*
            Exchange.request(BodyHandler)

         */

    }
}
