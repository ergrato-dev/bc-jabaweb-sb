# 🔧 Práctica 04: Perfiles y Variables de Entorno en Docker

## Objetivo

Configurar perfiles de Spring Boot para diferentes entornos y usar variables de entorno en Docker Compose para una configuración flexible.

---

## 📋 Requisitos Previos

- Práctica 03 completada
- Docker Compose funcionando
- Entender archivos YAML

---

## Parte 1: Crear Archivos de Configuración

### 1.1 Estructura de archivos

```
src/main/resources/
├── application.yml          # Configuración base
├── application-dev.yml      # Desarrollo
├── application-prod.yml     # Producción
└── application-test.yml     # Testing
```

### 1.2 application.yml (Base)

```yaml
# Configuración común a todos los entornos
spring:
  application:
    name: task-manager

  # Perfil activo por defecto
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:dev}

# Configuración del servidor
server:
  error:
    include-message: always
    include-binding-errors: always

# Información de la aplicación
app:
  name: Task Manager API
  version: 1.0.0
```

### 1.3 application-dev.yml (Desarrollo)

```yaml
# Configuración para desarrollo local
spring:
  # Base de datos H2 en memoria para desarrollo
  datasource:
    url: jdbc:h2:mem:devdb
    username: sa
    password:
    driver-class-name: org.h2.Driver

  # Consola H2 habilitada
  h2:
    console:
      enabled: true
      path: /h2-console

  # JPA/Hibernate
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true

# Puerto del servidor
server:
  port: ${SERVER_PORT:8080}

# Logging detallado
logging:
  level:
    root: INFO
    com.bootcamp.taskmanager: DEBUG
    org.springframework.web: DEBUG
    org.hibernate.SQL: DEBUG

# Variables de la aplicación
app:
  environment: development
  debug: true
```

### 1.4 application-prod.yml (Producción)

```yaml
# Configuración para producción
spring:
  # Base de datos PostgreSQL (variables de entorno)
  datasource:
    url: ${DATABASE_URL:jdbc:postgresql://localhost:5432/taskdb}
    username: ${DATABASE_USERNAME:postgres}
    password: ${DATABASE_PASSWORD:postgres}
    driver-class-name: org.postgresql.Driver

  # JPA/Hibernate - no modificar esquema automáticamente
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

# Puerto del servidor
server:
  port: ${PORT:8080}

# Logging mínimo
logging:
  level:
    root: WARN
    com.bootcamp.taskmanager: INFO

# Variables de la aplicación
app:
  environment: production
  debug: false
```

### 1.5 application-test.yml (Testing)

```yaml
# Configuración para tests
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:

  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: false

logging:
  level:
    root: WARN
    com.bootcamp.taskmanager: INFO

app:
  environment: test
  debug: false
```

---

## Parte 2: Crear Archivo .env

### 2.1 Crear .env.example (template para Git)

```env
# .env.example - Copiar a .env y completar valores
# NO subir .env a Git (agregar a .gitignore)

# Perfil de Spring
SPRING_PROFILES_ACTIVE=dev

# Puerto del servidor
SERVER_PORT=8080

# Base de datos (solo para producción)
DATABASE_URL=jdbc:postgresql://db:5432/taskdb
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=changeme

# Aplicación
APP_SECRET_KEY=generate-a-secure-key-here
```

### 2.2 Crear .env para desarrollo

```env
# .env - Configuración local (NO subir a Git)
SPRING_PROFILES_ACTIVE=dev
SERVER_PORT=8080
```

### 2.3 Agregar .env a .gitignore

```gitignore
# En .gitignore
.env
*.env
!.env.example
```

---

## Parte 3: Configurar Docker Compose

### 3.1 docker-compose.yml actualizado

