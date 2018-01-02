package io.descoped.client.api.test.lambda;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LambdaHandlerProcessorProducerTest {

    private static final Logger log = LoggerFactory.getLogger(LambdaHandlerProcessorProducerTest.class);

    class Factory<T> {
        private Handler<T> handler;

        public Factory() {
        }

        public Factory<T> handle(Handler<T> handler) {
            this.handler = handler;
            return this;
        }

        public Processor<T> produce() {
            return handler.apply("This is payload!");
        }
    }

    interface Processor<T> {
        T value();

        static <U> Processor<U> asValueProcessor(U value) {
            return new ValueProcessor<>(value);
        }
    }

    static class ValueProcessor<T> implements Processor<T> {
        private final T value;

        public ValueProcessor(T value) {
            this.value = value;
        }

        @Override
        public T value() {
            return value;
        }
    }

    @FunctionalInterface
    interface Handler<T> {
        Processor<T> apply(String payload);

        static Handler<String> asString() {
            return payload -> Processor.asValueProcessor(payload);
        }
    }

    @Test
    public void testMe() {
        Factory<String> factory = new Factory<>();
        log.trace("produced value: {}", factory.handle(Handler.asString()).produce().value());
    }

}
