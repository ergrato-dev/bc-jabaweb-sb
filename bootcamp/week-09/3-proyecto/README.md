# Proyecto Final Integrador - E-commerce API

## 📋 Descripción

API REST completa para un sistema de e-commerce que integra todos los conceptos del bootcamp:

- **Spring Boot 3.2** con Java 21
- **Spring Security + JWT** para autenticación
- **JPA/Hibernate** con PostgreSQL
- **Docker Compose** para orquestación
- **OpenAPI/Swagger** para documentación
- **JUnit 5 + TestContainers** para testing

## 🚀 Quick Start

```bash
# 1. Clonar y entrar al directorio
cd 3-proyecto

# 2. Copiar configuración
cp .env.example .env

# 3. Levantar con Docker
docker-compose up --build

# 4. Acceder a la API
# Swagger UI: http://localhost:8080/swagger-ui.html
# API Base: http://localhost:8080/api
```

## 📁 Estructura del Proyecto

```
3-proyecto/
├── docker-compose.yml       # Orquestación de servicios
├── docker-compose.prod.yml  # Configuración para producción
├── Dockerfile               # Build multi-stage
├── .env.example            # Variables de entorno de ejemplo
├── pom.xml                 # Dependencias Maven
├── README.md               # Este archivo
│
├── docs/                   # Documentación adicional
│   ├── API.md             # Documentación de endpoints
│   └── ARCHITECTURE.md    # Decisiones de arquitectura
│
└── src/
    ├── main/
    │   ├── java/com/bootcamp/finalproject/
    │   │   ├── FinalProjectApplication.java
    │   │   ├── config/
    │   │   ├── security/
    │   │   ├── auth/
    │   │   ├── user/
    │   │   ├── category/
    │   │   ├── product/
    │   │   ├── order/
    │   │   └── common/
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

## 🔐 Autenticación

### Registro
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### Usar Token
```bash
curl http://localhost:8080/api/auth/me \
  -H "Authorization: Bearer <tu-token>"
```

## 📚 Endpoints Principales

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | /api/auth/register | Registro | No |
| POST | /api/auth/login | Login | No |
| GET | /api/auth/me | Usuario actual | Sí |
| GET | /api/products | Listar productos | No |
| POST | /api/products | Crear producto | Admin |
| GET | /api/categories | Listar categorías | No |
| POST | /api/orders | Crear pedido | User |
| GET | /api/orders/my | Mis pedidos | User |

## 🐳 Docker

### Servicios

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| app | 8080 | API Spring Boot |
| db | 5432 | PostgreSQL |
| pgadmin | 5050 | Admin BD |

### Comandos Útiles

```bash
# Levantar todo
docker-compose up -d

# Ver logs
docker-compose logs -f app

# Reiniciar app
docker-compose restart app

# Limpiar todo
docker-compose down -v

# Rebuild
docker-compose up --build
```

## 🧪 Testing

```bash
# Ejecutar tests
./mvnw test

# Tests con cobertura
./mvnw test jacoco:report

# Ver reporte
open target/site/jacoco/index.html
```

## ⚙️ Variables de Entorno

| Variable | Default | Descripción |
|----------|---------|-------------|
| DB_HOST | db | Host de PostgreSQL |
| DB_PORT | 5432 | Puerto de PostgreSQL |
| DB_NAME | finalproject | Nombre de la BD |
| DB_USER | dev | Usuario de BD |
| DB_PASSWORD | dev123 | Contraseña de BD |
| JWT_SECRET | - | Secret para JWT (mín 256 bits) |

## 📊 Modelo de Datos

```
User (1) ─────── (N) Order (1) ─────── (N) OrderItem
                                              │
                                          (N) │
                                              │
Category (1) ─── (N) Product ─────────────────┘
```

### Entidades

- **User**: Usuarios del sistema (roles: USER, ADMIN)
- **Category**: Categorías de productos
- **Product**: Productos del catálogo
- **Order**: Pedidos de usuarios
- **OrderItem**: Items de cada pedido

## 🔒 Seguridad

- Contraseñas hasheadas con BCrypt
- JWT para autenticación stateless
- Validación en todos los endpoints
- CORS configurado
- Secrets en variables de entorno

## 📈 Cobertura de Tests

Objetivo: **≥70%**

- Tests unitarios de Services
- Tests de integración de Controllers
- Tests de repositories con TestContainers

## 👥 Roles

| Rol | Permisos |
|-----|----------|
| USER | Ver productos, crear pedidos, ver sus pedidos |
| ADMIN | Todo lo anterior + CRUD de productos y categorías |

## 📝 Notas

- La base de datos se inicializa automáticamente con `spring.jpa.hibernate.ddl-auto=update`
- Para datos de prueba, ejecutar el script `init.sql` o usar el `DataInitializer`
- En producción, cambiar el perfil a `prod` y usar secrets seguros

---

**Bootcamp Java Web con Spring Boot - Semana 9**
