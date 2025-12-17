package com.bootcamp.apidocs.dto;

// TODO 1: Importar la anotación @Schema de io.swagger.v3.oas.annotations.media
import java.time.LocalDateTime;

/**
 * DTO para respuestas de error estandarizadas.
 *
 * TODO 2: Añadir @Schema a nivel de clase con:
 *   - name = "ErrorResponse"
 *   - description = "Respuesta de error estandarizada de la API"
 */
public class ErrorResponse {

    // TODO 3: Añadir @Schema con description, example
    private int status;

    // TODO 4: Añadir @Schema con description, example
    private String error;

    // TODO 5: Añadir @Schema con description, example
    private String message;

    // TODO 6: Añadir @Schema con description, example
    private String path;

    // TODO 7: Añadir @Schema con description
    private LocalDateTime timestamp;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
