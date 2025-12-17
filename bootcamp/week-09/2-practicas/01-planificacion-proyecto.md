# Práctica 01: Planificación del Proyecto Final

## 📋 Objetivos

- Definir el alcance del proyecto
- Diseñar el modelo de datos
- Planificar los endpoints de la API
- Crear el cronograma de desarrollo

---

## Parte 1: Selección del Proyecto

### Opciones de Proyecto

Elige una de las siguientes opciones o propón una propia:

| Proyecto | Entidades Mínimas | Complejidad |
|----------|-------------------|-------------|
| **E-commerce** | User, Product, Category, Order, OrderItem | Media |
| **Gestión de Tareas** | User, Project, Task, Comment | Media |
| **Sistema de Reservas** | User, Resource, Reservation, Schedule | Media |
| **Blog/CMS** | User, Post, Category, Comment, Tag | Media |
| **Inventario** | User, Product, Warehouse, Movement, Supplier | Media-Alta |

### 📝 Ejercicio 1.1: Definición del Proyecto

Completa la siguiente plantilla:

```markdown
# Mi Proyecto Final

## Nombre del Proyecto
[Tu nombre de proyecto]

## Descripción
[Describe en 2-3 oraciones qué problema resuelve tu API]

## Usuarios Objetivo
[¿Quién usaría esta API?]

## Funcionalidades Principales
1. [Funcionalidad 1]
2. [Funcionalidad 2]
3. [Funcionalidad 3]
4. [Funcionalidad 4]
5. [Funcionalidad 5]

## Requisitos Técnicos
- [ ] Spring Boot 3.2 + Java 21
- [ ] PostgreSQL en Docker
- [ ] JWT Authentication
- [ ] Mínimo 3 entidades relacionadas
- [ ] Documentación Swagger
- [ ] Tests ≥70% cobertura
```

---

## Parte 2: Diseño del Modelo de Datos

### 📝 Ejercicio 2.1: Identificar Entidades

Lista todas las entidades de tu proyecto:

```markdown
## Entidades del Proyecto

### Entidad 1: [Nombre]
- **Propósito**: [Para qué sirve]
- **Atributos principales**:
  - id (Long) - PK
  - [atributo1] ([tipo])
  - [atributo2] ([tipo])
  - createdAt (LocalDateTime)
  - updatedAt (LocalDateTime)

### Entidad 2: [Nombre]
- **Propósito**: [Para qué sirve]
- **Atributos principales**:
  - id (Long) - PK
  - [atributo1] ([tipo])
  - [foreign_key] (Long) - FK a [OtraEntidad]
  - ...

### Entidad 3: [Nombre]
...
```

### 📝 Ejercicio 2.2: Definir Relaciones

Completa la matriz de relaciones:

| Entidad A | Relación | Entidad B | Cardinalidad | Descripción |
|-----------|----------|-----------|--------------|-------------|
| User | tiene | Order | 1:N | Un usuario puede tener muchos pedidos |
| Order | contiene | OrderItem | 1:N | Un pedido tiene muchos items |
| Product | pertenece a | Category | N:1 | Un producto pertenece a una categoría |
| ... | ... | ... | ... | ... |

### 📝 Ejercicio 2.3: Diagrama ER

Dibuja el diagrama entidad-relación (puedes usar draw.io, Excalidraw, o papel):

```
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│   [Entity]  │       │   [Entity]  │       │   [Entity]  │
├─────────────┤       ├─────────────┤       ├─────────────┤
│ PK id       │──1:N──│ PK id       │──N:1──│ PK id       │
│    attr1    │       │ FK entity_id│       │    attr1    │
│    attr2    │       │    attr1    │       │    attr2    │
└─────────────┘       └─────────────┘       └─────────────┘
```

---

## Parte 3: Diseño de la API

### 📝 Ejercicio 3.1: Definir Endpoints

Completa la tabla de endpoints para cada entidad:

#### Entidad: User (Autenticación)

