# Práctica 03: Optimizar Consultas con JOIN FETCH

## 🎯 Objetivo

Identificar el problema N+1 queries y solucionarlo usando JOIN FETCH y @EntityGraph.

---

## 📋 Requisitos Previos

- Prácticas 01 y 02 completadas
- Entidades User, Task y Category con relaciones

---

## 🛠️ Pasos

### Paso 1: Habilitar Logs de SQL

Modifica `application.properties`:

```properties
# Mostrar queries SQL
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Nivel de log para ver parámetros
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.orm.jdbc.bind=TRACE

# Estadísticas de Hibernate
spring.jpa.properties.hibernate.generate_statistics=true
logging.level.org.hibernate.stat=DEBUG
```

### Paso 2: Crear Servicio para Demostrar N+1

Crea `src/main/java/com/bootcamp/taskmanager/service/DemoService.java`:

```java
package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.entity.*;
import com.bootcamp.taskmanager.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class DemoService {

    private static final Logger log = LoggerFactory.getLogger(DemoService.class);

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public DemoService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // TODO: Método que CAUSA el problema N+1
    @Transactional(readOnly = true)
    public void demonstrateN1Problem() {
        log.info("=== INICIO: Demostración problema N+1 ===");

        // 1 query para obtener todos los usuarios
        List<User> users = userRepository.findAll();
        log.info("Usuarios encontrados: {}", users.size());

        // N queries adicionales (una por cada usuario)
        for (User user : users) {
            // Cada llamada a getTasks() dispara una query
            int taskCount = user.getTasks().size();
            log.info("Usuario: {} tiene {} tareas", user.getUsername(), taskCount);
        }

        log.info("=== FIN: Problema N+1 ===");
    }

    // TODO: Método que SOLUCIONA el problema con JOIN FETCH
    @Transactional(readOnly = true)
    public void demonstrateSolution() {
        log.info("=== INICIO: Solución con JOIN FETCH ===");

        // TODO: Usar método con JOIN FETCH del repository
        // List<User> users = userRepository.findAllWithTasks();

        // TODO: Ahora el loop NO genera queries adicionales
        // for (User user : users) {
        //     int taskCount = user.getTasks().size();
        //     log.info("Usuario: {} tiene {} tareas", user.getUsername(), taskCount);
        // }

        log.info("=== FIN: Solución JOIN FETCH ===");
    }
}
```

### Paso 3: Agregar Métodos con JOIN FETCH en Repositories

**UserRepository.java:**

```java
// TODO: Agregar estos métodos

// Cargar todos los usuarios con sus tareas (evita N+1)
@Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks")
List<User> findAllWithTasks();

// Usuario específico con tareas
@Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id")
Optional<User> findByIdWithTasks(@Param("id") UUID id);

// Usuario por username con tareas
@Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.username = :username")
Optional<User> findByUsernameWithTasks(@Param("username") String username);
```

**TaskRepository.java:**

```java
// TODO: Agregar estos métodos

// Task con usuario
@Query("SELECT t FROM Task t JOIN FETCH t.user WHERE t.id = :id")
Optional<Task> findByIdWithUser(@Param("id") UUID id);

// Task con categorías
@Query("SELECT t FROM Task t LEFT JOIN FETCH t.categories WHERE t.id = :id")
Optional<Task> findByIdWithCategories(@Param("id") UUID id);

// Task completa (usuario + categorías)
@Query("""
    SELECT DISTINCT t FROM Task t
    LEFT JOIN FETCH t.user
    LEFT JOIN FETCH t.categories
    WHERE t.id = :id
""")
Optional<Task> findByIdComplete(@Param("id") UUID id);

// Todas las tareas de un usuario con categorías
@Query("""
    SELECT DISTINCT t FROM Task t
    LEFT JOIN FETCH t.categories
    WHERE t.user.id = :userId
""")
List<Task> findByUserIdWithCategories(@Param("userId") UUID userId);
```

### Paso 4: Crear Endpoint para Probar

Crea `src/main/java/com/bootcamp/taskmanager/controller/DemoController.java`:

```java
package com.bootcamp.taskmanager.controller;

import com.bootcamp.taskmanager.service.DemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/n1-problem")
    public ResponseEntity<String> showN1Problem() {
        demoService.demonstrateN1Problem();
        return ResponseEntity.ok("Revisa los logs para ver el problema N+1");
    }

    @GetMapping("/n1-solution")
    public ResponseEntity<String> showSolution() {
        demoService.demonstrateSolution();
        return ResponseEntity.ok("Revisa los logs para ver la solución");
    }
}
```

---

## ✅ Verificación

### 1. Crear datos de prueba

```bash
docker exec -it taskmanager-db psql -U dev -d taskmanager
```

```sql
-- Crear 5 usuarios con tareas
INSERT INTO users (id, username, email, created_at) VALUES
(gen_random_uuid(), 'user1', 'user1@test.com', NOW()),
(gen_random_uuid(), 'user2', 'user2@test.com', NOW()),
(gen_random_uuid(), 'user3', 'user3@test.com', NOW());

-- Crear tareas para cada usuario
INSERT INTO tasks (id, title, completed, user_id)
SELECT gen_random_uuid(), 'Tarea ' || generate_series(1,3), false, id
FROM users WHERE username = 'user1';

INSERT INTO tasks (id, title, completed, user_id)
SELECT gen_random_uuid(), 'Tarea ' || generate_series(1,2), false, id
FROM users WHERE username = 'user2';
```

### 2. Probar el problema N+1

```bash
curl http://localhost:8080/api/demo/n1-problem
```

**Observa en los logs:**

```
Hibernate: select u1_0.id,u1_0.created_at,u1_0.email,u1_0.username from users u1_0
Hibernate: select t1_0.user_id,t1_0.id,... from tasks t1_0 where t1_0.user_id=?
Hibernate: select t1_0.user_id,t1_0.id,... from tasks t1_0 where t1_0.user_id=?
Hibernate: select t1_0.user_id,t1_0.id,... from tasks t1_0 where t1_0.user_id=?
```

**¡3 usuarios = 1 + 3 = 4 queries!**

### 3. Probar la solución

```bash
curl http://localhost:8080/api/demo/n1-solution
```

**Observa en los logs:**

```
Hibernate: select distinct u1_0.id,...,t1_0.id,... from users u1_0 left join tasks t1_0 on u1_0.id=t1_0.user_id
```

**¡Solo 1 query!**

---

## 🎯 Resultado Esperado

- Entender visualmente el problema N+1 en los logs
- Ver la diferencia de rendimiento con JOIN FETCH
- Saber cuándo aplicar cada solución

---

## ⏭️ Siguiente

Continúa con [04-docker-network-config.md](./04-docker-network-config.md) para configurar redes Docker.
