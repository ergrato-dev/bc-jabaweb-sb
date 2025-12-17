package com.bootcamp.finalproject.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * DTO para crear orden.
 */
public record CreateOrderRequest(
    @NotBlank(message = "La dirección de envío es requerida")
    @Size(max = 500, message = "La dirección no puede exceder 500 caracteres")
    String shippingAddress,

    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    String notes,

    @NotEmpty(message = "Debe incluir al menos un producto")
    @Valid
    List<OrderItemRequest> items
) {}
