# 🧪 Práctica 05: Testing de Seguridad

## Objetivo

Escribir tests para verificar que la autenticación y autorización funcionan correctamente.

**Duración estimada**: 45 minutos

---

## Requisitos Previos

- Práctica 04 completada
- Dependencia `spring-security-test` en pom.xml

---

## Paso 1: Configuración de Tests

### 1.1 Crear clase base para tests de seguridad

`src/test/java/com/bootcamp/security/BaseSecurityTest.java`

```java
package com.bootcamp.security;

import com.bootcamp.security.entity.Role;
import com.bootcamp.security.entity.User;
import com.bootcamp.security.repository.UserRepository;
import com.bootcamp.security.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseSecurityTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtService jwtService;

    protected User testUser;
    protected User testAdmin;
    protected String userToken;
    protected String adminToken;

    @BeforeEach
    void setUpUsers() {
        // Limpiar usuarios de prueba
        userRepository.findByUsername("testuser").ifPresent(userRepository::delete);
        userRepository.findByUsername("testadmin").ifPresent(userRepository::delete);

        // Crear usuario de prueba
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@test.com");
        testUser.setPassword(passwordEncoder.encode("Password123"));
        testUser.setRole(Role.USER);
        testUser = userRepository.save(testUser);

        // Crear admin de prueba
        testAdmin = new User();
        testAdmin.setUsername("testadmin");
        testAdmin.setEmail("testadmin@test.com");
        testAdmin.setPassword(passwordEncoder.encode("Password123"));
        testAdmin.setRole(Role.ADMIN);
        testAdmin = userRepository.save(testAdmin);

        // Generar tokens
        userToken = jwtService.generateToken(new HashMap<>(), testUser);
        adminToken = jwtService.generateToken(new HashMap<>(), testAdmin);
    }
}
```

---

## Paso 2: Tests de Autenticación

`src/test/java/com/bootcamp/security/AuthControllerTest.java`

```java
package com.bootcamp.security;

import com.bootcamp.security.dto.LoginRequest;
import com.bootcamp.security.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest extends BaseSecurityTest {

    @Test
    void register_WithValidData_ShouldReturn201() throws Exception {
        RegisterRequest request = new RegisterRequest(
            "newuser",
            "newuser@test.com",
            "Password123"
        );

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username", is("newuser")))
            .andExpect(jsonPath("$.email", is("newuser@test.com")))
            .andExpect(jsonPath("$.role", is("USER")))
            .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void register_WithExistingUsername_ShouldReturn409() throws Exception {
        RegisterRequest request = new RegisterRequest(
            "testuser",  // Ya existe
            "another@test.com",
            "Password123"
        );

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isConflict());
    }

    @Test
    void register_WithInvalidPassword_ShouldReturn400() throws Exception {
        RegisterRequest request = new RegisterRequest(
            "validuser",
            "valid@test.com",
            "weak"  // Password muy corta y sin requisitos
        );

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void login_WithValidCredentials_ShouldReturnTokens() throws Exception {
        LoginRequest request = new LoginRequest("testuser", "Password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken", notNullValue()))
            .andExpect(jsonPath("$.refreshToken", notNullValue()))
            .andExpect(jsonPath("$.tokenType", is("Bearer")))
            .andExpect(jsonPath("$.expiresIn", greaterThan(0)));
    }

    @Test
    void login_WithInvalidPassword_ShouldReturn401() throws Exception {
        LoginRequest request = new LoginRequest("testuser", "WrongPassword");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void login_WithNonExistentUser_ShouldReturn401() throws Exception {
        LoginRequest request = new LoginRequest("nonexistent", "Password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized());
    }
}
```

---

## Paso 3: Tests de Autorización

`src/test/java/com/bootcamp/security/AuthorizationTest.java`

