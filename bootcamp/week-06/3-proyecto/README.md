# 📦 Proyecto Semana 06: API Documentada con Swagger

## 🎯 Descripción

API REST de gestión de tareas con documentación completa usando SpringDoc OpenAPI y CORS configurado para consumo desde frontends.

---

## 🛠️ Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 21 | Lenguaje |
| Spring Boot | 3.2.0 | Framework |
| SpringDoc OpenAPI | 2.3.0 | Documentación Swagger |
| PostgreSQL | 16 | Base de datos |
| Docker | 24+ | Contenedorización |

---

## 🚀 Ejecución

### Con Docker Compose

```bash
docker-compose up --build
```

### URLs Disponibles

| Recurso | URL |
|---------|-----|
| API | http://localhost:8080/api/v1 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| OpenAPI YAML | http://localhost:8080/v3/api-docs.yaml |

---

## 📋 Endpoints

### Tasks

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/v1/tasks | Listar todas las tareas |
| GET | /api/v1/tasks/{id} | Obtener tarea por ID |
| POST | /api/v1/tasks | Crear nueva tarea |
| PUT | /api/v1/tasks/{id} | Actualizar tarea |
| DELETE | /api/v1/tasks/{id} | Eliminar tarea |

### Users

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/v1/users | Listar usuarios |
| GET | /api/v1/users/{id} | Obtener usuario por ID |
| POST | /api/v1/users | Crear usuario |

---

## 📁 Estructura del Proyecto

```
src/main/java/com/bootcamp/apidocs/
├── ApiDocsApplication.java
├── config/
│   ├── OpenApiConfig.java      ← Configuración Swagger
│   └── CorsConfig.java         ← Configuración CORS
├── controller/
│   ├── TaskController.java     ← Endpoints documentados
│   └── UserController.java
├── dto/
│   ├── TaskDTO.java            ← DTOs con @Schema
│   ├── UserDTO.java
│   ├── ErrorResponse.java
│   └── request/
│       ├── CreateTaskRequest.java
│       └── CreateUserRequest.java
├── entity/
│   ├── Task.java
│   └── User.java
├── exception/
│   └── GlobalExceptionHandler.java
├── repository/
│   ├── TaskRepository.java
│   └── UserRepository.java
└── service/
    ├── TaskService.java
    └── UserService.java
```

---

## 📝 TODOs para Completar

Este proyecto contiene **TODOs** que debes completar:

1. **OpenApiConfig.java** - Configurar información de la API
2. **CorsConfig.java** - Configurar orígenes permitidos
3. **DTOs** - Agregar @Schema con descripciones y ejemplos
4. **Controllers** - Agregar @Operation y @ApiResponse

Las soluciones están en [docs/SOLUCIONES.md](docs/SOLUCIONES.md).

---

## ✅ Verificación

1. Accede a http://localhost:8080/swagger-ui.html
2. Verifica que aparezca el título y descripción de la API
3. Expande los endpoints y verifica la documentación
4. Prueba un endpoint con "Try it out"
5. Verifica los schemas en la parte inferior

---

## 🔧 Configuración CORS

La API permite requests desde:
- http://localhost:3000 (React dev server)
- http://localhost:5173 (Vite dev server)

Para probar CORS:
```bash
curl -X OPTIONS http://localhost:8080/api/v1/tasks \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST" \
  -v
```
