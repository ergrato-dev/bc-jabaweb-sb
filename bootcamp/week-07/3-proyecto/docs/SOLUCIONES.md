# 📋 SOLUCIONES - Week 07: Seguridad con JWT

> **⚠️ SOLO PARA INSTRUCTORES**
> Este archivo contiene las soluciones a todos los TODOs del proyecto.

---

## SecurityConfig.java

### securityFilterChain()

```java
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
            .requestMatchers("/actuator/health").permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}
```

### authenticationProvider()

```java
@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}
```

### passwordEncoder()

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

## OpenApiConfig.java

```java
@Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("API Bootcamp - Seguridad")
            .version("1.0.0")
            .description("API REST con Spring Security y JWT")
            .contact(new Contact()
                .name("Bootcamp")
                .email("bootcamp@example.com")))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new Components()
            .addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .description("Ingresa el token JWT (sin 'Bearer ')")
            )
        );
}
```

---

## DataInitializer.java

```java
@Bean
CommandLineRunner initDatabase(UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
    return args -> {
        // Crear ADMIN
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@bootcamp.com");
            admin.setPassword(passwordEncoder.encode("Admin123!"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            log.info("Usuario ADMIN creado");
        }

        // Crear USER
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setEmail("user@bootcamp.com");
            user.setPassword(passwordEncoder.encode("User123!"));
            user.setRole(Role.USER);
            userRepository.save(user);
            log.info("Usuario USER creado");
        }

        log.info("Inicialización de datos completada");
    };
}
```

---

## User.java - getAuthorities()

```java
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
}
```

---

## JwtService.java

### buildToken()

```java
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
```

### isTokenValid()

```java
public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
}
```

### isTokenExpired()

```java
private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
}
```

### extractAllClaims()

```java
private Claims extractAllClaims(String token) {
    return Jwts.parser()
        .verifyWith(getSigningKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
}
```

---

## CustomUserDetailsService.java

```java
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Usuario no encontrado: " + username
        ));
}
```

---

## AuthService.java

### register()

```java
public UserDTO register(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
        throw new DuplicateResourceException("El username ya está en uso");
    }

    if (userRepository.existsByEmail(request.getEmail())) {
        throw new DuplicateResourceException("El email ya está registrado");
    }

    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.USER);

    User savedUser = userRepository.save(user);

    return toDTO(savedUser);
}
```

### login()

```java
public AuthResponse login(LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String accessToken = jwtService.generateToken(authentication);
    String refreshToken = jwtService.generateRefreshToken(userDetails);

    return new AuthResponse(
        accessToken,
        refreshToken,
        jwtService.getJwtExpiration()
    );
}
```

### refreshToken()

```java
public AuthResponse refreshToken(String refreshToken) {
    try {
        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new InvalidTokenException("Refresh token inválido o expirado");
        }

        String newAccessToken = jwtService.generateToken(new HashMap<>(), userDetails);

        return new AuthResponse(
            newAccessToken,
            refreshToken,
            jwtService.getJwtExpiration()
        );
    } catch (Exception e) {
        throw new InvalidTokenException("Error procesando refresh token: " + e.getMessage());
    }
}
```

---

## UserSecurityService.java

```java
public boolean isOwner(UUID userId, String username) {
    return userRepository.findById(userId)
        .map(user -> user.getUsername().equals(username))
        .orElse(false);
}
```

---

## JwtAuthenticationFilter.java

```java
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
```

---

## AuthController.java

```java
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
public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
    AuthResponse response = authService.refreshToken(request.getRefreshToken());
    return ResponseEntity.ok(response);
}
```

---

## UserController.java

```java
@GetMapping
@PreAuthorize("hasRole('ADMIN')")
public List<UserDTO> getAllUsers() {
    return userRepository.findAll().stream()
        .map(this::toDTO)
        .toList();
}

@GetMapping("/me")
@PreAuthorize("isAuthenticated()")
public UserDTO getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    User user = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    return toDTO(user);
}

@GetMapping("/{id}")
@PreAuthorize("hasRole('ADMIN') or @userSecurityService.isOwner(#id, principal.username)")
public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
    return userRepository.findById(id)
        .map(user -> ResponseEntity.ok(toDTO(user)))
        .orElse(ResponseEntity.notFound().build());
}

@DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    if (!userRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    userRepository.deleteById(id);
    return ResponseEntity.noContent().build();
}
```

---

## Verificación Rápida

```bash
# Iniciar servicios
docker-compose up -d

# Registrar usuario
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@test.com","password":"Password123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"Password123"}'

# Acceder a endpoint protegido
curl http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <token>"
```
