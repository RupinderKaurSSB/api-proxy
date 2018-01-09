package io.descoped.client.api.test;

import io.descoped.client.external.posten.PostalCode;
import io.descoped.client.http.*;
import io.descoped.client.http.internal.ResponseProcessors;
import io.descoped.server.http.TestWebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostnrProcessor<T> implements ResponseBodyHandler<Map<String, PostalCode>> {

    private static final Logger log = LoggerFactory.getLogger(PostnrProcessor.class);

    private TestWebServer testServer;


    public static PostnrProcessor<Map<String, PostalCode>> create(TestWebServer server) {
        PostnrProcessor pp = new PostnrProcessor();
        pp.setServer(server);
        return pp;
    }

    private void setServer(TestWebServer server) {
        this.testServer = server;
    }

    private Response<byte[]> testServerLookup(String code) {
        Request request = Request.builder(testServer.baseURL("/transform?code="+code)).GET().build();
        Response<byte[]> response = Client.create().send​(request, ResponseBodyHandler.asBytes());
        return response;
    }

    @Override
    public ResponseBodyProcessor<Map<String, PostalCode>> apply(int statusCode, Headers responseHeaders) {
        return new ResponseProcessors.ByteArrayProcessor<>((_bytes) -> {

            Map<String, PostalCode> internalMap = new LinkedHashMap<>();
            try {
                byte[] bytes = new String(_bytes, "Cp1252").getBytes();
                BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bytes)));
                String rowLine;
                while ((rowLine = br.readLine()) != null) {
                    PostalCode postalCode = PostalCode.valueOf(rowLine);
                    internalMap.put(postalCode.getCode(), postalCode);
                    Response<byte[]> bytesResponse = testServerLookup(postalCode.getCode());
                    log.trace("was: {} -- transformed: {}", postalCode.getCode(), new String(bytesResponse.body()));
                }
                PostenDataTest.inc();

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return internalMap;
        });

    }

}
