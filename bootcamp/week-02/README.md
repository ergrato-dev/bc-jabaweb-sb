# Semana 02 - Spring Boot en Docker: Primeros Pasos

## 📋 Descripción General

Esta semana damos el salto de Java básico a **Spring Boot**, el framework más utilizado para desarrollo de APIs REST en Java. Continuamos usando Docker para mantener entornos reproducibles y aprendemos a crear Dockerfiles específicos para aplicaciones Spring Boot.

## 🎯 Objetivos de Aprendizaje

Al finalizar esta semana, serás capaz de:

1. **Crear** un proyecto Spring Boot usando Spring Initializr
2. **Entender** la estructura de un proyecto Spring Boot con Maven
3. **Implementar** endpoints REST básicos con `@RestController`
4. **Construir** imágenes Docker para aplicaciones Spring Boot
5. **Configurar** Docker Compose para desarrollo con hot-reload
6. **Usar** path variables y query parameters en endpoints

## 📚 Requisitos Previos

- ✅ Semana 01 completada (Docker básico, REST fundamentals)
- ✅ Docker Desktop funcionando
- ✅ Conocimientos básicos de Java
- ✅ Familiaridad con línea de comandos

## 📑 Índice de Contenidos

| Sección | Contenido | Duración |
|---------|-----------|----------|
| 1 | [Introducción a Spring Boot](1-teoria/01-introduccion-spring-boot.md) | 45 min |
| 2 | [Estructura de Proyecto Maven](1-teoria/02-estructura-proyecto-maven.md) | 30 min |
| 3 | [Primeros Endpoints REST](1-teoria/03-endpoints-rest-basicos.md) | 45 min |
| 4 | [Dockerfile para Spring Boot](1-teoria/04-dockerfile-spring-boot.md) | 30 min |
| 5 | [Práctica: Crear Proyecto Spring Boot](2-practicas/01-crear-proyecto-spring-boot.md) | 1h |
| 6 | [Práctica: Endpoints y Docker](2-practicas/02-endpoints-docker.md) | 1h 30min |

**Duración Total**: 5 horas

## 🗂️ Estructura de la Semana

```
week-02/
├── README.md                    # Este archivo
├── rubrica-evaluacion.md        # Criterios de evaluación
├── 0-assets/                    # Imágenes y diagramas SVG
│   ├── 01-spring-boot-architecture.svg
│   ├── 02-maven-project-structure.svg
│   ├── 03-spring-initializr.svg
│   ├── 04-request-mapping.svg
│   └── 05-dockerfile-layers.svg
├── 1-teoria/                    # Material teórico
│   ├── 01-introduccion-spring-boot.md
│   ├── 02-estructura-proyecto-maven.md
│   ├── 03-endpoints-rest-basicos.md
│   └── 04-dockerfile-spring-boot.md
├── 2-practicas/                 # Ejercicios prácticos
│   ├── 01-crear-proyecto-spring-boot.md
│   └── 02-endpoints-docker.md
├── 3-proyecto/                  # Proyecto de la semana
│   ├── README.md
│   ├── Dockerfile              # 📝 EJERCICIO
│   ├── docker-compose.yml      # 📝 EJERCICIO
│   └── src/                    # Código fuente (plantillas)
├── 4-recursos/
│   ├── ebooks-free/
│   ├── videografia/
│   └── webgrafia/
└── 5-glosario/
    └── README.md
```

## 🔑 Conceptos Clave de la Semana

| Concepto | Descripción |
|----------|-------------|
| **Spring Boot** | Framework para crear aplicaciones Spring standalone |
| **Spring Initializr** | Herramienta web para generar proyectos Spring Boot |
| **Maven** | Herramienta de gestión de dependencias y build |
| **pom.xml** | Archivo de configuración de Maven |
| **@SpringBootApplication** | Anotación principal que habilita autoconfiguración |
| **@RestController** | Anotación para crear controladores REST |
| **@RequestMapping** | Mapea URLs a métodos del controlador |
| **@GetMapping/@PostMapping** | Atajos para mapear métodos HTTP específicos |
| **@PathVariable** | Extrae valores de la URL |
| **@RequestParam** | Extrae query parameters |

## ⚡ Inicio Rápido

```bash
# 1. Crear proyecto en Spring Initializr
# Visita: https://start.spring.io/
# - Project: Maven
# - Language: Java
# - Spring Boot: 3.2.x
# - Java: 21
# - Dependencies: Spring Web

# 2. Descomprimir y entrar al proyecto
unzip demo.zip && cd demo

# 3. Ejecutar con Docker
docker run --rm -v $(pwd):/app -w /app -p 8080:8080 \
  eclipse-temurin:21-jdk ./mvnw spring-boot:run

# 4. Probar
curl http://localhost:8080
```

## 📝 Entregables de la Semana

1. **Cuestionario** sobre Spring Boot y estructura Maven (Conocimiento)
2. **Ejercicios** de creación de endpoints REST (Desempeño)
3. **Proyecto**: API REST básica con Dockerfile funcional (Producto)

## 🎓 Estrategias de Aprendizaje

- **Aprendizaje por descubrimiento**: Explorar Spring Initializr
- **Codificación guiada**: Seguir ejemplos paso a paso
- **Pair programming**: Trabajar en parejas en la práctica
- **Debugging colaborativo**: Resolver errores en grupo

## ➡️ Próxima Semana

**Semana 03**: Arquitectura en Capas y Configuración Avanzada
- Controller, Service, Repository
- DTOs y validación
- Perfiles de configuración

---

> 💡 **Tip**: Spring Boot tiene mucha "magia" (autoconfiguración). No te frustres si algo funciona sin entender por qué - lo iremos explicando gradualmente.
