package com.bootcamp.taskmanager.dto;

import java.time.LocalDateTime;

/**
 * DTO para enviar datos de una tarea al cliente.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿POR QUÉ UN DTO DE RESPUESTA SEPARADO?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * El Response DTO controla QUÉ datos ve el cliente.
 *
 * Beneficios:
 *   1. Puedes excluir campos sensibles (ej: password, tokens internos)
 *   2. Puedes formatear datos (ej: fechas en formato específico)
 *   3. Puedes agregar campos calculados (ej: "createdAgo": "hace 2 horas")
 *   4. La entidad puede cambiar sin afectar la respuesta de la API
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * RECORD VS CLASE
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Este DTO está implementado como una CLASE tradicional.
 *
 * En Java 16+ puedes usar RECORDS que son más concisos:
 *
 *   public record TaskResponse(
 *       String id,
 *       String title,
 *       String description,
 *       boolean completed,
 *       LocalDateTime createdAt
 *   ) {}
 *
 * Los records son ideales para DTOs porque:
 *   - Son inmutables (no puedes cambiar valores después de crear)
 *   - equals(), hashCode() y toString() se generan automáticamente
 *   - Menos código
 *
 * Para el bootcamp usamos clases tradicionales para entender bien el concepto.
 *
 */
public class TaskResponse {

    // =========================================================================
    // ATRIBUTOS
    // =========================================================================
    //
    // Estos son los campos que el cliente VERÁ en la respuesta JSON.
    //
    // Nota: No incluimos 'updatedAt' en este ejemplo para mantenerlo simple,
    //       pero podrías agregarlo si quieres.

    private String id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;

    // =========================================================================
    // CONSTRUCTORES
    // =========================================================================

    /**
     * Constructor vacío
     */
    public TaskResponse() {
    }

    /**
     * Constructor con todos los campos
     */
    public TaskResponse(String id, String title, String description,
                        boolean completed, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    // =========================================================================
    // GETTERS (solo lectura - el Response no debería modificarse)
    // =========================================================================

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // =========================================================================
    // SETTERS (necesarios para algunas librerías de mapeo)
    // =========================================================================

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
