package io.descoped.client.api.test;

import io.descoped.client.exception.APIClientException;
import io.descoped.client.external.posten.PostalCode;
import io.descoped.client.http.*;
import io.descoped.client.http.internal.ResponseProcessors;
import io.descoped.server.http.TestWebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import static java.net.HttpURLConnection.HTTP_OK;

public class PostnrHandler<T> implements ResponseBodyHandler<Map<String, PostalCode>> {

    private static final Logger log = LoggerFactory.getLogger(PostnrHandler.class);

    private TestWebServer testServer;


    public static PostnrHandler<Map<String, PostalCode>> create(TestWebServer server) {
        PostnrHandler pp = new PostnrHandler();
        pp.setServer(server);
        return pp;
    }

    private void setServer(TestWebServer server) {
        this.testServer = server;
    }

    public Response<byte[]> GET(String code) {
        Request request = Request.builder(testServer.baseURL("/transform?code="+code)).GET().build();
        Response<byte[]> response = Client.create().send​(request, ResponseBodyHandler.asBytes());
        return response;
    }

    public static <R> ResponseProcessors.ByteArrayProcessor<R> asEmptyProcessor() {
        return asProcessor((byte[] bytes) -> (R) new ArrayList<R>());
    }

    public static <R> ResponseProcessors.ByteArrayProcessor<R> asProcessor(Function<byte[], R> processor) {
        return new ResponseProcessors.ByteArrayProcessor<>(processor);
    }

    @Override
    public ResponseBodyProcessor<Map<String, PostalCode>> apply(int statusCode, Headers responseHeaders) {
        if (statusCode != HTTP_OK) {
            return PostnrHandler.asEmptyProcessor();
        }
        return PostnrHandler.asProcessor(PostnrHandler::apply);
    }

    public static Map<String, PostalCode> apply(byte[] bytes) {

        Map<String, PostalCode> internalMap = new LinkedHashMap<>();
        try {
            byte[] convertedBytes = new String(bytes, "Cp1252").getBytes();
            BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(convertedBytes)));
            String rowLine;
            while ((rowLine = br.readLine()) != null) {
                PostalCode postalCode = PostalCode.valueOf(rowLine);
                internalMap.put(postalCode.getCode(), postalCode);
                PostenDataTest.inc();
            }

        } catch (UnsupportedEncodingException e) {
            throw new APIClientException(e);
        } catch (IOException e) {
            throw new APIClientException(e);
        }

        return internalMap;
    }

}
