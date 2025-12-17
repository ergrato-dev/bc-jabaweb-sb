package com.bootcamp.finalproject.common.exception;

/**
 * Excepción para recursos no encontrados (404).
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " no encontrado con ID: " + id);
    }
}
