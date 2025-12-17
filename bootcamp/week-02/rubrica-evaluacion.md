# Rúbrica de Evaluación - Semana 02

## Spring Boot en Docker: Primeros Pasos

**Duración de la sesión**: 5 horas
**Ponderación total**: 100 puntos

---

## 📊 Distribución de Evidencias

| Tipo de Evidencia | Porcentaje | Puntos |
|-------------------|------------|--------|
| Conocimiento | 25% | 25 |
| Desempeño | 35% | 35 |
| Producto | 40% | 40 |
| **Total** | **100%** | **100** |

---

## 📝 Evidencia de Conocimiento (25 puntos)

### Cuestionario Teórico

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| **Concepto de Spring Boot** | Explica correctamente qué es Spring Boot, autoconfiguración y diferencias con Spring Framework | Explica conceptos principales con alguna imprecisión menor | Conoce conceptos básicos pero con confusiones | No comprende los conceptos fundamentales |
| **Estructura Maven** | Identifica correctamente todas las carpetas y archivos clave de un proyecto Maven/Spring Boot | Identifica la mayoría de elementos con algún error | Conoce estructura básica pero omite elementos importantes | No comprende la estructura del proyecto |
| **Anotaciones REST** | Explica correctamente @RestController, @GetMapping, @PostMapping, @PathVariable, @RequestParam | Conoce las anotaciones principales con alguna confusión | Conoce algunas anotaciones pero confunde su uso | No comprende las anotaciones básicas |
| **Dockerfile básico** | Comprende los comandos FROM, WORKDIR, COPY, EXPOSE, ENTRYPOINT/CMD | Entiende la mayoría de comandos con alguna imprecisión | Conoce comandos básicos pero no su propósito | No comprende la estructura de un Dockerfile |
| **HTTP y REST** | Explica correctamente métodos HTTP, códigos de estado y su relación con operaciones CRUD | Conoce métodos y códigos principales con algún error | Conoce conceptos básicos de HTTP | No comprende el protocolo HTTP básico |

**Total Conocimiento: ___ / 25 puntos**

---

## 🔧 Evidencia de Desempeño (35 puntos)

### Ejercicios Prácticos en Clase

| Criterio | Excelente (7) | Bueno (5-6) | Suficiente (3-4) | Insuficiente (0-2) |
|----------|---------------|-------------|------------------|-------------------|
| **Crear proyecto con Spring Initializr** | Genera proyecto correctamente con todas las dependencias requeridas en menos de 5 minutos | Genera proyecto con dependencias correctas | Genera proyecto pero requiere correcciones menores | No logra generar proyecto correctamente |
| **Configurar Docker Compose** | Configura docker-compose.yml correctamente incluyendo volúmenes, puertos y variables de entorno | Configura compose funcional con algún elemento faltante | Configura compose básico que ejecuta la aplicación | No logra configurar Docker Compose |
| **Implementar endpoints GET** | Implementa endpoints GET con @GetMapping, @PathVariable y @RequestParam correctamente | Implementa endpoints GET funcionales con detalles menores | Implementa endpoints básicos que funcionan | No implementa endpoints GET funcionales |
| **Implementar endpoint POST** | Implementa POST con @RequestBody, retorna 201 Created y maneja errores | Implementa POST funcional que crea recursos | Implementa POST básico que funciona | No implementa endpoint POST funcional |
| **Probar con curl/Postman** | Demuestra fluidez probando todos los endpoints con diferentes herramientas | Prueba endpoints correctamente con una herramienta | Realiza pruebas básicas con ayuda | No logra probar los endpoints |

**Total Desempeño: ___ / 35 puntos**

---

## 📦 Evidencia de Producto (40 puntos)

### Proyecto Entregable: API REST de Tareas (ToDo)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| **Modelo Task completo** | Task incluye id, title, description, createdAt, completed con getters/setters y constructores | Modelo completo con algún atributo o método faltante | Modelo básico funcional pero incompleto | Modelo no funcional o muy incompleto |
| **Endpoints CRUD implementados** | Todos los endpoints (GET all, GET by id, POST, PUT, DELETE) funcionan correctamente con códigos HTTP apropiados | 4 de 5 endpoints funcionan correctamente | 3 de 5 endpoints funcionan | Menos de 3 endpoints funcionan |
| **Filtrado por estado** | Endpoint filter funciona correctamente con query parameter completed | Endpoint filter funciona con alguna limitación | Filtrado parcialmente implementado | Filtrado no implementado |
| **Docker Compose configurado** | docker-compose.yml correctamente configurado, aplicación inicia con `docker compose up` | Compose funcional con configuración básica | Compose requiere ajustes menores para funcionar | Compose no funciona |

**Total Producto: ___ / 40 puntos**

---

## 🎯 Escala de Calificación Final

| Rango | Calificación | Descripción |
|-------|--------------|-------------|
| 90-100 | Excelente | Dominio completo de los objetivos de la semana |
| 80-89 | Muy Bueno | Buen dominio con áreas menores de mejora |
| 70-79 | Bueno | Cumple objetivos principales con algunas deficiencias |
| 60-69 | Suficiente | Cumple mínimos requeridos para avanzar |
| < 60 | Insuficiente | Requiere refuerzo antes de continuar |

---

## 📋 Checklist de Entrega

### Archivos Requeridos

- [ ] Proyecto Spring Boot ejecutable
- [ ] `pom.xml` con dependencias correctas
- [ ] `docker-compose.yml` funcional
- [ ] Modelo `Task.java` completo
- [ ] Controlador `TaskController.java` con endpoints CRUD
- [ ] `application.properties` configurado

### Criterios de Aceptación

- [ ] La aplicación inicia con `docker compose up`
- [ ] GET `/api/tasks` retorna lista de tareas
- [ ] GET `/api/tasks/{id}` retorna tarea o 404
- [ ] POST `/api/tasks` crea tarea con 201 Created
- [ ] PUT `/api/tasks/{id}` actualiza tarea existente
- [ ] DELETE `/api/tasks/{id}` elimina con 204 No Content
- [ ] GET `/api/tasks/filter?completed=true|false` filtra correctamente

---

## 💡 Retroalimentación

### Fortalezas Observadas
```
(Espacio para comentarios positivos)
```

### Áreas de Mejora
```
(Espacio para sugerencias de mejora)
```

### Recomendaciones para la Próxima Semana
```
(Preparación sugerida para Semana 03: Arquitectura en Capas)
```

---

## 📅 Información de Evaluación

| Campo | Valor |
|-------|-------|
| Estudiante | |
| Fecha | |
| Evaluador | |
| Calificación Final | ___ / 100 |
| Observaciones | |
