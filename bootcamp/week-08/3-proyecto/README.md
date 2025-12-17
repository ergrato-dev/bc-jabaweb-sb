# Proyecto Semana 8: API con Testing Completo y Docker Multi-servicio

## 📋 Descripción

Proyecto Spring Boot con suite de tests completa (unitarios, integración con TestContainers) y configuración Docker multi-servicio lista para producción.

## 🎯 Objetivos

- Implementar tests unitarios con JUnit 5 y Mockito
- Implementar tests de integración con TestContainers
- Configurar JaCoCo para cobertura mínima del 70%
- Crear Docker Compose multi-servicio con healthchecks
- Aplicar multi-stage builds en Dockerfile

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────────────────┐
│                         Docker Compose                          │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │                 │  │                 │  │                 │ │
│  │   Spring Boot   │──│   PostgreSQL    │  │    PgAdmin      │ │
│  │      App        │  │       DB        │  │    (tools)      │ │
│  │                 │  │                 │  │                 │ │
│  └────────┬────────┘  └────────┬────────┘  └─────────────────┘ │
│           │                    │                                │
│           └────────────────────┘                                │
│                bootcamp-network                                 │
└─────────────────────────────────────────────────────────────────┘
```

## 📁 Estructura del Proyecto

```
3-proyecto/
├── docker-compose.yml           # Producción
├── docker-compose.override.yml  # Desarrollo
├── docker-compose.test.yml      # Testing CI/CD
├── Dockerfile                   # Multi-stage
├── pom.xml
├── .env.example
├── .dockerignore
├── README.md
├── docs/
│   ├── TESTING.md              # Guía de testing
│   └── SOLUCIONES.md           # Para instructores
└── src/
    ├── main/
    │   ├── java/com/bootcamp/week08/
    │   │   ├── Week08Application.java
    │   │   ├── config/
    │   │   │   ├── OpenApiConfig.java
    │   │   │   └── SecurityConfig.java
    │   │   ├── product/
    │   │   │   ├── controller/ProductController.java
    │   │   │   ├── service/ProductService.java
    │   │   │   ├── repository/ProductRepository.java
    │   │   │   ├── entity/Product.java
    │   │   │   └── dto/
    │   │   │       ├── ProductDTO.java
    │   │   │       └── CreateProductRequest.java
    │   │   ├── auth/
    │   │   │   ├── controller/AuthController.java
    │   │   │   ├── service/AuthService.java
    │   │   │   └── dto/
    │   │   └── exception/
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       ├── application.properties
    │       ├── application-dev.properties
    │       ├── application-prod.properties
    │       └── application-test.properties
    └── test/
        ├── java/com/bootcamp/week08/
        │   ├── unit/
        │   │   ├── service/ProductServiceTest.java
        │   │   └── controller/ProductControllerTest.java
        │   ├── integration/
        │   │   ├── AbstractIntegrationTest.java
        │   │   └── repository/ProductRepositoryIT.java
        │   └── auth/
        │       └── AuthControllerTest.java
        └── resources/
            └── application-test.properties
```

## 🚀 Inicio Rápido

### Con Docker (Recomendado)

```bash
# 1. Clonar y entrar al proyecto
cd 3-proyecto

# 2. Copiar variables de entorno
cp .env.example .env
# Editar .env con valores seguros

# 3. Levantar servicios
docker compose up -d --build

# 4. Verificar
docker compose ps
curl http://localhost:8080/actuator/health

# 5. Ver Swagger
open http://localhost:8080/swagger-ui.html
```

### Desarrollo Local

```bash
# 1. Levantar solo la BD
docker compose up -d db

# 2. Ejecutar la app
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# 3. Ejecutar tests
./mvnw test
```

## 🧪 Testing

### Ejecutar Tests

```bash
# Todos los tests
./mvnw test

# Solo unitarios (rápidos)
./mvnw test -Dtest="**/*Test.java"

# Solo integración (requiere Docker)
./mvnw test -Dtest="**/*IT.java"

# Con reporte de cobertura
./mvnw verify

# Ver reporte
open target/site/jacoco/index.html
```

### Cobertura Mínima

El proyecto requiere **70% de cobertura** mínima. Si no se alcanza, el build falla.

## 📊 Endpoints

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/auth/register | Registro de usuario |
| POST | /api/auth/login | Login (retorna JWT) |
| GET | /api/products | Listar productos |
| GET | /api/products/{id} | Obtener producto |
| POST | /api/products | Crear producto |
| PUT | /api/products/{id} | Actualizar producto |
| DELETE | /api/products/{id} | Eliminar producto |
| GET | /actuator/health | Health check |

## 🔧 Configuración

### Variables de Entorno

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| DB_NAME | Nombre de la BD | bootcamp |
| DB_USER | Usuario de BD | postgres |
| DB_PASSWORD | Contraseña de BD | (requerido) |
| JWT_SECRET | Secreto para JWT | (requerido) |
| APP_PORT | Puerto de la app | 8080 |

## 📝 Notas para Instructores

Ver [docs/SOLUCIONES.md](docs/SOLUCIONES.md) para las implementaciones completas de los TODOs.
