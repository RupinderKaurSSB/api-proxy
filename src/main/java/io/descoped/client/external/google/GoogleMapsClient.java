package io.descoped.client.external.google;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.config.Configuration;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class GoogleMapsClient {

    public HttpRequest GET(String params) {
        HttpRequest req = HttpRequest.get(String.format("https://maps.googleapis.com/maps/api/geocode/json?%s&key=%s", params, Configuration.getGoogleApiKey()));
        return req;
    }

    public HttpRequest getGeoLocation(String address) {
        HttpRequest req = HttpRequest.get(String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s", address, Configuration.getGoogleApiKey()));
        return req;
    }

}
