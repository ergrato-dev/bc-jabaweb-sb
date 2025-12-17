package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.dto.TaskRequest;
import com.bootcamp.taskmanager.dto.TaskResponse;
import com.bootcamp.taskmanager.exception.ResourceNotFoundException;
import com.bootcamp.taskmanager.model.Task;
import com.bootcamp.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de tareas - Capa de LÓGICA DE NEGOCIO.
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * ¿QUÉ ES LA CAPA SERVICE?
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * La capa Service es responsable de:
 *   - Lógica de negocio (reglas del dominio)
 *   - Orquestación de operaciones (combinar varios repositorios)
 *   - Transformación de datos (Entity ↔ DTO)
 *   - Validaciones de negocio (no las de formato, esas van en DTO)
 *
 * ¿QUÉ NO DEBE HACER?
 *   - Acceso directo a datos sin repositorio
 *   - Manejo de HTTP (eso va en Controller)
 *   - Validaciones de formato (eso va en DTO con Bean Validation)
 *
 * ═══════════════════════════════════════════════════════════════════════════
 * @Service
 * ═══════════════════════════════════════════════════════════════════════════
 *
 * @Service es una especialización de @Component que:
 *   - Indica semánticamente que esta clase tiene lógica de negocio
 *   - Permite que Spring la detecte y cree un bean automáticamente
 *   - Se inyecta en controllers que la necesiten
 *
 */
// TODO 6: Agregar la anotación @Service
//
// Sin @Service, Spring no detectará esta clase y fallará la inyección
// en el Controller con error: "No qualifying bean of type 'TaskService'"
//
// DESCOMENTA:
// @Service
public class TaskServiceImpl implements TaskService {

    // =========================================================================
    // TODO 7: Inyección de dependencias del Repository
    // =========================================================================
    //
    // El servicio NECESITA el repositorio para acceder a datos.
    //
    // HAY DOS FORMAS de inyectar dependencias:
    //
    // OPCIÓN A - Inyección por constructor (RECOMENDADA):
    //   private final TaskRepository taskRepository;
    //
    //   public TaskServiceImpl(TaskRepository taskRepository) {
    //       this.taskRepository = taskRepository;
    //   }
    //
    // OPCIÓN B - Inyección por campo con @Autowired (menos recomendada):
    //   @Autowired
    //   private TaskRepository taskRepository;
    //
    // ¿Por qué preferir inyección por constructor?
    //   - El campo puede ser 'final' (inmutable)
    //   - Las dependencias son explícitas
    //   - Más fácil de testear (pasas mocks en el constructor)
    //   - Falla rápido si falta una dependencia
    //
    // DESCOMENTA Y COMPLETA una de las dos opciones:
    //
    // private final TaskRepository taskRepository;
    //
    // public TaskServiceImpl(TaskRepository taskRepository) {
    //     this.taskRepository = ???;
    // }

    // =========================================================================
    // IMPLEMENTACIÓN DE MÉTODOS
    // =========================================================================

    @Override
    public TaskResponse create(TaskRequest request) {
        // TODO 8: Implementar creación de tarea
        //
        // Pasos:
        //   1. Crear un nuevo objeto Task
        //   2. Generar un ID único con UUID.randomUUID().toString()
        //   3. Asignar los valores del request (title, description)
        //   4. Asignar completed = false
        //   5. Asignar createdAt = LocalDateTime.now()
        //   6. Guardar con taskRepository.save(task)
        //   7. Convertir a TaskResponse y retornar
        //
        // Código de ayuda:
        //   Task task = new Task();
        //   task.setId(UUID.randomUUID().toString());
        //   task.setTitle(request.getTitle());
        //   task.setDescription(request.getDescription());
        //   task.setCompleted(false);
        //   task.setCreatedAt(LocalDateTime.now());
        //
        //   Task savedTask = taskRepository.save(task);
        //   return toResponse(savedTask);

        throw new UnsupportedOperationException("TODO 8: Implementar create()");
    }

    @Override
    public List<TaskResponse> findAll() {
        // TODO 9: Implementar obtener todas las tareas
        //
        // Pasos:
        //   1. Obtener todas las tareas del repositorio
        //   2. Convertir cada Task a TaskResponse
        //   3. Retornar la lista
        //
        // Código con Streams:
        //   return taskRepository.findAll().stream()
        //       .map(this::toResponse)
        //       .collect(Collectors.toList());

        throw new UnsupportedOperationException("TODO 9: Implementar findAll()");
    }

