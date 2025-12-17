package com.bootcamp.taskmanager.model;

import java.time.LocalDateTime;

/**
 * Entidad Task - Representa una tarea en el dominio.
 *
 * Esta es la ENTIDAD (modelo de dominio). En la Semana 04 agregaremos
 * anotaciones JPA (@Entity, @Id, etc.) para persistencia en base de datos.
 *
 * Por ahora usamos una clase Java simple (POJO).
 */
public class Task {

    // =========================================================================
    // ATRIBUTOS
    // =========================================================================

    private String id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // =========================================================================
    // CONSTRUCTORES
    // =========================================================================

    /**
     * Constructor vacío - necesario para deserialización JSON
     */
    public Task() {
    }

    /**
     * Constructor con campos principales
     */
    public Task(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    // =========================================================================
    // GETTERS Y SETTERS
    // =========================================================================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
