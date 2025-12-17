package com.bootcamp.taskmanager.controller;

import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;
import com.bootcamp.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para tareas.
 *
 * ⚠️ ARCHIVO DE PRÁCTICA: Completa los TODOs para implementar los endpoints.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 1: Implementar endpoint POST /api/tasks
    //
    // Anotación: @PostMapping
    // Parámetro: @Valid @RequestBody TaskRequest request
    // Retorno: ResponseEntity<TaskResponse>
    //
    // Pasos:
    // 1. Llamar taskService.create(request)
    // 2. Retornar ResponseEntity.status(HttpStatus.CREATED).body(response)
    // ═══════════════════════════════════════════════════════════
    public ResponseEntity<TaskResponse> create(TaskRequest request) {
        // TODO: Agregar anotaciones e implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 2: Implementar endpoint GET /api/tasks/{id}
    //
    // Anotación: @GetMapping("/{id}")
    // Parámetro: @PathVariable UUID id
    // Retorno: ResponseEntity<TaskResponse>
    //
    // Pasos:
    // 1. Llamar taskService.findById(id)
    // 2. Retornar ResponseEntity.ok(response)
    // ═══════════════════════════════════════════════════════════
    public ResponseEntity<TaskResponse> findById(UUID id) {
        // TODO: Agregar anotaciones e implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 3: Implementar endpoint GET /api/tasks
    //
    // Anotación: @GetMapping
    // Sin parámetros
    // Retorno: ResponseEntity<List<TaskResponse>>
    //
    // Pasos:
    // 1. Llamar taskService.findAll()
    // 2. Retornar ResponseEntity.ok(response)
    // ═══════════════════════════════════════════════════════════
    public ResponseEntity<List<TaskResponse>> findAll() {
        // TODO: Agregar anotaciones e implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 4: Implementar endpoint PUT /api/tasks/{id}
    //
    // Anotación: @PutMapping("/{id}")
    // Parámetros: @PathVariable UUID id, @Valid @RequestBody TaskRequest request
    // Retorno: ResponseEntity<TaskResponse>
    //
    // Pasos:
    // 1. Llamar taskService.update(id, request)
    // 2. Retornar ResponseEntity.ok(response)
    // ═══════════════════════════════════════════════════════════
    public ResponseEntity<TaskResponse> update(UUID id, TaskRequest request) {
        // TODO: Agregar anotaciones e implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 5: Implementar endpoint DELETE /api/tasks/{id}
    //
    // Anotación: @DeleteMapping("/{id}")
    // Parámetro: @PathVariable UUID id
    // Retorno: ResponseEntity<Void>
    //
    // Pasos:
    // 1. Llamar taskService.delete(id)
    // 2. Retornar ResponseEntity.noContent().build()
    // ═══════════════════════════════════════════════════════════
    public ResponseEntity<Void> delete(UUID id) {
        // TODO: Agregar anotaciones e implementar
        return null;
    }
}
