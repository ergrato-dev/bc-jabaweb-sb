# Práctica 03: Tests de Seguridad JWT

## 🎯 Objetivos

- Escribir tests para endpoints protegidos
- Usar @WithMockUser para simular usuarios
- Testear autenticación y autorización
- Verificar comportamiento con y sin token

## ⏱️ Duración: 35 minutos

---

## 📋 Prerrequisitos

- Spring Security configurado (Week-07)
- JWT implementado
- Endpoints protegidos con @PreAuthorize

---

## 🔧 Paso 1: Configuración

### 1.1 Dependencias

```xml
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 1.2 Crear AuthControllerTest

```java
package com.bootcamp.integration.controller;

import com.bootcamp.security.controller.AuthController;
import com.bootcamp.security.dto.AuthResponse;
import com.bootcamp.security.dto.LoginRequest;
import com.bootcamp.security.dto.RegisterRequest;
import com.bootcamp.security.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(TestSecurityConfig.class)  // Configuración de seguridad para tests
@DisplayName("AuthController Security Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    // TODO: Implementar tests
}
```

### 1.3 TestSecurityConfig (Opcional)

```java
package com.bootcamp.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }
}
```

---

## 📝 Paso 2: Tests de Registro

```java
@Nested
@DisplayName("POST /api/auth/register")
class RegisterTests {

    @Test
    @DisplayName("should register user with valid data")
    void register_ValidData_Returns201() throws Exception {
        // TODO: Implementar
        // Given
        RegisterRequest request = new RegisterRequest(
            "newuser@email.com",
            "New User",
            "SecurePass123!"
        );
        AuthResponse response = new AuthResponse(
            "jwt.token.here",
            "Bearer",
            3600L
        );

        when(authService.register(any(RegisterRequest.class)))
            .thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.tokenType").value("Bearer"));

        verify(authService).register(any(RegisterRequest.class));
    }

    @Test
    @DisplayName("should return 400 when email is invalid")
    void register_InvalidEmail_Returns400() throws Exception {
        // TODO: Implementar
        String invalidRequest = """
            {
                "email": "not-an-email",
                "name": "Test User",
                "password": "SecurePass123!"
            }
            """;

        // mockMvc.perform(...)
        //     .andExpect(status().isBadRequest())
        //     .andExpect(jsonPath("$.errors.email").exists());
    }

    @Test
    @DisplayName("should return 400 when password is weak")
    void register_WeakPassword_Returns400() throws Exception {
        // TODO: Implementar
        // Contraseña muy corta o sin requisitos de complejidad
    }

    @Test
    @DisplayName("should return 409 when email already exists")
    void register_DuplicateEmail_Returns409() throws Exception {
        // TODO: Implementar
        // Given
        // when(authService.register(any()))
        //     .thenThrow(new DuplicateResourceException("Email already registered"));
    }
}
```

---

## 📝 Paso 3: Tests de Login

```java
@Nested
@DisplayName("POST /api/auth/login")
class LoginTests {

    @Test
    @DisplayName("should login with valid credentials")
    void login_ValidCredentials_Returns200WithToken() throws Exception {
        // TODO: Implementar
        // Given
        LoginRequest request = new LoginRequest(
            "user@email.com",
            "correctPassword"
        );
        AuthResponse response = new AuthResponse(
            "jwt.token.here",
            "Bearer",
            3600L
        );

        when(authService.login(any(LoginRequest.class)))
            .thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("jwt.token.here"))
            .andExpect(jsonPath("$.expiresIn").value(3600));
    }

    @Test
    @DisplayName("should return 401 with invalid credentials")
    void login_InvalidCredentials_Returns401() throws Exception {
        // TODO: Implementar
        // Given
        LoginRequest request = new LoginRequest(
            "user@email.com",
            "wrongPassword"
        );

        when(authService.login(any(LoginRequest.class)))
            .thenThrow(new BadCredentialsException("Invalid credentials"));

        // When & Then
        // mockMvc.perform(...)
        //     .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("should return 400 when email is missing")
    void login_MissingEmail_Returns400() throws Exception {
        // TODO: Implementar
    }
}
```

---

## 📝 Paso 4: Tests de Endpoints Protegidos

### 4.1 Crear SecuredEndpointTest

```java
package com.bootcamp.integration.controller;

