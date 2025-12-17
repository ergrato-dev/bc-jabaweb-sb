# 🎫 Práctica 02: Implementar JwtService

## Objetivo

Crear el servicio de JWT para generar, validar y extraer información de tokens.

**Duración estimada**: 45 minutos

---

## Requisitos Previos

- Práctica 01 completada (Spring Security configurado)
- Dependencias jjwt agregadas

---

## Paso 1: Configurar Propiedades JWT

### 1.1 application.properties

```properties
# JWT Configuration
jwt.secret=${JWT_SECRET:dG9rZW5TZWNyZXRvTXV5TGFyZ29ZU2VndXJvUGFyYUpXVDI1NmJpdHMhIQ==}
jwt.expiration=86400000
jwt.refresh-expiration=604800000
```

### 1.2 .env (para Docker)

```env
JWT_SECRET=dG9rZW5TZWNyZXRvTXV5TGFyZ29ZU2VndXJvUGFyYUpXVDI1NmJpdHMhIQ==
```

### 1.3 Generar secret key seguro

```bash
# Genera clave de 256 bits en Base64
openssl rand -base64 32
```

---

## Paso 2: Crear JwtService

`src/main/java/com/bootcamp/security/service/JwtService.java`

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

    // ========== GENERACIÓN ==========

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(Map<String, Object> extraClaims,
                             UserDetails userDetails,
                             long expiration) {
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

    // ========== VALIDACIÓN ==========

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ========== EXTRACCIÓN ==========

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    // ========== UTILIDADES ==========

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public long getJwtExpiration() {
        return jwtExpiration;
    }
}
```

---

## Paso 3: Crear Test Unitario

`src/test/java/com/bootcamp/security/service/JwtServiceTest.java`

```java
package com.bootcamp.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // Configurar propiedades via reflection
        ReflectionTestUtils.setField(jwtService, "secretKey",
            "dG9rZW5TZWNyZXRvTXV5TGFyZ29ZU2VndXJvUGFyYUpXVDI1NmJpdHMhIQ==");
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86400000L);
        ReflectionTestUtils.setField(jwtService, "refreshExpiration", 604800000L);

        // Crear usuario de prueba
        userDetails = new User(
            "testuser",
            "password",
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    void generateToken_ShouldCreateValidToken() {
        // Act
        String token = jwtService.generateToken(new java.util.HashMap<>(), userDetails);

        // Assert
        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3); // Header.Payload.Signature
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        // Arrange
        String token = jwtService.generateToken(new java.util.HashMap<>(), userDetails);

        // Act
        String username = jwtService.extractUsername(token);

        // Assert
        assertEquals("testuser", username);
    }

    @Test
    void isTokenValid_WithValidToken_ShouldReturnTrue() {
        // Arrange
        String token = jwtService.generateToken(new java.util.HashMap<>(), userDetails);

        // Act
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void isTokenValid_WithWrongUser_ShouldReturnFalse() {
        // Arrange
        String token = jwtService.generateToken(new java.util.HashMap<>(), userDetails);
        UserDetails otherUser = new User("otheruser", "pass", List.of());

        // Act
        boolean isValid = jwtService.isTokenValid(token, otherUser);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void extractRoles_ShouldReturnRoles() {
        // Arrange
        String token = jwtService.generateToken(new java.util.HashMap<>(), userDetails);

        // Act
        String roles = jwtService.extractRoles(token);

        // Assert
        assertEquals("ROLE_USER", roles);
    }
}
```

---

## Paso 4: Ejecutar Tests

```bash
./mvnw test -Dtest=JwtServiceTest
```

Salida esperada:
```
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## Paso 5: Crear JwtAuthenticationFilter

`src/main/java/com/bootcamp/security/filter/JwtAuthenticationFilter.java`

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

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String username = jwtService.extractUsername(jwt);

            if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                        );

                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            logger.error("Error validando JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
```

---

## Paso 6: Registrar Filtro en SecurityConfig

Actualiza `SecurityConfig.java`:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                         JwtAuthenticationFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            // Agregar filtro JWT antes del filtro de autenticación
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    // ... resto de beans
}
```

---

## Verificación ✅

- [ ] JwtService creado con métodos de generación y validación
- [ ] Propiedades JWT configuradas en application.properties
- [ ] Tests unitarios pasando
- [ ] JwtAuthenticationFilter creado
- [ ] Filtro registrado en SecurityFilterChain
- [ ] Token se genera correctamente (3 partes separadas por puntos)

---

## Próximos Pasos

→ [03-endpoints-autenticacion.md](03-endpoints-autenticacion.md)
