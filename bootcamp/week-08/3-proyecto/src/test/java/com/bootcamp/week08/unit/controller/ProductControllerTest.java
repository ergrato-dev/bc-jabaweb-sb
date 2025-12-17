package com.bootcamp.week08.unit.controller;

import com.bootcamp.week08.config.SecurityConfig;
import com.bootcamp.week08.product.controller.ProductController;
import com.bootcamp.week08.product.dto.CreateProductRequest;
import com.bootcamp.week08.product.dto.ProductDTO;
import com.bootcamp.week08.product.service.ProductService;
import com.bootcamp.week08.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de controlador con MockMvc.
 * 
 * @WebMvcTest carga solo la capa web.
 */
@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
@DisplayName("ProductController Tests")
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ProductService productService;
    
    private ProductDTO sampleProduct;
    
    @BeforeEach
    void setUp() {
        sampleProduct = new ProductDTO(
            1L, "Test Product", "Description", "Electronics",
            99.99, 100, LocalDateTime.now(), LocalDateTime.now()
        );
    }
    
    // ====================================================
    // GET /api/products Tests
    // ====================================================
    @Nested
    @DisplayName("GET /api/products")
    class GetAllTests {
        
        @Test
        @DisplayName("should return paginated products")
        void getAll_ReturnsPage() throws Exception {
            // TODO: Implementar este test
            // Given
            Page<ProductDTO> page = new PageImpl<>(
                List.of(sampleProduct), 
                PageRequest.of(0, 10), 
                1
            );
            when(productService.findAll(any())).thenReturn(page);
            
            // When & Then
            mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Test Product"));
        }
        
        @Test
        @DisplayName("should accept pagination parameters")
        void getAll_WithPagination_UsesParams() throws Exception {
            // TODO: Implementar - verificar page y size
        }
    }
    
    // ====================================================
    // GET /api/products/{id} Tests
    // ====================================================
    @Nested
    @DisplayName("GET /api/products/{id}")
    class GetByIdTests {
        
        @Test
        @DisplayName("should return product when found")
        void getById_ExistingId_ReturnsProduct() throws Exception {
            // TODO: Implementar este test
            when(productService.findById(1L)).thenReturn(sampleProduct);
            
            mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(99.99));
        }
        
        @Test
        @DisplayName("should return 404 when not found")
        void getById_NonExistingId_Returns404() throws Exception {
            // TODO: Implementar este test
            when(productService.findById(anyLong()))
                .thenThrow(new ResourceNotFoundException("Producto no encontrado"));
            
            // mockMvc.perform(get("/api/products/999"))
            //     .andExpect(status().isNotFound());
        }
    }
    
    // ====================================================
    // POST /api/products Tests
    // ====================================================
    @Nested
    @DisplayName("POST /api/products")
    class CreateTests {
        
        @Test
        @DisplayName("should create product with valid data")
        void create_ValidRequest_Returns201() throws Exception {
            // TODO: Implementar este test
            CreateProductRequest request = new CreateProductRequest(
                "New Product", "Description", "Category", 199.99, 50
            );
            
            when(productService.create(any())).thenReturn(sampleProduct);
            
            mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
        }
        
        @Test
        @DisplayName("should return 400 when name is blank")
        void create_BlankName_Returns400() throws Exception {
            // TODO: Implementar este test
            CreateProductRequest request = new CreateProductRequest(
                "", "Description", "Category", 99.99, 10
            );
            
            // mockMvc.perform(post("/api/products")
            //         .contentType(MediaType.APPLICATION_JSON)
            //         .content(objectMapper.writeValueAsString(request)))
            //     .andExpect(status().isBadRequest())
            //     .andExpect(jsonPath("$.errors.name").exists());
        }
        
        @Test
        @DisplayName("should return 400 when price is negative")
        void create_NegativePrice_Returns400() throws Exception {
            // TODO: Implementar - price debe ser positivo
        }
        
        @Test
        @DisplayName("should return 400 when price is null")
        void create_NullPrice_Returns400() throws Exception {
            // TODO: Implementar
        }
    }
    
    // ====================================================
    // PUT /api/products/{id} Tests
    // ====================================================
    @Nested
    @DisplayName("PUT /api/products/{id}")
    class UpdateTests {
        
        @Test
        @DisplayName("should update product with valid data")
        void update_ValidRequest_Returns200() throws Exception {
            // TODO: Implementar este test
            CreateProductRequest request = new CreateProductRequest(
                "Updated", "Desc", "Cat", 150.0, 25
            );
            
            when(productService.update(eq(1L), any())).thenReturn(sampleProduct);
            
            // mockMvc.perform(put("/api/products/1")
            //         .contentType(MediaType.APPLICATION_JSON)
            //         .content(objectMapper.writeValueAsString(request)))
            //     .andExpect(status().isOk());
        }
        
        @Test
        @DisplayName("should return 404 when product not found")
        void update_NonExistingId_Returns404() throws Exception {
            // TODO: Implementar
        }
        
        @Test
        @DisplayName("should return 400 with invalid data")
        void update_InvalidRequest_Returns400() throws Exception {
            // TODO: Implementar
        }
    }
    
    // ====================================================
    // DELETE /api/products/{id} Tests
    // ====================================================
    @Nested
    @DisplayName("DELETE /api/products/{id}")
    class DeleteTests {
        
        @Test
        @DisplayName("should delete product and return 204")
        void delete_ExistingProduct_Returns204() throws Exception {
            // TODO: Implementar este test
            doNothing().when(productService).delete(1L);
            
            // mockMvc.perform(delete("/api/products/1"))
            //     .andExpect(status().isNoContent());
            
            // verify(productService).delete(1L);
        }
        
        @Test
        @DisplayName("should return 404 when product not found")
        void delete_NonExistingProduct_Returns404() throws Exception {
            // TODO: Implementar
        }
    }
    
    // ====================================================
    // GET /api/products/category/{category} Tests
    // ====================================================
    @Nested
    @DisplayName("GET /api/products/category/{category}")
    class GetByCategoryTests {
        
        @Test
        @DisplayName("should return products in category")
        void getByCategory_ReturnsProducts() throws Exception {
            // TODO: Implementar
        }
    }
    
    // ====================================================
    // GET /api/products/price-range Tests
    // ====================================================
    @Nested
    @DisplayName("GET /api/products/price-range")
    class GetByPriceRangeTests {
        
        @Test
        @DisplayName("should return products in price range")
        void getByPriceRange_ValidRange_ReturnsProducts() throws Exception {
            // TODO: Implementar
            when(productService.findByPriceRange(50.0, 150.0))
                .thenReturn(List.of(sampleProduct));
            
            // mockMvc.perform(get("/api/products/price-range")
            //         .param("minPrice", "50.0")
            //         .param("maxPrice", "150.0"))
            //     .andExpect(status().isOk())
            //     .andExpect(jsonPath("$").isArray())
            //     .andExpect(jsonPath("$[0].price").value(99.99));
        }
    }
}
