package com.bootcamp.week08.unit.service;

import com.bootcamp.week08.product.dto.CreateProductRequest;
import com.bootcamp.week08.product.dto.ProductDTO;
import com.bootcamp.week08.product.entity.Product;
import com.bootcamp.week08.product.repository.ProductRepository;
import com.bootcamp.week08.product.service.ProductService;
import com.bootcamp.week08.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Tests unitarios para ProductService.
 *
 * Usa Mockito para simular el repository.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Unit Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product("Test Product", "Description", 99.99, 100);
        sampleProduct.setId(1L);
        sampleProduct.setCategory("Electronics");
    }

    // ====================================================
    // findById Tests
    // ====================================================
    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("should return product when found")
        void findById_ExistingId_ReturnsProduct() {
            // TODO: Implementar este test
            // Given
            when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

            // When
            ProductDTO result = productService.findById(1L);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(1L);
            assertThat(result.name()).isEqualTo("Test Product");

            verify(productRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("should throw exception when not found")
        void findById_NonExistingId_ThrowsException() {
            // TODO: Implementar este test
            // Given
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> productService.findById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");

            verify(productRepository).findById(999L);
        }
    }

    // ====================================================
    // findAll Tests
    // ====================================================
    @Nested
    @DisplayName("findAll")
    class FindAllTests {

        @Test
        @DisplayName("should return all products")
        void findAll_HasProducts_ReturnsAll() {
            // TODO: Implementar este test
            // Given
            Product product2 = new Product("Second", "Desc", 50.0, 10);
            product2.setId(2L);

            when(productRepository.findAll())
                .thenReturn(Arrays.asList(sampleProduct, product2));

            // When
            List<ProductDTO> result = productService.findAll();

            // Then
            // assertThat(result).hasSize(2);
            // assertThat(result.get(0).name()).isEqualTo("Test Product");
        }

        @Test
        @DisplayName("should return empty list when no products")
        void findAll_NoProducts_ReturnsEmptyList() {
            // TODO: Implementar este test
            // Given
            when(productRepository.findAll()).thenReturn(List.of());

            // When
            List<ProductDTO> result = productService.findAll();

            // Then
            // assertThat(result).isEmpty();
        }
    }

    // ====================================================
    // create Tests
    // ====================================================
    @Nested
    @DisplayName("create")
    class CreateTests {

        @Test
        @DisplayName("should create product successfully")
        void create_ValidRequest_ReturnsCreatedProduct() {
            // TODO: Implementar este test
            // Given
            CreateProductRequest request = new CreateProductRequest(
                "New Product", "Description", "Category", 199.99, 50
            );

            when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

            // When
            ProductDTO result = productService.create(request);

            // Then
            // assertThat(result).isNotNull();
            // verify(productRepository).save(productCaptor.capture());
            // Product captured = productCaptor.getValue();
            // assertThat(captured.getName()).isEqualTo("New Product");
        }

        @Test
        @DisplayName("should set all fields from request")
        void create_ValidRequest_SetsAllFields() {
            // TODO: Implementar - verificar que todos los campos se copian
        }
    }

    // ====================================================
    // update Tests
    // ====================================================
    @Nested
    @DisplayName("update")
    class UpdateTests {

        @Test
        @DisplayName("should update existing product")
        void update_ExistingProduct_ReturnsUpdated() {
            // TODO: Implementar este test
            // Given
            CreateProductRequest request = new CreateProductRequest(
                "Updated Name", "Updated Desc", "NewCat", 299.99, 75
            );

            when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
            when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

            // When
            ProductDTO result = productService.update(1L, request);

            // Then
            // verify(productRepository).findById(1L);
            // verify(productRepository).save(productCaptor.capture());
            // Product captured = productCaptor.getValue();
            // assertThat(captured.getName()).isEqualTo("Updated Name");
        }

        @Test
        @DisplayName("should throw exception when product not found")
        void update_NonExistingProduct_ThrowsException() {
            // TODO: Implementar este test
            CreateProductRequest request = new CreateProductRequest(
                "Name", "Desc", "Cat", 99.99, 10
            );

            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            // assertThatThrownBy(() -> productService.update(999L, request))
            //     .isInstanceOf(ResourceNotFoundException.class);

            // verify(productRepository, never()).save(any());
        }
    }

    // ====================================================
    // delete Tests
    // ====================================================
    @Nested
    @DisplayName("delete")
    class DeleteTests {

        @Test
        @DisplayName("should delete existing product")
        void delete_ExistingProduct_DeletesSuccessfully() {
            // TODO: Implementar este test
            // Given
            when(productRepository.existsById(1L)).thenReturn(true);
            doNothing().when(productRepository).deleteById(1L);

            // When
            productService.delete(1L);

            // Then
            // verify(productRepository).existsById(1L);
            // verify(productRepository).deleteById(1L);
        }

        @Test
        @DisplayName("should throw exception when product not found")
        void delete_NonExistingProduct_ThrowsException() {
            // TODO: Implementar este test
            when(productRepository.existsById(anyLong())).thenReturn(false);

            // assertThatThrownBy(() -> productService.delete(999L))
            //     .isInstanceOf(ResourceNotFoundException.class);

            // verify(productRepository, never()).deleteById(anyLong());
        }
    }

    // ====================================================
    // findByCategory Tests
    // ====================================================
    @Nested
    @DisplayName("findByCategory")
    class FindByCategoryTests {

        @Test
        @DisplayName("should return products in category")
        void findByCategory_ExistingCategory_ReturnsProducts() {
            // TODO: Implementar
        }

        @Test
        @DisplayName("should return empty list when no products in category")
        void findByCategory_EmptyCategory_ReturnsEmptyList() {
            // TODO: Implementar
        }
    }

    // ====================================================
    // findByPriceRange Tests
    // ====================================================
    @Nested
    @DisplayName("findByPriceRange")
    class FindByPriceRangeTests {

        @Test
        @DisplayName("should return products in price range")
        void findByPriceRange_ValidRange_ReturnsProducts() {
            // TODO: Implementar
        }
    }
}
