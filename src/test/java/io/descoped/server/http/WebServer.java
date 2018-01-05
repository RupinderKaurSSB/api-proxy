package io.descoped.server.http;

import io.undertow.Undertow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;
import java.util.Random;

public class WebServer {

    private static final Logger log = LoggerFactory.getLogger(WebServer.class);
    private static final long SLEEP_TIMEOUT = 20000L;

    private final WebServerHandler handler;
    private Undertow server;
    private String host = "localhost";
    private int port;

    public static void sleep() {
        sleep(null);
    }

    public static void sleep(Long timeOut) {
        try {
            Thread.sleep(Optional.of(timeOut).orElse(SLEEP_TIMEOUT));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public WebServer() {
        handler = new WebServerHandler();
    }

    public void start() throws Exception {
        port = new Random().nextInt(500) + 9000;
        server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(handler).build();
        server.start();
        log.info("WebServer is listening on {}", baseURL());
    }

    public void stop() throws Exception {
        server.stop();
    }

    public String baseURL() {
        return baseURL(URI.create(""));
    }

    public String baseURL(URI uri) {
        try {
            URL url = new URL("http", host, port, uri.toString());
            return url.toExternalForm();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRoute(String contextPath, Route route) {
        handler.addRoute(contextPath, route);
    }

    public Undertow getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

}