```java
package com.bootcamp.security;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthorizationTest extends BaseSecurityTest {

    // ========== Tests sin autenticación ==========

    @Test
    void accessProtectedEndpoint_WithoutToken_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void accessPublicEndpoint_WithoutToken_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
            .andExpect(status().isOk());
    }

    // ========== Tests con USER ==========

    @Test
    void getUsers_AsUser_ShouldReturn403() throws Exception {
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + userToken))
            .andExpect(status().isForbidden());
    }

    @Test
    void getCurrentUser_AsUser_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users/me")
                .header("Authorization", "Bearer " + userToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getOwnProfile_AsUser_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users/" + testUser.getId())
                .header("Authorization", "Bearer " + userToken))
            .andExpect(status().isOk());
    }

    @Test
    void getOtherUserProfile_AsUser_ShouldReturn403() throws Exception {
        mockMvc.perform(get("/api/users/" + testAdmin.getId())
                .header("Authorization", "Bearer " + userToken))
            .andExpect(status().isForbidden());
    }

    @Test
    void deleteUser_AsUser_ShouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/users/" + testAdmin.getId())
                .header("Authorization", "Bearer " + userToken))
            .andExpect(status().isForbidden());
    }

    // ========== Tests con ADMIN ==========

    @Test
    void getUsers_AsAdmin_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk());
    }

    @Test
    void getAnyUserProfile_AsAdmin_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users/" + testUser.getId())
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isOk());
    }

    @Test
    void deleteUser_AsAdmin_ShouldReturn204() throws Exception {
        // Crear usuario temporal para eliminar
        var tempUser = new com.bootcamp.security.entity.User();
        tempUser.setUsername("tempuser");
        tempUser.setEmail("temp@test.com");
        tempUser.setPassword(passwordEncoder.encode("Password123"));
        tempUser.setRole(com.bootcamp.security.entity.Role.USER);
        tempUser = userRepository.save(tempUser);

        mockMvc.perform(delete("/api/users/" + tempUser.getId())
                .header("Authorization", "Bearer " + adminToken))
            .andExpect(status().isNoContent());
    }

    // ========== Tests con token inválido ==========

    @Test
    void accessEndpoint_WithInvalidToken_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/users/me")
                .header("Authorization", "Bearer invalid.token.here"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void accessEndpoint_WithMalformedHeader_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/users/me")
                .header("Authorization", "InvalidFormat " + userToken))
            .andExpect(status().isUnauthorized());
    }
}
```

---

## Paso 4: Tests con @WithMockUser

`src/test/java/com/bootcamp/security/MockUserTest.java`

```java
package com.bootcamp.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MockUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void protectedEndpoint_WithAnonymous_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/users/me"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    void usersEndpoint_WithUser_ShouldReturn403() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void usersEndpoint_WithAdmin_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "john", roles = "USER")
    void meEndpoint_WithMockUser_ShouldReturn200() throws Exception {
        // Nota: Este test puede fallar si el usuario "john" no existe en BD
        // Es mejor usar tokens reales para tests de integración completos
        mockMvc.perform(get("/api/users/me"))
            .andExpect(status().isOk());
    }
}
```

---

## Paso 5: Tests de JwtService

`src/test/java/com/bootcamp/security/service/JwtServiceIntegrationTest.java`

```java
package com.bootcamp.security.service;

import com.bootcamp.security.entity.Role;
import com.bootcamp.security.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtServiceIntegrationTest {

    @Autowired
    private JwtService jwtService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("jwttest");
        testUser.setEmail("jwttest@test.com");
        testUser.setPassword("encoded");
        testUser.setRole(Role.USER);
    }

    @Test
    void generateAndValidateToken_ShouldWork() {
        // Generate
        String token = jwtService.generateToken(new HashMap<>(), testUser);
        assertNotNull(token);

        // Validate
        assertTrue(jwtService.isTokenValid(token, testUser));

        // Extract
        assertEquals("jwttest", jwtService.extractUsername(token));
        assertTrue(jwtService.extractRoles(token).contains("ROLE_USER"));
    }

    @Test
    void validateToken_WithDifferentUser_ShouldReturnFalse() {
        String token = jwtService.generateToken(new HashMap<>(), testUser);

        User otherUser = new User();
        otherUser.setUsername("other");
        otherUser.setRole(Role.USER);

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    void generateRefreshToken_ShouldWork() {
        String refreshToken = jwtService.generateRefreshToken(testUser);
        assertNotNull(refreshToken);
        assertTrue(jwtService.isTokenValid(refreshToken, testUser));
    }
}
```

---

## Paso 6: Ejecutar Tests

```bash
# Ejecutar todos los tests de seguridad
./mvnw test -Dtest="*Security*,*Auth*,*Jwt*"

# Ejecutar con cobertura
./mvnw test jacoco:report
```

---

## Verificación ✅

- [ ] Tests de registro funcionando
- [ ] Tests de login funcionando
- [ ] Tests de acceso sin token (401)
- [ ] Tests de USER con acceso denegado (403)
- [ ] Tests de ADMIN con acceso permitido (200)
- [ ] Tests de token inválido (401)
- [ ] Tests con @WithMockUser
- [ ] Tests de JwtService
- [ ] Todos los tests pasando

---

## Resumen de Casos de Test

| Caso | Esperado |
|------|----------|
| Register válido | 201 Created |
| Register duplicado | 409 Conflict |
| Login correcto | 200 + tokens |
| Login incorrecto | 401 |
| Sin token | 401 |
| USER → /api/users | 403 |
| ADMIN → /api/users | 200 |
| USER → /api/users/me | 200 |
| Token inválido | 401 |

---

## Próximos Pasos

Continúa con el proyecto integrador de la semana.

→ [../3-proyecto/README.md](../3-proyecto/README.md)
