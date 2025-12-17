package com.bootcamp.finalproject.common.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO estándar para respuestas de error.
 */
public record ErrorResponse(
    String message,
    String code,
    LocalDateTime timestamp,
    List<FieldError> errors
) {
    public ErrorResponse(String message, String code) {
        this(message, code, LocalDateTime.now(), null);
    }

    public ErrorResponse(String message, String code, List<FieldError> errors) {
        this(message, code, LocalDateTime.now(), errors);
    }

    public record FieldError(String field, String message) {}
}
