# Práctica 03: Integración de Componentes

## 📋 Objetivos

- Implementar la capa de servicios con lógica de negocio
- Configurar Spring Security con JWT
- Crear los controladores REST
- Integrar Swagger/OpenAPI
- Implementar tests

---

## Parte 1: DTOs y Mappers

### 📝 Ejercicio 1.1: DTOs de Usuario

```java
// src/main/java/com/bootcamp/finalproject/user/dto/UserDTO.java
package com.bootcamp.finalproject.user.dto;

import java.time.LocalDateTime;

public record UserDTO(
    Long id,
    String name,
    String email,
    String role,
    LocalDateTime createdAt
) {}
```

```java
// src/main/java/com/bootcamp/finalproject/user/dto/UpdateUserRequest.java
package com.bootcamp.finalproject.user.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    String name
) {}
```

### 📝 Ejercicio 1.2: DTOs de Autenticación

```java
// src/main/java/com/bootcamp/finalproject/auth/dto/RegisterRequest.java
package com.bootcamp.finalproject.auth.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    String name,

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    String email,

    @NotBlank(message = "La contraseña es requerida")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password
) {}
```

```java
// src/main/java/com/bootcamp/finalproject/auth/dto/LoginRequest.java
package com.bootcamp.finalproject.auth.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    String email,

    @NotBlank(message = "La contraseña es requerida")
    String password
) {}
```

```java
// src/main/java/com/bootcamp/finalproject/auth/dto/AuthResponse.java
package com.bootcamp.finalproject.auth.dto;

import com.bootcamp.finalproject.user.dto.UserDTO;

public record AuthResponse(
    String token,
    String type,
    UserDTO user
) {
    public AuthResponse(String token, UserDTO user) {
        this(token, "Bearer", user);
    }
}
```

### 📝 Ejercicio 1.3: DTOs de Producto

```java
// src/main/java/com/bootcamp/finalproject/product/dto/ProductDTO.java
package com.bootcamp.finalproject.product.dto;

import java.time.LocalDateTime;

public record ProductDTO(
    Long id,
    String name,
    String description,
    Double price,
    Integer stock,
    String categoryName,
    Long categoryId,
    LocalDateTime createdAt
) {}
```

```java
// src/main/java/com/bootcamp/finalproject/product/dto/CreateProductRequest.java
package com.bootcamp.finalproject.product.dto;

import jakarta.validation.constraints.*;

public record CreateProductRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 200, message = "El nombre debe tener entre 2 y 200 caracteres")
    String name,

    String description,

    @NotNull(message = "El precio es requerido")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    Double price,

    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    Integer stock,

    @NotNull(message = "La categoría es requerida")
    Long categoryId
) {}
```

### 📝 Ejercicio 1.4: Mapper de Usuario

```java
// src/main/java/com/bootcamp/finalproject/user/mapper/UserMapper.java
package com.bootcamp.finalproject.user.mapper;

import com.bootcamp.finalproject.user.dto.UserDTO;
import com.bootcamp.finalproject.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        if (user == null) return null;

        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole().name(),
            user.getCreatedAt()
        );
    }
}
```

### 📝 Ejercicio 1.5: Mapper de Producto

```java
// src/main/java/com/bootcamp/finalproject/product/mapper/ProductMapper.java
package com.bootcamp.finalproject.product.mapper;

import com.bootcamp.finalproject.product.dto.CreateProductRequest;
import com.bootcamp.finalproject.product.dto.ProductDTO;
import com.bootcamp.finalproject.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        if (product == null) return null;

        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock(),
            product.getCategory() != null ? product.getCategory().getName() : null,
            product.getCategory() != null ? product.getCategory().getId() : null,
            product.getCreatedAt()
        );
    }

    public Product toEntity(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        // Category se asigna en el service
        return product;
    }
}
```

---

## Parte 2: Excepciones y Manejo de Errores

### 📝 Ejercicio 2.1: Excepciones Custom

```java
// src/main/java/com/bootcamp/finalproject/common/exception/ResourceNotFoundException.java
package com.bootcamp.finalproject.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " no encontrado con ID: " + id);
    }
}
```

```java
// src/main/java/com/bootcamp/finalproject/common/exception/BadRequestException.java
package com.bootcamp.finalproject.common.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
```

```java
// src/main/java/com/bootcamp/finalproject/common/exception/UnauthorizedException.java
package com.bootcamp.finalproject.common.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
```

