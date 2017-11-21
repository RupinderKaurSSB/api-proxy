package io.descoped.client.external.test;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.external.google.GoogleMapsClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class GoogleMapsClientTest {

    private static final Logger log = LoggerFactory.getLogger(GoogleMapsClientTest.class);

    @Test
    public void should_resolve_geo_location() throws Exception {
        GoogleMapsClient client = new GoogleMapsClient();
        HttpRequest req = client.getGeoLocation("Oslo");
        log.trace("{}", req.body());
    }
}
