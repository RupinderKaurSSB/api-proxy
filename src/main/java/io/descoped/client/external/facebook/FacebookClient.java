package io.descoped.client.external.facebook;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.api.config.Configuration;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 21/11/2017
 */
public class FacebookClient {

    private static final String API_VERSION = "v2.11";

    public static HttpRequest GET(String uri, String[]... headers) {
        HttpRequest req = HttpRequest.get(String.format("https://graph.facebook.com/%s/%s", API_VERSION, uri));
//        log.trace("URL => {}", req.url().toString());
        req.header("Authorization", "OAuth " + Configuration.getDeveloperAccessToken());
        for(String[] header : headers) {
            req.header(header[0], header[1]);
        }
        return req;
    }

    public static HttpRequest POST(String uri, String[]... headers) {
        HttpRequest req = HttpRequest.post(String.format("https://graph.facebook.com/%s/%s", API_VERSION, uri));
        req.header("Authorization", "OAuth " + Configuration.getDeveloperAccessToken());
        for(String[] header : headers) {
            req.header(header[0], header[1]);
        }
        return req;
    }

    public static HttpRequest PUT(String uri, String[]... headers) {
        HttpRequest req = HttpRequest.put(String.format("https://graph.facebook.com/%s/%s", API_VERSION, uri));
        req.header("Authorization", "OAuth " + Configuration.getDeveloperAccessToken());
        for(String[] header : headers) {
            req.header(header[0], header[1]);
        }
        return req;
    }

    public static HttpRequest DELETE(String uri, String[]... headers) {
        HttpRequest req = HttpRequest.delete(String.format("https://graph.facebook.com/%s/%s", API_VERSION, uri));
        req.header("Authorization", "OAuth " + Configuration.getDeveloperAccessToken());
        for(String[] header : headers) {
            req.header(header[0], header[1]);
        }
        return req;
    }

}