import com.bootcamp.product.controller.ProductController;
import com.bootcamp.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@DisplayName("Secured Endpoints Tests")
class SecuredEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Nested
    @DisplayName("Without Authentication")
    class WithoutAuthentication {

        @Test
        @WithAnonymousUser
        @DisplayName("should return 401 for protected endpoints")
        void protectedEndpoint_NoAuth_Returns401() throws Exception {
            // TODO: Implementar
            mockMvc.perform(get("/api/products"))
                .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("should allow access to public endpoints")
        void publicEndpoint_NoAuth_Returns200() throws Exception {
            // TODO: Implementar (si hay endpoints públicos)
            // mockMvc.perform(get("/api/public/products"))
            //     .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("With USER Role")
    class WithUserRole {

        @Test
        @WithMockUser(roles = "USER")
        @DisplayName("should allow USER to read products")
        void getProducts_AsUser_Returns200() throws Exception {
            // TODO: Implementar
            mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(roles = "USER")
        @DisplayName("should deny USER from deleting products")
        void deleteProduct_AsUser_Returns403() throws Exception {
            // TODO: Implementar
            mockMvc.perform(delete("/api/products/{id}", 1))
                .andExpect(status().isForbidden());
        }
    }

    @Nested
    @DisplayName("With ADMIN Role")
    class WithAdminRole {

        @Test
        @WithMockUser(roles = "ADMIN")
        @DisplayName("should allow ADMIN to delete products")
        void deleteProduct_AsAdmin_Returns204() throws Exception {
            // TODO: Implementar
            mockMvc.perform(delete("/api/products/{id}", 1))
                .andExpect(status().isNoContent());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        @DisplayName("should allow ADMIN to create products")
        void createProduct_AsAdmin_Returns201() throws Exception {
            // TODO: Implementar
        }
    }
}
```

---

## 📝 Paso 5: Tests con JWT Real

### 5.1 Test con Token Generado

```java
package com.bootcamp.integration.security;

import com.bootcamp.security.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("JWT Integration Tests")
class JwtIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    private String validToken;
    private String expiredToken;

    @BeforeEach
    void setUp() {
        // Generar token válido
        UserDetails userDetails = User.builder()
            .username("testuser@email.com")
            .password("password")
            .authorities("ROLE_USER")
            .build();

        validToken = jwtService.generateToken(userDetails);

        // TODO: Generar token expirado para tests
    }

    @Test
    @DisplayName("should access protected endpoint with valid token")
    void protectedEndpoint_ValidToken_Returns200() throws Exception {
        // TODO: Implementar
        mockMvc.perform(get("/api/products")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + validToken))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should return 401 with expired token")
    void protectedEndpoint_ExpiredToken_Returns401() throws Exception {
        // TODO: Implementar
        // mockMvc.perform(get("/api/products")
        //         .header(HttpHeaders.AUTHORIZATION, "Bearer " + expiredToken))
        //     .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("should return 401 with malformed token")
    void protectedEndpoint_MalformedToken_Returns401() throws Exception {
        // TODO: Implementar
        mockMvc.perform(get("/api/products")
                .header(HttpHeaders.AUTHORIZATION, "Bearer invalid.token.here"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("should return 401 when token is missing")
    void protectedEndpoint_NoToken_Returns401() throws Exception {
        // TODO: Implementar
        mockMvc.perform(get("/api/products"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("should return 401 with wrong token type")
    void protectedEndpoint_WrongTokenType_Returns401() throws Exception {
        // TODO: Implementar
        mockMvc.perform(get("/api/products")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + validToken))
            .andExpect(status().isUnauthorized());
    }
}
```

---

## 📝 Paso 6: Tests de Ownership

```java
@Nested
@DisplayName("Resource Ownership Tests")
class OwnershipTests {

    @Test
    @WithMockUser(username = "user1@email.com", roles = "USER")
    @DisplayName("should allow user to access own profile")
    void getProfile_OwnProfile_Returns200() throws Exception {
        // TODO: Implementar
        // Endpoint: GET /api/users/me
    }

    @Test
    @WithMockUser(username = "user1@email.com", roles = "USER")
    @DisplayName("should deny user from accessing other user's data")
    void getUser_OtherUser_Returns403() throws Exception {
        // TODO: Implementar
        // Endpoint: GET /api/users/{id} donde id != user actual
    }

    @Test
    @WithMockUser(username = "admin@email.com", roles = "ADMIN")
    @DisplayName("should allow admin to access any user's data")
    void getUser_AsAdmin_Returns200() throws Exception {
        // TODO: Implementar
    }
}
```

---

## ✅ Criterios de Éxito

- [ ] Tests cubren endpoints de auth (register, login)
- [ ] Tests verifican acceso sin autenticación (401)
- [ ] Tests verifican acceso sin autorización (403)
- [ ] Tests con @WithMockUser para diferentes roles
- [ ] Tests con token JWT real
- [ ] Tests de ownership funcionan

---

## 🚀 Ejecutar Tests

```bash
# Ejecutar tests de seguridad
mvn test -Dtest="**/security/**"

# Ejecutar tests de auth controller
mvn test -Dtest=AuthControllerTest

# Ejecutar con logs de Spring Security
mvn test -Dtest=AuthControllerTest -Dlogging.level.org.springframework.security=DEBUG
```

---

## 💡 Tips

### Anotaciones útiles de Spring Security Test

```java
// Usuario mock básico
@WithMockUser
@WithMockUser(username = "user", roles = "USER")
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})

// Usuario anónimo
@WithAnonymousUser

// Usuario de base de datos
@WithUserDetails("username")
@WithUserDetails(value = "admin", userDetailsServiceBeanName = "customUserDetailsService")
```

### Configurar autenticación en request

```java
// Con Spring Security Test
mockMvc.perform(get("/api/endpoint")
    .with(user("testuser").roles("USER")))

// Con JWT manual
mockMvc.perform(get("/api/endpoint")
    .header("Authorization", "Bearer " + token))

// Con CSRF (si no está deshabilitado)
mockMvc.perform(post("/api/endpoint")
    .with(csrf()))
```
