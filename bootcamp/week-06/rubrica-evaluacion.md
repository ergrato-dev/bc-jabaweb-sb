# 📊 Rúbrica de Evaluación - Semana 06

## Documentación con Swagger/OpenAPI y CORS

---

## 🎯 Competencias a Evaluar

| Competencia | Peso |
|-------------|------|
| Configuración de SpringDoc OpenAPI | 25% |
| Documentación de Endpoints | 30% |
| Documentación de Schemas/DTOs | 20% |
| Configuración de CORS | 15% |
| Calidad de Documentación | 10% |

---

## 📋 Evidencias de Conocimiento (30%)

### Cuestionario Teórico

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|-------------------|
| OpenAPI Specification | Explica OAS 3.0 completo con ejemplos | Explica conceptos principales | Conocimiento básico | No comprende OAS |
| SpringDoc vs Springfox | Diferencia claramente ambas | Conoce diferencias principales | Confunde conceptos | No diferencia |
| Anotaciones OpenAPI | Domina @Operation, @ApiResponse, @Schema | Usa correctamente las principales | Uso básico | No conoce anotaciones |
| CORS | Explica concepto, preflight, headers | Comprende flujo básico | Conocimiento superficial | No comprende CORS |

### Preguntas de Conocimiento

1. ¿Qué es OpenAPI Specification y cuál es su versión actual?
2. ¿Cuál es la diferencia entre SpringDoc y Springfox?
3. ¿Para qué sirve la anotación @Operation?
4. ¿Qué es una solicitud preflight en CORS?
5. ¿Cómo se documenta un parámetro path en OpenAPI?

---

## 🛠️ Evidencias de Desempeño (40%)

### Ejercicio Práctico 1: Configuración SpringDoc

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|-------------------|
| Dependencias Maven | Correctas y actualizadas | Correctas | Faltan algunas | Incorrectas |
| Configuración OpenAPI | Info completa, servers, tags | Configuración funcional | Configuración mínima | No funciona |
| Swagger UI accesible | Funciona perfectamente | Funciona con errores menores | Parcialmente funcional | No accesible |

### Ejercicio Práctico 2: Documentación de Endpoints

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|-------------------|
| @Operation | Summary, description, tags completos | Usa atributos principales | Uso básico | No usa |
| @ApiResponse | Todos los códigos documentados | Principales códigos | Solo éxito | No documenta |
| @Parameter | Todos los params documentados | Params principales | Algunos params | No documenta |
| Ejemplos | Incluye ejemplos realistas | Algunos ejemplos | Ejemplos básicos | Sin ejemplos |

### Ejercicio Práctico 3: Configuración CORS

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|-------------------|
| Configuración global | WebMvcConfigurer completo | Configuración funcional | Configuración básica | No funciona |
| Orígenes permitidos | Configurados correctamente | Funciona pero inseguro | Parcial | Bloqueado |
| Métodos/Headers | Todos configurados | Principales configurados | Solo GET/POST | No configurado |
| Pruebas CORS | Verificado con navegador | Probado con curl | Sin pruebas formales | No probado |

---

## 📦 Evidencias de Producto (30%)

### Proyecto: API Documentada

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|-------------------|
| **Swagger UI** | Todos los endpoints documentados, ejemplos, tags organizados | Endpoints principales documentados | Documentación básica | Sin documentación |
| **Schemas** | Todos los DTOs con @Schema, ejemplos, validaciones documentadas | DTOs principales documentados | Schemas básicos | Sin schemas |
| **Responses** | Todos los códigos (200, 201, 400, 404, 500) con ejemplos | Principales códigos | Solo éxito/error | Sin responses |
| **CORS** | Configuración segura para producción | Funcional para desarrollo | Configuración básica | No funciona |
| **Exportación** | JSON y YAML disponibles | JSON disponible | Parcial | No exporta |

### Entregables Requeridos

- [ ] Proyecto Spring Boot con SpringDoc configurado
- [ ] Swagger UI accesible en `/swagger-ui.html`
- [ ] Todos los endpoints documentados con @Operation
- [ ] DTOs documentados con @Schema
- [ ] CORS configurado y funcionando
- [ ] Especificación OpenAPI exportable
- [ ] README con instrucciones

---

## 📝 Criterios de Calidad de Documentación

| Aspecto | Excelente | Bueno | Suficiente | Insuficiente |
|---------|-----------|-------|------------|--------------|
| Claridad | Descripciones claras y concisas | Comprensible | Algo confuso | Incomprensible |
| Completitud | Todo documentado | Mayoría documentado | Parcial | Incompleto |
| Consistencia | Estilo uniforme | Mayormente uniforme | Inconsistente | Sin estilo |
| Ejemplos | Realistas y útiles | Funcionales | Básicos | Sin ejemplos |
| Organización | Tags y grupos lógicos | Organizado | Parcial | Desorganizado |

---

## 🎯 Escala de Calificación

| Rango | Calificación | Descripción |
|-------|--------------|-------------|
| 90-100% | Excelente | Supera expectativas |
| 75-89% | Bueno | Cumple expectativas |
| 50-74% | Suficiente | Cumple requisitos mínimos |
| 25-49% | Insuficiente | No cumple requisitos |
| 0-24% | No presentado | Sin evidencia |

---

## 📌 Checklist de Entrega

### Documentación Swagger

- [ ] SpringDoc OpenAPI configurado correctamente
- [ ] Información de API (título, versión, descripción)
- [ ] Contacto y licencia configurados
- [ ] Tags organizando endpoints por recurso
- [ ] @Operation en todos los endpoints
- [ ] @ApiResponse para códigos 200, 201, 400, 404, 500
- [ ] @Parameter para path variables y query params
- [ ] @Schema en todos los DTOs
- [ ] Ejemplos en requests y responses

### CORS

- [ ] Configuración global con WebMvcConfigurer
- [ ] Orígenes permitidos especificados
- [ ] Métodos HTTP permitidos
- [ ] Headers permitidos
- [ ] Credenciales configuradas si aplica
- [ ] Probado desde origen diferente

### Proyecto

- [ ] Compila sin errores
- [ ] Docker Compose funcional
- [ ] Swagger UI accesible
- [ ] Endpoints funcionan desde Swagger UI
- [ ] CORS permite requests desde localhost:3000
- [ ] README con instrucciones claras

---

## 🔍 Notas del Evaluador

```
Estudiante: _______________________
Fecha: ___________________________
Evaluador: _______________________

Observaciones:
________________________________________________
________________________________________________
________________________________________________

Calificación Final: _______ / 100
```
