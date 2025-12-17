# ══════════════════════════════════════════════════════════════
# STAGE 1: BUILD
# ══════════════════════════════════════════════════════════════
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copiar pom.xml primero (cachear dependencias)
COPY pom.xml .
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
