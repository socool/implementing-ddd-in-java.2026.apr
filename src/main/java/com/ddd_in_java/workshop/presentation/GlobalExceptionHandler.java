package com.ddd_in_java.workshop.presentation;

import com.ddd_in_java.workshop.domain.VisitorNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    record ErrorResponse(String message) {}

    @ExceptionHandler(VisitorNotFound.class)
    public ResponseEntity<ErrorResponse> handleVisitorNotFound(VisitorNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ex.getMessage()));
    }
}
