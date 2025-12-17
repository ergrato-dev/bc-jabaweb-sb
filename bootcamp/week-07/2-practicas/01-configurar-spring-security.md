# 🔧 Práctica 01: Configurar Spring Security

## Objetivo

Configurar Spring Security en un proyecto Spring Boot existente, deshabilitando CSRF para APIs REST y configurando autenticación stateless.

**Duración estimada**: 45 minutos

---

## Requisitos Previos

- Proyecto Spring Boot funcionando (week-03 o posterior)
- Conocimientos de arquitectura en capas
- Docker y docker-compose configurados

---

## Paso 1: Agregar Dependencias

### 1.1 Editar pom.xml

Agrega las siguientes dependencias:

```xml
<!-- Spring Security -->
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

<!-- Spring Security Test -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 1.2 Verificar

```bash
./mvnw dependency:tree | grep security
```

---

## Paso 2: Crear Estructura de Paquetes

```
src/main/java/com/bootcamp/
├── config/
│   └── SecurityConfig.java
├── security/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   ├── service/
│   ├── filter/
│   └── exception/
```

### Crear directorios

```bash
mkdir -p src/main/java/com/bootcamp/config
mkdir -p src/main/java/com/bootcamp/security/{dto,entity,repository,service,filter,exception}
```

---

## Paso 3: Crear SecurityConfig Básico

### 3.1 Crear archivo

`src/main/java/com/bootcamp/config/SecurityConfig.java`

```java
package com.bootcamp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // Deshabilitar CSRF (no necesario para APIs stateless)
            .csrf(csrf -> csrf.disable())

            // Configurar sesión como stateless
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Configurar autorización de rutas
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/actuator/health").permitAll()

                // Todo lo demás requiere autenticación
                .anyRequest().authenticated()
            )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

## Paso 4: Crear Entidad User

### 4.1 Crear Role enum

`src/main/java/com/bootcamp/security/entity/Role.java`

```java
package com.bootcamp.security.entity;

public enum Role {
    USER,
    ADMIN
}
```

### 4.2 Crear User entity

`src/main/java/com/bootcamp/security/entity/User.java`

```java
package com.bootcamp.security.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
```

---

## Paso 5: Crear UserRepository

`src/main/java/com/bootcamp/security/repository/UserRepository.java`

```java
package com.bootcamp.security.repository;

import com.bootcamp.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
```

---

## Paso 6: Crear UserDetailsService

`src/main/java/com/bootcamp/security/service/CustomUserDetailsService.java`

```java
package com.bootcamp.security.service;

import com.bootcamp.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Usuario no encontrado: " + username
            ));
    }
}
```

---

## Paso 7: Configurar AuthenticationManager

Actualiza `SecurityConfig.java`:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
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
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

---

## Paso 8: Probar Configuración

### 8.1 Iniciar aplicación

```bash
docker-compose up -d db
./mvnw spring-boot:run
```

### 8.2 Verificar seguridad activa

```bash
# Endpoint protegido (debe retornar 401)
curl -v http://localhost:8080/api/tasks
# HTTP/1.1 401 Unauthorized

# Endpoint público (si existe Swagger)
curl -v http://localhost:8080/swagger-ui/index.html
# HTTP/1.1 200 OK
```

---

## Verificación ✅

- [ ] Dependencias de Spring Security agregadas
- [ ] SecurityConfig creado con CSRF deshabilitado
- [ ] Sesión configurada como STATELESS
- [ ] Entidad User implementa UserDetails
- [ ] UserRepository con métodos de búsqueda
- [ ] CustomUserDetailsService implementado
- [ ] Endpoints públicos accesibles sin autenticación
- [ ] Endpoints protegidos retornan 401

---

## Errores Comunes

### "No AuthenticationProvider found"

```java
// Asegúrate de configurar el AuthenticationProvider
@Bean
public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
}
```

### Tabla users no se crea

Verifica que la entidad User tenga:
- `@Entity`
- `@Table(name = "users")`
- JPA configurado con `spring.jpa.hibernate.ddl-auto=update`

---

## Próximos Pasos

→ [02-implementar-jwt-service.md](02-implementar-jwt-service.md)
