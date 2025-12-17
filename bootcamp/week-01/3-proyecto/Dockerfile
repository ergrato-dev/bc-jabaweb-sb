# ============================================
# Bootcamp Java Web - Semana 01
# Dockerfile para entorno de desarrollo Java
# ============================================

# Imagen base: Eclipse Temurin JDK 21
FROM eclipse-temurin:21-jdk

# Metadatos de la imagen
LABEL maintainer="Bootcamp Java Web"
LABEL description="Entorno de desarrollo Java para bootcamp"
LABEL version="1.0"

# Crear directorio de trabajo
WORKDIR /app

# Crear directorios necesarios
RUN mkdir -p src out docs

# Variables de entorno por defecto
ENV APP_NAME="Bootcamp Java Web" \
    APP_VERSION="1.0.0" \
    APP_ENV="development" \
    JAVA_OPTS="-Xmx256m"

# Exponer puerto (para futuras aplicaciones web)
EXPOSE 8080

# Comando por defecto: shell interactivo
CMD ["bash"]
