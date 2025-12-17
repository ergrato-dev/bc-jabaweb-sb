# 📚 Soluciones - Semana 06: Swagger/OpenAPI y CORS

> ⚠️ **DOCUMENTO PARA INSTRUCTORES** - No compartir con estudiantes

Este documento contiene las soluciones completas a todos los TODOs del proyecto.

---

## Índice de Soluciones

1. [OpenApiConfig.java](#1-openapiconfigjava)
2. [CorsConfig.java](#2-corsconfigjava)
3. [DTOs con @Schema](#3-dtos-con-schema)
4. [UserController.java](#4-usercontrollerjava)
5. [TaskController.java](#5-taskcontrollerjava)

---

## 1. OpenApiConfig.java

**Archivo**: `src/main/java/com/bootcamp/apidocs/config/OpenApiConfig.java`

```java
package com.bootcamp.apidocs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    // SOLUCIÓN TODO 1:
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management API")
                        .version("1.0.0")
                        .description("API REST para gestión de tareas y usuarios - Bootcamp Semana 06")
                        .termsOfService("https://bootcamp.example.com/terms")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("dev@bootcamp.example.com")
                                .url("https://bootcamp.example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Docker")
                ));
    }
}
```

---

## 2. CorsConfig.java

**Archivo**: `src/main/java/com/bootcamp/apidocs/config/CorsConfig.java`

```java
package com.bootcamp.apidocs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    // SOLUCIÓN TODO 1:
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Content-Type", "X-Total-Count")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

---

## 3. DTOs con @Schema

### 3.1 TaskDTO.java

**Archivo**: `src/main/java/com/bootcamp/apidocs/dto/TaskDTO.java`

```java
package com.bootcamp.apidocs.dto;

import com.bootcamp.apidocs.entity.Priority;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "Task", description = "Representación de una tarea")
public class TaskDTO {

    @Schema(
            description = "Identificador único de la tarea",
            example = "550e8400-e29b-41d4-a716-446655440000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(
            description = "Título de la tarea",
            example = "Completar documentación API",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String title;

    @Schema(
            description = "Descripción detallada de la tarea",
            example = "Documentar todos los endpoints con Swagger",
            nullable = true
    )
    private String description;

    @Schema(
            description = "Indica si la tarea está completada",
            example = "false"
    )
    private Boolean completed;

    @Schema(
            description = "Nivel de prioridad de la tarea",
            example = "MEDIUM"
    )
    private Priority priority;

    @Schema(
            description = "ID del usuario propietario de la tarea",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID userId;

    @Schema(
            description = "Fecha y hora de creación",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Fecha y hora de última actualización",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime updatedAt;

    // ... constructores, getters y setters sin cambios
}
```

### 3.2 CreateTaskRequest.java

```java
package com.bootcamp.apidocs.dto;

import com.bootcamp.apidocs.entity.Priority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "CreateTaskRequest", description = "Datos necesarios para crear una tarea")
public class CreateTaskRequest {

    @Schema(
            description = "Título de la tarea",
            example = "Implementar endpoint de usuarios",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 3,
            maxLength = 100
    )
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;

    @Schema(
            description = "Descripción detallada de la tarea",
            example = "Crear CRUD completo para la entidad User",
            nullable = true,
            maxLength = 500
    )
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    @Schema(
            description = "Nivel de prioridad",
            example = "MEDIUM",
            defaultValue = "MEDIUM"
    )
    private Priority priority = Priority.MEDIUM;

    @Schema(
            description = "ID del usuario propietario",
            example = "550e8400-e29b-41d4-a716-446655440000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "El ID de usuario es obligatorio")
    private java.util.UUID userId;

    // ... constructores, getters y setters sin cambios
}
```

### 3.3 UpdateTaskRequest.java

```java
package com.bootcamp.apidocs.dto;

import com.bootcamp.apidocs.entity.Priority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(name = "UpdateTaskRequest", description = "Datos para actualizar una tarea (todos los campos son opcionales)")
public class UpdateTaskRequest {

    @Schema(
            description = "Nuevo título de la tarea",
            example = "Título actualizado",
            nullable = true
    )
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;

    @Schema(
            description = "Nueva descripción de la tarea",
            example = "Descripción actualizada",
            nullable = true
    )
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    @Schema(
            description = "Estado de completitud",
            example = "true"
    )
    private Boolean completed;

    @Schema(
            description = "Nueva prioridad",
            example = "HIGH"
    )
    private Priority priority;

    // ... constructores, getters y setters sin cambios
}
```

### 3.4 UserDTO.java

```java
package com.bootcamp.apidocs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(name = "User", description = "Representación de un usuario")
public class UserDTO {

    @Schema(
            description = "Identificador único del usuario",
            example = "550e8400-e29b-41d4-a716-446655440000",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(
            description = "Nombre de usuario único",
            example = "johndoe",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(
            description = "Fecha y hora de registro",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Número de tareas del usuario",
            example = "5",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer taskCount;

    // ... constructores, getters y setters sin cambios
}
```

### 3.5 CreateUserRequest.java

```java
package com.bootcamp.apidocs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(name = "CreateUserRequest", description = "Datos necesarios para crear un usuario")
public class CreateUserRequest {

    @Schema(
            description = "Nombre de usuario único",
            example = "johndoe",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 3,
            maxLength = 50
    )
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "El nombre de usuario solo puede contener letras, números y guiones bajos")
    private String username;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    // ... constructores, getters y setters sin cambios
}
```

### 3.6 ErrorResponse.java

```java
package com.bootcamp.apidocs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(name = "ErrorResponse", description = "Respuesta de error estandarizada de la API")
public class ErrorResponse {

    @Schema(description = "Código de estado HTTP", example = "404")
    private int status;

    @Schema(description = "Tipo de error", example = "Not Found")
    private String error;

    @Schema(description = "Mensaje descriptivo del error", example = "Usuario no encontrado con id: 550e8400...")
    private String message;

    @Schema(description = "Ruta del endpoint que generó el error", example = "/api/users/550e8400...")
    private String path;

    @Schema(description = "Momento en que ocurrió el error")
    private LocalDateTime timestamp;

    // ... constructores, getters y setters sin cambios
}
```

---

## 4. UserController.java

**Archivo**: `src/main/java/com/bootcamp/apidocs/controller/UserController.java`

```java
package com.bootcamp.apidocs.controller;

import com.bootcamp.apidocs.dto.CreateUserRequest;
import com.bootcamp.apidocs.dto.ErrorResponse;
import com.bootcamp.apidocs.dto.UserDTO;
import com.bootcamp.apidocs.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Usuarios", description = "Operaciones CRUD para la gestión de usuarios")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Retorna una lista con todos los usuarios registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de usuarios obtenida exitosamente"
    )
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(
            summary = "Obtener usuario por ID",
            description = "Busca y retorna un usuario específico por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(
                    description = "ID único del usuario (UUID)",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(
            summary = "Buscar usuario por username",
            description = "Busca un usuario por su nombre de usuario único"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/search")
    public ResponseEntity<UserDTO> getUserByUsername(
            @Parameter(
                    description = "Nombre de usuario a buscar",
                    required = true,
                    example = "johndoe"
            )
            @RequestParam String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @Operation(
            summary = "Crear nuevo usuario",
            description = "Crea un nuevo usuario en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "El usuario o email ya existe",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del nuevo usuario",
                    required = true
            )
            @Valid @RequestBody CreateUserRequest request) {
        UserDTO created = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario y todas sus tareas asociadas"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(
                    description = "ID único del usuario a eliminar",
                    required = true
            )
            @PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## 5. TaskController.java

**Archivo**: `src/main/java/com/bootcamp/apidocs/controller/TaskController.java`

```java
package com.bootcamp.apidocs.controller;

import com.bootcamp.apidocs.dto.*;
import com.bootcamp.apidocs.entity.Priority;
import com.bootcamp.apidocs.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Tareas", description = "Operaciones CRUD para la gestión de tareas")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Obtener todas las tareas (paginado)",
            description = "Retorna una lista paginada de todas las tareas"
    )
    @ApiResponse(responseCode = "200", description = "Lista de tareas obtenida exitosamente")
    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getAllTasks(
            @Parameter(description = "Número de página (comienza en 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo de ordenamiento", example = "createdAt,desc")
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(taskService.findAll(pageable));
    }

    @Operation(
            summary = "Obtener tarea por ID",
            description = "Busca y retorna una tarea específica por su ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea encontrada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarea no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(
            @Parameter(
                    description = "ID único de la tarea (UUID)",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000"
            )
            @PathVariable UUID id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @Operation(
            summary = "Obtener tareas por usuario",
            description = "Retorna todas las tareas de un usuario específico"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de tareas del usuario",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TaskDTO.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(
            @Parameter(description = "ID del usuario propietario", required = true)
            @PathVariable UUID userId) {
        return ResponseEntity.ok(taskService.findByUserId(userId));
    }

    @Operation(
            summary = "Filtrar tareas por estado",
            description = "Retorna las tareas de un usuario filtradas por estado (completada/pendiente)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tareas filtradas"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/user/{userId}/status")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable UUID userId,
            @Parameter(description = "Estado de completitud", example = "true")
            @RequestParam Boolean completed) {
        return ResponseEntity.ok(taskService.findByUserIdAndCompleted(userId, completed));
    }

    @Operation(
            summary = "Filtrar tareas por prioridad",
            description = "Retorna las tareas de un usuario filtradas por prioridad"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de tareas filtradas"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/user/{userId}/priority")
    public ResponseEntity<List<TaskDTO>> getTasksByPriority(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable UUID userId,
            @Parameter(
                    description = "Nivel de prioridad",
                    schema = @Schema(implementation = Priority.class)
            )
            @RequestParam Priority priority) {
        return ResponseEntity.ok(taskService.findByUserIdAndPriority(userId, priority));
    }

    @Operation(
            summary = "Crear nueva tarea",
            description = "Crea una nueva tarea asociada a un usuario"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarea creada exitosamente"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la nueva tarea",
                    required = true
            )
            @Valid @RequestBody CreateTaskRequest request) {
        TaskDTO created = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(
            summary = "Actualizar tarea",
            description = "Actualiza una tarea existente (actualización parcial)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea actualizada exitosamente"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarea no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @Parameter(description = "ID de la tarea a actualizar", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos a actualizar",
                    required = true
            )
            @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @Operation(
            summary = "Marcar tarea como completada",
            description = "Cambia el estado de una tarea a completada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarea marcada como completada"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarea no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskDTO> completeTask(
            @Parameter(description = "ID de la tarea", required = true)
            @PathVariable UUID id) {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setCompleted(true);
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @Operation(
            summary = "Eliminar tarea",
            description = "Elimina una tarea del sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarea eliminada exitosamente"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tarea no encontrada",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID de la tarea a eliminar", required = true)
            @PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## Resumen de TODOs Completados

| Archivo | TODOs Resueltos |
|---------|-----------------|
| OpenApiConfig.java | 1 |
| CorsConfig.java | 1 |
| TaskDTO.java | 10 |
| CreateTaskRequest.java | 6 |
| UpdateTaskRequest.java | 6 |
| UserDTO.java | 7 |
| CreateUserRequest.java | 4 |
| ErrorResponse.java | 7 |
| UserController.java | 16 |
| TaskController.java | 25 |
| **Total** | **83** |

---

## Verificación de Swagger UI

Una vez completados todos los TODOs, el estudiante debería poder:

1. Acceder a Swagger UI en: `http://localhost:8080/swagger-ui.html`
2. Ver la documentación OpenAPI en: `http://localhost:8080/api-docs`
3. Descargar especificación YAML en: `http://localhost:8080/api-docs.yaml`

### Resultado esperado en Swagger UI:

- **Info**: Título, versión, descripción, contacto, licencia
- **Tags**: Usuarios, Tareas (agrupación de endpoints)
- **Endpoints**: Cada uno con summary, description, parámetros documentados
- **Schemas**: DTOs con descripciones y ejemplos
- **Responses**: Códigos de estado con descripciones claras

---

## Comandos de Verificación

```bash
# Ejecutar localmente (H2)
./mvnw spring-boot:run

# Ejecutar con Docker
docker-compose up --build

# Verificar CORS (desde otro origen)
curl -v -X OPTIONS http://localhost:8080/api/users \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: GET"

# Obtener especificación OpenAPI
curl http://localhost:8080/api-docs | jq .
```
