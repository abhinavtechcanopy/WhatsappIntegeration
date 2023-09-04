package org.example.exceptions;

public class RequestInvalidated extends RuntimeException {
    public RequestInvalidated(String requestInvalidated) {

        super(requestInvalidated);
    }
}
