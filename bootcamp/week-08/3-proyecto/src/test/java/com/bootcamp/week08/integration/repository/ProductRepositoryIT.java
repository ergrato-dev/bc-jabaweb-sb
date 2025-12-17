package com.bootcamp.week08.integration.repository;

import com.bootcamp.week08.integration.AbstractIntegrationTest;
import com.bootcamp.week08.product.entity.Product;
import com.bootcamp.week08.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests de integración para ProductRepository.
 * 
 * Usa PostgreSQL real en contenedor Docker via TestContainers.
 */
@DisplayName("ProductRepository Integration Tests")
class ProductRepositoryIT extends AbstractIntegrationTest {
    
    @Autowired
    private ProductRepository productRepository;
    
    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }
    
    // ====================================================
    // CRUD Tests
    // ====================================================
    @Nested
    @DisplayName("CRUD Operations")
    class CrudTests {
        
        @Test
        @DisplayName("should save and retrieve product")
        void saveAndFind_ValidProduct_Success() {
            // TODO: Implementar
            // Given
            Product product = createProduct("Test Product", "Electronics", 99.99);
            
            // When
            Product saved = productRepository.save(product);
            
            // Then
            assertThat(saved.getId()).isNotNull();
            
            // Optional<Product> found = productRepository.findById(saved.getId());
            // assertThat(found).isPresent();
            // assertThat(found.get().getName()).isEqualTo("Test Product");
        }
        
        @Test
        @DisplayName("should update existing product")
        void update_ExistingProduct_Success() {
            // TODO: Implementar
            // Given
            Product product = productRepository.save(
                createProduct("Original", "Category", 50.0)
            );
            
            // When
            product.setName("Updated");
            product.setPrice(75.0);
            productRepository.save(product);
            
            // Then
            // Product updated = productRepository.findById(product.getId()).orElseThrow();
            // assertThat(updated.getName()).isEqualTo("Updated");
            // assertThat(updated.getPrice()).isEqualTo(75.0);
        }
        
        @Test
        @DisplayName("should delete product by id")
        void deleteById_ExistingProduct_Success() {
            // TODO: Implementar
            // Given
            Product product = productRepository.save(
                createProduct("To Delete", null, 30.0)
            );
            Long id = product.getId();
            
            // When
            productRepository.deleteById(id);
            
            // Then
            // assertThat(productRepository.findById(id)).isEmpty();
        }
    }
    
    // ====================================================
    // Custom Query Tests
    // ====================================================
    @Nested
    @DisplayName("Custom Queries")
    class CustomQueryTests {
        
        @Test
        @DisplayName("should find products by category")
        void findByCategory_ExistingCategory_ReturnsProducts() {
            // TODO: Implementar
            // Given
            productRepository.saveAll(List.of(
                createProduct("Laptop", "Electronics", 999.0),
                createProduct("Phone", "Electronics", 699.0),
                createProduct("Chair", "Furniture", 199.0)
            ));
            
            // When
            List<Product> result = productRepository.findByCategory("Electronics");
            
            // Then
            // assertThat(result).hasSize(2);
            // assertThat(result).extracting(Product::getName)
            //     .containsExactlyInAnyOrder("Laptop", "Phone");
        }
        
        @Test
        @DisplayName("should find products by price range")
        void findByPriceBetween_ValidRange_ReturnsProducts() {
            // TODO: Implementar
            // Given
            productRepository.saveAll(List.of(
                createProduct("Cheap", null, 10.0),
                createProduct("Medium", null, 50.0),
                createProduct("Expensive", null, 100.0)
            ));
            
            // When
            List<Product> result = productRepository.findByPriceBetween(20.0, 80.0);
            
            // Then
            // assertThat(result).hasSize(1);
            // assertThat(result.get(0).getName()).isEqualTo("Medium");
        }
        
        @Test
        @DisplayName("should find by name containing (case insensitive)")
        void findByNameContaining_Pattern_ReturnsMatches() {
            // TODO: Implementar
            // Given
            productRepository.saveAll(List.of(
                createProduct("Apple iPhone", null, 999.0),
                createProduct("Samsung Galaxy", null, 899.0),
                createProduct("Apple MacBook", null, 1999.0)
            ));
            
            // When
            List<Product> result = productRepository.findByNameContainingIgnoreCase("apple");
            
            // Then
            // assertThat(result).hasSize(2);
        }
        
        @Test
        @DisplayName("should check existence by name")
        void existsByName_ExistingName_ReturnsTrue() {
            // TODO: Implementar
            // Given
            productRepository.save(createProduct("Unique Product", null, 50.0));
            
            // When & Then
            // assertThat(productRepository.existsByName("Unique Product")).isTrue();
            // assertThat(productRepository.existsByName("Non Existing")).isFalse();
        }
        
        @Test
        @DisplayName("should find products with low stock")
        void findLowStock_BelowThreshold_ReturnsProducts() {
            // TODO: Implementar
            // Given
            Product lowStock = createProduct("Low Stock", null, 20.0);
            lowStock.setStock(5);
            
            Product normalStock = createProduct("Normal Stock", null, 30.0);
            normalStock.setStock(100);
            
            productRepository.saveAll(List.of(lowStock, normalStock));
            
            // When
            List<Product> result = productRepository.findLowStock(10);
            
            // Then
            // assertThat(result).hasSize(1);
            // assertThat(result.get(0).getName()).isEqualTo("Low Stock");
        }
    }
    
    // ====================================================
    // Pagination Tests
    // ====================================================
    @Nested
    @DisplayName("Pagination and Sorting")
    class PaginationTests {
        
        @BeforeEach
        void setUpData() {
            // Crear 25 productos
            for (int i = 1; i <= 25; i++) {
                productRepository.save(
                    createProduct("Product " + i, "Category", i * 10.0)
                );
            }
        }
        
        @Test
        @DisplayName("should return paginated results")
        void findAll_WithPagination_ReturnsPaginatedResults() {
            // TODO: Implementar
            // Given
            PageRequest pageRequest = PageRequest.of(0, 10);
            
            // When
            Page<Product> page = productRepository.findAll(pageRequest);
            
            // Then
            assertThat(page.getContent()).hasSize(10);
            assertThat(page.getTotalElements()).isEqualTo(25);
            assertThat(page.getTotalPages()).isEqualTo(3);
        }
        
        @Test
        @DisplayName("should return sorted results")
        void findAll_WithSorting_ReturnsSortedResults() {
            // TODO: Implementar
            // Given
            PageRequest pageRequest = PageRequest.of(
                0, 5, Sort.by(Sort.Direction.DESC, "price")
            );
            
            // When
            Page<Product> page = productRepository.findAll(pageRequest);
            
            // Then
            // assertThat(page.getContent().get(0).getPrice()).isEqualTo(250.0);
        }
    }
    
    // ====================================================
    // Helper Methods
    // ====================================================
    private Product createProduct(String name, String category, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setStock(10);
        return product;
    }
}
