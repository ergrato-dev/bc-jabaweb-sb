# 📋 Documentación de API Endpoints

## Base URL

```
http://localhost:8080/api
```

## Endpoints de Tareas

### Crear Tarea

```http
POST /api/tasks
Content-Type: application/json

{
  "title": "Mi nueva tarea",
  "description": "Descripción opcional"
}
```

**Response: 201 Created**
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "title": "Mi nueva tarea",
  "description": "Descripción opcional",
  "completed": false,
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": "2024-01-15T10:30:00"
}
```

### Listar Tareas

```http
GET /api/tasks
```

**Response: 200 OK**
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "title": "Mi nueva tarea",
    "description": "Descripción opcional",
    "completed": false,
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00"
  }
]
```

### Obtener Tarea por ID

```http
GET /api/tasks/{id}
```

**Response: 200 OK** o **404 Not Found**

### Actualizar Tarea

```http
PUT /api/tasks/{id}
Content-Type: application/json

{
  "title": "Título actualizado",
  "description": "Nueva descripción",
  "completed": true
}
```

**Response: 200 OK** o **404 Not Found**

### Eliminar Tarea

```http
DELETE /api/tasks/{id}
```

**Response: 204 No Content** o **404 Not Found**
