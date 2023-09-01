package org.example.exceptions;

public class RequestValidationfailed extends RuntimeException {
    public RequestValidationfailed(String requestInvalidated) {
        super(requestInvalidated);
    }
}
