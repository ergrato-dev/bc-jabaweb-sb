package com.bootcamp.taskmanager.exception;

import java.time.LocalDateTime;

/**
 * Estructura estándar para respuestas de error de la API.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿POR QUÉ ESTANDARIZAR LAS RESPUESTAS DE ERROR?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Una API profesional debe:
 *   1. Retornar SIEMPRE la misma estructura de error
 *   2. Incluir información útil para debugging (sin exponer datos sensibles)
 *   3. Permitir que el cliente maneje errores de forma consistente
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ESTRUCTURA DE RESPUESTA DE ERROR
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Ejemplo de respuesta JSON:
 *
 *   {
 *     "timestamp": "2024-01-15T10:30:00",
 *     "status": 404,
 *     "error": "Not Found",
 *     "message": "Task not found with id: 'abc123'",
 *     "path": "/api/tasks/abc123"
 *   }
 *
 * Esta estructura está inspirada en:
 *   - Spring Boot default error response
 *   - RFC 7807 Problem Details (simplificado)
 *
 */
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    // =========================================================================
    // CONSTRUCTORES
    // =========================================================================

    /**
     * Constructor vacío
     */
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructor con todos los campos
     */
    public ErrorResponse(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // =========================================================================
    // GETTERS Y SETTERS
    // =========================================================================

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

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
}
