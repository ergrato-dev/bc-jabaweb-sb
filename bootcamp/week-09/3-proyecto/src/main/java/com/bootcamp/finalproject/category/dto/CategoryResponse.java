package com.bootcamp.finalproject.category.dto;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de categoría.
 */
public record CategoryResponse(
    Long id,
    String name,
    String description,
    Boolean active,
    Integer productCount,
    LocalDateTime createdAt
) {}
