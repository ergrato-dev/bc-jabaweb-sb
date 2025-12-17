# 🛠️ Práctica 05: Proyecto Integrador

## 🎯 Objetivo

Aplicar todos los conocimientos de la semana para crear una API REST completamente documentada con Swagger/OpenAPI y CORS configurado.

---

## 📋 Requisitos

Crear una API de gestión de tareas que incluya:

1. **Documentación OpenAPI completa**
2. **Swagger UI funcional**
3. **CORS configurado**
4. **Todos los endpoints documentados**
5. **Schemas/DTOs documentados**

---

## 📝 Ejercicios

### Ejercicio 1: Configuración Base

1. Crear proyecto con dependencias:
   - spring-boot-starter-web
   - spring-boot-starter-data-jpa
   - spring-boot-starter-validation
   - springdoc-openapi-starter-webmvc-ui
   - postgresql

2. Configurar `OpenApiConfig.java` con:
   - Info completa (title, version, description, contact, license)
   - Servers (dev y prod)
   - Tags predefinidos

### Ejercicio 2: Entidades y DTOs

Crear las siguientes entidades/DTOs documentados:

**Task Entity:**
- id (UUID)
- title (String, 3-100 chars)
- description (String, opcional, max 500)
- completed (Boolean, default false)
- priority (Enum: LOW, MEDIUM, HIGH)
- createdAt, updatedAt

**TaskDTO** - Documentar con @Schema

**CreateTaskRequest** - Documentar campos requeridos

**UpdateTaskRequest** - Documentar campos opcionales

### Ejercicio 3: Controller Documentado

Crear `TaskController` con endpoints documentados:

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/v1/tasks | Listar todas |
| GET | /api/v1/tasks/{id} | Obtener por ID |
| GET | /api/v1/tasks/priority/{priority} | Filtrar por prioridad |
| POST | /api/v1/tasks | Crear |
| PUT | /api/v1/tasks/{id} | Actualizar |
| PATCH | /api/v1/tasks/{id}/complete | Marcar completada |
| DELETE | /api/v1/tasks/{id} | Eliminar |

Cada endpoint debe tener:
- @Operation con summary y description
- @ApiResponses con todos los códigos posibles
- @Parameter para path/query params

### Ejercicio 4: CORS

Configurar CORS para permitir:
- Orígenes: localhost:3000, localhost:5173
- Todos los métodos HTTP
- Credentials habilitadas
- Max age: 1 hora

### Ejercicio 5: Verificación

1. Acceder a Swagger UI
2. Probar cada endpoint desde Swagger
3. Verificar que los schemas muestran ejemplos
4. Probar CORS con curl o HTML

---

## 📊 Estructura del Proyecto

```
src/main/java/com/bootcamp/
├── Application.java
├── config/
│   ├── OpenApiConfig.java
│   └── CorsConfig.java
├── controller/
│   └── TaskController.java
├── dto/
│   ├── TaskDTO.java
│   ├── ErrorResponse.java
│   └── request/
│       ├── CreateTaskRequest.java
│       └── UpdateTaskRequest.java
├── entity/
│   ├── Task.java
│   └── Priority.java
├── exception/
│   └── GlobalExceptionHandler.java
├── repository/
│   └── TaskRepository.java
└── service/
    └── TaskService.java
```

---

## ✅ Checklist de Entrega

### Documentación
- [ ] OpenApiConfig con info completa
- [ ] Todos los controllers tienen @Tag
- [ ] Todos los endpoints tienen @Operation
- [ ] Todos los endpoints tienen @ApiResponses
- [ ] Todos los parámetros tienen @Parameter
- [ ] Todos los DTOs tienen @Schema

### CORS
- [ ] CorsConfig implementado
- [ ] Múltiples orígenes permitidos
- [ ] Preflight funciona

### Funcionalidad
- [ ] Swagger UI accesible
- [ ] Endpoints funcionan desde Swagger
- [ ] Schemas visibles y con ejemplos
- [ ] Exportación OpenAPI funciona

---

## 🎯 Bonus

1. **Documentar Enum Priority** con @Schema
2. **Agregar ejemplos múltiples** con @ExampleObject
3. **Documentar paginación** en GET all
4. **Agregar filtros** por completed y priority

---

## 📤 Entrega

Sube el proyecto a tu repositorio Git con:
- Código fuente completo
- README con instrucciones
- docker-compose.yml funcional
- Capturas de Swagger UI

---

> 💡 **Tip**: Usa el proyecto de la Semana 05 como base y agrega la documentación OpenAPI.
