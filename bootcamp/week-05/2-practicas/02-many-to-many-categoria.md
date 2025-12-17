# Práctica 02: Implementar @ManyToMany con Categorías

## 🎯 Objetivo

Crear la entidad **Category** y establecer una relación muchos a muchos con **Task**: una tarea puede tener varias categorías, una categoría puede agrupar varias tareas.

---

## 📋 Requisitos Previos

- Práctica 01 completada (User ↔ Task funcionando)
- Entender @JoinTable y tabla intermedia

---

## 🛠️ Pasos

### Paso 1: Crear la Entidad Category

Crea `src/main/java/com/bootcamp/taskmanager/entity/Category.java`:

```java
package com.bootcamp.taskmanager.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 7)  // Formato: #RRGGBB
    private String color = "#808080";

    private String description;

    // TODO: Agregar relación @ManyToMany (lado INVERSO)
    // - mappedBy = "categories" (campo en Task)
    // - fetch = FetchType.LAZY


    // Constructors
    public Category() {}

    public Category(String name, String color) {
        this.name = name;
        this.color = color;
    }

    // TODO: Implementar equals() basado en id
    // Importante para que Set funcione correctamente


    // TODO: Implementar hashCode()
    // return getClass().hashCode(); // Consistente para entidades JPA


    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // TODO: Getter y Setter para tasks
}
```

### Paso 2: Agregar Relación en Task

Modifica `Task.java` para agregar la relación ManyToMany:

```java
// TODO: Agregar import
import java.util.Set;
import java.util.HashSet;

// TODO: Agregar relación @ManyToMany (lado PROPIETARIO)
// @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
// @JoinTable(
//     name = "task_category",
//     joinColumns = @JoinColumn(name = "task_id"),
//     inverseJoinColumns = @JoinColumn(name = "category_id")
// )
// private Set<Category> categories = new HashSet<>();


// TODO: Método helper addCategory(Category category)
// - Agregar category al Set
// - Agregar this al Set de tasks de category


// TODO: Método helper removeCategory(Category category)
// - Remover category del Set
// - Remover this del Set de tasks de category


// TODO: Getter y Setter para categories
```

### Paso 3: Crear CategoryRepository

Crea `src/main/java/com/bootcamp/taskmanager/repository/CategoryRepository.java`:

```java
package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // TODO: Buscar por nombre
    Optional<Category> findByName(String name);

    // TODO: Buscar por nombre conteniendo (ignore case)
    List<Category> findByNameContainingIgnoreCase(String name);

    // TODO: JOIN FETCH para cargar categoría con sus tareas
    // @Query("SELECT c FROM Category c LEFT JOIN FETCH c.tasks WHERE c.id = :id")


    // TODO: Categorías que tienen al menos una tarea
    // @Query("SELECT DISTINCT c FROM Category c JOIN c.tasks")


    // TODO: Verificar si existe por nombre
    boolean existsByName(String name);
}
```

### Paso 4: Actualizar TaskRepository

Agrega consultas para categorías:

```java
// TODO: Agregar en TaskRepository

// JOIN FETCH para cargar task con sus categorías
@Query("SELECT t FROM Task t LEFT JOIN FETCH t.categories WHERE t.id = :id")
Optional<Task> findByIdWithCategories(@Param("id") UUID id);

// Tasks de una categoría específica
@Query("SELECT t FROM Task t JOIN t.categories c WHERE c.id = :categoryId")
List<Task> findByCategoryId(@Param("categoryId") UUID categoryId);

// Tasks con usuario Y categorías (completo)
@Query("SELECT DISTINCT t FROM Task t LEFT JOIN FETCH t.user LEFT JOIN FETCH t.categories WHERE t.id = :id")
Optional<Task> findByIdComplete(@Param("id") UUID id);
```

---

## ✅ Verificación

### 1. Reiniciar con nueva estructura

```bash
docker compose down -v  # Eliminar volúmenes para recrear tablas
docker compose up --build
```

### 2. Verificar tablas

```bash
docker exec -it taskmanager-db psql -U dev -d taskmanager

\dt  -- Debe mostrar: users, tasks, categories, task_category
\d task_category  -- Tabla intermedia
```

### 3. Probar relación

```sql
-- Crear categorías
INSERT INTO categories (id, name, color) VALUES
(gen_random_uuid(), 'Trabajo', '#2196F3'),
(gen_random_uuid(), 'Personal', '#4CAF50'),
(gen_random_uuid(), 'Urgente', '#F44336');

-- Asociar tarea con categorías
INSERT INTO task_category (task_id, category_id)
SELECT t.id, c.id FROM tasks t, categories c
WHERE t.title = 'Mi primera tarea' AND c.name IN ('Trabajo', 'Urgente');

-- Verificar
SELECT t.title, c.name, c.color
FROM tasks t
JOIN task_category tc ON t.id = tc.task_id
JOIN categories c ON tc.category_id = c.id;
```

---

## 🎯 Resultado Esperado

- Tabla `categories` creada
- Tabla intermedia `task_category` con PKs compuesta
- Relación ManyToMany bidireccional funcionando

---

## ⏭️ Siguiente

Continúa con [03-fetch-join-queries.md](./03-fetch-join-queries.md) para optimizar consultas.
