package com.example.heroapi.exception;

/**
 * A custom exception for server errors in the SuperHero application.
 */
public class SuperHeroServerException extends RuntimeException {

    public SuperHeroServerException(String message) {
        super(message);
    }

    public SuperHeroServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
