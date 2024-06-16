package com.example.heroapi.controller;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.heroapi.exception.SuperHeroNotFoundException;
import com.example.heroapi.exception.SuperHeroServerException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.heroapi.messagges.MessageKeys.SUPER_HERO_NOT_FOUND;
import static com.example.heroapi.messagges.MessageKeys.SUPER_HERO_REQUEST_VALIDATION_ERROR;
import static com.example.heroapi.messagges.MessageKeys.SUPER_HERO_INTERNAL_SERVER_ERROR;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class SuperHeroControllerErrorHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(SuperHeroNotFoundException.class)
    public ResponseEntity<String> handleSuperHeroNotFoundException(SuperHeroNotFoundException e) {

        log.error(messageSource.getMessage(SUPER_HERO_NOT_FOUND.getKey(), new Object[] { HttpStatus.NOT_FOUND },
                Locale.getDefault()), e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SuperHeroServerException.class)
    public ResponseEntity<String> handleServerException(SuperHeroServerException e) {

        log.error(messageSource.getMessage(SUPER_HERO_INTERNAL_SERVER_ERROR.getKey(),
                new Object[] { HttpStatus.INTERNAL_SERVER_ERROR },
                Locale.getDefault()), e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {

        log.error(messageSource.getMessage(SUPER_HERO_INTERNAL_SERVER_ERROR.getKey(),
                new Object[] { HttpStatus.INTERNAL_SERVER_ERROR },
                Locale.getDefault()), e);

        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error(messageSource.getMessage(SUPER_HERO_REQUEST_VALIDATION_ERROR.getKey(),
                new Object[] { status },
                Locale.getDefault()), ex);

        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
