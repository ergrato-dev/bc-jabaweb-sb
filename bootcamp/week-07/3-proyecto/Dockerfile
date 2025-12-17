# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Dar permisos de ejecución
RUN chmod +x mvnw

# Descargar dependencias (cache)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Construir aplicación
RUN ./mvnw package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crear usuario no-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar JAR desde build stage
COPY --from=build /app/target/*.jar app.jar

# Configurar JVM
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget -q --spider http://localhost:8080/actuator/health || exit 1

# Ejecutar aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
