# 🛠️ Práctica 02: Documentar Endpoints con Anotaciones

## 🎯 Objetivo

Aprender a documentar endpoints REST usando anotaciones OpenAPI (@Operation, @ApiResponse, @Parameter).

---

## 📋 Requisitos Previos

- Práctica 01 completada
- Controller REST existente

---

## 📝 Ejercicio

### Paso 1: Agregar @Tag al Controller

```java
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "tasks", description = "API para gestión de tareas")
public class TaskController {
    // ...
}
```

### Paso 2: Documentar GET All

```java
// TODO: Agregar @Operation con summary y description
// TODO: Agregar @ApiResponse para código 200
@GetMapping
public ResponseEntity<List<TaskDTO>> findAll() {
    return ResponseEntity.ok(taskService.findAll());
}
```

### Paso 3: Documentar GET by ID

```java
// TODO: Agregar @Operation
// TODO: Agregar @ApiResponses para códigos 200 y 404
// TODO: Agregar @Parameter para documentar el parámetro id
@GetMapping("/{id}")
public ResponseEntity<TaskDTO> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(taskService.findById(id));
}
```

### Paso 4: Documentar POST

```java
// TODO: Agregar @Operation
// TODO: Agregar @ApiResponses para códigos 201, 400
// TODO: Usar @Content y @Schema para documentar respuesta
@PostMapping
public ResponseEntity<TaskDTO> create(@Valid @RequestBody CreateTaskRequest request) {
    TaskDTO created = taskService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}
```

### Paso 5: Documentar PUT

```java
// TODO: Agregar @Operation
// TODO: Agregar @ApiResponses para códigos 200, 400, 404
// TODO: Documentar ambos parámetros (id y body)
@PutMapping("/{id}")
public ResponseEntity<TaskDTO> update(
    @PathVariable UUID id,
    @Valid @RequestBody UpdateTaskRequest request
) {
    return ResponseEntity.ok(taskService.update(id, request));
}
```

### Paso 6: Documentar DELETE

```java
// TODO: Agregar @Operation
// TODO: Agregar @ApiResponses para códigos 204, 404
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable UUID id) {
    taskService.delete(id);
    return ResponseEntity.noContent().build();
}
```

---

## 📦 Imports Necesarios

```java
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
```

---

## ✅ Criterios de Éxito

- [ ] Todos los endpoints tienen @Operation
- [ ] Cada endpoint documenta al menos 2 códigos de respuesta
- [ ] Los parámetros tienen @Parameter con descripción
- [ ] Los responses incluyen @Content con @Schema
- [ ] Swagger UI muestra toda la documentación

---

## 💡 Pistas

<details>
<summary>Ver ejemplo completo de GET by ID</summary>

```java
@GetMapping("/{id}")
@Operation(
    summary = "Obtener tarea por ID",
    description = "Busca y retorna una tarea por su UUID"
)
@ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Tarea encontrada",
        content = @Content(schema = @Schema(implementation = TaskDTO.class))
    ),
    @ApiResponse(
        responseCode = "404",
        description = "Tarea no encontrada",
        content = @Content
    )
})
public ResponseEntity<TaskDTO> findById(
    @Parameter(description = "UUID de la tarea", required = true)
    @PathVariable UUID id
) {
    return ResponseEntity.ok(taskService.findById(id));
}
```

</details>

---

## 🔗 Siguiente

Continúa con [03-documentar-schemas.md](03-documentar-schemas.md)
