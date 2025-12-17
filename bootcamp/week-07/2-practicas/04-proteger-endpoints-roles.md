# 🛡️ Práctica 04: Proteger Endpoints con Roles

## Objetivo

Implementar autorización basada en roles usando `@PreAuthorize` y verificación de propiedad de recursos.

**Duración estimada**: 45 minutos

---

## Requisitos Previos

- Práctica 03 completada (Endpoints de autenticación funcionando)
- Usuario de prueba registrado

---

## Paso 1: Habilitar Method Security

### 1.1 Actualizar SecurityConfig

Agrega `@EnableMethodSecurity`:

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // ← Agregar esta anotación
public class SecurityConfig {
    // ...
}
```

---

## Paso 2: Crear Usuario ADMIN

### 2.1 DataInitializer

`src/main/java/com/bootcamp/config/DataInitializer.java`

```java
package com.bootcamp.config;

import com.bootcamp.security.entity.Role;
import com.bootcamp.security.entity.User;
import com.bootcamp.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // Crear admin si no existe
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@bootcamp.com");
                admin.setPassword(passwordEncoder.encode("Admin123!"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                log.info("Usuario ADMIN creado");
            }

            // Crear usuario de prueba si no existe
            if (!userRepository.existsByUsername("user")) {
                User user = new User();
                user.setUsername("user");
                user.setEmail("user@bootcamp.com");
                user.setPassword(passwordEncoder.encode("User123!"));
                user.setRole(Role.USER);
                userRepository.save(user);
                log.info("Usuario USER creado");
            }
        };
    }
}
```

---

## Paso 3: Crear UserController con Roles

`src/main/java/com/bootcamp/security/controller/UserController.java`

```java
package com.bootcamp.security.controller;

