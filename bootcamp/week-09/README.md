# Semana 9: Proyecto Final Integrador

## 📋 Información General

| Dato | Valor |
|------|-------|
| **Duración** | 5 horas |
| **Semana** | 9 de 9 |
| **Tipo** | Proyecto Final |
| **Prerrequisitos** | Semanas 1-8 completadas |

## 🎯 Objetivos de Aprendizaje

Al finalizar esta semana, el estudiante será capaz de:

1. **Integrar** todos los conocimientos del bootcamp en un proyecto completo
2. **Diseñar** una API REST profesional con múltiples entidades relacionadas
3. **Implementar** autenticación JWT con Spring Security
4. **Documentar** la API con OpenAPI/Swagger
5. **Testear** el proyecto con cobertura mínima del 70%
6. **Containerizar** la aplicación con Docker multi-servicio
7. **Presentar** y defender decisiones técnicas

## 📚 Contenido

### 1. Teoría (30 min)
- [Integración de conceptos](1-teoria/01-integracion-conceptos.md)
- [Arquitectura del proyecto final](1-teoria/02-arquitectura-proyecto.md)
- [Checklist de calidad](1-teoria/03-checklist-calidad.md)
- [Preparación de presentación](1-teoria/04-presentacion-defensa.md)

### 2. Prácticas (1 hora)
- [Planificación del proyecto](2-practicas/01-planificacion-proyecto.md)
- [Implementación de entidades](2-practicas/02-implementacion-entidades.md)
- [Integración de componentes](2-practicas/03-integracion-componentes.md)

### 3. Proyecto Final (3 horas)
- [Proyecto Completo](3-proyecto/README.md)

### 4. Recursos
- [Bibliografía y recursos](4-recursos/README.md)

### 5. Glosario
- [Términos del proyecto final](5-glosario/README.md)

---

## 🏗️ Arquitectura del Proyecto Final

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           PROYECTO FINAL INTEGRADOR                         │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                         Docker Compose Stack                          │ │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  │ │
│  │  │   Nginx     │  │  Spring     │  │ PostgreSQL  │  │   PgAdmin   │  │ │
│  │  │  (Proxy)    │──│    Boot     │──│     DB      │  │   (Tools)   │  │ │
│  │  │   :80       │  │   :8080     │  │   :5432     │  │   :5050     │  │ │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘  │ │
│  │                          │                │                          │ │
│  │                          └────────────────┘                          │ │
│  │                         bootcamp-network                              │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
│                                                                             │
│  ┌───────────────────────────────────────────────────────────────────────┐ │
│  │                          Spring Boot App                              │ │
│  │  ┌─────────────────────────────────────────────────────────────────┐ │ │
│  │  │                    Security Layer (JWT)                         │ │ │
│  │  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐             │ │ │
│  │  │  │   Auth      │  │  Products   │  │   Orders    │             │ │ │
│  │  │  │ Controller  │  │ Controller  │  │ Controller  │             │ │ │
│  │  │  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘             │ │ │
│  │  │         │                │                │                     │ │ │
│  │  │  ┌──────┴──────┐  ┌──────┴──────┐  ┌──────┴──────┐             │ │ │
│  │  │  │   Auth      │  │  Product    │  │   Order     │             │ │ │
│  │  │  │  Service    │  │  Service    │  │  Service    │             │ │ │
│  │  │  └──────┬──────┘  └──────┬──────┘  └──────┴──────┘             │ │ │
│  │  │         │                │                │                     │ │ │
│  │  │  ┌──────┴──────┐  ┌──────┴──────┐  ┌──────┴──────┐             │ │ │
│  │  │  │   User      │  │  Product    │  │   Order     │             │ │ │
│  │  │  │ Repository  │  │ Repository  │  │ Repository  │             │ │ │
│  │  │  └─────────────┘  └─────────────┘  └─────────────┘             │ │ │
│  │  └─────────────────────────────────────────────────────────────────┘ │ │
│  └───────────────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Requisitos del Proyecto Final

### Requisitos Funcionales

