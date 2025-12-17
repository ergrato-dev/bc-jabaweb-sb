# Docker Compose Avanzado

## 📚 Conceptos Avanzados

### Arquitectura Multi-servicio

En un entorno de producción típico, una aplicación Spring Boot necesita:

- **app**: La aplicación Spring Boot
- **db**: Base de datos PostgreSQL
- **pgadmin**: Administrador de BD (opcional)
- **redis**: Cache (opcional)
- **nginx**: Reverse proxy (opcional)

---

## 📁 Estructura de Archivos

```
project/
├── docker-compose.yml          # Producción
├── docker-compose.override.yml # Desarrollo (auto-merge)
├── docker-compose.test.yml     # Testing
├── Dockerfile                  # Multi-stage build
├── .env                        # Variables (NO en git)
├── .env.example                # Template de variables
└── .dockerignore               # Archivos a ignorar
```

---

## 🔧 docker-compose.yml (Producción)

```yaml
version: '3.8'

services:
  app:
    build:
      context: .
      dockerfile: Dockerfile
      target: production
    container_name: bootcamp-app
    ports:
      - "${APP_PORT:-8080}:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/${DB_NAME}
      - SPRING_DATASOURCE_USERNAME=${DB_USER}
      - SPRING_DATASOURCE_PASSWORD=${DB_PASSWORD}
      - JWT_SECRET=${JWT_SECRET}
    depends_on:
      db:
        condition: service_healthy
    networks:
      - bootcamp-network
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s

  db:
    image: postgres:16-alpine
    container_name: bootcamp-db
    environment:
      - POSTGRES_DB=${DB_NAME}
      - POSTGRES_USER=${DB_USER}
      - POSTGRES_PASSWORD=${DB_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init-scripts:/docker-entrypoint-initdb.d:ro
    ports:
      - "${DB_PORT:-5432}:5432"
    networks:
      - bootcamp-network
    restart: unless-stopped
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${DB_USER} -d ${DB_NAME}"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 10s

  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: bootcamp-pgadmin
    environment:
      - PGADMIN_DEFAULT_EMAIL=${PGADMIN_EMAIL}
      - PGADMIN_DEFAULT_PASSWORD=${PGADMIN_PASSWORD}
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
    profiles:
      - tools  # Solo se levanta con --profile tools

networks:
  bootcamp-network:
    driver: bridge
    name: bootcamp-network

volumes:
  postgres_data:
    name: bootcamp-postgres-data
  pgadmin_data:
    name: bootcamp-pgadmin-data
```

---

## 🔄 docker-compose.override.yml (Desarrollo)

Este archivo se **fusiona automáticamente** con docker-compose.yml:

```yaml
version: '3.8'

services:
  app:
    build:
      target: development
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - SPRING_DEVTOOLS_RESTART_ENABLED=true
    volumes:
      # Hot reload: sincroniza código fuente
      - ./src:/app/src:ro
      - ./pom.xml:/app/pom.xml:ro
      # Cache de Maven para builds más rápidos
      - maven_cache:/root/.m2
    # Override del comando para desarrollo
    command: ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]
    ports:
      - "8080:8080"
      - "5005:5005"  # Debug port
    healthcheck:
      # Más tolerante en desarrollo
      start_period: 120s

  db:
    ports:
      - "5432:5432"  # Exponer para herramientas locales

volumes:
  maven_cache:
    name: bootcamp-maven-cache
```

---

## 🧪 docker-compose.test.yml (Testing)

```yaml
version: '3.8'

services:
  app-test:
    build:
      context: .
      dockerfile: Dockerfile
      target: test
    container_name: bootcamp-app-test
    environment:
      - SPRING_PROFILES_ACTIVE=test
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db-test:5432/testdb
      - SPRING_DATASOURCE_USERNAME=test
      - SPRING_DATASOURCE_PASSWORD=test
    depends_on:
      db-test:
        condition: service_healthy
    networks:
      - test-network
    command: ["mvn", "test", "-Dtest=**/*IT.java"]

  db-test:
    image: postgres:16-alpine
    container_name: bootcamp-db-test
    environment:
      - POSTGRES_DB=testdb
      - POSTGRES_USER=test
      - POSTGRES_PASSWORD=test
    networks:
      - test-network
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U test -d testdb"]
      interval: 5s
      timeout: 3s
      retries: 5
    # Sin volumen persistente para tests limpios

networks:
  test-network:
    driver: bridge
```

---

## 🐳 Dockerfile Multi-stage

```dockerfile
# ============================================
# Stage 1: Base con dependencias
# ============================================
FROM eclipse-temurin:21-jdk-alpine AS base

WORKDIR /app

# Instalar Maven
RUN apk add --no-cache maven

# Copiar solo archivos de dependencias primero (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# ============================================
# Stage 2: Development (con herramientas)
# ============================================
FROM base AS development

# Copiar código fuente
COPY src ./src

# Exponer puertos
EXPOSE 8080 5005

# Comando por defecto para desarrollo
CMD ["mvn", "spring-boot:run"]

# ============================================
# Stage 3: Build para producción
# ============================================
FROM base AS build

# Copiar código fuente
COPY src ./src

# Compilar sin tests (los tests corren en CI/CD)
RUN mvn clean package -DskipTests -B

# ============================================
# Stage 4: Test runner
# ============================================
FROM base AS test

COPY src ./src

# Ejecutar tests
CMD ["mvn", "test"]

# ============================================
# Stage 5: Production (imagen final ligera)
# ============================================
FROM eclipse-temurin:21-jre-alpine AS production

WORKDIR /app

# Usuario no-root para seguridad
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Copiar JAR desde stage de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar a usuario no-root
USER appuser

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

# Optimizaciones JVM para contenedores
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
```

