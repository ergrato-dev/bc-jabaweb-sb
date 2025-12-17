package com.bootcamp.apidocs.dto;

import com.bootcamp.apidocs.entity.Priority;
// TODO 1: Importar la anotación @Schema de io.swagger.v3.oas.annotations.media
import jakarta.validation.constraints.*;

/**
 * DTO para crear una nueva tarea.
 *
 * TODO 2: Añadir @Schema a nivel de clase con:
 *   - name = "CreateTaskRequest"
 *   - description = "Datos necesarios para crear una tarea"
 */
public class CreateTaskRequest {

    // TODO 3: Añadir @Schema con description, example, requiredMode.REQUIRED, minLength, maxLength
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;

    // TODO 4: Añadir @Schema con description, example, nullable = true, maxLength
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    // TODO 5: Añadir @Schema con description, example, defaultValue
    private Priority priority = Priority.MEDIUM;

    // TODO 6: Añadir @Schema con description, example, requiredMode.REQUIRED
    @NotNull(message = "El ID de usuario es obligatorio")
    private java.util.UUID userId;

    public CreateTaskRequest() {
    }

    public CreateTaskRequest(String title, String description, Priority priority, java.util.UUID userId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.userId = userId;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public java.util.UUID getUserId() {
        return userId;
    }

    public void setUserId(java.util.UUID userId) {
        this.userId = userId;
    }
}
