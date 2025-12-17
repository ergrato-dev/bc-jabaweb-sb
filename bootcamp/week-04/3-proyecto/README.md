# 📦 Proyecto Semana 04: Task Manager con JPA

## Descripción

Proyecto integrador que implementa persistencia con PostgreSQL usando Spring Data JPA.

---

## Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 21 | Lenguaje |
| Spring Boot | 3.2+ | Framework |
| Spring Data JPA | 3.2+ | Persistencia |
| PostgreSQL | 16 | Base de datos |
| Docker | 24+ | Contenedorización |

---

## Estructura del Proyecto

```
3-proyecto/
├── docker-compose.yml      # Orquestación de servicios
├── Dockerfile              # Multi-stage build
├── pom.xml                 # Dependencias Maven
├── README.md               # Este archivo
├── docs/
│   └── api-endpoints.md    # Documentación de endpoints
└── src/
    └── main/
        ├── java/com/bootcamp/taskmanager/
        │   ├── TaskManagerApplication.java
        │   ├── controller/
        │   │   └── TaskController.java
        │   ├── dto/
        │   │   ├── TaskRequest.java
        │   │   └── TaskResponse.java
        │   ├── exception/
        │   │   ├── GlobalExceptionHandler.java
        │   │   └── ResourceNotFoundException.java
        │   ├── model/
        │   │   └── Task.java
        │   ├── repository/
        │   │   └── TaskRepository.java
        │   └── service/
        │       ├── TaskService.java
        │       └── TaskServiceImpl.java
        └── resources/
            ├── application.properties
            └── application-dev.properties
```

---

## Ejecución

### Con Docker Compose (Recomendado)

```bash
# Iniciar todo
docker compose up -d

# Ver logs
docker compose logs -f

# Detener
docker compose down
```

### Acceso

- **API**: http://localhost:8080
- **PostgreSQL**: localhost:5432

---

## Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/tasks` | Listar todas |
| GET | `/api/tasks/{id}` | Obtener por ID |
| POST | `/api/tasks` | Crear tarea |
| PUT | `/api/tasks/{id}` | Actualizar |
| DELETE | `/api/tasks/{id}` | Eliminar |

---

## Probar con curl

```bash
# Crear
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Aprender JPA","description":"Persistencia con PostgreSQL"}'

# Listar
curl http://localhost:8080/api/tasks

# Verificar en BD
docker compose exec db psql -U dev -d taskmanager -c "SELECT * FROM tasks;"
```
