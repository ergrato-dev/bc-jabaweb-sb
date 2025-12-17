# 🔗 Práctica 04: Conectar Service con JPA

## Objetivo

Adaptar el servicio de la Semana 03 para usar JpaRepository en lugar del repositorio en memoria.

---

## Requisitos Previos

- Práctica 03 completada (TaskRepository creado)
- Servicio TaskService de Semana 03

---

## Paso 1: Adaptar TaskServiceImpl

Modifica `src/main/java/com/bootcamp/taskmanager/service/TaskServiceImpl.java`:

```java
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

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 1: Implementar create()
    // - Crear nueva Task desde TaskRequest
    // - NO asignar ID (JPA lo genera)
    // - NO asignar fechas (@PrePersist lo hace)
    // - Guardar con taskRepository.save()
    // - Retornar TaskResponse
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional
    public TaskResponse create(TaskRequest request) {
        // TODO: Implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 2: Implementar findById()
    // - Buscar con taskRepository.findById()
    // - Lanzar ResourceNotFoundException si no existe
    // - Retornar TaskResponse
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public TaskResponse findById(UUID id) {
        // TODO: Implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 3: Implementar findAll()
    // - Usar taskRepository.findAll()
    // - Mapear a List<TaskResponse>
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> findAll() {
        // TODO: Implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 4: Implementar update()
    // - Buscar tarea existente
    // - Actualizar campos desde request
    // - Guardar con save()
    // - Retornar TaskResponse
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional
    public TaskResponse update(UUID id, TaskRequest request) {
        // TODO: Implementar
        return null;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 5: Implementar delete()
    // - Verificar que existe
    // - Eliminar con taskRepository.deleteById()
    // ═══════════════════════════════════════════════════════════
    @Override
    @Transactional
    public void delete(UUID id) {
        // TODO: Implementar
    }

    // ═══════════════════════════════════════════════════════════
    // Método auxiliar para mapear Task -> TaskResponse
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
```

---

## Paso 2: Completar los TODOs

### Solución de Referencia

<details>
<summary>Ver solución</summary>

```java
@Override
@Transactional
public TaskResponse create(TaskRequest request) {
    Task task = new Task();
    task.setTitle(request.title());
    task.setDescription(request.description());
    task.setCompleted(false);

    Task saved = taskRepository.save(task);
    return toResponse(saved);
}

@Override
@Transactional(readOnly = true)
public TaskResponse findById(UUID id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    return toResponse(task);
}

@Override
@Transactional(readOnly = true)
public List<TaskResponse> findAll() {
    return taskRepository.findAll()
        .stream()
        .map(this::toResponse)
        .toList();
}

@Override
@Transactional
public TaskResponse update(UUID id, TaskRequest request) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

    task.setTitle(request.title());
    task.setDescription(request.description());
    if (request.completed() != null) {
        task.setCompleted(request.completed());
    }

    Task updated = taskRepository.save(task);
    return toResponse(updated);
}

@Override
@Transactional
public void delete(UUID id) {
    if (!taskRepository.existsById(id)) {
        throw new ResourceNotFoundException("Task", "id", id);
    }
    taskRepository.deleteById(id);
}
```

</details>

---

## Paso 3: Probar con curl

```bash
# Crear tarea
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Mi primera tarea JPA","description":"Persistida en PostgreSQL"}'

# Listar todas
curl http://localhost:8080/api/tasks

# Verificar en BD
docker compose exec db psql -U dev -d taskmanager -c "SELECT * FROM tasks;"
```

---

## Verificación

- [ ] Service inyecta TaskRepository
- [ ] Métodos CRUD implementados
- [ ] @Transactional en métodos de escritura
- [ ] Datos persisten en PostgreSQL

---

## Siguiente

➡️ [05-multi-stage-build.md](05-multi-stage-build.md)
