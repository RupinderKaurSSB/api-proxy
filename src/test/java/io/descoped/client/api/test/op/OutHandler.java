package io.descoped.client.api.test.op;

import io.descoped.client.api.test.storage.ResponseStorage;
import io.descoped.client.exception.APIClientException;
import io.descoped.client.http.Response;

import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

abstract public class OutHandler<T> {

    private final OpHandler<T> opHandler;
    private final Response<T> response;
    private ResponseStorage<T> responseStorage;

    public OutHandler(OpHandler<T> opHandler, Response<T> response) {
        this.opHandler = opHandler;
        this.response = response;
        responseStorage = new ResponseStorage<>();
    }

    public void param(String name, String value) {
        responseStorage.param(name, value);
    }

    public Map<String, String> params() {
        return responseStorage.params();
    }

    public void header(String name, String value) {
        responseStorage.header(name, value);
    }

    public Map<String, String> headers() {
        return responseStorage.headers();
    }

    public void outcome(String name, T value) {
        responseStorage.outcome(name, value);
    }

    public Map<String, T> outcome() {
        return responseStorage.outcome();
    }

    public void consume() {

        /*
            apply handler
            checkError
            handleError
            get body
         */

        // fail handling here
        if (response.statusCode() != HTTP_OK) {
            throw new APIClientException("Error with consumer!");
        }

        T body = response.body().get(); // the ResponseHandler will do all the requireed transformation
        T value = translate(body);
    }

    // todo: this method should be used to write to outcome map to collect data that is useful to other Operations
    abstract protected T translate(T body);

}
