package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.dto.TaskDTO;
import com.bootcamp.taskmanager.dto.request.CreateTaskRequest;
import com.bootcamp.taskmanager.dto.request.UpdateTaskRequest;
import com.bootcamp.taskmanager.entity.Category;
import com.bootcamp.taskmanager.entity.Task;
import com.bootcamp.taskmanager.entity.User;
import com.bootcamp.taskmanager.repository.CategoryRepository;
import com.bootcamp.taskmanager.repository.TaskRepository;
import com.bootcamp.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository,
                       CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    // ============================================================
    // TODO 1: Listar todas las tareas
    // ============================================================
    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        // TODO: Implementar
        // Usar stream().map(TaskDTO::simpleFromEntity) para evitar cargar relaciones
        return List.of();
    }


    // ============================================================
    // TODO 2: Buscar tareas de un usuario
    // ============================================================
    @Transactional(readOnly = true)
    public List<TaskDTO> findByUserId(UUID userId) {
        // TODO: Usar taskRepository.findByUserIdWithCategories(userId)
        // Mapear a TaskDTO
        return List.of();
    }


    // ============================================================
    // TODO 3: Buscar tarea por ID (completa con user y categories)
    // ============================================================
    @Transactional(readOnly = true)
    public TaskDTO findById(UUID id) {
        // TODO: Usar taskRepository.findByIdComplete(id)
        // Lanzar excepción si no existe
        // Mapear a TaskDTO
        return null;
    }


    // ============================================================
    // TODO 4: Crear tarea
    // ============================================================
    // Instrucciones:
    // - Buscar el User por userId (lanzar excepción si no existe)
    // - Crear nueva Task
    // - Establecer user con task.setUser(user)
    // - Si hay categoryIds, buscar cada categoría y agregarla con task.addCategory()
    // - Guardar con taskRepository.save()

    @Transactional
    public TaskDTO create(CreateTaskRequest request) {
        // TODO: Buscar usuario
        // User user = userRepository.findById(request.userId())
        //     .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // TODO: Crear task
        // Task task = new Task(request.title(), request.description());
        // task.setUser(user);

        // TODO: Agregar categorías si se proporcionaron
        // if (request.categoryIds() != null) {
        //     for (UUID catId : request.categoryIds()) {
        //         Category cat = categoryRepository.findById(catId)
        //             .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        //         task.addCategory(cat);
        //     }
        // }

        // TODO: Guardar y retornar DTO
        return null;
    }


    // ============================================================
    // TODO 5: Actualizar tarea
    // ============================================================
    @Transactional
    public TaskDTO update(UUID id, UpdateTaskRequest request) {
        // TODO: Buscar task existente
        // Actualizar campos no nulos del request
        // Guardar y retornar DTO
        return null;
    }


    // ============================================================
    // TODO 6: Agregar categoría a tarea
    // ============================================================
    @Transactional
    public TaskDTO addCategory(UUID taskId, UUID categoryId) {
        // TODO: Buscar task y category
        // Usar task.addCategory(category)
        // Guardar y retornar DTO
        return null;
    }


    // ============================================================
    // TODO 7: Remover categoría de tarea
    // ============================================================
    @Transactional
    public TaskDTO removeCategory(UUID taskId, UUID categoryId) {
        // TODO: Buscar task y category
        // Usar task.removeCategory(category)
        // Guardar y retornar DTO
        return null;
    }


    // ============================================================
    // TODO 8: Eliminar tarea
    // ============================================================
    @Transactional
    public void delete(UUID id) {
        // TODO: Verificar existencia y eliminar
    }
}
