# 🐘 Práctica 01: Configurar PostgreSQL en Docker

## Objetivo

Configurar un contenedor PostgreSQL con volúmenes persistentes y conectarlo a nuestra aplicación Spring Boot.

---

## Requisitos Previos

- Docker Desktop instalado y ejecutándose
- Proyecto de la Semana 03 (taskmanager)

---

## Paso 1: Crear docker-compose.yml

Crea el archivo `docker-compose.yml` en la raíz del proyecto:

```yaml
# docker-compose.yml
services:
  # ═══════════════════════════════════════════════════════════
  # PostgreSQL Database
  # ═══════════════════════════════════════════════════════════
  db:
    image: postgres:16-alpine
    container_name: taskmanager-db
    environment:
      POSTGRES_DB: taskmanager
      POSTGRES_USER: dev
      POSTGRES_PASSWORD: dev123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U dev -d taskmanager"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - backend

  # ═══════════════════════════════════════════════════════════
  # Spring Boot API
  # ═══════════════════════════════════════════════════════════
  api:
    build:
      context: .
      dockerfile: Dockerfile
    container_name: taskmanager-api
    ports:
      - "8080:8080"
    environment:
      SPRING_PROFILES_ACTIVE: dev
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/taskmanager
      SPRING_DATASOURCE_USERNAME: dev
      SPRING_DATASOURCE_PASSWORD: dev123
    depends_on:
      db:
        condition: service_healthy
    networks:
      - backend

# ═══════════════════════════════════════════════════════════
# Volumes y Networks
# ═══════════════════════════════════════════════════════════
volumes:
  postgres_data:

networks:
  backend:
    driver: bridge
```

---

## Paso 2: Verificar Volumen

```bash
# Iniciar solo la base de datos
docker compose up db -d

# Verificar que el contenedor está corriendo
docker compose ps

# Verificar que el volumen se creó
docker volume ls | grep postgres

# Verificar logs
docker compose logs db
```

**Resultado esperado:**
```
NAME                    DRIVER    SCOPE
taskmanager_postgres_data   local
```

---

## Paso 3: Conectar con psql

```bash
# Conectar al contenedor PostgreSQL
docker compose exec db psql -U dev -d taskmanager

# Dentro de psql, verificar conexión
\conninfo

# Listar bases de datos
\l

# Salir
\q
```

---

## Paso 4: Probar Persistencia

```bash
# Crear una tabla de prueba
docker compose exec db psql -U dev -d taskmanager -c \
  "CREATE TABLE test (id SERIAL PRIMARY KEY, name VARCHAR(50));"

# Insertar datos
docker compose exec db psql -U dev -d taskmanager -c \
  "INSERT INTO test (name) VALUES ('dato persistente');"

# Verificar
docker compose exec db psql -U dev -d taskmanager -c \
  "SELECT * FROM test;"

# Detener contenedor
docker compose down

# Iniciar de nuevo
docker compose up db -d

# Verificar que los datos persisten
docker compose exec db psql -U dev -d taskmanager -c \
  "SELECT * FROM test;"
```

---

## Paso 5: pgAdmin (Opcional)

Agrega pgAdmin al docker-compose.yml:

```yaml
services:
  # ... db y api existentes ...

  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: taskmanager-pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@local.dev
      PGADMIN_DEFAULT_PASSWORD: admin123
    ports:
      - "5050:80"
    depends_on:
      - db
    networks:
      - backend
```

Acceder en: http://localhost:5050

Configurar conexión:
- Host: `db` (nombre del servicio)
- Port: `5432`
- Database: `taskmanager`
- Username: `dev`
- Password: `dev123`

---

## Paso 6: Limpiar Tabla de Prueba

```bash
docker compose exec db psql -U dev -d taskmanager -c "DROP TABLE test;"
```

---

## Verificación

- [ ] PostgreSQL inicia correctamente
- [ ] Volumen `postgres_data` creado
- [ ] Datos persisten después de `docker compose down`
- [ ] Puedes conectar con psql

---

## Troubleshooting

| Error | Solución |
|-------|----------|
| `port 5432 already in use` | Detener PostgreSQL local o cambiar puerto |
| `connection refused` | Esperar healthcheck, verificar network |
| `authentication failed` | Verificar POSTGRES_USER y PASSWORD |

---

## Siguiente

➡️ [02-crear-entidad-jpa.md](02-crear-entidad-jpa.md)
