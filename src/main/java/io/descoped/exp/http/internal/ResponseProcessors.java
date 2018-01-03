package io.descoped.exp.http.internal;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ResponseProcessors {

    static abstract class AbstractProcessor<T> {
        abstract void open();
        abstract void write(byte[] bytes);
        abstract void error();
        abstract void complete();
    }

    public static int remaining(List<ByteBuffer> bufs) {
        int remain = 0;
        for (ByteBuffer buf : bufs) {
            remain += buf.remaining();
        }
        return remain;
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
    }


    public static class ByteArrayProcessor<T> extends AbstractProcessor<T> {

        private T result;
        private List<ByteBuffer> received;

        public ByteArrayProcessor() {
        }

        @Override
        void open() {
            received = new ArrayList<>();
        }

        @Override
        void write(byte[] bytes) {
            received.add(ByteBuffer.wrap(bytes));
        }


        @Override
        void error() {

        }

        @Override
        void complete() {
            int bufSize = remaining(received);

            for (ByteBuffer bb : received) {

            }
        }
    }


}
