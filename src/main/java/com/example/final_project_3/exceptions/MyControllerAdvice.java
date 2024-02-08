package com.example.final_project_3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MyControllerAdvice {
    @ExceptionHandler(value = DuplicateException.class)
    public ResponseEntity<String> handle(DuplicateException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT
        );
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoMatchResultException.class)
    public ResponseEntity<String> handleException(NoMatchResultException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DoesNotMatchField.class)
    public ResponseEntity<String> handleException(DoesNotMatchField ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InsufficientCreditException.class)
    public ResponseEntity<String> handleException(InsufficientCreditException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<String> handleException(PaymentProcessingException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid format")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleException(HttpMessageNotReadableException ex) {
        ex.getHttpInputMessage();
    }


}
