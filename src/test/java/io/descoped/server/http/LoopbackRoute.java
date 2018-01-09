package io.descoped.server.http;

import io.descoped.info.InfoBuilder;
import io.undertow.io.Receiver;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopbackRoute implements Route {

    private static final Logger log = LoggerFactory.getLogger(LoopbackRoute.class);

    @Override
    public void dispatch(HttpServerExchange exchange) {
        InfoBuilder infoBuilder = InfoBuilder.builder();

        {
            infoBuilder.key("request-headers");
            exchange.getRequestHeaders().getHeaderNames().forEach(h -> {
                exchange.getRequestHeaders().eachValue(h).forEach(v -> {
                    infoBuilder.keyValue(h.toString(), v);
                });
            });
            infoBuilder.up();
        }

        {
            infoBuilder.key("request-info");
            infoBuilder.keyValue("uri", exchange.getRequestURI());
            infoBuilder.keyValue("method", exchange.getRequestMethod().toString());
            infoBuilder.keyValue("statusCode", String.valueOf(exchange.getStatusCode()));
            infoBuilder.keyValue("isSecure", Boolean.valueOf(exchange.isSecure()).toString());
            infoBuilder.keyValue("sourceAddress", exchange.getSourceAddress().toString());
            infoBuilder.keyValue("destinationAddress", exchange.getDestinationAddress().toString());
            infoBuilder.key("cookies");
            exchange.getRequestCookies().forEach((k,v) -> {
                infoBuilder.keyValue(k,v.getValue());
            });
            infoBuilder.up();
            infoBuilder.key("path-parameters");
            exchange.getPathParameters().entrySet().forEach((e) -> {
                infoBuilder.keyValue(e.getKey(), e.getValue().element());
            });
            infoBuilder.up();
            infoBuilder.keyValue("queryString", exchange.getQueryString());
            infoBuilder.key("query-parameters");
            exchange.getQueryParameters().entrySet().forEach((e) -> {
                infoBuilder.keyValue(e.getKey(), e.getValue().element());
            });
            infoBuilder.up();
            infoBuilder.up();
        }

        {
            infoBuilder.key("request-body");
            infoBuilder.keyValue("contentLength", String.valueOf(exchange.getRequestContentLength()));
            exchange.getRequestReceiver().receiveFullBytes(new Receiver.FullBytesCallback() {
                @Override
                public void handle(HttpServerExchange httpServerExchange, byte[] bytes) {
                    infoBuilder.keyValue("payload", new String(bytes));
                }
            });
            infoBuilder.up();
        }

        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");

        {
            infoBuilder.key("response-headers");
            exchange.getResponseHeaders().getHeaderNames().forEach(h -> {
                exchange.getResponseHeaders().eachValue(h).forEach(v -> {
                    infoBuilder.keyValue(h.toString(), v);
                });
            });
            infoBuilder.up();
        }

        {
            infoBuilder.key("response-info");
            infoBuilder.key("cookies");
            exchange.getResponseCookies().forEach((k,v) -> {
                infoBuilder.keyValue(k,v.getValue());
            });
            infoBuilder.up();
        }
        infoBuilder.up();

        String payload = infoBuilder.build() + "\n";

        exchange.getResponseSender().send(payload);
        exchange.getResponseSender().close();
    }
}
