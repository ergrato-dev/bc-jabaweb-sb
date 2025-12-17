# Práctica 05: Docker Compose Multi-servicio

## 🎯 Objetivos

- Crear docker-compose con múltiples servicios
- Configurar healthchecks para dependencias
- Usar variables de entorno y .env
- Implementar Dockerfile multi-stage

## ⏱️ Duración: 30 minutos

---

## 📋 Prerrequisitos

- Docker y Docker Compose instalados
- Proyecto Spring Boot compilable
- Conocimientos básicos de Docker

---

## 🔧 Paso 1: Estructura de Archivos

```
project/
├── docker-compose.yml           # Producción
├── docker-compose.override.yml  # Desarrollo (auto-merge)
├── Dockerfile                   # Multi-stage
├── .env.example                 # Template
├── .env                         # Variables reales (NO en git)
└── .dockerignore
```

---

## 📝 Paso 2: Crear Dockerfile Multi-stage

### 2.1 Dockerfile

```dockerfile
# ============================================
# Stage 1: Builder
# ============================================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Instalar Maven
RUN apk add --no-cache maven

# Copiar archivos de dependencias primero (cache layer)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (cached si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación
RUN mvn clean package -DskipTests -B

# ============================================
# Stage 2: Development
# ============================================
FROM eclipse-temurin:21-jdk-alpine AS development

WORKDIR /app

# Instalar herramientas de desarrollo
RUN apk add --no-cache maven curl

# Copiar desde builder para tener dependencias
COPY --from=builder /root/.m2 /root/.m2
COPY pom.xml .
COPY src ./src

EXPOSE 8080 5005

# Comando para desarrollo con hot reload y debug
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]

# ============================================
# Stage 3: Production
# ============================================
FROM eclipse-temurin:21-jre-alpine AS production

WORKDIR /app

# Crear usuario no-root
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Instalar curl para healthcheck
RUN apk add --no-cache curl

# Copiar JAR desde builder
COPY --from=builder /app/target/*.jar app.jar

# Cambiar ownership
RUN chown -R appuser:appgroup /app

# Usar usuario no-root
USER appuser

EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Optimizaciones JVM para contenedores
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
```

---

## 📝 Paso 3: Crear docker-compose.yml (Producción)

```yaml
# TODO: Completar la configuración
version: '3.8'

services:
  # ============================================
  # Application Service
  # ============================================
  app:
    build:
      context: .
      dockerfile: Dockerfile
      target: production
    container_name: bootcamp-app
    ports:
      - "${APP_PORT:-8080}:8080"
    environment:
      # TODO: Agregar variables de entorno
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/${DB_NAME:-bootcamp}
      - SPRING_DATASOURCE_USERNAME=${DB_USER:-postgres}
      - SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
      - JWT_SECRET=${JWT_SECRET}
    depends_on:
      db:
        condition: service_healthy
    networks:
      - bootcamp-network
    restart: unless-stopped
    # TODO: Agregar healthcheck
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s

  # ============================================
  # Database Service
  # ============================================
  db:
    image: postgres:16-alpine
    container_name: bootcamp-db
    environment:
      # TODO: Usar variables de .env
      - POSTGRES_DB=${DB_NAME:-bootcamp}
      - POSTGRES_USER=${DB_USER:-postgres}
      - POSTGRES_PASSWORD=${DB_PASSWORD}
    volumes:
      # TODO: Agregar volumen persistente
      - postgres_data:/var/lib/postgresql/data
      # Scripts de inicialización (opcional)
      - ./init-scripts:/docker-entrypoint-initdb.d:ro
    ports:
      - "${DB_PORT:-5432}:5432"
    networks:
      - bootcamp-network
    restart: unless-stopped
    # TODO: Agregar healthcheck para PostgreSQL
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${DB_USER:-postgres} -d ${DB_NAME:-bootcamp}"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s

  # ============================================
  # PgAdmin Service (Tools Profile)
  # ============================================
  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: bootcamp-pgadmin
    environment:
      - PGADMIN_DEFAULT_EMAIL=${PGADMIN_EMAIL:-admin@bootcamp.local}
      - PGADMIN_DEFAULT_PASSWORD=${PGADMIN_PASSWORD:-admin}
      - PGADMIN_CONFIG_SERVER_MODE=False
    volumes:
      - pgadmin_data:/var/lib/pgadmin
    ports:
      - "${PGADMIN_PORT:-5050}:80"
    depends_on:
      - db
    networks:
      - bootcamp-network
    restart: unless-stopped
    # Solo se levanta con: docker compose --profile tools up
    profiles:
      - tools

# ============================================
# Networks
# ============================================
networks:
  bootcamp-network:
    driver: bridge
    name: bootcamp-network

# ============================================
# Volumes
# ============================================
volumes:
  postgres_data:
    name: bootcamp-postgres-data
  pgadmin_data:
    name: bootcamp-pgadmin-data
```

---

## 📝 Paso 4: Crear docker-compose.override.yml (Desarrollo)