### 📝 Ejercicio 2.2: ErrorResponse DTO

```java
// src/main/java/com/bootcamp/finalproject/common/dto/ErrorResponse.java
package com.bootcamp.finalproject.common.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
    String message,
    String code,
    LocalDateTime timestamp,
    List<FieldError> errors
) {
    public ErrorResponse(String message, String code) {
        this(message, code, LocalDateTime.now(), null);
    }

    public ErrorResponse(String message, String code, List<FieldError> errors) {
        this(message, code, LocalDateTime.now(), errors);
    }

    public record FieldError(String field, String message) {}
}
```

### 📝 Ejercicio 2.3: GlobalExceptionHandler

```java
// src/main/java/com/bootcamp/finalproject/common/exception/GlobalExceptionHandler.java
package com.bootcamp.finalproject.common.exception;

import com.bootcamp.finalproject.common.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse(ex.getMessage(), "NOT_FOUND"));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        log.warn("Solicitud inválida: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(ex.getMessage(), "BAD_REQUEST"));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        log.warn("No autorizado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse(ex.getMessage(), "UNAUTHORIZED"));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorResponse("Credenciales inválidas", "INVALID_CREDENTIALS"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponse("Acceso denegado", "FORBIDDEN"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorResponse.FieldError> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ErrorResponse.FieldError(error.getField(), error.getDefaultMessage()))
            .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("Error de validación", "VALIDATION_ERROR", errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Error interno: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorResponse("Error interno del servidor", "INTERNAL_ERROR"));
    }
}
```

---

## Parte 3: Spring Security y JWT

### 📝 Ejercicio 3.1: JwtService

```java
// src/main/java/com/bootcamp/finalproject/security/jwt/JwtService.java
package com.bootcamp.finalproject.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration:900000}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(getSigningKey())
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
```

### 📝 Ejercicio 3.2: JwtAuthenticationFilter

```java
// src/main/java/com/bootcamp/finalproject/security/jwt/JwtAuthenticationFilter.java
package com.bootcamp.finalproject.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token inválido, continuar sin autenticación
        }

        filterChain.doFilter(request, response);
    }
}
```

### 📝 Ejercicio 3.3: CustomUserDetailsService

```java
// src/main/java/com/bootcamp/finalproject/security/CustomUserDetailsService.java
package com.bootcamp.finalproject.security;

import com.bootcamp.finalproject.user.entity.User;
import com.bootcamp.finalproject.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}
```

### 📝 Ejercicio 3.4: SecurityConfig

```java
// src/main/java/com/bootcamp/finalproject/config/SecurityConfig.java
package com.bootcamp.finalproject.config;

import com.bootcamp.finalproject.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Públicos
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/api-docs/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                // GET de productos públicos
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

## Parte 4: Services

### 📝 Ejercicio 4.1: AuthService

```java
// src/main/java/com/bootcamp/finalproject/auth/service/AuthService.java
package com.bootcamp.finalproject.auth.service;

import com.bootcamp.finalproject.auth.dto.*;
import com.bootcamp.finalproject.common.exception.BadRequestException;
import com.bootcamp.finalproject.security.jwt.JwtService;
import com.bootcamp.finalproject.user.dto.UserDTO;
import com.bootcamp.finalproject.user.entity.User;
import com.bootcamp.finalproject.user.mapper.UserMapper;
import com.bootcamp.finalproject.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userMapper = userMapper;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("El email ya está registrado");
        }

        User user = new User(
            request.name(),
            request.email(),
            passwordEncoder.encode(request.password())
        );

        user = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, userMapper.toDTO(user));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
            .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, userMapper.toDTO(user));
    }

    public UserDTO getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        return userMapper.toDTO(user);
    }
}
```

### 📝 Ejercicio 4.2: ProductService

```java
// src/main/java/com/bootcamp/finalproject/product/service/ProductService.java
package com.bootcamp.finalproject.product.service;

