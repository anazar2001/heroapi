package com.example.heroapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.heroapi.exception.SuperHeroNotFoundException;
import com.example.heroapi.exception.SuperHeroServerException;

@ControllerAdvice
public class SuperHeroControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SuperHeroNotFoundException.class)
    public ResponseEntity<String> handleSuperHeroNotFoundException(SuperHeroNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SuperHeroServerException.class)
    public ResponseEntity<String> handleServerException(SuperHeroServerException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