```yaml
services:
  app:
    build: .
    container_name: task-manager
    ports:
      - "${SERVER_PORT:-8080}:8080"
    environment:
      # TODO: Usar variables de entorno
      - SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-dev}
      - SERVER_PORT=8080
      - TZ=America/Bogota
    env_file:
      - .env
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
    networks:
      - tasknet

networks:
  tasknet:
    driver: bridge
```

### 3.2 docker-compose.prod.yml (Para producción)

```yaml
# docker-compose.prod.yml - Extiende docker-compose.yml para producción
services:
  app:
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DATABASE_URL=jdbc:postgresql://db:5432/taskdb
      - DATABASE_USERNAME=${DB_USER:-postgres}
      - DATABASE_PASSWORD=${DB_PASSWORD:-postgres}
    depends_on:
      db:
        condition: service_healthy

  db:
    image: postgres:16-alpine
    container_name: task-db
    environment:
      - POSTGRES_DB=taskdb
      - POSTGRES_USER=${DB_USER:-postgres}
      - POSTGRES_PASSWORD=${DB_PASSWORD:-postgres}
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - tasknet

volumes:
  postgres_data:
```

---

## Parte 4: Acceder a Propiedades en Código

### 4.1 Crear AppProperties

```java
package com.bootcamp.taskmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String version;
    private String environment;
    private boolean debug;

    // Getters y Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getEnvironment() { return environment; }
    public void setEnvironment(String environment) { this.environment = environment; }

    public boolean isDebug() { return debug; }
    public void setDebug(boolean debug) { this.debug = debug; }
}
```

### 4.2 Crear endpoint de información

```java
package com.bootcamp.taskmanager.controller;

import com.bootcamp.taskmanager.config.AppProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/info")
public class InfoController {

    private final AppProperties appProperties;

    public InfoController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> info = Map.of(
            "name", appProperties.getName(),
            "version", appProperties.getVersion(),
            "environment", appProperties.getEnvironment(),
            "debug", appProperties.isDebug()
        );
        return ResponseEntity.ok(info);
    }
}
```

---

## Parte 5: Probar

### 5.1 Desarrollo (perfil dev)

```bash
# Crear .env con perfil dev
echo "SPRING_PROFILES_ACTIVE=dev" > .env

# Levantar
docker compose up --build

# Verificar perfil
curl http://localhost:8080/api/info
```

**Respuesta esperada:**
```json
{
  "name": "Task Manager API",
  "version": "1.0.0",
  "environment": "development",
  "debug": true
}
```

### 5.2 Producción (perfil prod)

```bash
# Usar archivo prod
docker compose -f docker-compose.yml -f docker-compose.prod.yml up --build

# O cambiar variable
SPRING_PROFILES_ACTIVE=prod docker compose up --build

# Verificar perfil
curl http://localhost:8080/api/info
```

**Respuesta esperada:**
```json
{
  "name": "Task Manager API",
  "version": "1.0.0",
  "environment": "production",
  "debug": false
}
```

### 5.3 Verificar logs

```bash
# En dev - logs detallados
docker compose logs -f app

# En prod - logs mínimos
```

---

## ✅ Checklist de Verificación

- [ ] `application.yml` base creado
- [ ] `application-dev.yml` con configuración de desarrollo
- [ ] `application-prod.yml` con variables de entorno
- [ ] `.env.example` creado y documentado
- [ ] `.env` en `.gitignore`
- [ ] `docker-compose.yml` usa `env_file`
- [ ] Perfil se cambia con `SPRING_PROFILES_ACTIVE`
- [ ] Endpoint `/api/info` muestra configuración actual
- [ ] Logs varían según perfil

---

## 🎯 Reto Adicional

1. Crear un perfil `staging` con configuración intermedia
2. Agregar `spring-boot-starter-actuator` para endpoints de health y metrics
3. Configurar un secreto `JWT_SECRET` diferente por entorno

---

## 📚 Recursos

- [Spring Profiles](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.profiles)
- [Docker Compose Environment](https://docs.docker.com/compose/environment-variables/)
- [Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
