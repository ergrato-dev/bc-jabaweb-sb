# 🔐 Semana 07: Seguridad con Spring Security y JWT

## Descripción General

En esta semana aprenderemos a **asegurar nuestra API REST** implementando autenticación y autorización con **Spring Security** y **JSON Web Tokens (JWT)**. Cubriremos desde los fundamentos de seguridad web hasta la implementación completa de un sistema de autenticación stateless.

## Objetivos de Aprendizaje

Al finalizar esta semana, serás capaz de:

1. ✅ Comprender la diferencia entre autenticación y autorización
2. ✅ Configurar Spring Security para APIs REST
3. ✅ Implementar autenticación basada en JWT
4. ✅ Proteger endpoints según roles y permisos
5. ✅ Crear endpoints de registro, login y refresh token
6. ✅ Manejar contraseñas de forma segura con BCrypt
7. ✅ Implementar filtros de seguridad personalizados

## Requisitos Previos

- ✅ Semana 06 completada (Swagger/OpenAPI y CORS)
- ✅ Conocimiento de arquitectura en capas
- ✅ Familiaridad con DTOs y validación
- ✅ Docker y Docker Compose funcionando

## Contenido de la Semana

### 📚 Teoría (1-teoria/)

| Archivo | Tema | Duración |
|---------|------|----------|
| [01-fundamentos-seguridad.md](1-teoria/01-fundamentos-seguridad.md) | Autenticación vs Autorización, Stateless | 30 min |
| [02-spring-security-configuracion.md](1-teoria/02-spring-security-configuracion.md) | Configuración de Spring Security | 40 min |
| [03-jwt-fundamentos.md](1-teoria/03-jwt-fundamentos.md) | Estructura y funcionamiento de JWT | 35 min |
| [04-implementacion-jwt.md](1-teoria/04-implementacion-jwt.md) | Generación y validación de tokens | 45 min |
| [05-proteccion-endpoints.md](1-teoria/05-proteccion-endpoints.md) | Roles, permisos y @PreAuthorize | 30 min |

### 🔧 Prácticas (2-practicas/)

| Archivo | Ejercicio | Duración |
|---------|-----------|----------|
| [01-configurar-spring-security.md](2-practicas/01-configurar-spring-security.md) | Configuración básica de seguridad | 40 min |
| [02-implementar-jwt-service.md](2-practicas/02-implementar-jwt-service.md) | Servicio de generación/validación JWT | 50 min |
| [03-auth-endpoints.md](2-practicas/03-auth-endpoints.md) | Endpoints de registro y login | 45 min |
| [04-proteger-endpoints.md](2-practicas/04-proteger-endpoints.md) | Protección por roles | 40 min |
| [05-proyecto-integrador.md](2-practicas/05-proyecto-integrador.md) | Sistema de autenticación completo | 60 min |

### 💻 Proyecto (3-proyecto/)

API REST de gestión de tareas con autenticación JWT completa:

- **Registro de usuarios** (`POST /api/auth/register`)
- **Login con JWT** (`POST /api/auth/login`)
- **Refresh token** (`POST /api/auth/refresh`)
- **Endpoints protegidos por roles** (USER, ADMIN)
- **Documentación Swagger con autenticación**

### 📖 Recursos (4-recursos/)

- eBooks gratuitos sobre seguridad en APIs
- Videos sobre Spring Security y JWT
- Artículos y documentación oficial

### 📝 Glosario (5-glosario/)

Términos clave: JWT, Bearer Token, BCrypt, SecurityContext, FilterChain, etc.

---

## Distribución del Tiempo (5 horas)

| Actividad | Tiempo |
|-----------|--------|
| Teoría: Fundamentos y Spring Security | 1:00 h |
| Teoría: JWT y protección | 1:00 h |
| Práctica guiada: Configuración | 1:00 h |
| Práctica guiada: Implementación JWT | 1:30 h |
| Proyecto integrador | 0:30 h |

---

## Estructura de Archivos

