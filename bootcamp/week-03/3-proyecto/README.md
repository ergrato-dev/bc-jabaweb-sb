# 📦 Proyecto Semana 03: Task Manager con Arquitectura en Capas

## Descripción

Aplicación Task Manager refactorizada con arquitectura en capas, DTOs, validación, manejo global de excepciones y configuración por perfiles.

---

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                    TASK MANAGER API                         │
├─────────────────────────────────────────────────────────────┤
│  Controller Layer                                           │
│  ┌─────────────────┐  ┌─────────────────┐                  │
│  │ TaskController  │  │ InfoController  │                  │
│  └────────┬────────┘  └─────────────────┘                  │
├───────────┼─────────────────────────────────────────────────┤
│  Service Layer       │                                      │
│  ┌────────┴────────┐                                       │
│  │  TaskService    │ ← Interface                           │
│  │ TaskServiceImpl │ ← Implementación                      │
│  └────────┬────────┘                                       │
├───────────┼─────────────────────────────────────────────────┤
│  Repository Layer    │                                      │
│  ┌────────┴────────┐                                       │
│  │ TaskRepository  │ (en memoria)                          │
│  └─────────────────┘                                       │
└─────────────────────────────────────────────────────────────┘
```

---

## 📁 Estructura del Proyecto

```
3-proyecto/
├── docker-compose.yml
├── docker-compose.prod.yml
├── Dockerfile
├── pom.xml
├── README.md
├── .env.example
├── .gitignore
├── docs/
│   └── ENDPOINTS.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── bootcamp/
        │           └── taskmanager/
        │               ├── TaskManagerApplication.java
        │               ├── config/
        │               │   └── AppProperties.java
        │               ├── controller/
        │               │   ├── TaskController.java
        │               │   └── InfoController.java
        │               ├── dto/
        │               │   ├── TaskRequest.java
        │               │   └── TaskResponse.java
        │               ├── exception/
        │               │   ├── GlobalExceptionHandler.java
        │               │   ├── ErrorResponse.java
        │               │   ├── ResourceNotFoundException.java
        │               │   └── BadRequestException.java
        │               ├── mapper/
        │               │   └── TaskMapper.java
        │               ├── model/
        │               │   └── Task.java
        │               ├── repository/
        │               │   └── TaskRepository.java
        │               └── service/
        │                   ├── TaskService.java
        │                   └── TaskServiceImpl.java
        └── resources/
            ├── application.yml
            ├── application-dev.yml
            ├── application-prod.yml
            └── application-test.yml
```

---

## 🚀 Inicio Rápido

### Prerrequisitos

- Docker y Docker Compose instalados
- Puerto 8080 disponible

### Ejecución

```bash
# 1. Clonar o navegar al proyecto
cd bootcamp/week-03/3-proyecto

# 2. Crear archivo .env
cp .env.example .env

# 3. Construir y ejecutar
docker compose up --build

# 4. Verificar
curl http://localhost:8080/api/info
curl http://localhost:8080/api/tasks
```

---

## 🔧 Configuración de Perfiles

### Desarrollo (por defecto)

```bash
# Usar perfil dev (H2 en memoria)
docker compose up --build
```

### Producción

```bash
# Usar perfil prod con PostgreSQL
docker compose -f docker-compose.yml -f docker-compose.prod.yml up --build
```

---

## 📡 Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/info` | Información de la aplicación |
| GET | `/api/tasks` | Listar todas las tareas |
| GET | `/api/tasks/{id}` | Obtener tarea por ID |
| POST | `/api/tasks` | Crear nueva tarea |
| PUT | `/api/tasks/{id}` | Actualizar tarea |
| DELETE | `/api/tasks/{id}` | Eliminar tarea |
| PATCH | `/api/tasks/{id}/complete` | Marcar como completada |

Ver [docs/ENDPOINTS.md](docs/ENDPOINTS.md) para ejemplos detallados.

---

## ✅ Características Implementadas

- [x] Arquitectura en 3 capas (Controller, Service, Repository)
- [x] DTOs con validación (TaskRequest, TaskResponse)
- [x] Mapper para conversión de entidades
- [x] Manejo global de excepciones (@RestControllerAdvice)
- [x] Perfiles de configuración (dev, prod, test)
- [x] Variables de entorno con Docker
- [x] Respuestas de error estandarizadas

---

## 🧪 Pruebas Rápidas

```bash
# Crear tarea
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Mi tarea", "description": "Descripción"}'

# Error de validación (título vacío)
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "", "description": "Test"}'

# Error 404 (tarea no existe)
curl http://localhost:8080/api/tasks/999
```
