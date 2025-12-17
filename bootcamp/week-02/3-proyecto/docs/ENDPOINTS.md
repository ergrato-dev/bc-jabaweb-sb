# Documentación de Endpoints - ToDo API

## Base URL

```
http://localhost:8080/api/tasks
```

---

## Endpoints Disponibles

### 1. Listar Todas las Tareas

```bash
curl http://localhost:8080/api/tasks
```

**Respuesta esperada** (200 OK):
```json
[
  {
    "id": 1,
    "title": "Aprender Spring Boot",
    "description": "Completar el bootcamp semana 02",
    "createdAt": "2024-01-15T10:30:00",
    "completed": false
  },
  {
    "id": 2,
    "title": "Configurar Docker",
    "description": "Crear docker-compose.yml",
    "createdAt": "2024-01-15T10:31:00",
    "completed": true
  }
]
```

---

### 2. Obtener Tarea por ID

```bash
# Tarea existente
curl http://localhost:8080/api/tasks/1

# Tarea no existente
curl http://localhost:8080/api/tasks/999
```

**Respuesta existente** (200 OK):
```json
{
  "id": 1,
  "title": "Aprender Spring Boot",
  "description": "Completar el bootcamp semana 02",
  "createdAt": "2024-01-15T10:30:00",
  "completed": false
}
```

**Respuesta no existente** (404 Not Found):
```
(vacío)
```

---

### 3. Crear Nueva Tarea

```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Nueva tarea",
    "description": "Descripción de la tarea"
  }'
```

**Respuesta** (201 Created):
```json
{
  "id": 4,
  "title": "Nueva tarea",
  "description": "Descripción de la tarea",
  "createdAt": "2024-01-15T11:00:00",
  "completed": false
}
```

---

### 4. Actualizar Tarea

```bash
curl -X PUT http://localhost:8080/api/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tarea actualizada",
    "description": "Nueva descripción",
    "completed": true
  }'
```

**Respuesta** (200 OK):
```json
{
  "id": 1,
  "title": "Tarea actualizada",
  "description": "Nueva descripción",
  "createdAt": "2024-01-15T10:30:00",
  "completed": true
}
```

---

### 5. Eliminar Tarea

```bash
curl -X DELETE http://localhost:8080/api/tasks/1
```

**Respuesta existente** (204 No Content):
```
(vacío)
```

**Respuesta no existente** (404 Not Found):
```
(vacío)
```

---

### 6. Filtrar por Estado

```bash
# Solo tareas completadas
curl "http://localhost:8080/api/tasks/filter?completed=true"

# Solo tareas pendientes
curl "http://localhost:8080/api/tasks/filter?completed=false"
```

**Respuesta** (200 OK):
```json
[
  {
    "id": 2,
    "title": "Configurar Docker",
    "description": "Crear docker-compose.yml",
    "createdAt": "2024-01-15T10:31:00",
    "completed": true
  }
]
```

---

## Health Check

```bash
curl http://localhost:8080/actuator/health
```

**Respuesta** (200 OK):
```json
{
  "status": "UP"
}
```

---

## Códigos de Estado HTTP

| Código | Significado | Cuándo se usa |
|--------|-------------|---------------|
| 200 | OK | GET exitoso, PUT exitoso |
| 201 | Created | POST exitoso |
| 204 | No Content | DELETE exitoso |
| 400 | Bad Request | JSON malformado |
| 404 | Not Found | Recurso no existe |
| 415 | Unsupported Media Type | Falta Content-Type header |
| 500 | Internal Server Error | Error en el servidor |

---

## Pruebas con Postman

Si prefieres usar Postman:

1. Crea una nueva colección "ToDo API"
2. Agrega requests para cada endpoint
3. Para POST y PUT, ve a "Body" → "raw" → "JSON"
4. No olvides el header: `Content-Type: application/json`

---

## Troubleshooting

### Error: "Connection refused"
- Verifica que Docker está corriendo: `docker compose ps`
- Verifica que la app inició: `docker compose logs`

### Error 404 en todos los endpoints
- Verifica que la URL es correcta: `/api/tasks` (no `/tasks`)
- Verifica que el controlador tiene `@RequestMapping("/api/tasks")`

### Error 415 Unsupported Media Type
- Agrega el header: `-H "Content-Type: application/json"`

### JSON vacío en respuesta
- Verifica que el modelo tiene getters
- Verifica que el constructor vacío existe
