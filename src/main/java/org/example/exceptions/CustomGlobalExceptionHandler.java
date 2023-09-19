package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
class CustomGlobalExceptionHandler {

    @ExceptionHandler(MessageNotSentException.class)
    public ResponseEntity<CustomErrorResponse> messageNotSent(MessageNotSentException messageNotSentException, WebRequest request) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMessageIdException.class)
    public ResponseEntity<CustomErrorResponse> IncorrectMessageBody(InvalidMessageIdException invalidMessageId, WebRequest request) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), invalidMessageId.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectMessageBody.class)
    public ResponseEntity<CustomErrorResponse> IncorrectMessageBody(IncorrectMessageBody incorrectMessageBody, WebRequest request) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), incorrectMessageBody.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequestInvalidated.class)
    public ResponseEntity<CustomErrorResponse> InvalidRequest(RequestInvalidated requestInvalidated, WebRequest request) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), requestInvalidated.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CustomErrorResponse> noHandler(NoHandlerFoundException noHandler, WebRequest request) {
        CustomErrorResponse errorDetails = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), "invalid request URL");
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleValidationException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        CustomErrorResponse err = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), methodArgumentNotValidException.getFieldError().getDefaultMessage());
        return new ResponseEntity<CustomErrorResponse>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> IllegalArgumentException(
            IllegalArgumentException illegalArgumentException) {
        CustomErrorResponse err = new CustomErrorResponse(HttpStatus.BAD_REQUEST.value(), illegalArgumentException.getMessage());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> ResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException) {
        CustomErrorResponse err = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), resourceNotFoundException.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
