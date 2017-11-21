package io.descoped.client.external.test;

import com.github.kevinsawicki.http.HttpRequest;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.descoped.client.api.config.Configuration;
import io.descoped.client.external.facebook.FacebookClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class FacebookClientTest {

    private static final Logger log = LoggerFactory.getLogger(FacebookClientTest.class);

    @Test
    public void should_get_graph_user_me() throws Exception {
        assertThat(Configuration.getDeveloperAccessToken()).isNotBlank();

        HttpRequest req = FacebookClient.GET("me");
        assertThat(req.code()).isEqualTo(HTTP_OK);

        String payload = new String(req.bytes(), StandardCharsets.UTF_8.name());
        assertThat(payload).isNotEmpty();

        DocumentContext ctx = JsonPath.parse(payload);
        Object name = ctx.read("$.name");
        assertThat(name).isOfAnyClassIn(String.class);

        log.trace("Name: {}", name);
    }

}
