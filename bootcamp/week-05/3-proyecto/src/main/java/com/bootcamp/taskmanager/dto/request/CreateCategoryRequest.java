package com.bootcamp.taskmanager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 50, message = "Nombre no puede exceder 50 caracteres")
    String name,

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Color debe ser formato hex (#RRGGBB)")
    String color,

    @Size(max = 255, message = "Descripción no puede exceder 255 caracteres")
    String description
) {}
