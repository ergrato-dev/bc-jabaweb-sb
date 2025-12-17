package com.bootcamp.week08.product.dto;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para Product.
 */
public record ProductDTO(
    Long id,
    String name,
    String description,
    String category,
    Double price,
    Integer stock,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    /**
     * Factory method para crear ProductDTO desde entidad Product.
     */
    public static ProductDTO fromEntity(com.bootcamp.week08.product.entity.Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getCategory(),
            product.getPrice(),
            product.getStock(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}
