# 🔐 Práctica 03: Endpoints de Autenticación

## Objetivo

Implementar los endpoints de registro, login y refresh token.

**Duración estimada**: 60 minutos

---

## Requisitos Previos

- Práctica 02 completada (JwtService funcionando)
- Base de datos PostgreSQL corriendo

---

## Paso 1: Crear DTOs

### 1.1 LoginRequest

`src/main/java/com/bootcamp/security/dto/LoginRequest.java`

```java
package com.bootcamp.security.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

### 1.2 RegisterRequest

`src/main/java/com/bootcamp/security/dto/RegisterRequest.java`

```java
package com.bootcamp.security.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50, message = "Username debe tener entre 3 y 50 caracteres")
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email debe ser válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
        message = "La contraseña debe contener mayúsculas, minúsculas y números"
    )
    private String password;

    public RegisterRequest() {}

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
```

### 1.3 AuthResponse

`src/main/java/com/bootcamp/security/dto/AuthResponse.java`

```java
package com.bootcamp.security.dto;

public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private long expiresIn;

    public AuthResponse() {}

    public AuthResponse(String accessToken, String refreshToken, long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
}
```

### 1.4 RefreshTokenRequest

`src/main/java/com/bootcamp/security/dto/RefreshTokenRequest.java`

```java
package com.bootcamp.security.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {

    @NotBlank(message = "El refresh token es obligatorio")
    private String refreshToken;

    public RefreshTokenRequest() {}

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
```

### 1.5 UserDTO

`src/main/java/com/bootcamp/security/dto/UserDTO.java`

```java
package com.bootcamp.security.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDTO {

    private UUID id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime createdAt;

    public UserDTO() {}

    public UserDTO(UUID id, String username, String email, String role, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
```

---

## Paso 2: Crear Excepciones

### 2.1 DuplicateResourceException

`src/main/java/com/bootcamp/security/exception/DuplicateResourceException.java`

```java
package com.bootcamp.security.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
```

### 2.2 InvalidTokenException

`src/main/java/com/bootcamp/security/exception/InvalidTokenException.java`

```java
package com.bootcamp.security.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
```

---

## Paso 3: Crear AuthService

`src/main/java/com/bootcamp/security/service/AuthService.java`

```java
package com.bootcamp.security.service;

import com.bootcamp.security.dto.*;
import com.bootcamp.security.entity.Role;
import com.bootcamp.security.entity.User;
import com.bootcamp.security.exception.DuplicateResourceException;
import com.bootcamp.security.exception.InvalidTokenException;
import com.bootcamp.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public UserDTO register(RegisterRequest request) {
        // Validar username único
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("El username ya está en uso");
        }

        // Validar email único
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("El email ya está registrado");
        }

        // Crear usuario
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return toDTO(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        // Autenticar con Spring Security
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Generar tokens
        String accessToken = jwtService.generateToken(authentication);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new AuthResponse(
            accessToken,
            refreshToken,
            jwtService.getJwtExpiration()
        );
    }

    public AuthResponse refreshToken(String refreshToken) {
        try {
            // Extraer username del refresh token
            String username = jwtService.extractUsername(refreshToken);

            // Cargar usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validar refresh token
            if (!jwtService.isTokenValid(refreshToken, userDetails)) {
                throw new InvalidTokenException("Refresh token inválido o expirado");
            }

            // Generar nuevo access token
            String newAccessToken = jwtService.generateToken(new HashMap<>(), userDetails);

            return new AuthResponse(
                newAccessToken,
                refreshToken,  // Mantener el mismo refresh token
                jwtService.getJwtExpiration()
            );
        } catch (Exception e) {
            throw new InvalidTokenException("Error procesando refresh token: " + e.getMessage());
        }
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name(),
            user.getCreatedAt()
        );
    }
}
```

---

## Paso 4: Crear AuthController

`src/main/java/com/bootcamp/security/controller/AuthController.java`

```java
package com.bootcamp.security.controller;

import com.bootcamp.security.dto.*;
import com.bootcamp.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación y registro")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "Username o email ya existe")
    })
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request) {
        UserDTO user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar access token")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token renovado"),
        @ApiResponse(responseCode = "401", description = "Refresh token inválido")
    })
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
```

---

## Paso 5: Crear Exception Handler

`src/main/java/com/bootcamp/security/exception/SecurityExceptionHandler.java`

```java
package com.bootcamp.security.exception;

import com.bootcamp.exception.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Unauthorized",
            "Credenciales inválidas",
            "/api/auth/login"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(DuplicateResourceException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Conflict",
            ex.getMessage(),
            "/api/auth/register"
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(InvalidTokenException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Invalid Token",
            ex.getMessage(),
            null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Token Expired",
            "El token ha expirado",
            null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
    public ResponseEntity<ErrorResponse> handleInvalidJwt(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(),
            "Invalid Token",
            "El token no es válido",
            null
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
```

---

## Paso 6: Probar Endpoints

### 6.1 Iniciar aplicación

```bash
docker-compose up -d
./mvnw spring-boot:run
```

### 6.2 Registrar usuario

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "email": "john@example.com",
    "password": "Password123"
  }'
```

Respuesta esperada:
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "username": "john",
  "email": "john@example.com",
  "role": "USER",
  "createdAt": "2024-12-17T10:00:00"
}
```

### 6.3 Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "password": "Password123"
  }'
```

Respuesta esperada:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 86400000
}
```

### 6.4 Acceder a endpoint protegido

```bash
# Sin token (401)
curl -v http://localhost:8080/api/tasks

# Con token (200)
curl http://localhost:8080/api/tasks \
  -H "Authorization: Bearer <tu_access_token>"
```

### 6.5 Refresh token

```bash
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "<tu_refresh_token>"
  }'
```

---

## Verificación ✅

- [ ] DTOs creados con validaciones
- [ ] AuthService implementado
- [ ] AuthController con 3 endpoints
- [ ] Exception handlers funcionando
- [ ] Registro crea usuario con password hasheado
- [ ] Login retorna tokens JWT válidos
- [ ] Refresh token renueva access token
- [ ] Endpoints protegidos rechazan sin token (401)
- [ ] Endpoints protegidos aceptan con token válido (200)

---

## Próximos Pasos

→ [04-proteger-endpoints-roles.md](04-proteger-endpoints-roles.md)
