package com.bootcamp.finalproject.product.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para respuesta de producto.
 */
public record ProductResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer stock,
    String imageUrl,
    Boolean active,
    Long categoryId,
    String categoryName,
    LocalDateTime createdAt
) {}
