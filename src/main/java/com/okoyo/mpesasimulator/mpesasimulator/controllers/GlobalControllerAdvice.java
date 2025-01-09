package com.okoyo.mpesasimulator.mpesasimulator.controllers;

import com.okoyo.mpesasimulator.mpesasimulator.dto.AuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;


@RestControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMissingAuthorizationHeader(MissingRequestHeaderException ex) {
        logger.error("Missing authorization header: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new AuthenticationResponse("1", "Missing Authorization header"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error("Request method error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new AuthenticationResponse("1", ex.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleNullPointerException(NullPointerException ex) {
        logger.error("NullPointerException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new AuthenticationResponse("1", "Null value encountered"));
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleNullPointerException(SQLException ex) {
        logger.error("SQLException: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new AuthenticationResponse("1", "Internal Processing Error"));
    }
}
