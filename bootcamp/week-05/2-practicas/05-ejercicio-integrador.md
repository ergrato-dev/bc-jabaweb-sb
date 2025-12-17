# Práctica 05: Ejercicio Integrador

## 🎯 Objetivo

Integrar todos los conceptos de la semana: relaciones JPA, consultas optimizadas y Docker networks en un ejercicio completo.

---

## 📋 Requisitos

- Prácticas 01-04 completadas
- Todas las entidades y relaciones funcionando
- Docker Compose configurado

---

## 📝 Ejercicio: Sistema de Gestión de Tareas Completo

### Escenario

Debes implementar los endpoints para gestionar usuarios, tareas y categorías con todas sus relaciones.

---

## 🛠️ Parte 1: DTOs para Evitar Recursión JSON

### Crear UserDTO

```java
// src/main/java/com/bootcamp/taskmanager/dto/UserDTO.java
package com.bootcamp.taskmanager.dto;

import com.bootcamp.taskmanager.entity.User;
import java.time.LocalDateTime;
import java.util.*;

public record UserDTO(
    UUID id,
    String username,
    String email,
    LocalDateTime createdAt,
    int taskCount
) {
    // TODO: Crear método estático fromEntity(User user)
    // - Mapear campos básicos
    // - Calcular taskCount desde user.getTasks().size()

}
```

### Crear TaskDTO

```java
// src/main/java/com/bootcamp/taskmanager/dto/TaskDTO.java
package com.bootcamp.taskmanager.dto;

import com.bootcamp.taskmanager.entity.Task;
import java.util.*;

public record TaskDTO(
    UUID id,
    String title,
    String description,
    Boolean completed,
    UUID userId,
    String username,
    List<CategoryDTO> categories
) {
    // TODO: Crear método estático fromEntity(Task task)
    // - Mapear campos de task
    // - Mapear userId y username desde task.getUser()
    // - Mapear categorías a CategoryDTO

}
```

### Crear CategoryDTO

```java
// src/main/java/com/bootcamp/taskmanager/dto/CategoryDTO.java
package com.bootcamp.taskmanager.dto;

import com.bootcamp.taskmanager.entity.Category;
import java.util.UUID;

public record CategoryDTO(
    UUID id,
    String name,
    String color,
    String description,
    int taskCount
) {
    // TODO: Crear método estático fromEntity(Category category)


    // TODO: Crear versión simple sin taskCount (para evitar cargar relación)
    // public static CategoryDTO simpleFromEntity(Category category)

}
```

---

## 🛠️ Parte 2: Servicios con Operaciones CRUD

### UserService

```java
// TODO: Implementar UserService con estos métodos

public interface UserService {
    List<UserDTO> findAll();
    UserDTO findById(UUID id);
    UserDTO findByIdWithTasks(UUID id);  // Con JOIN FETCH
    UserDTO create(CreateUserRequest request);
    UserDTO update(UUID id, UpdateUserRequest request);
    void delete(UUID id);
}
```

### TaskService Actualizado

```java
// TODO: Actualizar TaskService para incluir relaciones

public interface TaskService {
    List<TaskDTO> findAll();
    List<TaskDTO> findByUserId(UUID userId);
    TaskDTO findById(UUID id);
    TaskDTO findByIdComplete(UUID id);  // Con usuario y categorías
    TaskDTO create(CreateTaskRequest request);  // Incluye userId y categoryIds
    TaskDTO update(UUID id, UpdateTaskRequest request);
    TaskDTO addCategory(UUID taskId, UUID categoryId);
    TaskDTO removeCategory(UUID taskId, UUID categoryId);
    void delete(UUID id);
}
```

### CategoryService

```java
// TODO: Implementar CategoryService

public interface CategoryService {
    List<CategoryDTO> findAll();
    CategoryDTO findById(UUID id);
    CategoryDTO findByIdWithTasks(UUID id);
    CategoryDTO create(CreateCategoryRequest request);
    CategoryDTO update(UUID id, UpdateCategoryRequest request);
    void delete(UUID id);
}
```

---

## 🛠️ Parte 3: Controllers REST

### UserController

```java
// TODO: Implementar endpoints

@RestController
@RequestMapping("/api/users")
public class UserController {

    // GET /api/users - Listar todos
    // GET /api/users/{id} - Obtener por ID
    // GET /api/users/{id}/tasks - Obtener usuario con sus tareas
    // POST /api/users - Crear usuario
    // PUT /api/users/{id} - Actualizar usuario
    // DELETE /api/users/{id} - Eliminar usuario

}
```

### TaskController Actualizado

```java
// TODO: Agregar endpoints para relaciones

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // Endpoints existentes...

    // GET /api/tasks/user/{userId} - Tareas de un usuario
    // POST /api/tasks/{id}/categories/{categoryId} - Agregar categoría
    // DELETE /api/tasks/{id}/categories/{categoryId} - Remover categoría

}
```

### CategoryController

```java
// TODO: Implementar endpoints

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    // GET /api/categories - Listar todas
    // GET /api/categories/{id} - Obtener por ID
    // GET /api/categories/{id}/tasks - Categoría con sus tareas
    // POST /api/categories - Crear categoría
    // PUT /api/categories/{id} - Actualizar categoría
    // DELETE /api/categories/{id} - Eliminar categoría

}
```

---

## 🧪 Parte 4: Pruebas con cURL

### Crear usuario

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "email": "john@example.com"}'
```

### Crear categorías

```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name": "Trabajo", "color": "#2196F3"}'

curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{"name": "Urgente", "color": "#F44336"}'
```

### Crear tarea con usuario

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Completar ejercicio integrador",
    "description": "Implementar todas las relaciones",
    "userId": "<UUID_DEL_USUARIO>"
  }'
```

### Agregar categorías a tarea

```bash
curl -X POST http://localhost:8080/api/tasks/<TASK_ID>/categories/<CATEGORY_ID>
```

### Obtener tarea completa

```bash
curl http://localhost:8080/api/tasks/<TASK_ID> | jq
```

---

## ✅ Criterios de Evaluación

| Criterio | Puntos |
|----------|--------|
| Relación User-Task bidireccional funcionando | 20 |
| Relación Task-Category ManyToMany funcionando | 20 |
| DTOs implementados correctamente | 15 |
| Consultas con JOIN FETCH (sin N+1) | 15 |
| Docker network configurada | 15 |
| Endpoints REST completos | 15 |
| **Total** | **100** |

---

## 📤 Entregables

1. Código fuente completo en `3-proyecto/`
2. docker-compose.yml con red custom
3. Colección de requests (cURL o Postman)
4. Captura de logs mostrando queries optimizadas

---

## 💡 Tips

- Usa `@Transactional(readOnly = true)` para lecturas
- Siempre mapea a DTOs antes de retornar en controllers
- Usa JOIN FETCH solo cuando necesites los datos relacionados
- Verifica los logs de SQL para confirmar optimización

---

## ⏭️ Siguiente

Continúa con el proyecto de la semana en [../3-proyecto/](../3-proyecto/).
