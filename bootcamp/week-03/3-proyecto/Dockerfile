# ═══════════════════════════════════════════════════════════════════════════
# DOCKERFILE - Task Manager API (Semana 03)
# ═══════════════════════════════════════════════════════════════════════════
#
# Este Dockerfile construye y ejecuta la aplicación Spring Boot.
#
# Para la Semana 03, usamos un Dockerfile de una sola etapa (single-stage).
# En la Semana 04 aprenderás multi-stage builds para optimizar el tamaño.
#
# ═══════════════════════════════════════════════════════════════════════════

# ===========================================================================
# TODO 31: Especificar la imagen base
# ===========================================================================
#
# Usamos una imagen oficial de Eclipse Temurin (OpenJDK) con JDK 21.
# La versión 'alpine' es más ligera (~200MB vs ~400MB).
#
# DESCOMENTA:
# FROM eclipse-temurin:21-jdk-alpine

# ===========================================================================
# CONFIGURACIÓN DEL DIRECTORIO DE TRABAJO
# ===========================================================================
#
# WORKDIR establece el directorio donde se ejecutarán los comandos siguientes.
# Es como hacer 'cd /app' pero creando el directorio si no existe.

WORKDIR /app

# ===========================================================================
# TODO 32: Copiar archivos del proyecto
# ===========================================================================
#
# COPY <origen> <destino>
# El punto '.' como destino significa "directorio actual" (WORKDIR = /app)
#
# DESCOMENTA:
# COPY . .

# ===========================================================================
# TODO 33: Compilar el proyecto con Maven
# ===========================================================================
#
# El comando './mvnw' usa el Maven Wrapper incluido en el proyecto.
#
# Flags importantes:
#   -DskipTests  → No ejecutar tests (más rápido)
#   clean        → Limpiar compilaciones anteriores
#   package      → Compilar y crear el JAR
#
# DESCOMENTA:
# RUN ./mvnw clean package -DskipTests

# ===========================================================================
# TODO 34: Exponer el puerto de la aplicación
# ===========================================================================
#
# EXPOSE documenta que el contenedor escucha en el puerto 8080.
# No abre el puerto automáticamente - eso lo hace docker-compose con 'ports'.
#
# DESCOMENTA:
# EXPOSE ???

# ===========================================================================
# TODO 35: Definir el comando de inicio
# ===========================================================================
#
# CMD especifica qué ejecutar cuando el contenedor inicia.
#
# Usamos el formato JSON (exec form) que es más seguro:
#   CMD ["ejecutable", "arg1", "arg2"]
#
# El JAR generado está en: target/taskmanager-0.0.1-SNAPSHOT.jar
#
# DESCOMENTA y completa:
# CMD ["java", "-jar", "target/taskmanager-???.jar"]
