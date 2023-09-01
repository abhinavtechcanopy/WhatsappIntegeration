package org.example.exceptions;

public class IncorrectMessageBody extends RuntimeException{
    public IncorrectMessageBody(String incorrectMessageBody) {
        super(incorrectMessageBody);
    }
}
