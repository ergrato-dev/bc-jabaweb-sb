# ⚙️ Configuración de Spring Security

## Introducción

**Spring Security** es el framework de seguridad más utilizado en el ecosistema Spring. Proporciona autenticación, autorización y protección contra ataques comunes. En esta sección aprenderemos a configurarlo para una API REST con JWT.

![Arquitectura Spring Security](../0-assets/02-spring-security-architecture.svg)

---

## 1. Dependencias Maven

```xml
<!-- Spring Boot Starter Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT (JSON Web Tokens) -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<!-- Testing de Security -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 2. Comportamiento por Defecto

Al agregar `spring-boot-starter-security`, Spring Boot aplica automáticamente:

- ✅ Todos los endpoints requieren autenticación
- ✅ Formulario de login generado
- ✅ Usuario por defecto: `user`
- ✅ Contraseña generada en consola
- ✅ Protección CSRF habilitada

> ⚠️ Esta configuración por defecto **NO es adecuada para APIs REST**.

---

## 3. SecurityConfig Básico para APIs REST

```java
package com.bootcamp.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // 1. Deshabilitar CSRF (no necesario en APIs stateless)
            .csrf(csrf -> csrf.disable())

            // 2. Configurar política de sesiones como STATELESS
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 3. Configurar autorización de endpoints
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )

            .build();
    }
}
```

### Explicación de cada configuración:

| Configuración | Propósito |
|---------------|-----------|
| `csrf().disable()` | APIs REST son stateless, no necesitan CSRF |
| `sessionCreationPolicy(STATELESS)` | No crear sesiones HTTP |
| `requestMatchers(...).permitAll()` | Endpoints sin autenticación |
| `anyRequest().authenticated()` | El resto requiere autenticación |

---

## 4. PasswordEncoder

El `PasswordEncoder` es esencial para hashear contraseñas de forma segura.

```java
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ... resto de configuración
}
```

### Uso del PasswordEncoder:

```java
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // Constructor injection

    public User register(CreateUserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // ✅ SIEMPRE hashear la contraseña
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
```

---

## 5. AuthenticationManager

El `AuthenticationManager` coordina el proceso de autenticación.

```java
@Configuration
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ... resto de configuración
}
```

### Uso en el AuthController:

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Autenticar con Spring Security
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // Generar JWT
        String token = jwtService.generateToken(authentication);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
```

---

## 6. UserDetailsService

Spring Security necesita una forma de cargar usuarios. Implementamos `UserDetailsService`:

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Usuario no encontrado: " + username));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .authorities(user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList())
            .build();
    }
}
```

---

## 7. Entidad User con Roles

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "enabled")
    private boolean enabled = true;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters y Setters
}
```

### Enum Role:

```java
public enum Role {
    USER,
    ADMIN
}
```

---

## 8. Configuración Completa con JWT Filter

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // Habilita @PreAuthorize
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                         CustomUserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            // Añadir filtro JWT antes del filtro de autenticación
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .userDetailsService(userDetailsService)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

---

## 9. application.properties para JWT

```properties
# JWT Configuration
jwt.secret=${JWT_SECRET:miClaveSecretaMuyLargaYSeguraParaJWT256bits!}
jwt.expiration=86400000
jwt.refresh-expiration=604800000

# 86400000 ms = 24 horas
# 604800000 ms = 7 días
```

> ⚠️ **IMPORTANTE**: En producción, `jwt.secret` debe estar en variables de entorno, nunca en el código.

---

## 10. Patrones de Autorización

### Por URL Pattern:

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/public/**").permitAll()
    .requestMatchers("/api/user/**").hasRole("USER")
    .requestMatchers("/api/admin/**").hasRole("ADMIN")
    .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
    .anyRequest().authenticated()
)
```

### Por Método con @PreAuthorize:

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() { ... }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserDTO getCurrentUser() { ... }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public void deleteUser(@PathVariable UUID id) { ... }
}
```

---

## 11. Manejo de Errores de Seguridad

```java
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ErrorResponse error = new ErrorResponse(
            401,
            "Unauthorized",
            "Token inválido o no proporcionado",
            request.getRequestURI()
        );

        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
```

### Integrar en SecurityConfig:

```java
.exceptionHandling(ex -> ex
    .authenticationEntryPoint(customAuthenticationEntryPoint)
    .accessDeniedHandler(customAccessDeniedHandler)
)
```

---

## Resumen de Configuración

| Componente | Responsabilidad |
|------------|-----------------|
| `SecurityFilterChain` | Configuración principal de seguridad |
| `PasswordEncoder` | Hashear contraseñas (BCrypt) |
| `AuthenticationManager` | Coordinar autenticación |
| `UserDetailsService` | Cargar usuarios de BD |
| `JwtAuthenticationFilter` | Validar JWT en cada request |
| `@EnableMethodSecurity` | Habilitar @PreAuthorize |

---

## Próximos Pasos

En la siguiente sección aprenderemos la **estructura y funcionamiento de JWT**.

→ [03-jwt-fundamentos.md](03-jwt-fundamentos.md)
