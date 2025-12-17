# Proyecto Semana 02: API REST de Tareas (ToDo)

## 🎯 Objetivo

Desarrollar una API REST básica para gestionar tareas (ToDo) utilizando Spring Boot en Docker.

**Duración estimada**: 90 minutos

---

## 📋 Descripción

Crearás una API REST que permita:

- Listar todas las tareas
- Obtener una tarea por ID
- Crear nuevas tareas
- Actualizar el estado de una tarea
- Eliminar tareas
- Filtrar tareas por estado

---

## 🏗️ Estructura del Proyecto

```
3-proyecto/
├── README.md                    # Este archivo
├── docker-compose.yml           # Configuración Docker (con TODOs)
├── pom.xml                      # Dependencias Maven
├── src/
│   └── main/
│       ├── java/
│       │   └── com/bootcamp/todo/
│       │       ├── TodoApplication.java
│       │       ├── model/
│       │       │   └── Task.java           # (con TODOs)
│       │       └── controller/
│       │           └── TaskController.java # (con TODOs)
│       └── resources/
│           └── application.properties
└── docs/
    └── ENDPOINTS.md             # Documentación de la API
```

---

## 🚀 Instrucciones

### Paso 1: Revisar los Archivos

1. Examina cada archivo del proyecto
2. Busca los comentarios `// TODO:`
3. Implementa cada TODO siguiendo las instrucciones

### Paso 2: Ejecutar

```bash
cd 3-proyecto
docker compose up
```

### Paso 3: Probar

Usa los comandos en [docs/ENDPOINTS.md](docs/ENDPOINTS.md) para verificar tu implementación.

---

## 📝 TODOs a Completar

### En `model/Task.java`:
- [ ] Agregar atributo `completed` (boolean)
- [ ] Crear constructor con parámetros
- [ ] Implementar getters y setters faltantes

### En `controller/TaskController.java`:
- [ ] Implementar GET `/api/tasks/{id}`
- [ ] Implementar POST `/api/tasks`
- [ ] Implementar PUT `/api/tasks/{id}`
- [ ] Implementar DELETE `/api/tasks/{id}`
- [ ] Implementar GET `/api/tasks/filter?completed=true|false`

### En `docker-compose.yml`:
- [ ] Configurar el volumen para el código
- [ ] Configurar el puerto correcto
- [ ] Agregar variable de entorno para perfil

---

## ✅ Criterios de Evaluación

| Criterio | Puntos |
|----------|--------|
| Modelo Task completo con todos los atributos | 10 |
| GET /api/tasks funciona | 10 |
| GET /api/tasks/{id} retorna tarea o 404 | 15 |
| POST /api/tasks crea con 201 Created | 15 |
| PUT /api/tasks/{id} actualiza correctamente | 15 |
| DELETE /api/tasks/{id} elimina con 204 | 15 |
| Filtro por estado funciona | 10 |
| Docker Compose configurado correctamente | 10 |
| **Total** | **100** |

---

## 💡 Pistas

1. Usa `ResponseEntity<T>` para controlar códigos HTTP
2. Para 201 Created: `ResponseEntity.status(HttpStatus.CREATED).body(task)`
3. Para 204 No Content: `ResponseEntity.noContent().build()`
4. Para filtrar: `stream().filter().collect(Collectors.toList())`

---

## 📚 Recursos de Apoyo

- [Teoría: Endpoints REST Básicos](../1-teoria/03-endpoints-rest-basicos.md)
- [Teoría: Dockerfile Spring Boot](../1-teoria/04-dockerfile-spring-boot.md)
- [Práctica 02: Endpoints y Docker](../2-practicas/02-endpoints-docker.md)
