# 📡 Documentación de Endpoints - Task Manager API

## Base URL

```
http://localhost:8080
```

---

## 🔍 Información de la Aplicación

### GET /api/info

Obtiene información sobre la aplicación y el entorno actual.

**Request:**
```bash
curl http://localhost:8080/api/info
```

**Response (200 OK):**
```json
{
  "name": "Task Manager API",
  "version": "1.0.0",
  "environment": "development",
  "debug": true
}
```

---

## 📋 Endpoints de Tareas

### GET /api/tasks

Obtiene todas las tareas.

**Request:**
```bash
curl http://localhost:8080/api/tasks
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "title": "Mi primera tarea",
    "description": "Descripción de la tarea",
    "completed": false,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

---

### GET /api/tasks/{id}

Obtiene una tarea por su ID.

**Request:**
```bash
curl http://localhost:8080/api/tasks/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Mi primera tarea",
  "description": "Descripción de la tarea",
  "completed": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

**Response (404 Not Found):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: '999'",
  "path": "/api/tasks/999",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

### POST /api/tasks

Crea una nueva tarea.

**Request:**
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Nueva tarea",
    "description": "Descripción opcional"
  }'
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Nueva tarea",
  "description": "Descripción opcional",
  "completed": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

**Response (400 Bad Request) - Error de validación:**
```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "Error de validación en los datos enviados",
  "path": "/api/tasks",
  "timestamp": "2024-01-15T10:30:00",
  "errors": {
    "title": "El título es requerido"
  }
}
```

---

### PUT /api/tasks/{id}

Actualiza una tarea existente.

**Request:**
```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tarea actualizada",
    "description": "Nueva descripción"
  }'
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Tarea actualizada",
  "description": "Nueva descripción",
  "completed": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:35:00"
}
```

---

### DELETE /api/tasks/{id}

Elimina una tarea.

**Request:**
```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

**Response (204 No Content):**
Sin cuerpo en la respuesta.

**Response (404 Not Found):**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: '1'",
  "path": "/api/tasks/1",
  "timestamp": "2024-01-15T10:30:00"
}
```

---

### PATCH /api/tasks/{id}/complete

Marca una tarea como completada.

**Request:**
```bash
curl -X PATCH http://localhost:8080/api/tasks/1/complete
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Mi tarea",
  "description": "Descripción",
  "completed": true,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:40:00"
}
```

---

## ❤️ Health Check

### GET /actuator/health

Verifica el estado de la aplicación.

**Request:**
```bash
curl http://localhost:8080/actuator/health
```

**Response (200 OK):**
```json
{
  "status": "UP"
}
```

---

## 📊 Códigos de Respuesta

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado |
| 204 | No Content - Eliminación exitosa |
| 400 | Bad Request - Error de validación |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |

---

## 🔒 Validaciones

### TaskRequest

| Campo | Reglas |
|-------|--------|
| title | Requerido, 3-100 caracteres |
| description | Opcional, máximo 500 caracteres |
