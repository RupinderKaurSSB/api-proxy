package io.descoped.exp.http;

public interface ResponseBodyProcessor<T> {

    /**
     * Returns the body object
     *
     * @return a body object
     */
    T getBody();

}
