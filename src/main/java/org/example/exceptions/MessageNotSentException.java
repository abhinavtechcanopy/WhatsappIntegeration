package org.example.exceptions;

public class MessageNotSentException extends RuntimeException {
    public MessageNotSentException(String messageWasNotSentCorrectly)
    {
        super(messageWasNotSentCorrectly);
    }
}
