package io.descoped.client.http;

public interface HttpResponse<T> {

    @FunctionalInterface
    interface BodyProcessor<T> {
        static HttpResponse.BodyProcessor<byte[]> asByteArray​() {
            return null;
        }
        BodyProcessor<T> apply(int statusCode);
    }
}
