# Semana 01 - Entorno de Desarrollo con Docker y Fundamentos REST

## 📋 Descripción General

Esta semana establecemos las bases fundamentales del bootcamp: **Docker como solución a entornos inestables** y los **principios de arquitectura REST**. Docker se introduce desde el día uno porque garantiza que todos los participantes trabajen en entornos idénticos y reproducibles, eliminando el problema de "en mi máquina funciona".

## 🎯 Objetivos de Aprendizaje

Al finalizar esta semana, serás capaz de:

1. **Explicar** por qué Docker es esencial para entornos de desarrollo consistentes
2. **Instalar y configurar** Docker Desktop en tu sistema
3. **Ejecutar** contenedores básicos y entender imágenes, contenedores y volúmenes
4. **Describir** la arquitectura cliente-servidor y el protocolo HTTP
5. **Identificar** los principios REST y métodos HTTP
6. **Crear** un archivo docker-compose.yml básico para desarrollo Java

## 📚 Requisitos Previos

- Conocimientos básicos de programación (variables, funciones, estructuras de control)
- Sistema operativo: Windows 10/11, macOS o Linux
- Mínimo 8GB RAM (recomendado 16GB)
- 20GB de espacio libre en disco
- Conexión a internet estable

## 📑 Índice de Contenidos

| Sección | Contenido | Duración |
|---------|-----------|----------|
| 1 | [Docker: Solución a Entornos Inestables](1-teoria/01-docker-fundamentos.md) | 1h 30min |
| 2 | [Arquitectura Web y Protocolo HTTP](1-teoria/02-arquitectura-web-http.md) | 1h |
| 3 | [Principios REST y APIs RESTful](1-teoria/03-principios-rest.md) | 1h |
| 4 | [Práctica: Primeros Pasos con Docker](2-practicas/01-docker-primeros-pasos.md) | 1h |
| 5 | [Práctica: Docker Compose para Java](2-practicas/02-docker-compose-java.md) | 30min |

**Duración Total**: 5 horas

## 🗂️ Estructura de la Semana

```
week-01/
├── README.md                    # Este archivo
├── rubrica-evaluacion.md        # Criterios de evaluación (30% conocimiento, 40% desempeño, 30% producto)
├── 0-assets/                    # Imágenes y diagramas SVG
│   ├── 01-docker-containers.svg
│   ├── 02-docker-image-layers.svg
│   ├── 03-docker-container-layers.svg
│   ├── 04-docker-volumes.svg
│   ├── 05-client-server-architecture.svg
│   ├── 06-http-request-response.svg
│   ├── 07-http-methods-crud.svg
│   └── 08-http-status-codes.svg
├── 1-teoria/                    # Material teórico
│   ├── 01-docker-fundamentos.md    # Docker: conceptos, instalación, comandos
│   ├── 02-arquitectura-web-http.md # Cliente-servidor, HTTP, JSON
│   └── 03-principios-rest.md       # 6 principios REST, URIs, Richardson
├── 2-practicas/                 # Ejercicios prácticos
│   ├── 01-docker-primeros-pasos.md # 7 ejercicios: imágenes, contenedores, volúmenes
│   └── 02-docker-compose-java.md   # Docker Compose para desarrollo Java
├── 3-proyecto/                  # Proyecto de la semana
│   ├── README.md                   # Instrucciones del proyecto
│   ├── Dockerfile                  # Imagen personalizada Java
│   ├── docker-compose.yml          # Orquestación de servicios
│   ├── .env.example                # Variables de entorno
│   ├── .gitignore                  # Archivos a ignorar
│   ├── src/
│   │   └── Main.java               # Programa de demostración
│   └── docs/
│       └── COMMANDS.md             # Referencia de comandos
├── 4-recursos/                  # Material complementario
│   ├── ebooks-free/                # Libros electrónicos gratuitos
│   ├── videografia/                # Videos y tutoriales recomendados
│   └── webgrafia/                  # Enlaces y documentación
└── 5-glosario/                  # Términos clave A-Z
    └── README.md
```

## 🔑 Conceptos Clave de la Semana

| Concepto | Descripción |
|----------|-------------|
| **Container** | Unidad de software que empaqueta código y dependencias |
| **Image** | Plantilla de solo lectura para crear contenedores |
| **Dockerfile** | Archivo de texto con instrucciones para construir imágenes |
| **Docker Compose** | Herramienta para definir aplicaciones multi-contenedor |
| **REST** | Estilo arquitectónico para sistemas distribuidos |
| **HTTP** | Protocolo de transferencia de hipertexto |
| **API** | Interfaz de programación de aplicaciones |
| **Endpoint** | URL específica que expone una funcionalidad |

## ⚡ Inicio Rápido

```bash
# 1. Verificar instalación de Docker
docker --version
docker-compose --version

# 2. Tu primer contenedor
docker run hello-world

# 3. Ejecutar Java en contenedor
docker run -it eclipse-temurin:21-jdk java --version
```

## 📝 Entregables de la Semana

1. **Cuestionario** sobre Docker básico y arquitectura REST (Conocimiento)
2. **Ejecución** de aplicación Java "Hola Mundo" en contenedor Docker (Desempeño)
3. **Proyecto**: docker-compose.yml funcional con JDK 21 + documento REST (Producto)

## 🎓 Estrategias de Aprendizaje

- **Clase invertida**: Lee la teoría antes de la sesión práctica
- **Codificación en vivo**: Sigue los ejemplos paso a paso
- **Troubleshooting guiado**: Aprende a resolver errores comunes

## ➡️ Próxima Semana

**Semana 02**: Spring Boot en Docker - Primeros Pasos
- Estructura de proyecto Spring Boot
- Dockerfile para Spring Boot
- Primeros endpoints REST

---

> 💡 **Tip**: Si encuentras problemas, revisa primero la sección de troubleshooting en las prácticas antes de buscar ayuda externa.
