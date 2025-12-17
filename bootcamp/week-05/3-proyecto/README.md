# Proyecto Semana 05: Task Manager con Relaciones JPA

## 📋 Descripción

API REST para gestión de tareas con modelo de datos relacional completo:
- **User** (1:N) → **Task** (N:M) ↔ **Category**

---

## 🚀 Inicio Rápido

```bash
# Levantar todo el stack
docker compose up --build

# La API estará disponible en:
# http://localhost:8080

# Swagger UI:
# http://localhost:8080/swagger-ui.html
```

---

## 📊 Modelo de Datos

```
┌──────────┐       ┌──────────┐       ┌────────────┐
│   User   │ 1───N │   Task   │ N───M │  Category  │
├──────────┤       ├──────────┤       ├────────────┤
│ id       │       │ id       │       │ id         │
│ username │       │ title    │       │ name       │
│ email    │       │ desc     │       │ color      │
│ tasks[]  │       │ user     │       │ tasks[]    │
└──────────┘       │ categories│       └────────────┘
                   └──────────┘
```

---

## 🗂️ Estructura del Proyecto

```
3-proyecto/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── src/
│   └── main/
│       ├── java/com/bootcamp/taskmanager/
│       │   ├── TaskManagerApplication.java
│       │   ├── entity/
│       │   │   ├── User.java
│       │   │   ├── Task.java
│       │   │   └── Category.java
│       │   ├── repository/
│       │   │   ├── UserRepository.java
│       │   │   ├── TaskRepository.java
│       │   │   └── CategoryRepository.java
│       │   ├── service/
│       │   │   ├── UserService.java
│       │   │   ├── TaskService.java
│       │   │   └── CategoryService.java
│       │   ├── controller/
│       │   │   ├── UserController.java
│       │   │   ├── TaskController.java
│       │   │   └── CategoryController.java
│       │   ├── dto/
│       │   │   ├── UserDTO.java
│       │   │   ├── TaskDTO.java
│       │   │   ├── CategoryDTO.java
│       │   │   └── request/
│       │   └── exception/
│       └── resources/
│           ├── application.properties
│           └── application-docker.properties
└── docs/
    └── SOLUCIONES.md
```

---

## 🔌 Endpoints

### Users
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/users` | Listar usuarios |
| GET | `/api/users/{id}` | Obtener usuario |
| GET | `/api/users/{id}/tasks` | Usuario con tareas |
| POST | `/api/users` | Crear usuario |
| PUT | `/api/users/{id}` | Actualizar usuario |
| DELETE | `/api/users/{id}` | Eliminar usuario |

### Tasks
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/tasks` | Listar tareas |
| GET | `/api/tasks/{id}` | Obtener tarea |
| GET | `/api/tasks/user/{userId}` | Tareas de usuario |
| POST | `/api/tasks` | Crear tarea |
| PUT | `/api/tasks/{id}` | Actualizar tarea |
| POST | `/api/tasks/{id}/categories/{catId}` | Agregar categoría |
| DELETE | `/api/tasks/{id}/categories/{catId}` | Quitar categoría |
| DELETE | `/api/tasks/{id}` | Eliminar tarea |

### Categories
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/categories` | Listar categorías |
| GET | `/api/categories/{id}` | Obtener categoría |
| POST | `/api/categories` | Crear categoría |
| PUT | `/api/categories/{id}` | Actualizar categoría |
| DELETE | `/api/categories/{id}` | Eliminar categoría |

---

## 🐳 Docker

### Servicios

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| api | 8080 | Spring Boot API |
| db | 5432 | PostgreSQL 16 |

### Red

Todos los servicios están en la red `taskmanager-network` y se comunican por nombre de servicio.

---

## 📝 Archivos con TODOs

Los siguientes archivos contienen TODOs que debes completar:

1. `entity/User.java` - Relación @OneToMany
2. `entity/Task.java` - Relaciones @ManyToOne y @ManyToMany
3. `entity/Category.java` - Relación @ManyToMany inversa
4. `repository/*Repository.java` - Consultas JOIN FETCH
5. `service/*Service.java` - Lógica de negocio
6. `controller/*Controller.java` - Endpoints REST

Las soluciones están en `docs/SOLUCIONES.md` (solo para instructores).

---

## ✅ Criterios de Evaluación

- [ ] Relación User-Task funcionando
- [ ] Relación Task-Category funcionando
- [ ] Consultas optimizadas (sin N+1)
- [ ] DTOs implementados
- [ ] Docker network configurada
- [ ] Todos los endpoints funcionando
