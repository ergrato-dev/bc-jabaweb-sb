package com.bootcamp.taskmanager.controller;

import com.bootcamp.taskmanager.dto.TaskDTO;
import com.bootcamp.taskmanager.dto.request.CreateTaskRequest;
import com.bootcamp.taskmanager.dto.request.UpdateTaskRequest;
import com.bootcamp.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ============================================================
    // TODO 1: GET /api/tasks - Listar todas las tareas
    // ============================================================
    @GetMapping
    public ResponseEntity<List<TaskDTO>> findAll() {
        // TODO: Implementar
        return ResponseEntity.ok(List.of());
    }


    // ============================================================
    // TODO 2: GET /api/tasks/{id} - Obtener tarea por ID
    // ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable UUID id) {
        // TODO: Implementar
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 3: GET /api/tasks/user/{userId} - Tareas de un usuario
    // ============================================================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> findByUserId(@PathVariable UUID userId) {
        // TODO: Implementar
        return ResponseEntity.ok(List.of());
    }


    // ============================================================
    // TODO 4: POST /api/tasks - Crear tarea
    // ============================================================
    @PostMapping
    public ResponseEntity<TaskDTO> create(@Valid @RequestBody CreateTaskRequest request) {
        // TODO: Implementar
        // TaskDTO created = taskService.create(request);
        // return ResponseEntity.status(HttpStatus.CREATED).body(created);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // ============================================================
    // TODO 5: PUT /api/tasks/{id} - Actualizar tarea
    // ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTaskRequest request) {
        // TODO: Implementar
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 6: POST /api/tasks/{id}/categories/{categoryId} - Agregar categoría
    // ============================================================
    @PostMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<TaskDTO> addCategory(
            @PathVariable UUID id,
            @PathVariable UUID categoryId) {
        // TODO: Implementar
        // TaskDTO updated = taskService.addCategory(id, categoryId);
        // return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 7: DELETE /api/tasks/{id}/categories/{categoryId} - Quitar categoría
    // ============================================================
    @DeleteMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<TaskDTO> removeCategory(
            @PathVariable UUID id,
            @PathVariable UUID categoryId) {
        // TODO: Implementar
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 8: DELETE /api/tasks/{id} - Eliminar tarea
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // TODO: Implementar
        return ResponseEntity.noContent().build();
    }
}