| Método | Endpoint | Descripción | Auth | Rol |
|--------|----------|-------------|------|-----|
| POST | /api/auth/register | Registro de usuario | No | - |
| POST | /api/auth/login | Login y obtener JWT | No | - |
| GET | /api/auth/me | Obtener usuario actual | Sí | USER |
| PUT | /api/users/{id} | Actualizar perfil | Sí | USER (propio) |

#### Entidad: [Tu Entidad Principal]

| Método | Endpoint | Descripción | Auth | Rol |
|--------|----------|-------------|------|-----|
| GET | /api/[recursos] | Listar todos | ? | ? |
| GET | /api/[recursos]/{id} | Obtener por ID | ? | ? |
| POST | /api/[recursos] | Crear nuevo | ? | ? |
| PUT | /api/[recursos]/{id} | Actualizar | ? | ? |
| DELETE | /api/[recursos]/{id} | Eliminar | ? | ? |

### 📝 Ejercicio 3.2: Definir DTOs

Para cada endpoint, define los DTOs de request y response:

```java
// Request para crear [Recurso]
public record Create[Recurso]Request(
    @NotBlank String campo1,
    @NotNull Long campo2,
    @Min(0) Integer campo3
) {}

// Response de [Recurso]
public record [Recurso]DTO(
    Long id,
    String campo1,
    String campoRelacionado, // De otra entidad
    LocalDateTime createdAt
) {}
```

### 📝 Ejercicio 3.3: Códigos de Respuesta

Define los códigos HTTP para cada operación:

| Operación | Éxito | Error común | Error auth |
|-----------|-------|-------------|------------|
| GET lista | 200 OK | - | 401/403 |
| GET por ID | 200 OK | 404 Not Found | 401/403 |
| POST crear | 201 Created | 400 Bad Request | 401/403 |
| PUT actualizar | 200 OK | 400/404 | 401/403 |
| DELETE | 204 No Content | 404 Not Found | 401/403 |

---

## Parte 4: Planificación del Desarrollo

### 📝 Ejercicio 4.1: Cronograma

Distribuye las 5 horas de la semana 9:

| Fase | Duración | Actividades |
|------|----------|-------------|
| **Hora 1** | 1h | Setup inicial, Docker, estructura base |
| **Hora 2** | 1h | Entidades JPA, Repositories, relaciones |
| **Hora 3** | 1h | Services, DTOs, Mappers |
| **Hora 4** | 1h | Controllers, Security, Swagger |
| **Hora 5** | 1h | Testing, documentación, ajustes finales |

### 📝 Ejercicio 4.2: Checklist de Tareas

Crea tu checklist personalizado:

#### Setup (Hora 1)
- [ ] Crear proyecto con Spring Initializr
- [ ] Configurar pom.xml con dependencias
- [ ] Crear Dockerfile multi-stage
- [ ] Crear docker-compose.yml
- [ ] Configurar application.properties/yml
- [ ] Crear .env y .env.example
- [ ] Verificar que levanta con `docker-compose up`

#### Persistencia (Hora 2)
- [ ] Crear entidad User con roles
- [ ] Crear entidad [Entidad1]
- [ ] Crear entidad [Entidad2]
- [ ] Crear entidad [Entidad3]
- [ ] Configurar relaciones JPA
- [ ] Crear repositories
- [ ] Verificar que las tablas se crean

#### Negocio (Hora 3)
- [ ] Crear DTOs para cada entidad
- [ ] Crear Mappers
- [ ] Crear Services con CRUD
- [ ] Implementar validaciones de negocio
- [ ] Crear excepciones custom
- [ ] Crear GlobalExceptionHandler

#### API (Hora 4)
- [ ] Configurar Spring Security
- [ ] Implementar JWT (JwtService, Filter)
- [ ] Crear AuthController (register, login)
- [ ] Crear Controllers CRUD
- [ ] Configurar Swagger/OpenAPI
- [ ] Configurar CORS
- [ ] Probar todos los endpoints

#### Calidad (Hora 5)
- [ ] Tests unitarios de services
- [ ] Tests de integración de controllers
- [ ] Tests de auth (login, register, acceso)
- [ ] Verificar cobertura ≥70%
- [ ] Completar README.md
- [ ] Documentar endpoints en Swagger
- [ ] Prueba final completa

