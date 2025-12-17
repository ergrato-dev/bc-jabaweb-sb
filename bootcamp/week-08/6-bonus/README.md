# 🎁 Bonus Week 08: Full Stack Auth con Docker

## Objetivo

Integrar completamente Spring Boot + PostgreSQL + React en un solo `docker-compose.yml`, con flujo de autenticación completo.

**Duración estimada**: 90-120 minutos

> 💡 **Este es contenido bonus** - Diseñado para estudiantes que deseen un ejercicio completo de integración full-stack que puedan adaptar a su proyecto formativo.

**Prerrequisitos**:
- Completar bonus Week 06 (CORS)
- Completar bonus Week 07 (React Auth)
- API con Spring Security + JWT funcionando

---

## Estructura del Stack

```
┌─────────────────────────────────────────────────────────┐
│                    docker-compose.yml                    │
├─────────────────────────────────────────────────────────┤
│                                                          │
│   ┌──────────────┐   ┌──────────────┐   ┌────────────┐  │
│   │   frontend   │   │   backend    │   │  postgres  │  │
│   │    React     │──▶│ Spring Boot  │──▶│    DB      │  │
│   │  :5173       │   │   :8080      │   │   :5432    │  │
│   └──────────────┘   └──────────────┘   └────────────┘  │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

---

## Parte 1: Estructura de Carpetas

```
6-bonus/
├── README.md
├── docker-compose.yml
├── backend/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/bootcamp/auth/
│           │   ├── AuthApplication.java
│           │   ├── config/
│           │   │   ├── SecurityConfig.java
│           │   │   └── CorsConfig.java
│           │   ├── controller/
│           │   │   └── AuthController.java
│           │   ├── model/
│           │   │   └── User.java
│           │   ├── repository/
│           │   │   └── UserRepository.java
│           │   ├── service/
│           │   │   └── AuthService.java
│           │   └── security/
│           │       ├── JwtTokenProvider.java
│           │       └── JwtAuthenticationFilter.java
│           └── resources/
│               └── application.yml
└── frontend/
    ├── Dockerfile
    ├── package.json
    ├── vite.config.js
    └── src/
        ├── components/
        ├── context/
        ├── services/
        └── App.jsx
```

---

## Parte 2: Docker Compose

### 2.1 Crear `docker-compose.yml`

```yaml
services:
  # Base de datos PostgreSQL
  postgres:
    image: postgres:16-alpine
    container_name: auth-db
    environment:
      POSTGRES_DB: authdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - auth-network

  # Backend Spring Boot
  backend:
    build:
      context: ./backend
      dockerfile: Dockerfile
    container_name: auth-backend
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/authdb
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres
      JWT_SECRET: tu-secreto-super-seguro-de-al-menos-256-bits-para-jwt
      JWT_EXPIRATION: 86400000
    ports:
      - "8080:8080"
    depends_on:
      postgres:
        condition: service_healthy
    networks:
      - auth-network

  # Frontend React
  frontend:
    build:
      context: ./frontend
      dockerfile: Dockerfile
    container_name: auth-frontend
    ports:
      - "5173:5173"
    environment:
      VITE_API_URL: http://localhost:8080
    depends_on:
      - backend
    networks:
      - auth-network

volumes:
  postgres_data:

networks:
  auth-network:
    driver: bridge
```

---

## Parte 3: Backend Dockerfile

### 3.1 Crear `backend/Dockerfile`

```dockerfile
# ============================================
# STAGE 1: Build
# ============================================
FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dar permisos al wrapper
RUN chmod +x mvnw

# Descargar dependencias (capa cacheada)
RUN ./mvnw dependency:go-offline

# Copiar código fuente
COPY src src

# Compilar
RUN ./mvnw package -DskipTests

# ============================================
# STAGE 2: Runtime
# ============================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar JAR del stage anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## Parte 4: Frontend Dockerfile

### 4.1 Crear `frontend/Dockerfile`

```dockerfile
FROM node:20-alpine

WORKDIR /app

# Instalar pnpm
RUN corepack enable && corepack prepare pnpm@latest --activate

# Copiar archivos de dependencias
COPY package.json pnpm-lock.yaml* ./

# Instalar dependencias
RUN pnpm install

# Copiar código fuente
COPY . .

EXPOSE 5173

# Comando para desarrollo (hot reload)
CMD ["pnpm", "dev", "--host"]
```

---

## Parte 5: Ejecutar el Stack Completo

### 5.1 Construir y Levantar

```bash
cd 6-bonus

# Construir todas las imágenes
docker compose build

# Levantar todos los servicios
docker compose up
```

### 5.2 Verificar

| Servicio | URL | Descripción |
|----------|-----|-------------|
| Frontend | http://localhost:5173 | Aplicación React |
| Backend | http://localhost:8080 | API Spring Boot |
| Swagger | http://localhost:8080/swagger-ui.html | Documentación API |
| PostgreSQL | localhost:5432 | Base de datos |

### 5.3 Probar Flujo Completo

1. Abre http://localhost:5173
2. Click en "Registrarse"
3. Completa el formulario
4. Inicia sesión con las credenciales
5. Verifica que ves el dashboard

---

## 🎯 Adaptar a tu Proyecto Formativo

Este ejercicio está diseñado para que lo adaptes a tu proyecto:

### Cambios Típicos

1. **Modelo de Usuario**: Agregar campos según tu dominio
2. **Roles**: Implementar roles específicos de tu negocio
3. **Entidades adicionales**: Agregar tus entidades de negocio
4. **Endpoints**: Crear endpoints para tu caso de uso

### Checklist para Proyecto Formativo

- [ ] Registro de usuarios ✅ (incluido)
- [ ] Login ✅ (incluido)
- [ ] Recuperación de contraseña (agregar)
- [ ] Caso de uso de negocio (agregar)

---

## 📚 Guía de Adaptación

Ver [ADAPTATION-GUIDE.md](./docs/ADAPTATION-GUIDE.md) para instrucciones detalladas de cómo adaptar este ejercicio a tu proyecto formativo.

---

## ✅ Checklist Final

- [ ] `docker compose up` levanta los 3 servicios
- [ ] PostgreSQL está healthy
- [ ] Backend responde en :8080
- [ ] Frontend carga en :5173
- [ ] Registro funciona
- [ ] Login funciona
- [ ] Token se envía en peticiones
- [ ] Logout limpia la sesión

---

## 🎉 ¡Felicidades!

Has completado el bonus de integración full-stack. Ahora tienes una base sólida para tu proyecto formativo con:

- ✅ Autenticación JWT
- ✅ React + Spring Boot
- ✅ PostgreSQL
- ✅ Docker Compose
- ✅ CORS configurado
