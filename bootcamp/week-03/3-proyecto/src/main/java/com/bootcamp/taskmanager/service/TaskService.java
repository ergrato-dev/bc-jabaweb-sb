package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;

import java.util.List;

/**
 * Interfaz del servicio de tareas.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿POR QUÉ USAR UNA INTERFAZ?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Programar contra interfaces (no implementaciones) permite:
 *
 *   1. DESACOPLAMIENTO
 *      - El Controller depende de TaskService (interfaz)
 *      - No depende de TaskServiceImpl (implementación)
 *      - Puedes cambiar la implementación sin tocar el Controller
 *
 *   2. TESTABILIDAD
 *      - En tests puedes crear un Mock de TaskService fácilmente
 *      - Sin interfaz, mockear clases concretas es más complicado
 *
 *   3. MÚLTIPLES IMPLEMENTACIONES
 *      - Podrías tener TaskServiceImpl (en memoria)
 *      - Y TaskServiceDbImpl (con base de datos)
 *      - Spring inyecta la que configures
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * CONTRATO DE LA API DE SERVICIO
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * Esta interfaz define el CONTRATO de operaciones disponibles.
 * La implementación decide CÓMO hacerlo.
 *
 */
public interface TaskService {

    /**
     * Crea una nueva tarea.
     *
     * @param request Datos de la tarea a crear
     * @return La tarea creada
     */
    TaskResponse create(TaskRequest request);

    /**
     * Obtiene todas las tareas.
     *
     * @return Lista de todas las tareas
     */
    List<TaskResponse> findAll();

    /**
     * Obtiene una tarea por ID.
     *
     * @param id ID de la tarea
     * @return La tarea encontrada
     * @throws ResourceNotFoundException si no existe
     */
    TaskResponse findById(String id);

    /**
     * Actualiza una tarea existente.
     *
     * @param id ID de la tarea a actualizar
     * @param request Nuevos datos
     * @return La tarea actualizada
     * @throws ResourceNotFoundException si no existe
     */
    TaskResponse update(String id, TaskRequest request);

    /**
     * Elimina una tarea por ID.
     *
     * @param id ID de la tarea a eliminar
     * @throws ResourceNotFoundException si no existe
     */
    void delete(String id);

    /**
     * Marca una tarea como completada.
     *
     * @param id ID de la tarea
     * @return La tarea actualizada
     * @throws ResourceNotFoundException si no existe
     */
    TaskResponse markAsCompleted(String id);
}
