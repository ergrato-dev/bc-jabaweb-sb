package com.bootcamp.finalproject.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para item de orden en solicitud.
 */
public record OrderItemRequest(
    @NotNull(message = "El ID del producto es requerido")
    Long productId,

    @NotNull(message = "La cantidad es requerida")
    @Min(value = 1, message = "La cantidad mínima es 1")
    Integer quantity
) {}
