# 📖 Semana 06: Documentación con Swagger/OpenAPI y CORS

## 🎯 Objetivos de Aprendizaje

Al finalizar esta semana, serás capaz de:

1. Comprender la importancia de documentar APIs REST
2. Configurar SpringDoc OpenAPI en proyectos Spring Boot
3. Usar anotaciones OpenAPI para documentar endpoints
4. Interactuar con APIs usando Swagger UI
5. Configurar CORS para permitir consumo desde frontends
6. Exportar especificaciones OpenAPI en formato JSON/YAML

---

## 📋 Contenido de la Semana

### Teoría (1-teoria/)

| # | Tema | Archivo | Duración |
|---|------|---------|----------|
| 1 | Introducción a OpenAPI y Swagger | [01-introduccion-openapi.md](1-teoria/01-introduccion-openapi.md) | 30 min |
| 2 | Configuración de SpringDoc | [02-springdoc-configuracion.md](1-teoria/02-springdoc-configuracion.md) | 25 min |
| 3 | Anotaciones OpenAPI | [03-anotaciones-openapi.md](1-teoria/03-anotaciones-openapi.md) | 35 min |
| 4 | Swagger UI Interactivo | [04-swagger-ui.md](1-teoria/04-swagger-ui.md) | 20 min |
| 5 | CORS en Spring Boot | [05-cors-spring-boot.md](1-teoria/05-cors-spring-boot.md) | 30 min |

### Prácticas (2-practicas/)

| # | Práctica | Archivo | Duración |
|---|----------|---------|----------|
| 1 | Configurar SpringDoc OpenAPI | [01-configurar-springdoc.md](2-practicas/01-configurar-springdoc.md) | 40 min |
| 2 | Documentar Endpoints con Anotaciones | [02-documentar-endpoints.md](2-practicas/02-documentar-endpoints.md) | 45 min |
| 3 | Documentar DTOs y Schemas | [03-documentar-schemas.md](2-practicas/03-documentar-schemas.md) | 35 min |
| 4 | Configurar CORS | [04-configurar-cors.md](2-practicas/04-configurar-cors.md) | 30 min |
| 5 | Proyecto Integrador | [05-proyecto-integrador.md](2-practicas/05-proyecto-integrador.md) | 50 min |

### Proyecto (3-proyecto/)

API REST de gestión de tareas con documentación completa Swagger/OpenAPI y CORS configurado.

---

## 🛠️ Requisitos Previos

- ✅ Semana 05 completada (Relaciones JPA y Redes Docker)
- ✅ Docker y Docker Compose instalados
- ✅ Conocimiento de arquitectura en capas
- ✅ Familiaridad con anotaciones Spring

---

## 📦 Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | JDK 21 | Lenguaje principal |
| Spring Boot | 3.2+ | Framework web |
| SpringDoc OpenAPI | 2.3+ | Documentación Swagger |
| PostgreSQL | 16+ | Base de datos |
| Docker | 24+ | Contenedorización |

---

## 📚 Recursos de la Semana

- [4-recursos/ebooks-free/](4-recursos/ebooks-free/) - Libros gratuitos
- [4-recursos/videografia/](4-recursos/videografia/) - Videos recomendados
- [4-recursos/webgrafia/](4-recursos/webgrafia/) - Artículos y documentación

---

## 📊 Diagrama de la Semana

```
┌─────────────────────────────────────────────────────────────┐
│                    Cliente (Browser/Postman)                │
└─────────────────────────┬───────────────────────────────────┘
                          │ HTTP Request
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                      CORS Filter                            │
│            (Valida origen, métodos, headers)                │
└─────────────────────────┬───────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────┐
│                    Spring Boot API                          │
│  ┌─────────────────┐  ┌─────────────────┐                  │
│  │   /swagger-ui   │  │   /api/v1/**    │                  │
│  │   Swagger UI    │  │   REST API      │                  │
│  └─────────────────┘  └─────────────────┘                  │
│  ┌─────────────────┐                                        │
│  │  /v3/api-docs   │                                        │
│  │  OpenAPI Spec   │                                        │
│  └─────────────────┘                                        │
└─────────────────────────────────────────────────────────────┘
```

---

## ⏱️ Distribución del Tiempo (5 horas)

| Actividad | Duración | Descripción |
|-----------|----------|-------------|
| Teoría OpenAPI | 45 min | Conceptos y configuración |
| Práctica Swagger | 60 min | Documentar endpoints |
| Teoría CORS | 30 min | Concepto y configuración |
| Práctica CORS | 30 min | Configurar y probar |
| Proyecto | 90 min | API documentada completa |
| Evaluación | 45 min | Quiz y revisión |

---

## ✅ Checklist de la Semana

- [ ] Comprender OpenAPI Specification 3.0
- [ ] Configurar SpringDoc en proyecto Spring Boot
- [ ] Documentar endpoints con @Operation, @ApiResponse
- [ ] Documentar DTOs con @Schema
- [ ] Usar Swagger UI para probar endpoints
- [ ] Exportar especificación OpenAPI
- [ ] Configurar CORS global y por endpoint
- [ ] Completar proyecto con documentación

---

## 🔗 Enlaces Rápidos

| Recurso | URL |
|---------|-----|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| OpenAPI YAML | http://localhost:8080/v3/api-docs.yaml |

---

## 📝 Evaluación

Ver [rubrica-evaluacion.md](rubrica-evaluacion.md) para los criterios de evaluación detallados.

---

> 💡 **Tip de la semana**: Una API bien documentada es una API fácil de consumir. Swagger UI permite a los desarrolladores frontend probar endpoints sin escribir código.
