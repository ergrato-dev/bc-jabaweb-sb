# 🛠️ Práctica 03: Documentar DTOs y Schemas

## 🎯 Objetivo

Documentar modelos de datos (DTOs) usando @Schema para mejorar la documentación en Swagger UI.

---

## 📋 Requisitos Previos

- Prácticas anteriores completadas
- DTOs existentes en el proyecto

---

## 📝 Ejercicio

### Paso 1: Documentar DTO de Respuesta

```java
package com.bootcamp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

// TODO 1: Agregar @Schema a nivel de clase con description
public record TaskDTO(
    // TODO 2: Agregar @Schema con description y example
    UUID id,

    // TODO 3: Agregar @Schema con description, example, minLength, maxLength
    String title,

    // TODO 4: Agregar @Schema con description, example, nullable=true
    String description,

    // TODO 5: Agregar @Schema con description, example, defaultValue
    Boolean completed,

    // TODO 6: Agregar @Schema con description y example de fecha
    LocalDateTime createdAt,

    // TODO 7: Agregar @Schema con accessMode = READ_ONLY
    LocalDateTime updatedAt
) {}
```

### Paso 2: Documentar DTO de Request (Create)

```java
package com.bootcamp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.util.UUID;

// TODO 1: Agregar @Schema describiendo el propósito del request
public record CreateTaskRequest(
    // TODO 2: Agregar @Schema con requiredMode = REQUIRED
    @NotBlank(message = "El título es requerido")
    @Size(min = 3, max = 100)
    String title,

    // TODO 3: Agregar @Schema con requiredMode = NOT_REQUIRED
    @Size(max = 500)
    String description,

    // TODO 4: Agregar @Schema con requiredMode = REQUIRED y example
    @NotNull(message = "El userId es requerido")
    UUID userId
) {}
```

### Paso 3: Documentar DTO de Request (Update)

```java
package com.bootcamp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

// TODO: Documentar todos los campos como opcionales
@Schema(description = "Request para actualizar una tarea existente")
public record UpdateTaskRequest(
    @Schema(
        description = "Nuevo título de la tarea",
        example = "Título actualizado",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Size(min = 3, max = 100)
    String title,

    // TODO: Completar documentación de description
    String description,

    // TODO: Completar documentación de completed
    Boolean completed
) {}
```

### Paso 4: Documentar ErrorResponse

```java
package com.bootcamp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Respuesta estándar de error de la API")
public record ErrorResponse(
    @Schema(description = "Momento del error", example = "2024-01-15T10:30:00")
    LocalDateTime timestamp,

    @Schema(description = "Código HTTP", example = "404")
    int status,

    @Schema(description = "Tipo de error", example = "Not Found")
    String error,

    @Schema(description = "Mensaje descriptivo", example = "Tarea no encontrada")
    String message,

    @Schema(description = "Path del request", example = "/api/v1/tasks/123")
    String path
) {}
```

### Paso 5: Verificar en Swagger UI

1. Reinicia la aplicación
2. Ve a Swagger UI
3. Scroll hasta la sección "Schemas"
4. Expande cada schema y verifica:
   - Descripciones visibles
   - Ejemplos correctos
   - Campos requeridos marcados

---

## ✅ Criterios de Éxito

- [ ] Todos los DTOs tienen @Schema a nivel de clase
- [ ] Todos los campos tienen description y example
- [ ] Campos opcionales marcados con nullable o NOT_REQUIRED
- [ ] Campos de solo lectura con READ_ONLY
- [ ] Validaciones documentadas (minLength, maxLength)

---

## 💡 Pistas

<details>
<summary>Ver TaskDTO completo</summary>

```java
@Schema(description = "DTO de respuesta para una tarea")
public record TaskDTO(
    @Schema(
        description = "Identificador único",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    UUID id,

    @Schema(
        description = "Título de la tarea",
        example = "Completar informe",
        minLength = 3,
        maxLength = 100
    )
    String title,

    @Schema(
        description = "Descripción detallada",
        example = "Elaborar el informe mensual",
        nullable = true
    )
    String description,

    @Schema(
        description = "Estado de completitud",
        example = "false",
        defaultValue = "false"
    )
    Boolean completed,

    @Schema(
        description = "Fecha de creación",
        example = "2024-01-15T10:30:00"
    )
    LocalDateTime createdAt,

    @Schema(
        description = "Última actualización",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    LocalDateTime updatedAt
) {}
```

</details>

---

## 🔗 Siguiente

Continúa con [04-configurar-cors.md](04-configurar-cors.md)
