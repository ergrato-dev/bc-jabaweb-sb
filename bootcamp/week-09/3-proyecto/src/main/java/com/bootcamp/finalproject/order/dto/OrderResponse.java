package com.bootcamp.finalproject.order.dto;

import com.bootcamp.finalproject.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para respuesta de orden.
 */
public record OrderResponse(
    Long id,
    Long userId,
    String userName,
    OrderStatus status,
    BigDecimal total,
    String shippingAddress,
    String notes,
    List<OrderItemResponse> items,
    LocalDateTime createdAt
) {}