---

## 📋 Archivo .env

### .env.example (Template - SÍ en git)

```bash
# Application
APP_PORT=8080
SPRING_PROFILES_ACTIVE=dev

# Database
DB_NAME=bootcamp
DB_USER=postgres
DB_PASSWORD=CHANGE_ME_IN_PRODUCTION
DB_PORT=5432

# PgAdmin
PGADMIN_EMAIL=admin@bootcamp.local
PGADMIN_PASSWORD=CHANGE_ME
PGADMIN_PORT=5050

# JWT
JWT_SECRET=CHANGE_THIS_TO_A_LONG_RANDOM_STRING_MIN_256_BITS
JWT_EXPIRATION=3600000
```

### .env (Real - NO en git)

```bash
# Copiar de .env.example y cambiar valores
DB_PASSWORD=my_actual_secure_password_123!
JWT_SECRET=my_actual_256_bit_secret_key_here_very_long_and_random
PGADMIN_PASSWORD=admin_secure_pass
```

---

## 🔐 Healthchecks en Detalle

### Tipos de Healthcheck

```yaml
services:
  app:
    healthcheck:
      # Usando curl
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]

      # Usando wget (más ligero en Alpine)
      test: ["CMD", "wget", "--no-verbose", "--tries=1", "--spider", "http://localhost:8080/actuator/health"]

      # Script personalizado
      test: ["CMD-SHELL", "/app/healthcheck.sh"]

      interval: 30s      # Frecuencia de checks
      timeout: 10s       # Tiempo máximo por check
      retries: 3         # Intentos antes de unhealthy
      start_period: 40s  # Tiempo de gracia al iniciar

  db:
    healthcheck:
      # PostgreSQL
      test: ["CMD-SHELL", "pg_isready -U postgres -d mydb"]

      # MySQL
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]

      # Redis
      test: ["CMD", "redis-cli", "ping"]
```

### depends_on con Condiciones

```yaml
services:
  app:
    depends_on:
      db:
        condition: service_healthy  # Espera healthcheck OK
      redis:
        condition: service_started  # Solo espera que inicie
      migrations:
        condition: service_completed_successfully  # Espera que termine OK
```

---

## 🌐 Redes en Docker Compose

### Red Bridge (Default)

```yaml
networks:
  app-network:
    driver: bridge
```

### Red con Subnet Personalizada

```yaml
networks:
  app-network:
    driver: bridge
    ipam:
      config:
        - subnet: 172.28.0.0/16
```

### Múltiples Redes

```yaml
services:
  app:
    networks:
      - frontend
      - backend

  db:
    networks:
      - backend  # Solo accesible desde backend

  nginx:
    networks:
      - frontend

networks:
  frontend:
    driver: bridge
  backend:
    driver: bridge
    internal: true  # Sin acceso a internet
```

---

## 📦 Volúmenes

### Tipos de Volúmenes

```yaml
services:
  db:
    volumes:
      # Named volume (recomendado para datos)
      - postgres_data:/var/lib/postgresql/data

      # Bind mount (para desarrollo)
      - ./init-scripts:/docker-entrypoint-initdb.d:ro

      # Anonymous volume (temporal)
      - /var/log

volumes:
  postgres_data:
    driver: local
    # driver_opts para NFS, etc.
```

### Backup de Volúmenes

```bash
# Backup
docker run --rm \
  -v bootcamp-postgres-data:/data:ro \
  -v $(pwd):/backup \
  alpine tar czf /backup/postgres-backup.tar.gz -C /data .

# Restore
docker run --rm \
  -v bootcamp-postgres-data:/data \
  -v $(pwd):/backup \
  alpine tar xzf /backup/postgres-backup.tar.gz -C /data
```

---

## 🚀 Comandos Útiles

### Operaciones Básicas

```bash
# Levantar servicios
docker compose up -d

# Levantar con rebuild
docker compose up -d --build

# Levantar con profile específico
docker compose --profile tools up -d

# Ver logs
docker compose logs -f
docker compose logs -f app

# Detener
docker compose down

# Detener y eliminar volúmenes
docker compose down -v

# Detener y eliminar todo (imágenes incluidas)
docker compose down -v --rmi all
```

### Operaciones de Desarrollo

```bash
# Rebuild solo un servicio
docker compose build app

# Restart un servicio
docker compose restart app

# Ejecutar comando en contenedor
docker compose exec app bash
docker compose exec db psql -U postgres -d bootcamp

# Ver estado
docker compose ps

# Ver uso de recursos
docker compose top
docker stats
```

### Operaciones de Testing

```bash
# Ejecutar tests
docker compose -f docker-compose.yml -f docker-compose.test.yml up --abort-on-container-exit --exit-code-from app-test

# Limpiar después de tests
docker compose -f docker-compose.test.yml down -v
```

---

## 🔗 Recursos

- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Compose Specification](https://docs.docker.com/compose/compose-file/)
- [Docker Best Practices](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/)
