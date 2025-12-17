package com.bootcamp.finalproject.category;

import com.bootcamp.finalproject.category.dto.CategoryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para CategoryController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/categories - Listar categorías sin autenticación")
    void findAll_NoAuth() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", isA(java.util.List.class)));
    }

    @Test
    @DisplayName("POST /api/categories - Crear categoría como ADMIN")
    @WithMockUser(roles = "ADMIN")
    void create_AsAdmin() throws Exception {
        // Arrange
        CategoryRequest request = new CategoryRequest(
            "Categoría Test " + System.currentTimeMillis(),
            "Descripción de prueba"
        );

        // Act
        ResultActions result = mockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)));

        // Assert
        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.name", is(request.name())))
            .andExpect(jsonPath("$.description", is(request.description())))
            .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    @DisplayName("POST /api/categories - Crear categoría como USER (Forbidden)")
    @WithMockUser(roles = "USER")
    void create_AsUser_Forbidden() throws Exception {
        // Arrange
        CategoryRequest request = new CategoryRequest(
            "Categoría Test",
            "Descripción"
        );

        // Act & Assert
        mockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /api/categories - Validación de datos")
    @WithMockUser(roles = "ADMIN")
    void create_ValidationError() throws Exception {
        // Arrange - nombre vacío
        CategoryRequest request = new CategoryRequest("", "Descripción");

        // Act & Assert
        mockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is("VALIDATION_ERROR")));
    }
}
