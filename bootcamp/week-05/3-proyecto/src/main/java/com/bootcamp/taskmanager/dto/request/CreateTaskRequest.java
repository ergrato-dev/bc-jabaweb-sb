package com.bootcamp.taskmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record CreateTaskRequest(
    @NotBlank(message = "El título es requerido")
    @Size(max = 255, message = "Título no puede exceder 255 caracteres")
    String title,

    @Size(max = 1000, message = "Descripción no puede exceder 1000 caracteres")
    String description,

    @NotNull(message = "El userId es requerido")
    UUID userId,

    Set<UUID> categoryIds  // Opcional: categorías iniciales
) {}
