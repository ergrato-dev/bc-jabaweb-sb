package com.bootcamp.security.exception;

/**
 * Excepción para recursos duplicados (username, email, etc.)
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
