package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;
import com.bootcamp.taskmanager.exception.ResourceNotFoundException;
import com.bootcamp.taskmanager.model.Task;
import com.bootcamp.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementación del servicio de tareas con JPA.
 *
 * ⚠️ ARCHIVO DE PRÁCTICA: Completa los TODOs para implementar los métodos.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 1: Implementar método create()
    //
    // Pasos:
    // 1. Crear nueva instancia de Task
    // 2. Asignar title desde request.title()
    // 3. Asignar description desde request.description()
    // 4. Asignar completed = false
    // 5. Guardar con taskRepository.save(task)
    // 6. Retornar toResponse(saved)
    //
    // Nota: NO asignar ID ni fechas (JPA lo hace automáticamente)
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional
    public TaskResponse create(TaskRequest request) {
        // TODO: Implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 2: Implementar método findById()
    //
    // Pasos:
    // 1. Buscar con taskRepository.findById(id)
    // 2. Si no existe, lanzar: new ResourceNotFoundException("Task", "id", id)
    // 3. Retornar toResponse(task)
    //
    // Hint: Usar .orElseThrow() del Optional
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public TaskResponse findById(UUID id) {
        // TODO: Implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 3: Implementar método findAll()
    //
    // Pasos:
    // 1. Obtener todas las tareas con taskRepository.findAll()
    // 2. Convertir cada Task a TaskResponse usando stream y map
    // 3. Retornar como List
    //
    // Hint: .stream().map(this::toResponse).toList()
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> findAll() {
        // TODO: Implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 4: Implementar método update()
    //
    // Pasos:
    // 1. Buscar tarea existente por id (lanzar excepción si no existe)
    // 2. Actualizar title desde request
    // 3. Actualizar description desde request
    // 4. Si request.completed() no es null, actualizar completed
    // 5. Guardar con taskRepository.save(task)
    // 6. Retornar toResponse(updated)
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional
    public TaskResponse update(UUID id, TaskRequest request) {
        // TODO: Implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 5: Implementar método delete()
    //
    // Pasos:
    // 1. Verificar que existe con taskRepository.existsById(id)
    // 2. Si no existe, lanzar ResourceNotFoundException
    // 3. Eliminar con taskRepository.deleteById(id)
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional
    public void delete(UUID id) {
        // TODO: Implementar
    }

    // ═══════════════════════════════════════════════════════════
    // Método auxiliar para convertir Task a TaskResponse
    // (Ya implementado - usar en los métodos anteriores)
    // ═══════════════════════════════════════════════════════════
    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