| Requisito | Descripción | Puntos |
|-----------|-------------|--------|
| **Entidades** | Mínimo 3 entidades relacionadas | 15 |
| **CRUD completo** | Operaciones para cada entidad | 15 |
| **Autenticación** | JWT con registro y login | 15 |
| **Autorización** | Roles y permisos | 10 |
| **Relaciones** | @OneToMany, @ManyToOne, etc. | 10 |
| **Paginación** | En endpoints de listado | 5 |
| **Total Funcional** | | **70** |

### Requisitos Técnicos

| Requisito | Descripción | Puntos |
|-----------|-------------|--------|
| **Docker** | Compose funcional | 10 |
| **Tests** | Cobertura ≥70% | 10 |
| **Swagger** | Documentación completa | 5 |
| **Código limpio** | Arquitectura en capas | 5 |
| **Total Técnico** | | **30** |

---

## 🧪 Criterios de Evaluación

### Evidencias de Conocimiento (20%)
- Cuestionario sobre arquitectura del proyecto
- Defensa oral de decisiones técnicas
- Justificación de diseño de entidades

### Evidencias de Desempeño (30%)
- Implementación correcta de capas
- Uso apropiado de anotaciones Spring
- Manejo de errores y excepciones
- Calidad del código

### Evidencias de Producto (50%)
- API funcional con todos los endpoints
- Docker Compose ejecutable
- Suite de tests pasando
- Documentación Swagger completa

---

## 📅 Distribución del Tiempo

| Actividad | Duración | Descripción |
|-----------|----------|-------------|
| Revisión de requisitos | 15 min | Entender alcance del proyecto |
| Teoría de integración | 15 min | Cómo conectar todos los conceptos |
| Planificación | 30 min | Diseño de entidades y endpoints |
| Desarrollo | 2.5 horas | Implementación del proyecto |
| Testing | 30 min | Tests unitarios e integración |
| Presentación | 30 min | Defensa del proyecto |
| **Total** | **5 horas** | |

---

## ✅ Checklist del Proyecto Final

### Estructura
- [ ] Arquitectura en capas (Controller → Service → Repository)
- [ ] DTOs para transferencia de datos
- [ ] Entidades JPA con relaciones
- [ ] Manejo global de excepciones

### Seguridad
- [ ] Autenticación con JWT
- [ ] Endpoints de registro y login
- [ ] Protección de rutas por roles
- [ ] Variables de entorno para secretos

### Base de Datos
- [ ] PostgreSQL en Docker
- [ ] Entidades con relaciones correctas
- [ ] Consultas personalizadas
- [ ] Paginación implementada

### Testing
- [ ] Tests unitarios de servicios
- [ ] Tests de controladores con MockMvc
- [ ] Tests de integración con TestContainers
- [ ] Cobertura ≥70%

### Docker
- [ ] Dockerfile multi-stage
- [ ] docker-compose.yml funcional
- [ ] Healthchecks configurados
- [ ] Variables de entorno

### Documentación
- [ ] README.md completo
- [ ] Swagger/OpenAPI configurado
- [ ] Instrucciones de despliegue
- [ ] Colección Postman (opcional)

---

## 🎁 Ideas de Proyecto

### Opción 1: Sistema de E-commerce
- **Entidades**: User, Product, Order, OrderItem, Category
- **Funcionalidades**: Carrito, pedidos, historial

### Opción 2: Sistema de Gestión de Tareas
- **Entidades**: User, Project, Task, Comment
- **Funcionalidades**: Asignación, estados, comentarios

### Opción 3: Sistema de Reservas
- **Entidades**: User, Resource, Reservation, Schedule
- **Funcionalidades**: Disponibilidad, confirmación

### Opción 4: Blog/CMS
- **Entidades**: User, Post, Category, Comment, Tag
- **Funcionalidades**: Publicación, categorías, búsqueda

---

## 📝 Entregables

1. **Código fuente** en repositorio Git
2. **docker-compose.yml** funcional
3. **README.md** con instrucciones
4. **Tests** con cobertura ≥70%
5. **Presentación** del proyecto (5-10 min)

---

## 🔗 Navegación

| Anterior | Índice | Siguiente |
|----------|--------|-----------|
| [Semana 8: Testing y Docker](../week-08/README.md) | [Bootcamp](../README.md) | - |
