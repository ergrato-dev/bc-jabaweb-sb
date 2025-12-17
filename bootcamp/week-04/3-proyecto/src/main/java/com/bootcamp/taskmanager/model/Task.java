package com.bootcamp.taskmanager.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa una tarea.
 *
 * ⚠️ ARCHIVO DE PRÁCTICA: Completa los TODOs para que funcione.
 */
// ═══════════════════════════════════════════════════════════
// TODO 1: Agregar anotación @Entity para marcar como entidad JPA
// TODO 2: Agregar @Table(name = "tasks") para definir nombre de tabla
// ═══════════════════════════════════════════════════════════

public class Task {

    // ═══════════════════════════════════════════════════════════
    // TODO 3: Agregar @Id para marcar como clave primaria
    // TODO 4: Agregar @GeneratedValue(strategy = GenerationType.UUID)
    // ═══════════════════════════════════════════════════════════
    private UUID id;

    // ═══════════════════════════════════════════════════════════
    // TODO 5: Agregar @Column(nullable = false, length = 100)
    // ═══════════════════════════════════════════════════════════
    private String title;

    // ═══════════════════════════════════════════════════════════
    // TODO 6: Agregar @Column(columnDefinition = "TEXT")
    // ═══════════════════════════════════════════════════════════
    private String description;

    // ═══════════════════════════════════════════════════════════
    // TODO 7: Agregar @Column(nullable = false)
    // ═══════════════════════════════════════════════════════════
    private boolean completed = false;

    // ═══════════════════════════════════════════════════════════
    // TODO 8: Agregar @Column(name = "created_at", updatable = false)
    // ═══════════════════════════════════════════════════════════
    private LocalDateTime createdAt;

    // ═══════════════════════════════════════════════════════════
    // TODO 9: Agregar @Column(name = "updated_at")
    // ═══════════════════════════════════════════════════════════
    private LocalDateTime updatedAt;

    // Constructor vacío (requerido por JPA)
    public Task() {
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 10: Agregar método con @PrePersist que asigne
    //          createdAt y updatedAt con LocalDateTime.now()
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // TODO 11: Agregar método con @PreUpdate que asigne
    //          updatedAt con LocalDateTime.now()
    // ═══════════════════════════════════════════════════════════

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
