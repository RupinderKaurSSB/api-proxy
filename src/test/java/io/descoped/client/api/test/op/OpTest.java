package io.descoped.client.api.test.op;

import io.descoped.client.api.test.storage.Variables;
import io.descoped.client.http.ResponseHandler;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class OpTest {

    private static Logger log = LoggerFactory.getLogger(OpTest.class);

    //    @Test
    public void testOpHandler() {
        OpHandler<String> operation = new OpHandler<String>("op0", URI.create("http://httpbin.org/get"), ResponseHandler.asString()) {
            @Override
            public OutHandler<String> run() {
                log.trace("requestBody(): {}", requestBody());
                return null;
            }
        };
    }

//    @Test
    public void should_transform_map() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "b");
        Map<String, String> result = map.entrySet().stream()
                .collect(toMap(Map.Entry::getKey, e -> "${" + String.valueOf(e.getValue()) + "}"));
        result.forEach((k, v) -> {
            log.trace("{}={}", k, v);
        });
    }

    @Test
    public void should_bind_variables() {
        Variables variables = new Variables();
        variables.add("foo", "bar");
        variables.add("bar", "foo");
        variables.add("baz", "foobar");
        variables.add("a", 1);
        variables.add("b", 2);
        variables.add("c", "1");
        variables.add("d", "2");
        variables.add("now", System.currentTimeMillis());


        log.trace("isExpr: {}", variables.isExpression("${foo}"));
        log.trace("getExpr: {}", variables.getExpression("${a + b}"));
        log.trace("Eval: {}", variables.evaluateExpression("${foo}"));
        log.trace("Eval: {}", variables.evaluateExpression("${a + b}"));

        String expr = "http://www.ssb.no/${foo}?foo=${foo}&val=${a + b}&val2=${c + d}&date=${now}";
        log.trace("Eval: {} --> {}", expr, variables.evaluate(expr));
    }

}
