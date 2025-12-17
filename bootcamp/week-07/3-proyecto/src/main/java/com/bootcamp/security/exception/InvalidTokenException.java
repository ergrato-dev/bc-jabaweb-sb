package com.bootcamp.security.exception;

/**
 * Excepción para tokens JWT inválidos.
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
