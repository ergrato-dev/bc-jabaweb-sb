package com.bootcamp.todo.controller;

import com.bootcamp.todo.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar Tareas (Tasks).
 *
 * @RestController = @Controller + @ResponseBody
 * - Los métodos retornan datos (JSON), no vistas
 *
 * @RequestMapping("/api/tasks")
 * - Prefijo para todas las URLs de este controlador
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    /**
     * Lista de tareas en memoria (simula base de datos).
     * En semanas posteriores usaremos JPA + PostgreSQL.
     */
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    /**
     * Constructor - inicializa con datos de ejemplo
     */
    public TaskController() {
        // Datos de ejemplo para probar
        // Nota: Estos funcionarán cuando completes los TODOs del modelo Task
        // tasks.add(new Task(nextId++, "Aprender Spring Boot", "Completar el bootcamp semana 02"));
        // tasks.add(new Task(nextId++, "Configurar Docker", "Crear docker-compose.yml"));
        // tasks.add(new Task(nextId++, "Crear endpoints REST", "Implementar CRUD completo"));
    }

    // =========================================================================
    // GET /api/tasks - Listar todas las tareas
    // =========================================================================

    /**
     * Retorna todas las tareas.
     *
     * @return Lista de tareas (puede estar vacía)
     */
    @GetMapping
    public List<Task> getAll() {
        return tasks;
    }

    // =========================================================================
    // GET /api/tasks/{id} - Obtener una tarea por ID
    // =========================================================================

    // TODO 1: Implementar getById
    //
    // Requisitos:
    // - Anotación: @GetMapping("/{id}")
    // - Parámetro: @PathVariable Long id
    // - Retorno: ResponseEntity<Task>
    // - Si existe: retornar 200 OK con la tarea
    // - Si no existe: retornar 404 Not Found
    //
    // Pistas:
    // - Usa tasks.stream().filter(t -> t.getId().equals(id)).findFirst()
    // - Esto retorna Optional<Task>
    // - Usa .map(ResponseEntity::ok) para 200
    // - Usa .orElse(ResponseEntity.notFound().build()) para 404
    //
    // @GetMapping("/{id}")
    // public ResponseEntity<Task> getById(@PathVariable Long id) {
    //     ???
    // }


    // =========================================================================
    // POST /api/tasks - Crear nueva tarea
    // =========================================================================

    // TODO 2: Implementar create
    //
    // Requisitos:
    // - Anotación: @PostMapping
    // - Parámetro: @RequestBody Task task
    // - Retorno: ResponseEntity<Task>
    // - Asignar ID automáticamente (nextId++)
    // - Agregar a la lista
    // - Retornar 201 Created con la tarea creada
    //
    // Pistas:
    // - task.setId(nextId++);
    // - tasks.add(task);
    // - ResponseEntity.status(HttpStatus.CREATED).body(task);
    //
    // @PostMapping
    // public ResponseEntity<Task> create(@RequestBody Task task) {
    //     ???
    // }


    // =========================================================================
    // PUT /api/tasks/{id} - Actualizar tarea existente
    // =========================================================================

    // TODO 3: Implementar update
    //
    // Requisitos:
    // - Anotación: @PutMapping("/{id}")
    // - Parámetros: @PathVariable Long id, @RequestBody Task updatedTask
    // - Retorno: ResponseEntity<Task>
    // - Buscar la tarea por ID
    // - Si existe: actualizar title, description y completed, retornar 200 OK
    // - Si no existe: retornar 404 Not Found
    //
    // Pistas:
    // - Busca con stream().filter().findFirst()
    // - Si existe, actualiza con setTitle(), setDescription(), setCompleted()
    // - El ID y createdAt NO deben cambiar
    //
    // @PutMapping("/{id}")
    // public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody Task updatedTask) {
    //     ???
    // }


    // =========================================================================
    // DELETE /api/tasks/{id} - Eliminar tarea
    // =========================================================================

    // TODO 4: Implementar delete
    //
    // Requisitos:
    // - Anotación: @DeleteMapping("/{id}")
    // - Parámetro: @PathVariable Long id
    // - Retorno: ResponseEntity<Void>
    // - Si existe: eliminar y retornar 204 No Content
    // - Si no existe: retornar 404 Not Found
    //
    // Pistas:
    // - Usa tasks.removeIf(t -> t.getId().equals(id))
    // - removeIf() retorna true si eliminó algo
    // - Para 204: ResponseEntity.noContent().build()
    //
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable Long id) {
    //     ???
    // }


    // =========================================================================
    // GET /api/tasks/filter?completed=true|false - Filtrar por estado
    // =========================================================================

    // TODO 5: Implementar filterByCompleted
    //
    // Requisitos:
    // - Anotación: @GetMapping("/filter")
    // - Parámetro: @RequestParam Boolean completed
    // - Retorno: List<Task>
    // - Filtrar tareas donde task.isCompleted() == completed
    //
    // Pistas:
    // - Usa stream().filter(t -> t.isCompleted() == completed)
    // - Termina con .collect(Collectors.toList()) o .toList()
    //
    // Ejemplo de uso:
    // GET /api/tasks/filter?completed=true  -> tareas completadas
    // GET /api/tasks/filter?completed=false -> tareas pendientes
    //
    // @GetMapping("/filter")
    // public List<Task> filterByCompleted(@RequestParam Boolean completed) {
    //     ???
    // }


    // =========================================================================
    // Método auxiliar (opcional, para ayudarte)
    // =========================================================================

    /**
     * Busca una tarea por ID.
     * @param id ID de la tarea
     * @return Optional con la tarea si existe
     */
    private Optional<Task> findById(Long id) {
        return tasks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }
}