```yaml
# TODO: Configurar para desarrollo
# Este archivo se fusiona automáticamente con docker-compose.yml
version: '3.8'

services:
  app:
    build:
      target: development  # Usar stage de desarrollo
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DEVTOOLS_RESTART_ENABLED=true
    volumes:
      # Hot reload: sincronizar código fuente
      - ./src:/app/src:ro
      - ./pom.xml:/app/pom.xml:ro
      # Cache de Maven
      - maven_cache:/root/.m2
    ports:
      - "8080:8080"
      - "5005:5005"  # Puerto de debug
    # TODO: Comando de desarrollo
    command: ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]
    healthcheck:
      start_period: 120s  # Más tiempo en desarrollo

  db:
    ports:
      - "5432:5432"  # Exponer para herramientas locales

volumes:
  maven_cache:
    name: bootcamp-maven-cache
```

---

## 📝 Paso 5: Crear .env.example

```bash
# ============================================
# Application
# ============================================
APP_PORT=8080
SPRING_PROFILES_ACTIVE=prod

# ============================================
# Database
# ============================================
DB_NAME=bootcamp
DB_USER=postgres
DB_PASSWORD=CHANGE_ME_SECURE_PASSWORD
DB_PORT=5432

# ============================================
# JWT Security
# ============================================
# Generar con: openssl rand -base64 64
JWT_SECRET=CHANGE_THIS_TO_A_VERY_LONG_RANDOM_SECRET_AT_LEAST_256_BITS

# ============================================
# PgAdmin (opcional)
# ============================================
PGADMIN_EMAIL=admin@bootcamp.local
PGADMIN_PASSWORD=CHANGE_ME
PGADMIN_PORT=5050
```

---

## 📝 Paso 6: Crear .dockerignore

```
# Build outputs
target/
*.jar
*.war

# IDE
.idea/
*.iml
.vscode/
*.code-workspace

# Logs
*.log
logs/

# Git
.git/
.gitignore

# Docker
Dockerfile*
docker-compose*
.dockerignore

# Environment files
.env
.env.local
.env.*.local

# Documentation
README.md
docs/

# Tests
src/test/

# OS
.DS_Store
Thumbs.db
```

---

## 📝 Paso 7: Comandos de Operación

### 7.1 Script de inicio (start.sh)

```bash
#!/bin/bash

# Verificar que .env existe
if [ ! -f .env ]; then
    echo "⚠️  Archivo .env no encontrado. Copiando desde .env.example..."
    cp .env.example .env
    echo "📝 Por favor, edita .env con tus valores antes de continuar."
    exit 1
fi

# Construir y levantar
echo "🚀 Iniciando servicios..."
docker compose up -d --build

# Esperar a que esté healthy
echo "⏳ Esperando a que la aplicación esté lista..."
timeout 120 bash -c 'until docker compose ps app | grep -q "healthy"; do sleep 5; echo "Esperando..."; done'

echo "✅ Servicios iniciados!"
echo "📊 App: http://localhost:8080"
echo "📊 Swagger: http://localhost:8080/swagger-ui.html"
docker compose ps
```

### 7.2 Comandos útiles

```bash
# TODO: Practicar estos comandos

# Desarrollo (usa override automáticamente)
docker compose up -d

# Producción (solo docker-compose.yml)
docker compose -f docker-compose.yml up -d

# Con herramientas (pgadmin)
docker compose --profile tools up -d

# Ver logs
docker compose logs -f
docker compose logs -f app

# Rebuild después de cambios en Dockerfile
docker compose up -d --build

# Detener todo
docker compose down

# Detener y eliminar volúmenes (¡CUIDADO! Borra datos)
docker compose down -v

# Ejecutar comando en contenedor
docker compose exec app bash
docker compose exec db psql -U postgres -d bootcamp

# Ver estado de salud
docker compose ps
docker inspect --format='{{.State.Health.Status}}' bootcamp-app

# Ver uso de recursos
docker stats
```

---

## ✅ Criterios de Éxito

- [ ] `docker compose up -d` levanta todos los servicios
- [ ] La app espera a que la BD esté healthy antes de iniciar
- [ ] Variables de entorno se leen desde .env
- [ ] PgAdmin se levanta solo con --profile tools
- [ ] Datos de PostgreSQL persisten en volumen
- [ ] En desarrollo hay hot reload funcionando
- [ ] Imagen de producción es pequeña (<200MB)

---

## 🚀 Verificación Final

```bash
# 1. Crear .env desde template
cp .env.example .env
# Editar .env con valores seguros

# 2. Construir y levantar
docker compose up -d --build

# 3. Verificar estado
docker compose ps

# 4. Ver logs
docker compose logs -f app

# 5. Probar la API
curl http://localhost:8080/actuator/health

# 6. Probar Swagger
open http://localhost:8080/swagger-ui.html

# 7. (Opcional) Levantar PgAdmin
docker compose --profile tools up -d pgadmin
open http://localhost:5050

# 8. Limpiar
docker compose down
```

---

## 💡 Tips

### Verificar tamaño de imagen

```bash
docker images | grep bootcamp
# Objetivo: <200MB para producción
```

### Debug de healthcheck

```bash
# Ver historial de healthchecks
docker inspect --format='{{json .State.Health}}' bootcamp-app | jq

# Ejecutar healthcheck manualmente
docker compose exec app curl -f http://localhost:8080/actuator/health
```

### Optimizar tiempo de build

```bash
# Ver capas de la imagen
docker history bootcamp-app:latest

# Build con cache
docker compose build --parallel
```
