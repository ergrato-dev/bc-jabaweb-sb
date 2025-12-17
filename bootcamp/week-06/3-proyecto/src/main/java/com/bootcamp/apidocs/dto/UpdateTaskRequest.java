package com.bootcamp.apidocs.dto;

import com.bootcamp.apidocs.entity.Priority;
// TODO 1: Importar la anotación @Schema de io.swagger.v3.oas.annotations.media
import jakarta.validation.constraints.Size;

/**
 * DTO para actualizar una tarea existente.
 *
 * TODO 2: Añadir @Schema a nivel de clase con:
 *   - name = "UpdateTaskRequest"
 *   - description = "Datos para actualizar una tarea (todos los campos son opcionales)"
 */
public class UpdateTaskRequest {

    // TODO 3: Añadir @Schema con description, example, nullable = true
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;

    // TODO 4: Añadir @Schema con description, example, nullable = true
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    // TODO 5: Añadir @Schema con description, example
    private Boolean completed;

    // TODO 6: Añadir @Schema con description, example
    private Priority priority;

    public UpdateTaskRequest() {
    }

    // Getters and Setters
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
}
