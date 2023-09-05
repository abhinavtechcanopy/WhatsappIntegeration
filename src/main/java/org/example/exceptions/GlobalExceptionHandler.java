package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MessageNotSentException.class)
    public ResponseEntity<ErrorResponse> messageNotSent(MessageNotSentException messageNotSentException, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectMessageBody.class)
    public ResponseEntity<ErrorResponse> IncorrectMessageBody(IncorrectMessageBody incorrectMessageBody, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), incorrectMessageBody.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RequestInvalidated.class)
    public ResponseEntity<ErrorResponse> InvalidRequest(RequestInvalidated requestInvalidated, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), requestInvalidated.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> noHandler(NoHandlerFoundException noHandler, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "invalid URL");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentInvalidExceptionHandler(
            MethodArgumentNotValidException methodArgumentNotValidException) {

        ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), methodArgumentNotValidException.getFieldError().getDefaultMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> IllegalArgumentException(
            IllegalArgumentException illegalArgumentException) {

        ErrorResponse err = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), illegalArgumentException.getMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> ResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException) {
        ErrorResponse err = new ErrorResponse(HttpStatus.NOT_FOUND.value(), resourceNotFoundException.getMessage());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);

    }
}
