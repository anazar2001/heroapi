package com.example.heroapi.exception;

import static com.example.heroapi.messagges.MessageKeys.SUPER_HERO_INTERNAL_SERVER_ERROR;
import static com.example.heroapi.messagges.MessageKeys.SUPER_HERO_NOT_FOUND;
import static com.example.heroapi.messagges.MessageKeys.SUPER_HERO_REQUEST_VALIDATION_ERROR;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The root error handler for the SuperHero API endpoints.
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class SuperHeroErrorHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    /**
     * Handler for {@link SuperHeroNotFoundException}.
     * 
     * @param e SuperHeroNotFoundException exception
     * @return Response with http status NOT_FOUND (404)
     */
    @ExceptionHandler(SuperHeroNotFoundException.class)
    public ResponseEntity<String> handleSuperHeroNotFoundException(SuperHeroNotFoundException e) {

        log.error(messageSource.getMessage(SUPER_HERO_NOT_FOUND.getKey(), new Object[] { HttpStatus.NOT_FOUND },
                Locale.getDefault()), e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handler for {@link SuperHeroServerException}.
     * 
     * @param e SuperHeroNotFoundException exception
     * @return Response with http status INTERNAL_SERVER_ERROR (500)
     */
    @ExceptionHandler(SuperHeroServerException.class)
    public ResponseEntity<String> handleServerException(SuperHeroServerException e) {

        log.error(messageSource.getMessage(SUPER_HERO_INTERNAL_SERVER_ERROR.getKey(),
                new Object[] { HttpStatus.INTERNAL_SERVER_ERROR },
                Locale.getDefault()), e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handler for {@link Exception}. This is a generic exception handler that will catch any other exceptions.
     * 
     * @param e Generic Exception
     * @return Response with http status INTERNAL_SERVER_ERROR (500)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {

        log.error(messageSource.getMessage(SUPER_HERO_INTERNAL_SERVER_ERROR.getKey(),
                new Object[] { HttpStatus.INTERNAL_SERVER_ERROR },
                Locale.getDefault()), e);

        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This method is overridden to provide a custom message for validation errors
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        log.error(messageSource.getMessage(SUPER_HERO_REQUEST_VALIDATION_ERROR.getKey(),
                new Object[] { status },
                Locale.getDefault()), ex);

        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }
}
