# 🏷️ Práctica 02: Crear Entidad JPA

## Objetivo

Transformar la clase Task del proyecto en una entidad JPA con todas las anotaciones necesarias.

---

## Requisitos Previos

- Práctica 01 completada (PostgreSQL funcionando)
- Proyecto taskmanager de Semana 03

---

## Paso 1: Agregar Dependencias JPA

Edita `pom.xml` para agregar las dependencias necesarias:

```xml
<dependencies>
    <!-- Dependencias existentes... -->

    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- PostgreSQL Driver -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

---

## Paso 2: Configurar application.properties

Crea o edita `src/main/resources/application-dev.properties`:

```properties
# ═══════════════════════════════════════════════════════════
# PostgreSQL Configuration
# ═══════════════════════════════════════════════════════════
spring.datasource.url=jdbc:postgresql://db:5432/taskmanager
spring.datasource.username=dev
spring.datasource.password=dev123
spring.datasource.driver-class-name=org.postgresql.Driver

# ═══════════════════════════════════════════════════════════
# JPA / Hibernate Configuration
# ═══════════════════════════════════════════════════════════
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## Paso 3: Crear la Entidad Task

Crea el archivo `src/main/java/com/bootcamp/taskmanager/model/Task.java`:

```java
package com.bootcamp.taskmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa una tarea en el sistema.
 * Mapeada a la tabla 'tasks' en PostgreSQL.
 */
@Entity
@Table(name = "tasks")
public class Task {

    // ═══════════════════════════════════════════════════════════
    // TODO 1: Agregar anotación @Id
    // TODO 2: Agregar @GeneratedValue con strategy UUID
    // ═══════════════════════════════════════════════════════════
    private UUID id;

    // ═══════════════════════════════════════════════════════════
    // TODO 3: Agregar @Column con nullable=false y length=100
    // ═══════════════════════════════════════════════════════════
    private String title;

    // ═══════════════════════════════════════════════════════════
    // TODO 4: Agregar @Column con columnDefinition="TEXT"
    // ═══════════════════════════════════════════════════════════
    private String description;

    // ═══════════════════════════════════════════════════════════
    // TODO 5: Agregar @Column con nullable=false
    // ═══════════════════════════════════════════════════════════
    private boolean completed = false;

    // ═══════════════════════════════════════════════════════════
    // TODO 6: Agregar @Column con name="created_at" y updatable=false
    // ═══════════════════════════════════════════════════════════
    private LocalDateTime createdAt;

    // ═══════════════════════════════════════════════════════════
    // TODO 7: Agregar @Column con name="updated_at"
    // ═══════════════════════════════════════════════════════════
    private LocalDateTime updatedAt;

    // ═══════════════════════════════════════════════════════════
    // Constructor vacío (requerido por JPA)
    // ═══════════════════════════════════════════════════════════
    public Task() {
    }

    // ═══════════════════════════════════════════════════════════
    // Constructor con parámetros
    // ═══════════════════════════════════════════════════════════
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.completed = false;
    }

    // ═══════════════════════════════════════════════════════════
    // TODO 8: Agregar método con @PrePersist para onCreate()
    // Debe asignar createdAt y updatedAt con LocalDateTime.now()
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // TODO 9: Agregar método con @PreUpdate para onUpdate()
    // Debe asignar updatedAt con LocalDateTime.now()
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // Getters y Setters
    // ═══════════════════════════════════════════════════════════
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
```

---

## Paso 4: Completar los TODOs

### Solución de Referencia

<details>
<summary>Ver solución (intenta primero)</summary>

```java
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;

@Column(nullable = false, length = 100)
private String title;

@Column(columnDefinition = "TEXT")
private String description;

@Column(nullable = false)
private boolean completed = false;

@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

@Column(name = "updated_at")
private LocalDateTime updatedAt;

@PrePersist
protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
}

@PreUpdate
protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
}
```

</details>

---

## Paso 5: Verificar Creación de Tabla

```bash
# Iniciar servicios
docker compose up -d

# Esperar a que la API inicie
docker compose logs -f api

# Verificar tabla creada
docker compose exec db psql -U dev -d taskmanager -c "\dt"

# Ver estructura de la tabla
docker compose exec db psql -U dev -d taskmanager -c "\d tasks"
```

**Resultado esperado:**
```
          Table "public.tasks"
   Column    |            Type
-------------+-----------------------------
 id          | uuid
 title       | character varying(100)
 description | text
 completed   | boolean
 created_at  | timestamp without time zone
 updated_at  | timestamp without time zone
```

---

## Verificación

- [ ] Dependencias JPA y PostgreSQL agregadas
- [ ] application-dev.properties configurado
- [ ] Entidad Task con todas las anotaciones
- [ ] Tabla `tasks` creada automáticamente
- [ ] Callbacks @PrePersist y @PreUpdate implementados

---

## Siguiente

➡️ [03-crear-repository.md](03-crear-repository.md)
