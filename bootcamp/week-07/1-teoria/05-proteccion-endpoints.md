# 🛡️ Protección de Endpoints con Roles

## Introducción

Una vez implementada la autenticación JWT, necesitamos **autorizar** qué usuarios pueden acceder a qué recursos. Spring Security ofrece múltiples formas de implementar autorización basada en roles y permisos.

---

## 1. Configuración Previa

### Habilitar Method Security

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // ← Habilita @PreAuthorize, @PostAuthorize, etc.
public class SecurityConfig {
    // ...
}
```

### Enum de Roles

```java
public enum Role {
    USER,
    ADMIN
}
```

---

## 2. Autorización por URL Pattern

### En SecurityConfig

```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // Endpoints públicos
            .requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()

            // Por rol específico
            .requestMatchers("/api/admin/**").hasRole("ADMIN")
            .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")

            // Por método HTTP
            .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/public/**").permitAll()

            // Todo lo demás requiere autenticación
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
}
```

### Métodos de Autorización

| Método | Descripción |
|--------|-------------|
| `permitAll()` | Acceso sin autenticación |
| `authenticated()` | Requiere autenticación (cualquier rol) |
| `hasRole("ADMIN")` | Requiere rol específico |
| `hasAnyRole("USER", "ADMIN")` | Cualquiera de los roles |
| `hasAuthority("ROLE_ADMIN")` | Autoridad específica |
| `denyAll()` | Denegado para todos |

---

## 3. Autorización con @PreAuthorize

### Uso Básico

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Solo ADMIN puede ver todos los usuarios
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    // Cualquier usuario autenticado
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserDTO getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return userService.findByUsername(user.getUsername());
    }

    // Usuario puede ver su perfil O admin puede ver cualquiera
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.isOwner(#id, principal.username)")
    public UserDTO getUserById(@PathVariable UUID id) {
        return userService.findById(id);
    }

    // Solo ADMIN puede eliminar usuarios
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Expresiones SpEL Disponibles

| Expresión | Descripción |
|-----------|-------------|
| `hasRole('ROLE')` | Tiene el rol especificado |
| `hasAnyRole('R1', 'R2')` | Tiene alguno de los roles |
| `hasAuthority('AUTH')` | Tiene la autoridad específica |
| `isAuthenticated()` | Usuario autenticado |
| `isAnonymous()` | Usuario anónimo |
| `principal` | Objeto UserDetails actual |
| `#paramName` | Parámetro del método |
| `@beanName.method()` | Llamar método de un bean |

---

## 4. Verificación de Propiedad de Recursos

### Escenario

Un usuario solo puede acceder/modificar **sus propias tareas**.

### UserService con método de verificación

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    // Verifica si el usuario es propietario del recurso
    public boolean isOwner(UUID userId, String username) {
        return userRepository.findById(userId)
            .map(user -> user.getUsername().equals(username))
            .orElse(false);
    }
}
```

### TaskController con verificación

```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // Usuario ve solo sus tareas, ADMIN ve todas
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<TaskDTO> getTasks(@AuthenticationPrincipal UserDetails user) {
        if (user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return taskService.findAll();
        }
        return taskService.findByUsername(user.getUsername());
    }

    // Solo el propietario o ADMIN puede ver una tarea
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskService.isOwner(#id, principal.username)")
    public TaskDTO getTask(@PathVariable UUID id) {
        return taskService.findById(id);
    }

    // Cualquier usuario autenticado puede crear tareas
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TaskDTO> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            @AuthenticationPrincipal UserDetails user) {
        TaskDTO task = taskService.create(request, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    // Solo el propietario o ADMIN puede actualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskService.isOwner(#id, principal.username)")
    public TaskDTO updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTaskRequest request) {
        return taskService.update(id, request);
    }

    // Solo el propietario o ADMIN puede eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskService.isOwner(#id, principal.username)")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

### TaskService

```java
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public boolean isOwner(UUID taskId, String username) {
        return taskRepository.findById(taskId)
            .map(task -> task.getUser().getUsername().equals(username))
            .orElse(false);
    }

    public List<TaskDTO> findByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return taskRepository.findByUser(user).stream()
            .map(this::toDTO)
            .toList();
    }

    // ... otros métodos
}
```

---

## 5. @PostAuthorize - Verificación Post-Ejecución

A veces necesitas verificar **después** de ejecutar el método:

```java
// Retorna el recurso solo si el usuario es propietario
@GetMapping("/{id}")
@PostAuthorize("hasRole('ADMIN') or returnObject.userId == principal.id")
public TaskDTO getTask(@PathVariable UUID id) {
    return taskService.findById(id);
}
```

> ⚠️ Usar con cuidado: el método se ejecuta antes de la verificación.

---

## 6. Acceder al Usuario Actual

### Con @AuthenticationPrincipal

```java
@GetMapping("/me")
public UserDTO getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    return userService.findByUsername(userDetails.getUsername());
}
```

### Con SecurityContextHolder

```java
@GetMapping("/me")
public UserDTO getCurrentUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String username = auth.getName();
    return userService.findByUsername(username);
}
```

### Custom Principal

```java
// Si tu UserDetails es una clase personalizada
@GetMapping("/me")
public UserDTO getCurrentUser(@AuthenticationPrincipal CustomUserDetails user) {
    UUID userId = user.getId();  // Acceso a métodos personalizados
    return userService.findById(userId);
}
```

---

## 7. Roles en JWT Claims

### Incluir roles en el token

```java
// En JwtService.buildToken()
String roles = userDetails.getAuthorities().stream()
    .map(GrantedAuthority::getAuthority)
    .collect(Collectors.joining(","));

extraClaims.put("roles", roles);
```

### Token generado

```json
{
  "sub": "johndoe",
  "roles": "ROLE_USER,ROLE_ADMIN",
  "iat": 1702800000,
  "exp": 1702886400
}
```

---

## 8. Documentar Seguridad en Swagger

```java
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("API Segura").version("1.0"))
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components()
                .addSecuritySchemes("bearerAuth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("Ingresa el token JWT")
                )
            );
    }
}
```

### En Controller

```java
@Operation(
    summary = "Obtener todas las tareas",
    security = @SecurityRequirement(name = "bearerAuth")
)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Lista de tareas"),
    @ApiResponse(responseCode = "401", description = "No autenticado"),
    @ApiResponse(responseCode = "403", description = "Sin permisos")
})
@GetMapping
@PreAuthorize("hasRole('ADMIN')")
public List<TaskDTO> getAllTasks() {
    return taskService.findAll();
}
```

---

## 9. Tabla de Permisos por Endpoint

| Endpoint | Método | USER | ADMIN | Descripción |
|----------|--------|------|-------|-------------|
| `/api/auth/register` | POST | ✅ | ✅ | Público |
| `/api/auth/login` | POST | ✅ | ✅ | Público |
| `/api/users` | GET | ❌ | ✅ | Solo admin |
| `/api/users/me` | GET | ✅ | ✅ | Usuario actual |
| `/api/users/{id}` | GET | ⚠️ | ✅ | Propio o admin |
| `/api/users/{id}` | DELETE | ❌ | ✅ | Solo admin |
| `/api/tasks` | GET | ⚠️ | ✅ | Propias o todas |
| `/api/tasks` | POST | ✅ | ✅ | Crear propias |
| `/api/tasks/{id}` | PUT | ⚠️ | ✅ | Propia o admin |
| `/api/tasks/{id}` | DELETE | ⚠️ | ✅ | Propia o admin |

**Leyenda**: ✅ Permitido | ❌ Denegado | ⚠️ Condicional (propietario)

---

## 10. Testing de Autorización

```bash
# 1. Login como USER
TOKEN_USER=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"Password123"}' | jq -r '.accessToken')

# 2. Login como ADMIN
TOKEN_ADMIN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin123"}' | jq -r '.accessToken')

# 3. USER intenta acceder a /api/users (403 Forbidden)
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer $TOKEN_USER"
# Respuesta: 403 Forbidden

# 4. ADMIN accede a /api/users (200 OK)
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer $TOKEN_ADMIN"
# Respuesta: 200 OK con lista de usuarios

# 5. USER accede a sus tareas (200 OK)
curl -X GET http://localhost:8080/api/tasks \
  -H "Authorization: Bearer $TOKEN_USER"
# Respuesta: 200 OK con tareas del usuario

# 6. Sin token (401 Unauthorized)
curl -X GET http://localhost:8080/api/tasks
# Respuesta: 401 Unauthorized
```

---

## Resumen

| Concepto | Uso |
|----------|-----|
| `hasRole('ROLE')` | Verificar rol específico |
| `@PreAuthorize` | Verificar antes de ejecutar método |
| `@PostAuthorize` | Verificar después de ejecutar |
| `@AuthenticationPrincipal` | Inyectar usuario actual |
| `@beanName.method()` | Lógica personalizada de autorización |
| `principal.username` | Acceder a datos del usuario en SpEL |

---

## Próximos Pasos

Continúa con las **prácticas** para implementar todo lo aprendido.

→ [../2-practicas/01-configurar-spring-security.md](../2-practicas/01-configurar-spring-security.md)
