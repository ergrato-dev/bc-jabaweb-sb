package com.bootcamp.finalproject.common.exception;

/**
 * Excepción para solicitudes inválidas (400).
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
