package io.descoped.exp.http.internal;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ResponseProcessors {

    static abstract class AbstractProcessor<T> {
        abstract void open();
        abstract void write(byte[] bytes);
        abstract void error();
        abstract void complete();
        abstract T getBody();
    }

    public static int remaining(List<ByteBuffer> bufs) {
        int remain = 0;
        for (ByteBuffer buf : bufs) {
            remain += buf.remaining();
        }
        return remain;
    }

    static private byte[] join(List<ByteBuffer> bytes) {
        int size = remaining(bytes);
        byte[] res = new byte[size];
        int from = 0;
        for (ByteBuffer b : bytes) {
            int l = b.remaining();
            b.get(res, from, l);
            from += l;
        }
        return res;
    }


    public static class PathProcessor<T> extends AbstractProcessor<T> {
        private final Path file;
//        private ByteBuff

        private FileChannel out;
        private final OpenOption[] options;

        PathProcessor(Path file, OpenOption... options) {
            this.file = file;
            this.options = options;
        }

        @Override
        void open() {

        }

        @Override
        void write(byte[] bytes) {

        }

        @Override
        void error() {

        }

        @Override
        void complete() {

        }

        @Override
        T getBody() {
            return null;
        }
    }


    public static class ByteArrayProcessor<T> extends AbstractProcessor<T> {
        private Function<byte[], T> finisher;
        private List<ByteBuffer> received;
        private T result;

        public ByteArrayProcessor(Function<byte[],T> finisher) {
            this.finisher = finisher;
        }

        @Override
        public void open() {
            received = new ArrayList<>();
        }

        @Override
        public void write(byte[] bytes) {
            received.add(ByteBuffer.wrap(bytes));
        }


        @Override
        public void error() {
            // incomplete handling
        }

        @Override
        public void complete() {
            result = finisher.apply(join(received));
            received.clear();
        }

        @Override
        public T getBody() {
            return result;
        }

    }

}
