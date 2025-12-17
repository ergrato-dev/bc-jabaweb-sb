package com.bootcamp.finalproject.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO para crear/actualizar producto.
 */
public record ProductRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    String name,

    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    String description,

    @NotNull(message = "El precio es requerido")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
    BigDecimal price,

    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock no puede ser negativo")
    Integer stock,

    @Size(max = 500, message = "La URL de imagen no puede exceder 500 caracteres")
    String imageUrl,

    @NotNull(message = "La categoría es requerida")
    Long categoryId
) {}
