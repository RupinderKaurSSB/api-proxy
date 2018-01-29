package io.descoped.client.external.klass;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.exception.APIClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import static java.net.HttpURLConnection.HTTP_OK;

public class KlassClient {

    private static final Logger log = LoggerFactory.getLogger(KlassClient.class);
    private static String KLASS_URL = "http://data.ssb.no/api/klass/v1/classifications/";

    public String getClassifications() {
        HttpRequest request = HttpRequest.get(KLASS_URL);
        if (request.code() == HTTP_OK) {
            try {
                String json = new String(request.bytes(), StandardCharsets.UTF_8.name());
                //GeoLocation geoLocation = new GeoLocation(json);
                //return geoLocation;
                return json;
            } catch (UnsupportedEncodingException e) {
                log.error( request.message() );
                throw new APIClientException(e);
            }
        }
        return null;
    }
}
