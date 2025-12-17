package com.bootcamp.apidocs.controller;

import com.bootcamp.apidocs.dto.*;
import com.bootcamp.apidocs.entity.Priority;
import com.bootcamp.apidocs.service.TaskService;
// TODO 1: Importar las anotaciones de io.swagger.v3.oas.annotations:
//   - @Tag (para el controlador)
//   - @Operation, @ApiResponse, @ApiResponses (para los métodos)
//   - @Parameter (para parámetros)
// TODO 2: Importar @Content, @Schema de io.swagger.v3.oas.annotations.media
// TODO 3: Importar @ArraySchema para listas de objetos
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para la gestión de tareas.
 *
 * TODO 4: Añadir @Tag con:
 *   - name = "Tareas"
 *   - description = "Operaciones CRUD para la gestión de tareas"
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * TODO 5: Añadir @Operation con:
     *   - summary = "Obtener todas las tareas (paginado)"
     *   - description = "Retorna una lista paginada de todas las tareas"
     *
     * TODO 6: Añadir @ApiResponse para código 200
     *
     * TODO 7: Añadir @Parameter para cada parámetro de paginación:
     *   - page (número de página, default 0)
     *   - size (tamaño de página, default 10)
     *   - sort (ordenamiento, ejemplo: "createdAt,desc")
     */
    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getAllTasks(
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(taskService.findAll(pageable));
    }

    /**
     * TODO 8: Añadir @Operation con:
     *   - summary = "Obtener tarea por ID"
     *   - description = "Busca y retorna una tarea específica por su ID"
     *
     * TODO 9: Añadir @ApiResponses con respuestas 200 y 404
     *
     * TODO 10: Añadir @Parameter al parámetro id
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(
            @PathVariable UUID id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    /**
     * TODO 11: Añadir @Operation con:
     *   - summary = "Obtener tareas por usuario"
     *   - description = "Retorna todas las tareas de un usuario específico"
     *
     * TODO 12: Añadir @ApiResponses con respuestas 200 y 404 (usuario no encontrado)
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUserId(
            @PathVariable UUID userId) {
        return ResponseEntity.ok(taskService.findByUserId(userId));
    }

    /**
     * TODO 13: Añadir @Operation con:
     *   - summary = "Filtrar tareas por estado"
     *   - description = "Retorna las tareas de un usuario filtradas por estado (completada/pendiente)"
     *
     * TODO 14: Añadir @Parameter para completed con:
     *   - description = "Estado de completitud"
     *   - example = "true"
     */
    @GetMapping("/user/{userId}/status")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(
            @PathVariable UUID userId,
            @RequestParam Boolean completed) {
        return ResponseEntity.ok(taskService.findByUserIdAndCompleted(userId, completed));
    }

    /**
     * TODO 15: Añadir @Operation con:
     *   - summary = "Filtrar tareas por prioridad"
     *   - description = "Retorna las tareas de un usuario filtradas por prioridad"
     *
     * TODO 16: Añadir @Parameter para priority con:
     *   - description = "Nivel de prioridad"
     *   - schema con enum de Priority (LOW, MEDIUM, HIGH)
     */
    @GetMapping("/user/{userId}/priority")
    public ResponseEntity<List<TaskDTO>> getTasksByPriority(
            @PathVariable UUID userId,
            @RequestParam Priority priority) {
        return ResponseEntity.ok(taskService.findByUserIdAndPriority(userId, priority));
    }

    /**
     * TODO 17: Añadir @Operation con:
     *   - summary = "Crear nueva tarea"
     *   - description = "Crea una nueva tarea asociada a un usuario"
     *
     * TODO 18: Añadir @ApiResponses con:
     *   - 201: Tarea creada exitosamente
     *   - 400: Datos de entrada inválidos
     *   - 404: Usuario no encontrado
     *
     * TODO 19: Añadir @io.swagger.v3.oas.annotations.parameters.RequestBody
     */
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @Valid @RequestBody CreateTaskRequest request) {
        TaskDTO created = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * TODO 20: Añadir @Operation con:
     *   - summary = "Actualizar tarea"
     *   - description = "Actualiza una tarea existente (actualización parcial)"
     *
     * TODO 21: Añadir @ApiResponses con respuestas 200, 400 y 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    /**
     * TODO 22: Añadir @Operation con:
     *   - summary = "Marcar tarea como completada"
     *   - description = "Cambia el estado de una tarea a completada"
     *
     * TODO 23: Añadir @ApiResponses con respuestas 200 y 404
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskDTO> completeTask(
            @PathVariable UUID id) {
        UpdateTaskRequest request = new UpdateTaskRequest();
        request.setCompleted(true);
        return ResponseEntity.ok(taskService.update(id, request));
    }

    /**
     * TODO 24: Añadir @Operation con:
     *   - summary = "Eliminar tarea"
     *   - description = "Elimina una tarea del sistema"
     *
     * TODO 25: Añadir @ApiResponses con respuestas 204 y 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
