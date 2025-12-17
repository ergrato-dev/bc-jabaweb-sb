# 🔧 Práctica 01: Refactorizar en Capas

## Objetivo

Transformar el proyecto de la Semana 02 (TODO en Controller) a una arquitectura en capas con Controller, Service y Repository separados.

---

## 📋 Requisitos Previos

- Proyecto de la Semana 02 funcionando
- Docker Desktop corriendo
- Conocimiento de teoría de arquitectura en capas

---

## Parte 1: Analizar el Código Actual

### 1.1 Revisar el Controller de la Semana 02

El código actual tiene todo mezclado en el Controller:

```java
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;  // Acceso directo a datos
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setId(nextId++);  // Lógica de negocio
        task.setCompleted(false);
        tasks.add(task);  // Persistencia
        return task;
    }
}
```

**Problemas identificados:**
- [ ] Controller accede directamente a los datos
- [ ] Lógica de negocio en el Controller
- [ ] Difícil de probar
- [ ] No hay separación de responsabilidades

---

## Parte 2: Crear la Estructura de Paquetes

### 2.1 Crear los paquetes

En `src/main/java/com/bootcamp/taskmanager/` crear:

```
com.bootcamp.taskmanager/
├── TaskManagerApplication.java
├── controller/
│   └── TaskController.java
├── service/
│   ├── TaskService.java          # Interface
│   └── impl/
│       └── TaskServiceImpl.java  # Implementación
├── repository/
│   └── TaskRepository.java
└── model/
    └── Task.java
```

---

## Parte 3: Implementar la Capa Repository

### 3.1 Crear TaskRepository

```java
package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TaskRepository {

    // TODO: Almacenamiento en memoria (en Semana 04 usaremos JPA)
    private final Map<Long, Task> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Guarda una tarea (nueva o existente)
     */
    public Task save(Task task) {
        // TODO: Si no tiene ID, asignar uno nuevo
        if (task.getId() == null) {
            task.setId(idGenerator.getAndIncrement());
        }
        storage.put(task.getId(), task);
        return task;
    }

    /**
     * Busca una tarea por ID
     */
    public Optional<Task> findById(Long id) {
        // TODO: Retornar Optional para manejar caso de no encontrado
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Retorna todas las tareas
     */
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Elimina una tarea por ID
     */
    public void deleteById(Long id) {
        storage.remove(id);
    }

    /**
     * Verifica si existe una tarea con el ID dado
     */
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }

    /**
     * Cuenta el total de tareas
     */
    public long count() {
        return storage.size();
    }
}
```

---

## Parte 4: Implementar la Capa Service

### 4.1 Crear la Interface TaskService

```java
package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.model.Task;

import java.util.List;

/**
 * Interface que define las operaciones de negocio para Task
 */
public interface TaskService {

    /**
     * Obtiene todas las tareas
     */
    List<Task> getAllTasks();

    /**
     * Obtiene una tarea por su ID
     * @throws RuntimeException si no existe
     */
    Task getTaskById(Long id);

    /**
     * Crea una nueva tarea
     */
    Task createTask(Task task);

    /**
     * Actualiza una tarea existente
     * @throws RuntimeException si no existe
     */
    Task updateTask(Long id, Task task);

    /**
     * Elimina una tarea por su ID
     * @throws RuntimeException si no existe
     */
    void deleteTask(Long id);

    /**
     * Marca una tarea como completada
     */
    Task completeTask(Long id);
}
```

### 4.2 Crear la Implementación TaskServiceImpl

```java
package com.bootcamp.taskmanager.service.impl;

import com.bootcamp.taskmanager.model.Task;
import com.bootcamp.taskmanager.repository.TaskRepository;
import com.bootcamp.taskmanager.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    // TODO: Inyección por constructor (Spring la resuelve automáticamente)
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        // TODO: Usar orElseThrow para manejar caso de no encontrado
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
    }

    @Override
    public Task createTask(Task task) {
        // TODO: Lógica de negocio - establecer valores iniciales
        task.setId(null); // Asegurar que sea nueva
        task.setCompleted(false);
        task.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task taskDetails) {
        // TODO: Verificar que existe antes de actualizar
        Task existingTask = getTaskById(id);

        // Actualizar campos permitidos
        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setUpdatedAt(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        // TODO: Verificar que existe antes de eliminar
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    public Task completeTask(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        task.setCompletedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }
}
```

---

## Parte 5: Refactorizar el Controller

### 5.1 Controller Refactorizado

```java
package com.bootcamp.taskmanager.controller;

import com.bootcamp.taskmanager.model.Task;
import com.bootcamp.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // TODO: Inyección por constructor del Service
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        // TODO: Delegar al service, no acceder a datos directamente
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        // TODO: El controller solo recibe y delega
        Task created = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {
        Task updated = taskService.updateTask(id, task);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long id) {
        Task completed = taskService.completeTask(id);
        return ResponseEntity.ok(completed);
    }
}
```

---

## Parte 6: Actualizar el Modelo

### 6.1 Task con campos adicionales

```java
package com.bootcamp.taskmanager.model;

import java.time.LocalDateTime;

public class Task {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    // Constructor vacío (necesario para deserialización JSON)
    public Task() {}

    // Constructor con campos principales
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    // TODO: Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}
```

---

## Parte 7: Probar

### 7.1 Levantar la aplicación

```bash
docker compose up --build
```

### 7.2 Probar endpoints

```bash
# Crear tarea
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Aprender capas", "description": "Refactorizar código"}'

# Listar tareas
curl http://localhost:8080/api/tasks

# Obtener tarea por ID
curl http://localhost:8080/api/tasks/1

# Completar tarea
curl -X PATCH http://localhost:8080/api/tasks/1/complete

# Eliminar tarea
curl -X DELETE http://localhost:8080/api/tasks/1
```

---

## ✅ Checklist de Verificación

- [ ] Paquete `repository` creado con `TaskRepository`
- [ ] Paquete `service` creado con `TaskService` (interface) y `TaskServiceImpl`
- [ ] `TaskController` refactorizado, solo delega al service
- [ ] Inyección por constructor funcionando
- [ ] Todos los endpoints responden correctamente
- [ ] `docker compose up` funciona sin errores

---

## 🎯 Reto Adicional

1. Agregar un endpoint `GET /api/tasks/completed` que retorne solo tareas completadas
2. Agregar un endpoint `GET /api/tasks/pending` que retorne solo tareas pendientes
3. Implementar búsqueda por título: `GET /api/tasks/search?title=texto`

---

## 📚 Recursos

- [Spring @Service Annotation](https://www.baeldung.com/spring-component-repository-service)
- [Dependency Injection in Spring](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-dependencies)
