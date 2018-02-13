package io.descoped.client.http;

import java.util.Optional;

public interface ResponseProcessor<T> {

    /**
     * Returns the body object
     *
     * @return a body object
     */
    Optional<T> getBody();

}
