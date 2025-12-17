# ════════════════════════════════════════════════════════════════════════════
# Bootcamp Java Web - Semana 01
# EJERCICIO: Completa este Dockerfile
# ════════════════════════════════════════════════════════════════════════════
#
# OBJETIVO: Crear una imagen Docker para desarrollo Java
#
# INSTRUCCIONES:
# 1. Lee cada TODO y completa la instrucción correspondiente
# 2. Usa la documentación: https://docs.docker.com/engine/reference/builder/
# 3. Prueba tu Dockerfile con: docker build -t mi-java .
#
# REFERENCIA DE INSTRUCCIONES:
#   FROM imagen:tag      - Define la imagen base
#   LABEL key="value"    - Agrega metadatos
#   WORKDIR /path        - Establece directorio de trabajo
#   RUN comando          - Ejecuta comando durante build
#   ENV VAR="valor"      - Define variable de entorno
#   EXPOSE puerto        - Documenta puerto que usa la app
#   CMD ["comando"]      - Comando por defecto al ejecutar
#
# ════════════════════════════════════════════════════════════════════════════

# TODO 1: Define la imagen base
# Usa la imagen oficial de Eclipse Temurin con JDK 21
# Pista: eclipse-temurin:21-jdk
# Sintaxis: FROM imagen:tag


# TODO 2: Agrega metadatos con LABEL
# Incluye al menos:
#   - maintainer: tu nombre o email
#   - description: descripción de la imagen
#   - version: 1.0
# Sintaxis: LABEL key="value"


# TODO 3: Establece el directorio de trabajo
# Usa /app como directorio de trabajo
# Sintaxis: WORKDIR /path


# TODO 4: Crea los directorios necesarios
# Crea las carpetas: src, out, docs
# Sintaxis: RUN mkdir -p carpeta1 carpeta2 carpeta3


# TODO 5: Define variables de entorno por defecto
# Variables a definir:
#   - APP_NAME: nombre de tu aplicación
#   - APP_VERSION: 1.0.0
#   - APP_ENV: development
# Sintaxis: ENV VARIABLE="valor"
# Tip: puedes definir múltiples en una línea con \
#      ENV VAR1="val1" \
#          VAR2="val2"


# TODO 6: Documenta el puerto (para futuro uso con Spring Boot)
# Expón el puerto 8080
# Sintaxis: EXPOSE puerto


# TODO 7: Define el comando por defecto
# Usa bash como comando por defecto para modo interactivo
# Sintaxis: CMD ["comando"]


# ════════════════════════════════════════════════════════════════════════════
# VERIFICACIÓN:
# Después de completar, ejecuta:
#   docker build -t bootcamp-java .
#   docker run -it bootcamp-java
#   java --version   (dentro del contenedor)
# ════════════════════════════════════════════════════════════════════════════
