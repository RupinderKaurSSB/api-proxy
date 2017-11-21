package io.descoped.client.external.google;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class GoogleMapsClient {

    public static HttpRequest GoogleMapsRequest(String params) {
        HttpRequest req = HttpRequest.get(String.format("https://maps.googleapis.com/maps/api/geocode/json?%s&key=AIzaSyCqB0UVR7_t3j3Y0tkBmuTzJ7BvE4x3ZtM", params));
//        req.header("Authorization", "APIKEY " + "AIzaSyCqB0UVR7_t3j3Y0tkBmuTzJ7BvE4x3ZtM");
        return req;
    }

}