    @Override
    public TaskResponse findById(String id) {
        // TODO 10: Implementar buscar por ID
        //
        // Pasos:
        //   1. Buscar la tarea con taskRepository.findById(id)
        //   2. Si no existe, lanzar ResourceNotFoundException
        //   3. Si existe, convertir a TaskResponse y retornar
        //
        // Código:
        //   Task task = taskRepository.findById(id)
        //       .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        //   return toResponse(task);
        //
        // 💡 orElseThrow() es el patrón correcto con Optional
        //    - Si el Optional tiene valor, lo retorna
        //    - Si está vacío, lanza la excepción que le pases

        throw new UnsupportedOperationException("TODO 10: Implementar findById()");
    }

    @Override
    public TaskResponse update(String id, TaskRequest request) {
        // TODO 11: Implementar actualizar tarea
        //
        // Pasos:
        //   1. Buscar la tarea existente (reusar findById interno)
        //   2. Si no existe, lanzar ResourceNotFoundException
        //   3. Actualizar los campos con los valores del request
        //   4. Guardar con taskRepository.save()
        //   5. Convertir a TaskResponse y retornar
        //
        // Código:
        //   Task task = taskRepository.findById(id)
        //       .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        //
        //   task.setTitle(request.getTitle());
        //   task.setDescription(request.getDescription());
        //
        //   Task updatedTask = taskRepository.save(task);
        //   return toResponse(updatedTask);

        throw new UnsupportedOperationException("TODO 11: Implementar update()");
    }

    @Override
    public void delete(String id) {
        // TODO 12: Implementar eliminar tarea
        //
        // Pasos:
        //   1. Verificar que la tarea existe con existsById()
        //   2. Si no existe, lanzar ResourceNotFoundException
        //   3. Eliminar con taskRepository.deleteById(id)
        //
        // Código:
        //   if (!taskRepository.existsById(id)) {
        //       throw new ResourceNotFoundException("Task", "id", id);
        //   }
        //   taskRepository.deleteById(id);

        throw new UnsupportedOperationException("TODO 12: Implementar delete()");
    }

    @Override
    public TaskResponse markAsCompleted(String id) {
        // TODO 13: Implementar marcar como completada
        //
        // Esta es una operación de NEGOCIO específica.
        // No es un simple update, tiene semántica propia.
        //
        // Pasos:
        //   1. Buscar la tarea
        //   2. Si no existe, lanzar ResourceNotFoundException
        //   3. Marcar completed = true
        //   4. Guardar y retornar
        //
        // Código:
        //   Task task = taskRepository.findById(id)
        //       .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        //
        //   task.setCompleted(true);
        //
        //   Task updatedTask = taskRepository.save(task);
        //   return toResponse(updatedTask);

        throw new UnsupportedOperationException("TODO 13: Implementar markAsCompleted()");
    }

    // =========================================================================
    // MÉTODOS AUXILIARES - Mapeo Entity ↔ DTO
    // =========================================================================

    /**
     * Convierte una entidad Task a TaskResponse DTO.
     *
     * Este método de mapeo está aquí en el Service.
     * En proyectos grandes, usarías MapStruct o ModelMapper.
     *
     * @param task La entidad a convertir
     * @return El DTO de respuesta
     */
    private TaskResponse toResponse(Task task) {
        // TODO 14: Implementar el mapeo de Entity a DTO
        //
        // Código:
        //   TaskResponse response = new TaskResponse();
        //   response.setId(task.getId());
        //   response.setTitle(task.getTitle());
        //   response.setDescription(task.getDescription());
        //   response.setCompleted(task.isCompleted());
        //   response.setCreatedAt(task.getCreatedAt());
        //   return response;
        //
        // ALTERNATIVA con constructor:
        //   return new TaskResponse(
        //       task.getId(),
        //       task.getTitle(),
        //       task.getDescription(),
        //       task.isCompleted(),
        //       task.getCreatedAt()
        //   );

        throw new UnsupportedOperationException("TODO 14: Implementar toResponse()");
    }
}
