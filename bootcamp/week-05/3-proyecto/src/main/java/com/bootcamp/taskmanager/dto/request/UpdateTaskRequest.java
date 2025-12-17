package com.bootcamp.taskmanager.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateTaskRequest(
    @Size(max = 255, message = "Título no puede exceder 255 caracteres")
    String title,

    @Size(max = 1000, message = "Descripción no puede exceder 1000 caracteres")
    String description,

    Boolean completed
) {}
