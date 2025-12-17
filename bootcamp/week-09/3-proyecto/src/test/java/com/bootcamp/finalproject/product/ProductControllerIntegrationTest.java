package com.bootcamp.finalproject.product;

import com.bootcamp.finalproject.category.Category;
import com.bootcamp.finalproject.category.CategoryRepository;
import com.bootcamp.finalproject.product.dto.ProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para ProductController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory;

    @BeforeEach
    void setUp() {
        testCategory = new Category("Test Category " + System.currentTimeMillis(), "Test");
        testCategory = categoryRepository.save(testCategory);
    }

    @Test
    @DisplayName("GET /api/products - Listar productos sin autenticación")
    void findAll_NoAuth() throws Exception {
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content", isA(java.util.List.class)));
    }

    @Test
    @DisplayName("GET /api/products/search - Buscar productos")
    void search_NoAuth() throws Exception {
        mockMvc.perform(get("/api/products/search")
            .param("q", "test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(java.util.List.class)));
    }

    @Test
    @DisplayName("POST /api/products - Crear producto como ADMIN")
    @WithMockUser(roles = "ADMIN")
    void create_AsAdmin() throws Exception {
        // Arrange
        ProductRequest request = new ProductRequest(
            "Producto Test",
            "Descripción del producto",
            new BigDecimal("99.99"),
            100,
            "https://example.com/image.jpg",
            testCategory.getId()
        );

        // Act & Assert
        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.name", is(request.name())))
            .andExpect(jsonPath("$.price", is(99.99)))
            .andExpect(jsonPath("$.stock", is(100)));
    }

    @Test
    @DisplayName("POST /api/products - Validación de precio negativo")
    @WithMockUser(roles = "ADMIN")
    void create_InvalidPrice() throws Exception {
        // Arrange
        ProductRequest request = new ProductRequest(
            "Producto Test",
            "Descripción",
            new BigDecimal("-10.00"),
            100,
            null,
            testCategory.getId()
        );

        // Act & Assert
        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is("VALIDATION_ERROR")));
    }

    @Test
    @DisplayName("POST /api/products - Categoría inexistente")
    @WithMockUser(roles = "ADMIN")
    void create_CategoryNotFound() throws Exception {
        // Arrange
        ProductRequest request = new ProductRequest(
            "Producto Test",
            "Descripción",
            new BigDecimal("99.99"),
            100,
            null,
            99999L // ID inexistente
        );

        // Act & Assert
        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound());
    }
}
