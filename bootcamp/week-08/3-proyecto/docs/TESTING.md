# Guía de Testing

## 📋 Introducción

Este documento explica cómo ejecutar y extender la suite de tests del proyecto.

## 🧪 Tipos de Tests

### Tests Unitarios (`*Test.java`)

- Ubicación: `src/test/java/**/unit/`
- Framework: JUnit 5 + Mockito
- Ejecución: `mvn test`
- No requiere Docker

### Tests de Integración (`*IT.java`)

- Ubicación: `src/test/java/**/integration/`
- Framework: TestContainers + Spring Boot Test
- Ejecución: `mvn verify`
- Requiere Docker

## 🚀 Ejecución

```bash
# Solo tests unitarios (rápido)
mvn test

# Todos los tests (unitarios + integración)
mvn verify

# Test específico
mvn test -Dtest=ProductServiceTest

# Tests de integración
mvn verify -DskipUnitTests

# Con reporte de cobertura
mvn verify jacoco:report
open target/site/jacoco/index.html
```

## 📊 Cobertura

La cobertura mínima requerida es **70%**.

### Ver reporte

```bash
mvn verify
open target/site/jacoco/index.html
```

### Configuración en pom.xml

```xml
<jacoco.coverage.minimum>0.70</jacoco.coverage.minimum>
```

## 🐳 TestContainers

Los tests de integración usan PostgreSQL en contenedor Docker.

### Prerrequisitos

- Docker instalado y corriendo
- Usuario con permisos de Docker

### Optimización

Para acelerar tests repetidos:

1. Crear archivo `~/.testcontainers.properties`:
   ```properties
   testcontainers.reuse.enable=true
   ```

2. Los contenedores se reutilizarán entre ejecuciones

## 📝 Convenciones

### Nomenclatura de Tests

```java
@Test
@DisplayName("should return product when found")
void findById_ExistingId_ReturnsProduct() {
    // Given: Configuración
    // When: Ejecución
    // Then: Verificación
}
```

### Estructura de Carpetas

```
src/test/java/
├── unit/           # Tests unitarios
│   ├── service/
│   └── controller/
├── integration/    # Tests de integración
│   └── repository/
└── auth/           # Tests de autenticación
```
