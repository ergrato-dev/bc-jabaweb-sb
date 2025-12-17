# 🔧 Práctica 02: DTOs y Validación

## Objetivo

Implementar DTOs (Data Transfer Objects) para separar la representación de la API de las entidades internas, y agregar validaciones con Bean Validation.

---

## 📋 Requisitos Previos

- Práctica 01 completada (arquitectura en capas)
- Dependencia `spring-boot-starter-validation` en pom.xml

---

## Parte 1: Agregar Dependencia de Validación

### 1.1 Verificar pom.xml

```xml
<!-- En la sección de dependencias -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

---

## Parte 2: Crear DTOs

### 2.1 Crear el paquete dto

```
src/main/java/com/bootcamp/taskmanager/
└── dto/
    ├── TaskRequest.java    # Datos de entrada
    └── TaskResponse.java   # Datos de salida
```

### 2.2 Crear TaskRequest (entrada)

```java
package com.bootcamp.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para crear/actualizar tareas
 * Solo contiene los campos que el cliente puede enviar
 */
public class TaskRequest {

    @NotBlank(message = "El título es requerido")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String title;

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String description;

    // TODO: Constructor vacío para deserialización
    public TaskRequest() {}

    public TaskRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getters y Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
```

### 2.3 Crear TaskResponse (salida)

```java
package com.bootcamp.taskmanager.dto;

import java.time.LocalDateTime;

/**
 * DTO para respuestas de tareas
 * Controla qué información se expone al cliente
 */
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;

    // TODO: Constructor completo
    public TaskResponse(Long id, String title, String description,
                        boolean completed, LocalDateTime createdAt,
                        LocalDateTime completedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    // Solo Getters (inmutable)
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
}
```

---

## Parte 3: Crear Mapper

### 3.1 Crear TaskMapper

```java
package com.bootcamp.taskmanager.dto;

import com.bootcamp.taskmanager.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Convierte entre Task (entity) y DTOs
 */
@Component
public class TaskMapper {

    /**
     * Convierte TaskRequest a Task (entity)
     */
    public Task toEntity(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        return task;
    }

    /**
     * Convierte Task (entity) a TaskResponse
     */
    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isCompleted(),
            task.getCreatedAt(),
            task.getCompletedAt()
        );
    }

    /**
     * Convierte lista de Task a lista de TaskResponse
     */
    public List<TaskResponse> toResponseList(List<Task> tasks) {
        return tasks.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    /**
     * Actualiza una entidad existente con datos del request
     */
    public void updateEntity(Task task, TaskRequest request) {
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
    }
}
```

---

## Parte 4: Actualizar Service

### 4.1 Modificar TaskService interface

```java
package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;

import java.util.List;

public interface TaskService {

    List<TaskResponse> getAllTasks();

    TaskResponse getTaskById(Long id);

    TaskResponse createTask(TaskRequest request);

    TaskResponse updateTask(Long id, TaskRequest request);

    void deleteTask(Long id);

    TaskResponse completeTask(Long id);
}
```

### 4.2 Modificar TaskServiceImpl

```java
package com.bootcamp.taskmanager.service.impl;

import com.bootcamp.taskmanager.dto.TaskMapper;
import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;
import com.bootcamp.taskmanager.model.Task;
import com.bootcamp.taskmanager.repository.TaskRepository;
import com.bootcamp.taskmanager.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    // TODO: Inyectar también el TaskMapper
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        // TODO: Usar mapper para convertir a DTOs
        return taskMapper.toResponseList(tasks);
    }

    @Override
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        return taskMapper.toResponse(task);
    }

    @Override
    public TaskResponse createTask(TaskRequest request) {
        // TODO: Convertir DTO a Entity
        Task task = taskMapper.toEntity(request);
        task.setCompleted(false);
        task.setCreatedAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);
        // TODO: Convertir Entity a DTO para respuesta
        return taskMapper.toResponse(saved);
    }

    @Override
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task existingTask = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        // TODO: Usar mapper para actualizar
        taskMapper.updateEntity(existingTask, request);
        existingTask.setUpdatedAt(LocalDateTime.now());

        Task saved = taskRepository.save(existingTask);
        return taskMapper.toResponse(saved);
    }

    @Override
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponse completeTask(Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setCompleted(true);
        task.setCompletedAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);
        return taskMapper.toResponse(saved);
    }
}
```

---

## Parte 5: Actualizar Controller con Validación

### 5.1 Modificar TaskController

```java
package com.bootcamp.taskmanager.controller;

import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;
import com.bootcamp.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request) {  // TODO: @Valid activa validación
        TaskResponse created = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {  // TODO: @Valid también aquí
        return ResponseEntity.ok(taskService.updateTask(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> completeTask(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.completeTask(id));
    }
}
```

---

## Parte 6: Probar Validaciones

### 6.1 Levantar aplicación

```bash
docker compose up --build
```

### 6.2 Probar validación exitosa

```bash
# Crear tarea válida
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Tarea válida", "description": "Con descripción"}'
```

### 6.3 Probar errores de validación

```bash
# Título vacío - debe fallar
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "", "description": "Sin título"}'

# Título muy corto - debe fallar
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "AB", "description": "Título corto"}'

# Sin título - debe fallar
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"description": "Sin campo title"}'
```

---

## ✅ Checklist de Verificación

- [ ] Dependencia `spring-boot-starter-validation` agregada
- [ ] `TaskRequest` creado con validaciones
- [ ] `TaskResponse` creado
- [ ] `TaskMapper` implementado
- [ ] Service actualizado para usar DTOs
- [ ] Controller usa `@Valid` en `@RequestBody`
- [ ] Errores de validación retornan HTTP 400

---

## 🎯 Reto Adicional

1. Agregar validación `@Email` a un campo de email en el Task
2. Crear un DTO diferente para actualización (`TaskUpdateRequest`) que permita actualización parcial
3. Usar Java Records en lugar de clases para los DTOs

---

## 📚 Recursos

- [Bean Validation Annotations](https://jakarta.ee/specifications/bean-validation/3.0/jakarta-bean-validation-spec-3.0.html)
- [Spring Validation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation)
