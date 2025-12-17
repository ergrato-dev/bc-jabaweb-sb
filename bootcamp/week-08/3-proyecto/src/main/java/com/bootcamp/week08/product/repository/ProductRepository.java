package com.bootcamp.week08.product.repository;

import com.bootcamp.week08.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para operaciones CRUD y consultas de Product.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca productos por categoría.
     */
    List<Product> findByCategory(String category);

    /**
     * Busca productos por rango de precio.
     */
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Busca productos cuyo nombre contenga el texto (case insensitive).
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Verifica si existe un producto con el nombre dado.
     */
    boolean existsByName(String name);

    /**
     * Busca productos con stock bajo.
     */
    @Query("SELECT p FROM Product p WHERE p.stock < :threshold")
    List<Product> findLowStock(@Param("threshold") Integer threshold);

    /**
     * Búsqueda paginada por categoría.
     */
    Page<Product> findByCategory(String category, Pageable pageable);
}
