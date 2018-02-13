package io.descoped.client.http.internal.httpRequest;

import com.github.kevinsawicki.http.HttpRequest;
import io.descoped.client.exception.APIClientException;
import io.descoped.client.http.*;
import io.descoped.client.http.internal.HeadersImpl;
import io.descoped.client.http.internal.RequestImpl;
import io.descoped.client.http.internal.ResponseImpl;
import io.descoped.client.http.internal.ResponseProcessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpRequestExchange<T> implements Exchange<T> {

    private static Logger log = LoggerFactory.getLogger(HttpRequestExchange.class);

    private final Request request;
    private final ResponseHandler<T> responseHandler;
    private String errorMessage;
    private String errorBody;

    public HttpRequestExchange(Request request, ResponseHandler<T> responseHandler) {
        this.request = request;
        this.responseHandler = responseHandler;
    }

    public ResponseHandler<T> getResponseHandler() {
        return responseHandler;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public Response<T> response() {
        RequestImpl requestImpl = (RequestImpl) request;

        HttpRequest httpRequest;

        if ("GET".equals(requestImpl.getMethod())) {
            httpRequest = HttpRequest.get(requestImpl.getUri().toString());

        } else if ("POST".equals(requestImpl.getMethod())) {
            httpRequest = HttpRequest.post(requestImpl.getUri().toString());

        } else if ("PUT".equals(requestImpl.getMethod())) {
            httpRequest = HttpRequest.put(requestImpl.getUri().toString());

        } else if ("DELETE".equals(requestImpl.getMethod())) {
            httpRequest = HttpRequest.delete(requestImpl.getUri().toString());

        } else {
            throw new UnsupportedOperationException("Method not supported!");
        }

        // copy to HttpRequest userHeaders (request)
        Map<String, String> userHeaders = HeadersImpl.asFlatMap(requestImpl.headers());
        httpRequest.headers(userHeaders);
        log.info("----> {}", userHeaders);

        // send request processor if assigned
        if (requestImpl.hasRequestProcessor()) {
            httpRequest.send(requestImpl.getRequestProcessor().body());
        }


        int statusCode = httpRequest.code();

        // copy from HttpRequest userHeaders (response)
        Map<String, List<String>> responseHeadersMap = httpRequest.headers();
        log.info("===> {}", responseHeadersMap);

        // obtain payload

        Headers responseHeaders = new HeadersImpl(responseHeadersMap);
        ResponseProcessor<T> result = responseHandler.apply(statusCode, responseHeaders);
        if (result != null) {
            ResponseProcessors.AbstractProcessor abstractProcessor = (ResponseProcessors.AbstractProcessor) result;
            abstractProcessor.open();
            byte[] bytes = httpRequest.bytes(); // read payload
            abstractProcessor.write(bytes);
            try {
                abstractProcessor.complete();
            } catch (Exception e) {
                try {
                    errorMessage = new String(httpRequest.message().getBytes(), StandardCharsets.UTF_8.name());
                    errorBody = new String(bytes, StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e1) {
                    throw new APIClientException(e); // todo: this is unsafe as no response will be delivered outside the client
                }
                ResponseImpl<T> response = new ResponseImpl<>(requestImpl, statusCode, responseHeaders, result.getBody(), this);
                response.setError(e);
                return response;
            }
            ResponseImpl<T> response = new ResponseImpl<>(requestImpl, statusCode, responseHeaders, result.getBody(), this);
            return response;

        }
        return null;
    }

}
