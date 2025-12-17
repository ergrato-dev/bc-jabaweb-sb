package com.bootcamp.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear o actualizar tareas.
 */
public record TaskRequest(
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    String title,

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    String description,

    Boolean completed
) {
}
