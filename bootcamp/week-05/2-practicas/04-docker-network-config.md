# Práctica 04: Configurar Redes Docker Custom

## 🎯 Objetivo

Configurar una red Docker personalizada para comunicación entre contenedores Spring Boot y PostgreSQL usando DNS interno.

---

## 📋 Requisitos Previos

- Docker y Docker Compose instalados
- Proyecto de semanas anteriores funcionando

---

## 🛠️ Pasos

### Paso 1: Actualizar docker-compose.yml

Crea/actualiza `docker-compose.yml`:

```yaml
version: '3.8'

services:
  # ========== APLICACIÓN SPRING BOOT ==========
  api:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: taskmanager-api
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      # Conexión usando nombre de servicio (DNS interno)
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/taskmanager
      - SPRING_DATASOURCE_USERNAME=dev
      - SPRING_DATASOURCE_PASSWORD=dev123
    depends_on:
      db:
        condition: service_healthy
    networks:
      - backend
    restart: unless-stopped
    # Healthcheck para la API
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s

  # ========== BASE DE DATOS ==========
  db:
    image: postgres:16-alpine
    container_name: taskmanager-db
    environment:
      POSTGRES_DB: taskmanager
      POSTGRES_USER: dev
      POSTGRES_PASSWORD: dev123
    # Puerto expuesto al host (para herramientas como DBeaver)
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init-db:/docker-entrypoint-initdb.d
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U dev -d taskmanager"]
      interval: 5s
      timeout: 5s
      retries: 5
    networks:
      - backend
    restart: unless-stopped

# ========== REDES ==========
networks:
  backend:
    driver: bridge
    name: taskmanager-network

# ========== VOLÚMENES ==========
volumes:
  postgres_data:
    name: taskmanager-pgdata
```

### Paso 2: Crear application-docker.properties

Crea `src/main/resources/application-docker.properties`:

```properties
# ========== PERFIL DOCKER ==========
# Este perfil se activa cuando SPRING_PROFILES_ACTIVE=docker

# Conexión a PostgreSQL via Docker network
# "db" es el nombre del servicio en docker-compose.yml
spring.datasource.url=jdbc:postgresql://db:5432/taskmanager
spring.datasource.username=dev
spring.datasource.password=dev123
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Logging
logging.level.org.hibernate.SQL=DEBUG
logging.level.com.bootcamp.taskmanager=DEBUG
```

### Paso 3: Crear Script de Inicialización (Opcional)

Crea `init-db/01-init.sql`:

```sql
-- Script que se ejecuta al crear el contenedor por primera vez

-- Crear extensiones útiles
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Datos iniciales de categorías
INSERT INTO categories (id, name, color, description) VALUES
(uuid_generate_v4(), 'Trabajo', '#2196F3', 'Tareas laborales'),
(uuid_generate_v4(), 'Personal', '#4CAF50', 'Tareas personales'),
(uuid_generate_v4(), 'Urgente', '#F44336', 'Requiere atención inmediata'),
(uuid_generate_v4(), 'Estudio', '#9C27B0', 'Tareas de aprendizaje')
ON CONFLICT DO NOTHING;
```

### Paso 4: Verificar Comunicación entre Contenedores

```bash
# Levantar servicios
docker compose up -d

# Verificar que están en la misma red
docker network inspect taskmanager-network

# Entrar al contenedor de la API
docker exec -it taskmanager-api sh

# Probar resolución DNS
nslookup db
ping db -c 3

# Probar conexión a PostgreSQL
nc -zv db 5432

# Salir
exit
```

### Paso 5: Probar Conectividad desde la API

```bash
# Ver logs de la API
docker compose logs -f api

# Verificar que conecta a la DB
curl http://localhost:8080/actuator/health
```

---

## ✅ Verificación

### 1. Verificar red creada

```bash
docker network ls | grep taskmanager
# taskmanager-network   bridge   local
```

### 2. Verificar contenedores en la red

```bash
docker network inspect taskmanager-network --format '{{range .Containers}}{{.Name}} {{end}}'
# taskmanager-api taskmanager-db
```

### 3. Verificar comunicación

```bash
# Desde el host
curl http://localhost:8080/api/tasks

# Ver que la API conecta a "db:5432" (no localhost)
docker compose logs api | grep -i "datasource\|connection"
```

---

## 🔧 Troubleshooting

### Error: Connection refused

```bash
# Verificar que db está healthy
docker compose ps

# Verificar logs de db
docker compose logs db

# Reiniciar con recreación
docker compose down && docker compose up --build
```

### Error: Name resolution failed

```bash
# Verificar que ambos están en la misma red
docker inspect taskmanager-api --format '{{.NetworkSettings.Networks}}'
docker inspect taskmanager-db --format '{{.NetworkSettings.Networks}}'
```

---

## 🎯 Resultado Esperado

- Red custom `taskmanager-network` creada
- Contenedores comunicándose por nombre de servicio
- API conectando a `db:5432` exitosamente

---

## ⏭️ Siguiente

Continúa con [05-ejercicio-integrador.md](./05-ejercicio-integrador.md) para el ejercicio final.
