# 🗄️ Práctica 03: Crear Repository JPA

## Objetivo

Crear el repositorio JPA para la entidad Task usando Spring Data JPA.

---

## Requisitos Previos

- Práctica 02 completada (Entidad Task creada)
- PostgreSQL corriendo en Docker

---

## Paso 1: Crear TaskRepository

Crea `src/main/java/com/bootcamp/taskmanager/repository/TaskRepository.java`:

```java
package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositorio JPA para la entidad Task.
 * Hereda métodos CRUD de JpaRepository.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // ═══════════════════════════════════════════════════════════
    // Los siguientes métodos ya están disponibles heredados:
    // - save(Task task)
    // - findById(UUID id) -> Optional<Task>
    // - findAll() -> List<Task>
    // - deleteById(UUID id)
    // - existsById(UUID id)
    // - count()
    // ═══════════════════════════════════════════════════════════

    // ═══════════════════════════════════════════════════════════
    // TODO 1: Query Method para buscar por estado completado
    // Firma: List<Task> findByCompleted(boolean completed)
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // TODO 2: Query Method para buscar por título conteniendo texto
    // Firma: List<Task> findByTitleContainingIgnoreCase(String title)
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // TODO 3: Query Method para contar tareas por estado
    // Firma: long countByCompleted(boolean completed)
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // TODO 4: Query Method para verificar si existe título
    // Firma: boolean existsByTitle(String title)
    // ═══════════════════════════════════════════════════════════

}
```

---

## Paso 2: Completar los TODOs

### Solución de Referencia

<details>
<summary>Ver solución</summary>

```java
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitleContainingIgnoreCase(String title);

    long countByCompleted(boolean completed);

    boolean existsByTitle(String title);
}
```

</details>

---

## Paso 3: Probar el Repository

Crea una clase de prueba temporal o usa el `CommandLineRunner`:

```java
// En TaskManagerApplication.java (temporal para probar)
@Bean
CommandLineRunner testRepository(TaskRepository taskRepository) {
    return args -> {
        // Crear tarea
        Task task = new Task("Tarea de prueba", "Descripción");
        Task saved = taskRepository.save(task);
        System.out.println("✅ Tarea guardada: " + saved.getId());

        // Buscar por ID
        taskRepository.findById(saved.getId())
            .ifPresent(t -> System.out.println("✅ Encontrada: " + t.getTitle()));

        // Contar
        System.out.println("✅ Total tareas: " + taskRepository.count());

        // Buscar pendientes
        List<Task> pendientes = taskRepository.findByCompleted(false);
        System.out.println("✅ Pendientes: " + pendientes.size());
    };
}
```

---

## Paso 4: Verificar en Base de Datos

```bash
# Iniciar servicios
docker compose up -d

# Ver logs de la aplicación
docker compose logs -f api

# Verificar datos en PostgreSQL
docker compose exec db psql -U dev -d taskmanager -c "SELECT * FROM tasks;"
```

---

## Verificación

- [ ] TaskRepository creado como interface
- [ ] Extiende JpaRepository<Task, UUID>
- [ ] Query Methods implementados
- [ ] Datos se persisten en PostgreSQL

---

## Siguiente

➡️ [04-conectar-servicio-jpa.md](04-conectar-servicio-jpa.md)
