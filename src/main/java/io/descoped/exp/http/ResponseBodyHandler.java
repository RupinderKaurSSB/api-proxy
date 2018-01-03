package io.descoped.exp.http;

public interface ResponseBodyHandler<T> {

    ResponseBodyProcessor<T> apply(int statusCode, Headers responseHeaders);

}
