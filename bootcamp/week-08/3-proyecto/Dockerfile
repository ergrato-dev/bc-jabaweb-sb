# ============================================
# Stage 1: Builder
# ============================================
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Instalar Maven
RUN apk add --no-cache maven

# Copiar archivos de dependencias primero (cache layer)
COPY pom.xml .

# Descargar dependencias (cached si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Compilar aplicación (skip tests, se ejecutan en CI)
RUN mvn clean package -DskipTests -B

# ============================================
# Stage 2: Development
# ============================================
FROM eclipse-temurin:21-jdk-alpine AS development

WORKDIR /app

# Instalar herramientas de desarrollo
RUN apk add --no-cache maven curl

# Copiar desde builder para tener dependencias
COPY --from=builder /root/.m2 /root/.m2
COPY pom.xml .
COPY src ./src

EXPOSE 8080 5005

# Comando para desarrollo con hot reload y debug
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"]

# ============================================
# Stage 3: Production
# ============================================
FROM eclipse-temurin:21-jre-alpine AS production

WORKDIR /app

# Crear usuario no-root por seguridad
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Instalar curl para healthcheck
RUN apk add --no-cache curl

# Copiar JAR desde builder
COPY --from=builder /app/target/*.jar app.jar

# Cambiar ownership
RUN chown -R appuser:appgroup /app

# Usar usuario no-root
USER appuser

EXPOSE 8080

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# Optimizaciones JVM para contenedores
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", "app.jar"]
