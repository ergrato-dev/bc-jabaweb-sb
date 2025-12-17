# Práctica 01: Configurar Relaciones @OneToMany/@ManyToOne

## 🎯 Objetivo

Implementar la relación bidireccional entre **User** y **Task**: un usuario puede tener muchas tareas, cada tarea pertenece a un usuario.

---

## 📋 Requisitos Previos

- Proyecto de Week-04 funcionando con Docker
- PostgreSQL corriendo en contenedor
- Conocimientos de entidades JPA básicas

---

## 🛠️ Pasos

### Paso 1: Crear la Entidad User

Crea el archivo `src/main/java/com/bootcamp/taskmanager/entity/User.java`:

```java
package com.bootcamp.taskmanager.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // TODO: Agregar relación @OneToMany con Task
    // - mappedBy = "user" (campo en Task)
    // - cascade = CascadeType.ALL
    // - orphanRemoval = true
    // - fetch = FetchType.LAZY


    // TODO: Crear método helper addTask(Task task)
    // - Agregar task a la lista
    // - Establecer this como user del task


    // TODO: Crear método helper removeTask(Task task)
    // - Remover task de la lista
    // - Establecer null como user del task


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructors
    public User() {}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    // TODO: Getter y Setter para tasks
}
```

### Paso 2: Modificar la Entidad Task

Actualiza `Task.java` para agregar la relación con User:

```java
// TODO: Agregar estos imports
import com.bootcamp.taskmanager.entity.User;

// TODO: Agregar campo de relación @ManyToOne
// - fetch = FetchType.LAZY
// - @JoinColumn con name = "user_id" y nullable = false


// TODO: Agregar getter y setter para user
```

### Paso 3: Crear UserRepository

Crea `src/main/java/com/bootcamp/taskmanager/repository/UserRepository.java`:

```java
package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // TODO: Método para buscar por username


    // TODO: Método para buscar por email


    // TODO: Query con JOIN FETCH para cargar usuario con sus tareas
    // @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id")


    // TODO: Verificar si existe por username


    // TODO: Verificar si existe por email
}
```

### Paso 4: Actualizar TaskRepository

Agrega consultas relacionadas con User:

```java
// TODO: Agregar en TaskRepository

// Buscar tareas por userId
List<Task> findByUserId(UUID userId);

// Buscar tareas por username del usuario
List<Task> findByUserUsername(String username);

// Buscar tareas completadas de un usuario
List<Task> findByUserIdAndCompleted(UUID userId, Boolean completed);

// JOIN FETCH para cargar task con su user
@Query("SELECT t FROM Task t JOIN FETCH t.user WHERE t.id = :id")
Optional<Task> findByIdWithUser(@Param("id") UUID id);
```

---

## ✅ Verificación

### 1. Compilar el proyecto

```bash
docker compose up --build
```

### 2. Verificar tablas en PostgreSQL

```bash
docker exec -it taskmanager-db psql -U dev -d taskmanager

\dt  -- Listar tablas
\d users  -- Describir tabla users
\d tasks  -- Verificar que tiene user_id FK
```

### 3. Probar con datos

```sql
-- Insertar usuario
INSERT INTO users (id, username, email, created_at)
VALUES (gen_random_uuid(), 'john', 'john@example.com', NOW());

-- Insertar tarea para ese usuario
INSERT INTO tasks (id, title, completed, user_id)
SELECT gen_random_uuid(), 'Mi primera tarea', false, id FROM users WHERE username = 'john';

-- Verificar relación
SELECT t.title, u.username
FROM tasks t
JOIN users u ON t.user_id = u.id;
```

---

## 🎯 Resultado Esperado

- Tabla `users` creada con columnas: id, username, email, created_at
- Tabla `tasks` con columna `user_id` como FK a `users.id`
- Relación bidireccional funcionando

---

## ⏭️ Siguiente

Continúa con [02-many-to-many-categoria.md](./02-many-to-many-categoria.md) para agregar categorías.
