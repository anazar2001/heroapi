package com.example.heroapi.exception;

/**
 * A custom exception for the cases when a SuperHero is not found.
 */
public class SuperHeroNotFoundException extends RuntimeException {
    public SuperHeroNotFoundException(String message) {
        super(message);
    }
}