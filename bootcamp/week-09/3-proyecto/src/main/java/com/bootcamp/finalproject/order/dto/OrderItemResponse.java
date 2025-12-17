package com.bootcamp.finalproject.order.dto;

import java.math.BigDecimal;

/**
 * DTO para item de orden en respuesta.
 */
public record OrderItemResponse(
    Long id,
    Long productId,
    String productName,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal
) {}
