# 🔧 Implementación de JWT en Spring Boot

## Introducción

En esta sección implementaremos el servicio completo de JWT, incluyendo generación de tokens, validación y el filtro de autenticación que integra JWT con Spring Security.

---

## 1. JwtService - Servicio Principal

El `JwtService` es el componente central que maneja todas las operaciones relacionadas con JWT.

```java
package com.bootcamp.security.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    // ============ GENERACIÓN DE TOKENS ============

    /**
     * Genera un access token para el usuario autenticado.
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un access token con claims adicionales.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Genera un refresh token.
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    /**
     * Construye el token JWT con los claims especificados.
     */
    private String buildToken(Map<String, Object> extraClaims,
                             UserDetails userDetails,
                             long expiration) {
        // Agregar roles como claim
        String roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        extraClaims.put("roles", roles);

        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey(), Jwts.SIG.HS256)
            .compact();
    }

    // ============ VALIDACIÓN DE TOKENS ============

    /**
     * Valida si el token es válido para el usuario dado.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Verifica si el token ha expirado.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ============ EXTRACCIÓN DE CLAIMS ============

    /**
     * Extrae el username (subject) del token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae los roles del token.
     */
    public String extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", String.class));
    }

    /**
     * Extrae un claim específico usando una función resolver.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    // ============ UTILIDADES ============

    /**
     * Obtiene la clave de firma a partir del secreto configurado.
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtiene el tiempo de expiración del access token.
     */
    public long getJwtExpiration() {
        return jwtExpiration;
    }
}
```

---

## 2. Configuración en application.properties

```properties
# JWT Configuration
# Secret key en Base64 (generar con: openssl rand -base64 32)
jwt.secret=${JWT_SECRET:bWlDbGF2ZVNlY3JldGFNdXlMYXJnYVlTZWd1cmFQYXJhSldUMjU2Yml0cyE=}

# Access token expiration: 24 horas (en milisegundos)
jwt.expiration=86400000

# Refresh token expiration: 7 días (en milisegundos)
jwt.refresh-expiration=604800000
```

> 🔐 **IMPORTANTE**: En producción, usar variable de entorno `JWT_SECRET`.

### Generar Secret Key Seguro:

```bash
# Generar clave aleatoria de 256 bits en Base64
openssl rand -base64 32

# Resultado ejemplo:
# K7gNU3sdo+OL0wNhqoVWhr3g6s1xYv72ol/pe/Unols=
```

---

## 3. JwtAuthenticationFilter

El filtro que intercepta cada request y valida el JWT:

```java
package com.bootcamp.security.filter;

import com.bootcamp.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Obtener el header Authorization
        final String authHeader = request.getHeader("Authorization");

        // 2. Verificar que el header existe y tiene formato Bearer
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token (quitar "Bearer ")
        final String jwt = authHeader.substring(7);

        try {
            // 4. Extraer username del token
            final String username = jwtService.extractUsername(jwt);

            // 5. Si hay username y no hay autenticación previa
            if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

                // 6. Cargar usuario de la base de datos
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 7. Validar el token
                if (jwtService.isTokenValid(jwt, userDetails)) {

                    // 8. Crear objeto de autenticación
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );

                    // 9. Agregar detalles de la request
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // 10. Establecer autenticación en el SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token inválido - no establecer autenticación
            // El filtro de autorización retornará 401
            logger.error("Error validando JWT: " + e.getMessage());
        }

        // 11. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
```

### Flujo del Filtro

![Security Filter Chain](../0-assets/05-security-filter-chain.svg)

---

## 4. DTOs para Autenticación

### LoginRequest

```java
package com.bootcamp.security.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    // Constructors, Getters, Setters
}
```

### RegisterRequest

```java
package com.bootcamp.security.dto;

import jakarta.validation.constraints.*;

public class RegisterRequest {

    @NotBlank(message = "El username es obligatorio")
    @Size(min = 3, max = 50)
    private String username;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
        message = "La contraseña debe contener mayúsculas, minúsculas y números"
    )
    private String password;

    // Constructors, Getters, Setters
}
```

### AuthResponse

```java
package com.bootcamp.security.dto;

public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private long expiresIn;

    public AuthResponse(String accessToken, String refreshToken, long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    // Getters, Setters
}
```

---

## 5. AuthController

```java
package com.bootcamp.security.controller;

import com.bootcamp.security.dto.*;
import com.bootcamp.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request) {
        UserDTO user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        AuthResponse response = authService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
}
```

---

## 6. AuthService

```java
package com.bootcamp.security.service;

import com.bootcamp.security.dto.*;
import com.bootcamp.security.entity.Role;
import com.bootcamp.security.entity.User;
import com.bootcamp.security.exception.DuplicateResourceException;
import com.bootcamp.security.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // Verificar que no exista el username
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Username ya existe");
        }

        // Verificar que no exista el email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email ya existe");
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
        // Extraer username del refresh token
        String username = jwtService.extractUsername(refreshToken);

        // Cargar usuario
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Validar refresh token
        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new RuntimeException("Refresh token inválido");
        }

        // Generar nuevo access token
        String newAccessToken = jwtService.generateToken(
            new HashMap<>(), userDetails
        );

        return new AuthResponse(
            newAccessToken,
            refreshToken,  // Mantener el mismo refresh token
            jwtService.getJwtExpiration()
        );
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

## 7. Manejo de Excepciones JWT

```java
package com.bootcamp.security.exception;

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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(401, "Unauthorized",
                "Credenciales inválidas", "/api/auth/login"));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwt(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(401, "Token Expired",
                "El token ha expirado", null));
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSignature(SignatureException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(401, "Invalid Token",
                "La firma del token no es válida", null));
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwt(MalformedJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(401, "Malformed Token",
                "El formato del token no es válido", null));
    }
}
```

---

## 8. Testing con curl

```bash
# 1. Registrar usuario
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "email": "john@example.com",
    "password": "Password123"
  }'

# 2. Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "password": "Password123"
  }'

# Respuesta:
# {
#   "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
#   "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
#   "tokenType": "Bearer",
#   "expiresIn": 86400000
# }

# 3. Acceder a endpoint protegido
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 4. Refresh token
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{
    "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }'
```

---

## Resumen

| Componente | Responsabilidad |
|------------|-----------------|
| `JwtService` | Generar, validar y extraer claims de JWT |
| `JwtAuthenticationFilter` | Interceptar requests y validar tokens |
| `AuthController` | Endpoints de register, login, refresh |
| `AuthService` | Lógica de negocio de autenticación |

---

## Próximos Pasos

En la siguiente sección aprenderemos a **proteger endpoints con roles y @PreAuthorize**.

→ [05-proteccion-endpoints.md](05-proteccion-endpoints.md)
