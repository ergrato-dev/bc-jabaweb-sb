# 🔐 Proyecto Semana 07: API Segura con JWT

## Descripción

API REST de gestión de tareas con autenticación JWT y autorización basada en roles.

## Características

- ✅ Autenticación con JWT (JSON Web Tokens)
- ✅ Roles: USER y ADMIN
- ✅ Registro y login de usuarios
- ✅ Refresh tokens
- ✅ Protección de endpoints por rol
- ✅ Verificación de propiedad de recursos
- ✅ PostgreSQL containerizado
- ✅ Documentación Swagger con soporte JWT

## Stack Tecnológico

| Tecnología | Versión |
|------------|---------|
| Java | 21 |
| Spring Boot | 3.2.0 |
| Spring Security | 6.2+ |
| jjwt | 0.12.3 |
| PostgreSQL | 16-alpine |
| Docker | 24+ |

## Estructura del Proyecto

```
3-proyecto/
├── docker-compose.yml
├── Dockerfile
├── pom.xml
├── README.md
├── docs/
│   └── SOLUCIONES.md
└── src/
    ├── main/
    │   ├── java/com/bootcamp/
    │   │   ├── Application.java
    │   │   ├── config/
    │   │   │   ├── DataInitializer.java
    │   │   │   ├── OpenApiConfig.java
    │   │   │   └── SecurityConfig.java
    │   │   ├── exception/
    │   │   │   ├── ErrorResponse.java
    │   │   │   └── GlobalExceptionHandler.java
    │   │   ├── security/
    │   │   │   ├── controller/
    │   │   │   │   └── AuthController.java
    │   │   │   ├── dto/
    │   │   │   │   ├── AuthResponse.java
    │   │   │   │   ├── LoginRequest.java
    │   │   │   │   ├── RefreshTokenRequest.java
    │   │   │   │   ├── RegisterRequest.java
    │   │   │   │   └── UserDTO.java
    │   │   │   ├── entity/
    │   │   │   │   ├── Role.java
    │   │   │   │   └── User.java
    │   │   │   ├── exception/
    │   │   │   │   ├── DuplicateResourceException.java
    │   │   │   │   ├── InvalidTokenException.java
    │   │   │   │   └── SecurityExceptionHandler.java
    │   │   │   ├── filter/
    │   │   │   │   └── JwtAuthenticationFilter.java
    │   │   │   ├── repository/
    │   │   │   │   └── UserRepository.java
    │   │   │   └── service/
    │   │   │       ├── AuthService.java
    │   │   │       ├── CustomUserDetailsService.java
    │   │   │       ├── JwtService.java
    │   │   │       └── UserSecurityService.java
    │   │   └── task/
    │   │       ├── controller/
    │   │       │   └── TaskController.java
    │   │       ├── dto/
    │   │       ├── entity/
    │   │       │   └── Task.java
    │   │       ├── repository/
    │   │       │   └── TaskRepository.java
    │   │       └── service/
    │   │           ├── TaskSecurityService.java
    │   │           └── TaskService.java
    │   └── resources/
    │       ├── application.properties
    │       ├── application-dev.properties
    │       └── application-prod.properties
    └── test/
        └── java/com/bootcamp/
            └── security/
                ├── AuthControllerTest.java
                ├── AuthorizationTest.java
                └── JwtServiceTest.java
```

## Endpoints

### Autenticación (públicos)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/register` | Registrar usuario |
| POST | `/api/auth/login` | Iniciar sesión |
| POST | `/api/auth/refresh` | Renovar token |

### Usuarios (protegidos)

| Método | Endpoint | Rol | Descripción |
|--------|----------|-----|-------------|
| GET | `/api/users` | ADMIN | Listar usuarios |
| GET | `/api/users/me` | USER/ADMIN | Perfil actual |
| GET | `/api/users/{id}` | Propietario/ADMIN | Ver usuario |
| DELETE | `/api/users/{id}` | ADMIN | Eliminar usuario |

### Tareas (protegidos)

| Método | Endpoint | Rol | Descripción |
|--------|----------|-----|-------------|
| GET | `/api/tasks` | USER/ADMIN | Listar tareas |
| GET | `/api/tasks/{id}` | Propietario/ADMIN | Ver tarea |
| POST | `/api/tasks` | USER/ADMIN | Crear tarea |
| PUT | `/api/tasks/{id}` | Propietario/ADMIN | Actualizar |
| DELETE | `/api/tasks/{id}` | Propietario/ADMIN | Eliminar |

## Inicio Rápido

### Con Docker Compose

```bash
# Iniciar todo
docker-compose up -d

# Ver logs
docker-compose logs -f app
```

### Desarrollo local

```bash
# Iniciar solo BD
docker-compose up -d db

# Ejecutar aplicación
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

## Probar la API

### 1. Registrar usuario

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "email": "john@example.com",
    "password": "Password123"
  }'
```

### 2. Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john",
    "password": "Password123"
  }'
```

### 3. Acceder a endpoint protegido

```bash
curl http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <tu_token>"
```

## Usuarios de Prueba

| Username | Password | Rol |
|----------|----------|-----|
| admin | Admin123! | ADMIN |
| user | User123! | USER |

## Swagger UI

Accede a la documentación interactiva:
- http://localhost:8080/swagger-ui.html

Para probar endpoints protegidos:
1. Ejecuta login desde Swagger
2. Copia el `accessToken` de la respuesta
3. Click en "Authorize" (candado)
4. Pega el token (sin "Bearer ")
5. Click "Authorize"

## Variables de Entorno

| Variable | Descripción | Default |
|----------|-------------|---------|
| `DB_HOST` | Host de PostgreSQL | db |
| `DB_PORT` | Puerto de PostgreSQL | 5432 |
| `DB_NAME` | Nombre de BD | bootcamp |
| `DB_USER` | Usuario de BD | dev |
| `DB_PASSWORD` | Password de BD | dev123 |
| `JWT_SECRET` | Clave secreta JWT | (generada) |
| `JWT_EXPIRATION` | Expiración access token (ms) | 86400000 |

## Ejecutar Tests

```bash
# Todos los tests
./mvnw test

# Solo tests de seguridad
./mvnw test -Dtest="*Security*,*Auth*,*Jwt*"

# Con cobertura
./mvnw test jacoco:report
```

## Para Instructores

Ver [docs/SOLUCIONES.md](docs/SOLUCIONES.md) para las soluciones a los TODOs.
