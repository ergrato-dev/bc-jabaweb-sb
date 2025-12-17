package com.bootcamp.week08.product.dto;

import jakarta.validation.constraints.*;

/**
 * DTO para crear un nuevo producto.
 */
public record CreateProductRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 100, message = "Nombre debe tener entre 2 y 100 caracteres")
    String name,

    @Size(max = 500, message = "Descripción no puede exceder 500 caracteres")
    String description,

    @Size(max = 50, message = "Categoría no puede exceder 50 caracteres")
    String category,

    @NotNull(message = "El precio es requerido")
    @Positive(message = "El precio debe ser positivo")
    Double price,

    @NotNull(message = "El stock es requerido")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    Integer stock
) {}
