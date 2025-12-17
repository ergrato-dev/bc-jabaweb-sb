# 🐳 Práctica 05: Multi-Stage Docker Build

## Objetivo

Optimizar la imagen Docker del proyecto usando multi-stage builds.

---

## Requisitos Previos

- Práctica 04 completada
- Proyecto funcionando con PostgreSQL

---

## Paso 1: Crear Dockerfile Optimizado

Crea o reemplaza `Dockerfile` en la raíz del proyecto:

```dockerfile
# ══════════════════════════════════════════════════════════════
# STAGE 1: BUILD
# ══════════════════════════════════════════════════════════════
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copiar pom.xml primero (cachear dependencias)
COPY pom.xml .

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar
RUN mvn clean package -DskipTests -B

# ══════════════════════════════════════════════════════════════
# STAGE 2: RUNTIME
# ══════════════════════════════════════════════════════════════
FROM eclipse-temurin:21-jre-alpine

# Usuario no-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# Copiar JAR del stage build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
```

---

## Paso 2: Crear .dockerignore

Crea `.dockerignore` en la raíz:

```
target/
!target/*.jar
*.md
.git
.gitignore
.idea
*.iml
.vscode
.env
docker-compose*.yml
```

---

## Paso 3: Construir y Comparar

```bash
# Construir imagen
docker compose build api

# Ver tamaño
docker images | grep taskmanager

# Ver capas
docker history taskmanager-api
```

**Tamaño esperado:** ~180-200MB (vs ~800MB sin multi-stage)

---

## Paso 4: Probar

```bash
# Iniciar todo
docker compose up -d

# Verificar logs
docker compose logs -f api

# Probar endpoint
curl http://localhost:8080/api/tasks
```

---

## Verificación

- [ ] Dockerfile con 2 stages (build y runtime)
- [ ] .dockerignore creado
- [ ] Imagen final < 250MB
- [ ] Usuario no-root configurado
- [ ] Aplicación funciona correctamente

---

## Resumen Semana 04

Has completado:
1. ✅ PostgreSQL en Docker con volúmenes
2. ✅ Entidad JPA con anotaciones
3. ✅ Repository con Spring Data JPA
4. ✅ Service conectado a JPA
5. ✅ Multi-stage Docker build

**Tu aplicación ahora persiste datos en PostgreSQL!**
