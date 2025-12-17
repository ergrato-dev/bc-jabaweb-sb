# 📦 Semana 03 - Arquitectura en Capas y Configuración Avanzada

## 🎯 Objetivos de Aprendizaje

Al finalizar esta semana, serás capaz de:

1. **Implementar** arquitectura en capas (Controller-Service-Repository)
2. **Aplicar** el patrón de inyección de dependencias de Spring
3. **Crear** DTOs para transferencia de datos entre capas
4. **Validar** datos de entrada usando Bean Validation
5. **Manejar** excepciones de forma global y consistente
6. **Configurar** perfiles de Spring para diferentes entornos
7. **Utilizar** variables de entorno en Docker Compose

---

## 📋 Requisitos Previos

- ✅ Completar Semana 01 (Docker y Fundamentos REST)
- ✅ Completar Semana 02 (Spring Boot en Docker)
- ✅ Docker Desktop instalado y funcionando
- ✅ Conocimiento básico de Spring Boot y endpoints REST

---

## 📚 Contenidos

### 1. Teoría (1.5 horas)

| Documento | Tema | Duración |
|-----------|------|----------|
| [01-arquitectura-capas.md](1-teoria/01-arquitectura-capas.md) | Arquitectura en capas y MVC | 30 min |
| [02-inyeccion-dependencias.md](1-teoria/02-inyeccion-dependencias.md) | IoC y DI en Spring | 25 min |
| [03-dtos-validacion.md](1-teoria/03-dtos-validacion.md) | DTOs y Bean Validation | 20 min |
| [04-manejo-excepciones.md](1-teoria/04-manejo-excepciones.md) | Manejo global de excepciones | 15 min |
| [05-perfiles-configuracion.md](1-teoria/05-perfiles-configuracion.md) | Perfiles y configuración por entorno | 20 min |

### 2. Prácticas (2 horas)

| Práctica | Descripción | Duración |
|----------|-------------|----------|
| [01-refactorizar-capas.md](2-practicas/01-refactorizar-capas.md) | Separar código en capas | 45 min |
| [02-dtos-validacion.md](2-practicas/02-dtos-validacion.md) | Implementar DTOs y validaciones | 35 min |
| [03-excepciones-globales.md](2-practicas/03-excepciones-globales.md) | Configurar manejo de excepciones | 20 min |
| [04-perfiles-docker.md](2-practicas/04-perfiles-docker.md) | Perfiles y variables de entorno | 20 min |

### 3. Proyecto Integrador (1.5 horas)

| Componente | Descripción |
|------------|-------------|
| [README.md](3-proyecto/README.md) | Instrucciones del proyecto |
| [Código fuente](3-proyecto/src/) | API Task Manager refactorizada |

---

## 🗂️ Estructura de la Semana

```
week-03/
├── README.md                    # Este archivo
├── rubrica-evaluacion.md        # Criterios de evaluación
├── 0-assets/                    # Diagramas y recursos visuales
│   ├── layered-architecture.svg
│   ├── dependency-injection.svg
│   ├── dto-flow.svg
│   └── exception-handling.svg
├── 1-teoria/                    # Material teórico
│   ├── 01-arquitectura-capas.md
│   ├── 02-inyeccion-dependencias.md
│   ├── 03-dtos-validacion.md
│   ├── 04-manejo-excepciones.md
│   └── 05-perfiles-configuracion.md
├── 2-practicas/                 # Ejercicios guiados
│   ├── 01-refactorizar-capas.md
│   ├── 02-dtos-validacion.md
│   ├── 03-excepciones-globales.md
│   └── 04-perfiles-docker.md
├── 3-proyecto/                  # Proyecto integrador
│   ├── README.md
│   ├── docker-compose.yml
│   ├── .env.example
│   ├── pom.xml
│   └── src/
├── 4-recursos/                  # Material complementario
│   ├── ebooks-free/
│   ├── videografia/
│   └── webgrafia/
└── 5-glosario/                  # Términos clave
    └── README.md
```

---

## 🔑 Conceptos Clave de la Semana

| Concepto | Descripción |
|----------|-------------|
| **Arquitectura en Capas** | Separación de responsabilidades en Controller, Service, Repository |
| **IoC/DI** | Inversión de Control e Inyección de Dependencias |
| **DTO** | Data Transfer Object - objeto para transferir datos entre capas |
| **Bean Validation** | Validación declarativa con anotaciones |
| **@ControllerAdvice** | Manejo centralizado de excepciones |
| **Profiles** | Configuraciones específicas por entorno |

---

## 🛠️ Tecnologías Utilizadas

- Java 21 (en contenedor Docker)
- Spring Boot 3.2+
- Spring Validation (Bean Validation)
- Docker & Docker Compose
- Maven

---

## 📊 Evaluación

Ver [rubrica-evaluacion.md](rubrica-evaluacion.md) para los criterios detallados.

| Tipo | Peso | Descripción |
|------|------|-------------|
| **Conocimiento** | 20% | Cuestionario sobre arquitectura y configuración |
| **Desempeño** | 30% | Ejercicios prácticos en clase |
| **Producto** | 50% | API refactorizada con arquitectura limpia |

---

## ⏱️ Distribución del Tiempo (5 horas)

```
┌─────────────────────────────────────────────────────────────┐
│  Teoría (1.5h)  │  Prácticas (2h)  │  Proyecto (1.5h)       │
│     30%         │       40%        │       30%              │
└─────────────────────────────────────────────────────────────┘
```

---

## 📌 Entregables

Al finalizar la semana, debes entregar:

1. **Proyecto refactorizado** con arquitectura en capas
2. **DTOs** con validaciones implementadas
3. **Manejo de excepciones** global configurado
4. **docker-compose.yml** con perfiles y variables de entorno
5. **Cuestionario** de conocimientos completado

---

## 🔗 Enlaces Rápidos

- [← Semana 02: Spring Boot en Docker](../week-02/README.md)
- [→ Semana 04: JPA y PostgreSQL](../week-04/README.md)
- [📖 Documentación Spring](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [🐳 Documentación Docker](https://docs.docker.com/)