import com.bootcamp.security.dto.UserDTO;
import com.bootcamp.security.entity.User;
import com.bootcamp.security.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos los usuarios (solo ADMIN)")
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtener perfil del usuario actual")
    public UserDTO getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return toDTO(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurityService.isOwner(#id, principal.username)")
    @Operation(summary = "Obtener usuario por ID (propietario o ADMIN)")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return userRepository.findById(id)
            .map(user -> ResponseEntity.ok(toDTO(user)))
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar usuario (solo ADMIN)")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
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

## Paso 4: Crear UserSecurityService

`src/main/java/com/bootcamp/security/service/UserSecurityService.java`

```java
package com.bootcamp.security.service;

import com.bootcamp.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userSecurityService")
public class UserSecurityService {

    private final UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Verifica si el usuario es propietario del recurso.
     * Se usa en @PreAuthorize para verificación de acceso.
     */
    public boolean isOwner(UUID userId, String username) {
        return userRepository.findById(userId)
            .map(user -> user.getUsername().equals(username))
            .orElse(false);
    }
}
```

---

## Paso 5: Proteger TaskController

Actualiza tu `TaskController` existente:

```java
@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tareas", description = "Gestión de tareas")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Listar tareas (propias o todas para ADMIN)")
    public List<TaskDTO> getTasks(@AuthenticationPrincipal UserDetails user) {
        // ADMIN ve todas, USER ve solo las suyas
        if (hasRole(user, "ROLE_ADMIN")) {
            return taskService.findAll();
        }
        return taskService.findByUsername(user.getUsername());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurityService.isOwner(#id, principal.username)")
    @Operation(summary = "Obtener tarea por ID")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable UUID id) {
        return taskService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Crear nueva tarea")
    public ResponseEntity<TaskDTO> createTask(
            @Valid @RequestBody CreateTaskRequest request,
            @AuthenticationPrincipal UserDetails user) {
        TaskDTO task = taskService.create(request, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurityService.isOwner(#id, principal.username)")
    @Operation(summary = "Actualizar tarea")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTaskRequest request) {
        return taskService.update(id, request)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskSecurityService.isOwner(#id, principal.username)")
    @Operation(summary = "Eliminar tarea")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        if (taskService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean hasRole(UserDetails user, String role) {
        return user.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals(role));
    }
}
```

---

## Paso 6: Crear TaskSecurityService

`src/main/java/com/bootcamp/security/service/TaskSecurityService.java`

```java
package com.bootcamp.security.service;

import com.bootcamp.task.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("taskSecurityService")
public class TaskSecurityService {

    private final TaskRepository taskRepository;

    public TaskSecurityService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public boolean isOwner(UUID taskId, String username) {
        return taskRepository.findById(taskId)
            .map(task -> task.getUser().getUsername().equals(username))
            .orElse(false);
    }
}
```

---

## Paso 7: Configurar Swagger para JWT

`src/main/java/com/bootcamp/config/OpenApiConfig.java`

```java
package com.bootcamp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Bootcamp - Seguridad")
                .version("1.0")
                .description("API REST con Spring Security y JWT"))
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
}
```

---

## Paso 8: Probar Autorización

### 8.1 Obtener tokens

```bash
# Login como USER
USER_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"User123!"}' | jq -r '.accessToken')

# Login como ADMIN
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"Admin123!"}' | jq -r '.accessToken')

echo "USER_TOKEN: $USER_TOKEN"
echo "ADMIN_TOKEN: $ADMIN_TOKEN"
```

### 8.2 Probar permisos

```bash
# USER intenta ver todos los usuarios (403 Forbidden)
curl -v http://localhost:8080/api/users \
  -H "Authorization: Bearer $USER_TOKEN"
# Respuesta: 403 Forbidden

# ADMIN ve todos los usuarios (200 OK)
curl http://localhost:8080/api/users \
  -H "Authorization: Bearer $ADMIN_TOKEN"
# Respuesta: 200 OK con lista

# USER ve su propio perfil (200 OK)
curl http://localhost:8080/api/users/me \
  -H "Authorization: Bearer $USER_TOKEN"
# Respuesta: 200 OK con datos del usuario

# USER crea tarea (200 OK)
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer $USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"Mi tarea","description":"Descripción"}'
# Respuesta: 201 Created

# USER ve sus tareas (200 OK)
curl http://localhost:8080/api/tasks \
  -H "Authorization: Bearer $USER_TOKEN"
# Respuesta: 200 OK (solo sus tareas)

# ADMIN ve todas las tareas (200 OK)
curl http://localhost:8080/api/tasks \
  -H "Authorization: Bearer $ADMIN_TOKEN"
# Respuesta: 200 OK (todas las tareas)
```

---

## Verificación ✅

- [ ] `@EnableMethodSecurity` habilitado
- [ ] DataInitializer crea usuarios admin y user
- [ ] UserController con endpoints protegidos por rol
- [ ] TaskController protege operaciones por propiedad
- [ ] UserSecurityService verifica propiedad de usuarios
- [ ] TaskSecurityService verifica propiedad de tareas
- [ ] Swagger configurado para JWT
- [ ] ADMIN accede a todo
- [ ] USER solo accede a sus recursos
- [ ] 403 Forbidden para acceso denegado

---

## Tabla de Permisos

| Endpoint | Método | USER | ADMIN |
|----------|--------|------|-------|
| `/api/users` | GET | ❌ | ✅ |
| `/api/users/me` | GET | ✅ | ✅ |
| `/api/users/{id}` | GET | ⚠️ | ✅ |
| `/api/users/{id}` | DELETE | ❌ | ✅ |
| `/api/tasks` | GET | ⚠️ | ✅ |
| `/api/tasks` | POST | ✅ | ✅ |
| `/api/tasks/{id}` | GET/PUT/DELETE | ⚠️ | ✅ |

**Leyenda**: ✅ Permitido | ❌ Denegado | ⚠️ Solo propios

---

## Próximos Pasos

→ [05-testing-seguridad.md](05-testing-seguridad.md)