import com.bootcamp.finalproject.category.entity.Category;
import com.bootcamp.finalproject.category.repository.CategoryRepository;
import com.bootcamp.finalproject.common.exception.ResourceNotFoundException;
import com.bootcamp.finalproject.product.dto.*;
import com.bootcamp.finalproject.product.entity.Product;
import com.bootcamp.finalproject.product.mapper.ProductMapper;
import com.bootcamp.finalproject.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(productMapper::toDTO);
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
        return productMapper.toDTO(product);
    }

    @Transactional
    public ProductDTO create(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.categoryId()));

        Product product = productMapper.toEntity(request);
        product.setCategory(category);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO update(Long id, CreateProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto", id));

        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.categoryId()));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setCategory(category);

        return productMapper.toDTO(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto", id);
        }
        productRepository.deleteById(id);
    }

    public Page<ProductDTO> findByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable)
            .map(productMapper::toDTO);
    }
}
```

---

## Parte 5: Controllers

### 📝 Ejercicio 5.1: AuthController

```java
// src/main/java/com/bootcamp/finalproject/auth/controller/AuthController.java
package com.bootcamp.finalproject.auth.controller;

import com.bootcamp.finalproject.auth.dto.*;
import com.bootcamp.finalproject.auth.service.AuthService;
import com.bootcamp.finalproject.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints de autenticación")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    @Operation(summary = "Obtener usuario actual")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(authService.getCurrentUser(authentication.getName()));
    }
}
```

### 📝 Ejercicio 5.2: ProductController

```java
// src/main/java/com/bootcamp/finalproject/product/controller/ProductController.java
package com.bootcamp.finalproject.product.controller;

import com.bootcamp.finalproject.product.dto.*;
import com.bootcamp.finalproject.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Gestión de productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear producto", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(productService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar producto", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ProductDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductRequest request
    ) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar producto", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## Parte 6: Configuración de Swagger

### 📝 Ejercicio 6.1: OpenApiConfig

```java
// src/main/java/com/bootcamp/finalproject/config/OpenApiConfig.java
package com.bootcamp.finalproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Final Project API")
                .version("1.0.0")
                .description("API REST del proyecto final del bootcamp Java Web")
                .contact(new Contact()
                    .name("Bootcamp")
                    .email("bootcamp@example.com")))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Ingresa tu token JWT")));
    }
}
```

---

## Parte 7: Testing Básico

### 📝 Ejercicio 7.1: Test de ProductService

```java
// src/test/java/com/bootcamp/finalproject/unit/product/ProductServiceTest.java
package com.bootcamp.finalproject.unit.product;

import com.bootcamp.finalproject.category.entity.Category;
import com.bootcamp.finalproject.category.repository.CategoryRepository;
import com.bootcamp.finalproject.common.exception.ResourceNotFoundException;
import com.bootcamp.finalproject.product.dto.*;
import com.bootcamp.finalproject.product.entity.Product;
import com.bootcamp.finalproject.product.mapper.ProductMapper;
import com.bootcamp.finalproject.product.repository.ProductRepository;
import com.bootcamp.finalproject.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private Category category;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        category = new Category("Electronics", "Electronic products");
        category.setId(1L);

        product = new Product("Laptop", "A laptop", 999.99, 10, category);
        product.setId(1L);

        productDTO = new ProductDTO(1L, "Laptop", "A laptop", 999.99, 10, "Electronics", 1L, null);
    }

    @Test
    @DisplayName("findById - debería retornar producto cuando existe")
    void findById_WhenProductExists_ShouldReturnProduct() {
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        // When
        ProductDTO result = productService.findById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Laptop");
        verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("findById - debería lanzar excepción cuando no existe")
    void findById_WhenProductNotExists_ShouldThrowException() {
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> productService.findById(999L))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("create - debería crear producto correctamente")
    void create_ShouldCreateProductSuccessfully() {
        // Given
        CreateProductRequest request = new CreateProductRequest("Laptop", "A laptop", 999.99, 10, 1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productMapper.toEntity(request)).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        // When
        ProductDTO result = productService.create(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo("Laptop");
        verify(productRepository).save(any(Product.class));
    }
}
```

---

## ✅ Verificación Final

```bash
# Levantar todo
docker-compose up --build

# Verificar endpoints
curl http://localhost:8080/swagger-ui.html

# Registrar usuario
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test","email":"test@test.com","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@test.com","password":"password123"}'

# Ejecutar tests
./mvnw test
```

---

## 🚀 Tareas Pendientes

Completa tu proyecto implementando:

- [ ] CategoryController y CategoryService
- [ ] Entidades adicionales (Order, OrderItem si aplica)
- [ ] Más tests unitarios y de integración
- [ ] README.md completo
- [ ] Datos de prueba (DataInitializer)

---

> **💡 Tip**: Con esta base ya tienes el 70% del proyecto. Añade las entidades específicas de tu dominio y completa los tests para el 100%.
