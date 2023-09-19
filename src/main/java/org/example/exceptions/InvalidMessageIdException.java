package org.example.exceptions;

public class InvalidMessageIdException extends RuntimeException {
    public InvalidMessageIdException(String invalidMessageId) {
        super(invalidMessageId);
    }
}
