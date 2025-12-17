package com.bootcamp.finalproject.auth;

import com.bootcamp.finalproject.auth.dto.LoginRequest;
import com.bootcamp.finalproject.auth.dto.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests de integración para AuthController.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterRequest validRegisterRequest;

    @BeforeEach
    void setUp() {
        validRegisterRequest = new RegisterRequest(
            "Test User",
            "test" + System.currentTimeMillis() + "@example.com",
            "password123"
        );
    }

    @Test
    @DisplayName("POST /api/auth/register - Registro exitoso")
    void register_Success() throws Exception {
        // Act
        ResultActions result = mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRegisterRequest)));

        // Assert
        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.token", notNullValue()))
            .andExpect(jsonPath("$.type", is("Bearer")))
            .andExpect(jsonPath("$.email", is(validRegisterRequest.email())))
            .andExpect(jsonPath("$.name", is(validRegisterRequest.name())))
            .andExpect(jsonPath("$.role", is("USER")));
    }

    @Test
    @DisplayName("POST /api/auth/register - Error con email inválido")
    void register_InvalidEmail() throws Exception {
        // Arrange
        RegisterRequest invalidRequest = new RegisterRequest(
            "Test User",
            "invalid-email",
            "password123"
        );

        // Act
        ResultActions result = mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)));

        // Assert
        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is("VALIDATION_ERROR")))
            .andExpect(jsonPath("$.errors", hasSize(1)))
            .andExpect(jsonPath("$.errors[0].field", is("email")));
    }

    @Test
    @DisplayName("POST /api/auth/register - Error con datos vacíos")
    void register_EmptyData() throws Exception {
        // Arrange
        RegisterRequest emptyRequest = new RegisterRequest("", "", "");

        // Act
        ResultActions result = mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(emptyRequest)));

        // Assert
        result.andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", is("VALIDATION_ERROR")))
            .andExpect(jsonPath("$.errors", hasSize(greaterThanOrEqualTo(3))));
    }

    @Test
    @DisplayName("POST /api/auth/login - Login exitoso")
    void login_Success() throws Exception {
        // Arrange - Primero registrar usuario
        mockMvc.perform(post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRegisterRequest)));

        LoginRequest loginRequest = new LoginRequest(
            validRegisterRequest.email(),
            validRegisterRequest.password()
        );

        // Act
        ResultActions result = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginRequest)));

        // Assert
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.token", notNullValue()))
            .andExpect(jsonPath("$.type", is("Bearer")))
            .andExpect(jsonPath("$.email", is(validRegisterRequest.email())));
    }

    @Test
    @DisplayName("POST /api/auth/login - Credenciales inválidas")
    void login_InvalidCredentials() throws Exception {
        // Arrange
        LoginRequest invalidRequest = new LoginRequest(
            "nonexistent@example.com",
            "wrongpassword"
        );

        // Act
        ResultActions result = mockMvc.perform(post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(invalidRequest)));

        // Assert
        result.andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.code", is("INVALID_CREDENTIALS")));
    }
}