---

## Parte 5: Estructura del Proyecto

### 📝 Ejercicio 5.1: Árbol de Directorios

Completa la estructura de tu proyecto:

```
mi-proyecto-final/
├── .env.example
├── .gitignore
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── README.md
│
└── src/
    ├── main/
    │   ├── java/com/bootcamp/finalproject/
    │   │   ├── FinalProjectApplication.java
    │   │   │
    │   │   ├── config/
    │   │   │   ├── OpenApiConfig.java
    │   │   │   ├── SecurityConfig.java
    │   │   │   └── CorsConfig.java
    │   │   │
    │   │   ├── security/
    │   │   │   └── jwt/
    │   │   │       ├── JwtService.java
    │   │   │       └── JwtAuthenticationFilter.java
    │   │   │
    │   │   ├── auth/
    │   │   │   ├── controller/
    │   │   │   ├── dto/
    │   │   │   └── service/
    │   │   │
    │   │   ├── user/
    │   │   │   ├── controller/
    │   │   │   ├── dto/
    │   │   │   ├── entity/
    │   │   │   ├── repository/
    │   │   │   ├── service/
    │   │   │   └── mapper/
    │   │   │
    │   │   ├── [modulo1]/
    │   │   │   └── ... (misma estructura)
    │   │   │
    │   │   ├── [modulo2]/
    │   │   │   └── ...
    │   │   │
    │   │   └── common/
    │   │       ├── dto/
    │   │       │   ├── ErrorResponse.java
    │   │       │   └── PageResponse.java
    │   │       └── exception/
    │   │           ├── ResourceNotFoundException.java
    │   │           └── GlobalExceptionHandler.java
    │   │
    │   └── resources/
    │       ├── application.properties
    │       ├── application-dev.properties
    │       └── application-prod.properties
    │
    └── test/
        └── java/com/bootcamp/finalproject/
            ├── unit/
            └── integration/
```

---

## Parte 6: Entregable de Planificación

### 📝 Ejercicio Final: Documento de Planificación

Crea un archivo `docs/PLANNING.md` en tu proyecto con:

```markdown
# Planificación - [Nombre del Proyecto]

## 1. Descripción del Proyecto
[2-3 párrafos describiendo el proyecto]

## 2. Modelo de Datos

### 2.1 Entidades
[Lista de entidades con atributos]

### 2.2 Relaciones
[Tabla o diagrama de relaciones]

### 2.3 Diagrama ER
[Imagen o diagrama ASCII]

## 3. API Endpoints

### 3.1 Autenticación
[Tabla de endpoints de auth]

### 3.2 [Módulo 1]
[Tabla de endpoints]

### 3.3 [Módulo 2]
[Tabla de endpoints]

## 4. Decisiones Técnicas
- **Base de datos**: PostgreSQL porque...
- **Autenticación**: JWT porque...
- **Estructura**: Por módulos porque...

## 5. Cronograma
[Tabla con distribución de tiempo]

## 6. Riesgos y Mitigación
| Riesgo | Probabilidad | Mitigación |
|--------|--------------|------------|
| Falta de tiempo | Media | Priorizar funcionalidades core |
| Complejidad de relaciones | Baja | Empezar con modelo simple |
| Problemas con Docker | Baja | Tener backup de config |
```

---

## ✅ Criterios de Evaluación

| Criterio | Puntos |
|----------|--------|
| Proyecto claramente definido | 2 |
| Modelo de datos completo (≥3 entidades) | 3 |
| Relaciones correctamente identificadas | 2 |
| Endpoints bien diseñados | 2 |
| Cronograma realista | 1 |
| **Total** | **10** |

---

## 🚀 Siguiente Paso

Una vez completada la planificación, continúa con:

→ [Práctica 02: Implementación de Entidades](./02-implementacion-entidades.md)

---

> **💡 Consejo**: Una buena planificación ahorra tiempo de desarrollo. Invierte 30-45 minutos en esta fase antes de escribir código.