```
week-07/
├── README.md
├── rubrica-evaluacion.md
├── 0-assets/
│   ├── 01-auth-vs-authz.svg
│   ├── 02-spring-security-architecture.svg
│   ├── 03-jwt-structure.svg
│   ├── 04-jwt-flow.svg
│   └── 05-security-filter-chain.svg
├── 1-teoria/
│   ├── 01-fundamentos-seguridad.md
│   ├── 02-spring-security-configuracion.md
│   ├── 03-jwt-fundamentos.md
│   ├── 04-implementacion-jwt.md
│   └── 05-proteccion-endpoints.md
├── 2-practicas/
│   ├── 01-configurar-spring-security.md
│   ├── 02-implementar-jwt-service.md
│   ├── 03-auth-endpoints.md
│   ├── 04-proteger-endpoints.md
│   └── 05-proyecto-integrador.md
├── 3-proyecto/
│   ├── README.md
│   ├── pom.xml
│   ├── Dockerfile
│   ├── docker-compose.yml
│   ├── docs/
│   │   └── SOLUCIONES.md
│   └── src/
├── 4-recursos/
│   ├── ebooks-free/
│   ├── videografia/
│   └── webgrafia/
├── 5-glosario/
│   └── README.md
└── 6-bonus/
    └── (contenido existente preservado)
```

---

## Endpoints del Proyecto

### Autenticación (públicos)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/register` | Registro de nuevo usuario |
| POST | `/api/auth/login` | Autenticación y obtención de JWT |
| POST | `/api/auth/refresh` | Renovar token de acceso |

### Usuarios (protegidos)

| Método | Endpoint | Rol Requerido |
|--------|----------|---------------|
| GET | `/api/users` | ADMIN |
| GET | `/api/users/{id}` | USER (propio) o ADMIN |
| GET | `/api/users/me` | USER |
| PUT | `/api/users/{id}` | USER (propio) o ADMIN |
| DELETE | `/api/users/{id}` | ADMIN |

### Tareas (protegidos)

| Método | Endpoint | Rol Requerido |
|--------|----------|---------------|
| GET | `/api/tasks` | USER (propias) o ADMIN (todas) |
| GET | `/api/tasks/{id}` | USER (propias) o ADMIN |
| POST | `/api/tasks` | USER |
| PUT | `/api/tasks/{id}` | USER (propias) o ADMIN |
| DELETE | `/api/tasks/{id}` | USER (propias) o ADMIN |

---

## Tecnologías de la Semana

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Spring Security | 6.2+ | Framework de seguridad |
| jjwt (io.jsonwebtoken) | 0.12.3 | Librería JWT |
| BCrypt | - | Hash de contraseñas |
| Spring Boot Starter Security | 3.2+ | Autoconfiguración |

---

## Flujo de Autenticación JWT

```
┌─────────┐      1. POST /auth/login       ┌─────────┐
│ Cliente │ ─────────────────────────────► │ Servidor│
│         │    { username, password }      │         │
│         │                                │         │
│         │      2. JWT Token              │         │
│         │ ◄───────────────────────────── │         │
│         │    { accessToken, refresh }    │         │
│         │                                │         │
│         │   3. GET /api/tasks            │         │
│         │ ─────────────────────────────► │         │
│         │   Authorization: Bearer <JWT>  │         │
│         │                                │         │
│         │      4. Protected Resource     │         │
│         │ ◄───────────────────────────── │         │
└─────────┘    { tasks: [...] }            └─────────┘
```

---

## Checklist de la Semana

- [ ] Leer teoría sobre fundamentos de seguridad
- [ ] Comprender estructura y flujo de JWT
- [ ] Configurar Spring Security básico
- [ ] Implementar JwtService para generar/validar tokens
- [ ] Crear AuthController con registro y login
- [ ] Implementar JwtAuthenticationFilter
- [ ] Proteger endpoints con @PreAuthorize
- [ ] Probar con Postman/curl
- [ ] Documentar endpoints de auth en Swagger
- [ ] Completar proyecto integrador

---

## Notas Importantes

> ⚠️ **Seguridad**: Nunca almacenes contraseñas en texto plano. Siempre usa BCrypt u otro algoritmo de hash seguro.

> 🔑 **JWT Secret**: En producción, usa una clave secreta fuerte (mínimo 256 bits) y almacénala en variables de entorno.

> 🔄 **Refresh Tokens**: Implementa refresh tokens para mejorar la experiencia de usuario sin comprometer la seguridad.

---

## Navegación

| ← Anterior | Inicio | Siguiente → |
|------------|--------|-------------|
| [Semana 06: Swagger/OpenAPI](../week-06/README.md) | [Índice](../../README.md) | [Semana 08: Testing](../week-08/README.md) |
