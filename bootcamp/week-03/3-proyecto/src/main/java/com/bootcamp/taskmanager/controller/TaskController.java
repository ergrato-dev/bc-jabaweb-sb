package com.bootcamp.taskmanager.controller;

// TODO 19: Importar las clases necesarias
//
// Importaciones necesarias:
//   import com.bootcamp.taskmanager.dto.TaskRequest;
//   import com.bootcamp.taskmanager.dto.TaskResponse;
//   import com.bootcamp.taskmanager.service.TaskService;
//   import jakarta.validation.Valid;
//   import org.springframework.http.HttpStatus;
//   import org.springframework.http.ResponseEntity;
//   import org.springframework.web.bind.annotation.*;
//   import java.util.List;

import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;
import com.bootcamp.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gestión de tareas - Capa de PRESENTACIÓN.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿QUÉ ES LA CAPA CONTROLLER?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * La capa Controller es responsable de:
 *   - Recibir peticiones HTTP
 *   - Validar datos de entrada (con @Valid)
 *   - Delegar al Service
 *   - Formatear respuestas HTTP
 *
 * ¿QUÉ NO DEBE HACER?
 *   - Lógica de negocio (eso va en Service)
 *   - Acceso a datos (eso va en Repository)
 *   - Transformaciones complejas de datos
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ANOTACIONES CLAVE
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * @RestController = @Controller + @ResponseBody
 *   - Indica que es un controller REST
 *   - Retorna JSON automáticamente (no vistas HTML)
 *
 * @RequestMapping("/api/tasks")
 *   - Base path para todos los endpoints de este controller
 *   - Todos los métodos heredan este prefijo
 *
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // =========================================================================
    // TODO 20: Inyección del servicio
    // =========================================================================
    //
    // El controller NECESITA el servicio para delegar operaciones.
    //
    // Usa inyección por constructor (igual que en TaskServiceImpl):
    //
    // private final TaskService taskService;
    //
    // public TaskController(TaskService taskService) {
    //     this.taskService = taskService;
    // }
    //
    // 💡 Nota: Inyectamos TaskService (interfaz), NO TaskServiceImpl (implementación)
    //    Spring automáticamente encuentra la implementación correcta.


    // =========================================================================
    // ENDPOINTS CRUD
    // =========================================================================

    /**
     * POST /api/tasks - Crear una nueva tarea
     */
    // TODO 21: Implementar endpoint de creación
    //
    // Puntos clave:
    //   1. @PostMapping indica que responde a HTTP POST
    //   2. @Valid activa la validación del DTO (Bean Validation)
    //   3. @RequestBody indica que el JSON del body se deserializa a TaskRequest
    //   4. Retorna HTTP 201 (Created) en caso de éxito
    //
    // DESCOMENTA y completa:
    //
    // @PostMapping
    // public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request) {
    //     TaskResponse response = taskService.???;
    //     return ResponseEntity
    //         .status(HttpStatus.???)      // CREATED (201)
    //         .body(response);
    // }


    /**
     * GET /api/tasks - Obtener todas las tareas
     */
    // TODO 22: Implementar endpoint para listar todas las tareas
    //
    // Puntos clave:
    //   1. @GetMapping sin path = responde a GET /api/tasks
    //   2. No necesita @Valid ni @RequestBody (es GET, no recibe body)
    //   3. Retorna HTTP 200 (OK) con la lista
    //
    // DESCOMENTA y completa:
    //
    // @GetMapping
    // public ResponseEntity<List<TaskResponse>> findAll() {
    //     List<TaskResponse> tasks = taskService.???;
    //     return ResponseEntity.ok(tasks);
    // }


    /**
     * GET /api/tasks/{id} - Obtener una tarea por ID
     */
    // TODO 23: Implementar endpoint para obtener por ID
    //
    // Puntos clave:
    //   1. @GetMapping("/{id}") captura el ID de la URL
    //   2. @PathVariable extrae el valor de {id}
    //   3. Si no existe, el Service lanza ResourceNotFoundException
    //      y el GlobalExceptionHandler retorna 404
    //
    // DESCOMENTA y completa:
    //
    // @GetMapping("/{id}")
    // public ResponseEntity<TaskResponse> findById(@PathVariable String id) {
    //     TaskResponse response = taskService.???;
    //     return ResponseEntity.ok(response);
    // }


    /**
     * PUT /api/tasks/{id} - Actualizar una tarea
     */
    // TODO 24: Implementar endpoint de actualización
    //
    // Puntos clave:
    //   1. @PutMapping("/{id}") para HTTP PUT
    //   2. Combina @PathVariable (id de URL) + @RequestBody (datos nuevos)
    //   3. @Valid valida el request body
    //   4. Retorna HTTP 200 (OK) con la tarea actualizada
    //
    // DESCOMENTA y completa:
    //
    // @PutMapping("/{id}")
    // public ResponseEntity<TaskResponse> update(
    //         @PathVariable String id,
    //         @Valid @RequestBody TaskRequest request) {
    //     TaskResponse response = taskService.???;
    //     return ResponseEntity.ok(response);
    // }


    /**
     * DELETE /api/tasks/{id} - Eliminar una tarea
     */
    // TODO 25: Implementar endpoint de eliminación
    //
    // Puntos clave:
    //   1. @DeleteMapping("/{id}") para HTTP DELETE
    //   2. Retorna HTTP 204 (No Content) - indica éxito sin body
    //   3. ResponseEntity.noContent().build() crea respuesta 204 vacía
    //
    // DESCOMENTA y completa:
    //
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable String id) {
    //     taskService.???;
    //     return ResponseEntity.noContent().build();
    // }


    /**
     * PATCH /api/tasks/{id}/complete - Marcar tarea como completada
     */
    // TODO 26: Implementar endpoint para marcar como completada
    //
    // Puntos clave:
    //   1. @PatchMapping para modificaciones parciales
    //   2. El path incluye la acción: /{id}/complete
    //   3. No necesita body - la acción está en la URL
    //   4. Es un endpoint de "acción" (RPC-like), aceptable en REST
    //
    // DESCOMENTA y completa:
    //
    // @PatchMapping("/{id}/complete")
    // public ResponseEntity<TaskResponse> markAsCompleted(@PathVariable String id) {
    //     TaskResponse response = taskService.???;
    //     return ResponseEntity.ok(response);
    // }

}
