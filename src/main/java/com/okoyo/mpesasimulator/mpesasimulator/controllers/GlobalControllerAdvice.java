package com.okoyo.mpesasimulator.mpesasimulator.controllers;

import com.okoyo.mpesasimulator.mpesasimulator.dto.AuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMissingAuthorizationHeader(MissingRequestHeaderException ex) {
        System.out.println("Missing authorization header: " + ex.getMessage());
        return ResponseEntity.badRequest().body(new AuthenticationResponse("1", "Missing Authorization header"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        System.out.println("Request method error: " + ex.getMessage());
        return ResponseEntity.badRequest().body(new AuthenticationResponse("1", ex.getMessage()));
    }
}
