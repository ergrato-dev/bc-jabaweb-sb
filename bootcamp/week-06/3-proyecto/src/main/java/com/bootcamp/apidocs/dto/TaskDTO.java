package com.bootcamp.apidocs.dto;

import com.bootcamp.apidocs.entity.Priority;
// TODO 1: Importar la anotación @Schema de io.swagger.v3.oas.annotations.media
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para transferir datos de Task.
 *
 * TODO 2: Añadir @Schema a nivel de clase con:
 *   - name = "Task"
 *   - description = "Representación de una tarea"
 */
public class TaskDTO {

    // TODO 3: Añadir @Schema con description, example, y accessMode.READ_ONLY
    private UUID id;

    // TODO 4: Añadir @Schema con description, example, requiredMode.REQUIRED
    private String title;

    // TODO 5: Añadir @Schema con description, example, nullable = true
    private String description;

    // TODO 6: Añadir @Schema con description, example = "false"
    private Boolean completed;

    // TODO 7: Añadir @Schema con description, example = "MEDIUM"
    private Priority priority;

    // TODO 8: Añadir @Schema con description y accessMode.READ_ONLY
    private UUID userId;

    // TODO 9: Añadir @Schema con description y accessMode.READ_ONLY
    private LocalDateTime createdAt;

    // TODO 10: Añadir @Schema con description y accessMode.READ_ONLY
    private LocalDateTime updatedAt;

    public TaskDTO() {
    }

    public TaskDTO(UUID id, String title, String description, Boolean completed,
                   Priority priority, UUID userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.priority = priority;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
