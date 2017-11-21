package io.descoped.client.api.test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.descoped.client.api.config.Configuration;
import io.descoped.client.api.facebook.FacebookClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.HTTP_OK;

public class ApiProxyTest {

    private static final Logger log = LoggerFactory.getLogger(ApiProxyTest.class);

    @Test
    public void testBuilder() throws Exception {
        log.trace("AccessToken: {}", Configuration.getDeveloperAccessToken());
    }

    @Test
    public void testGraphMe() throws Exception {
        HttpRequest req = FacebookClient.GET("me");
        if (req.code() == HTTP_OK) {
            String payload = new String(req.bytes(), StandardCharsets.UTF_8.name());
            DocumentContext ctx = JsonPath.parse(payload);
            Object name = ctx.read("$.name");
            log.trace("Name: {}", name);
        } else {
            log.trace("HTTP Status: {}", req.code());
        }
    }
}
