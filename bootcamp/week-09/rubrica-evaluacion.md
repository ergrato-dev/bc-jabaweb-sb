# Rúbrica de Evaluación - Semana 9: Proyecto Final Integrador

## 📊 Distribución de Puntuación

| Tipo de Evidencia | Porcentaje | Puntos |
|-------------------|------------|--------|
| Conocimiento | 20% | 20 |
| Desempeño | 30% | 30 |
| Producto | 50% | 50 |
| **Total** | **100%** | **100** |

---

## 📝 Evidencias de Conocimiento (20 puntos)

### 1. Defensa del Proyecto (12 puntos)

| Criterio | Excelente (12) | Bueno (9) | Suficiente (6) | Insuficiente (0-3) |
|----------|----------------|-----------|----------------|-------------------|
| **Explicación de arquitectura** | Explica claramente todas las capas, justifica decisiones de diseño con argumentos técnicos sólidos | Explica la mayoría de las capas con buenas justificaciones | Explicación básica con algunas justificaciones | No puede explicar la arquitectura |
| **Conocimiento técnico** | Demuestra dominio de Spring Boot, JPA, Security, Docker | Buen conocimiento con algunas lagunas menores | Conocimiento básico suficiente | Conocimiento insuficiente |

### 2. Preguntas Técnicas (8 puntos)

| Criterio | Excelente (8) | Bueno (6) | Suficiente (4) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| **Respuestas a preguntas** | Responde correctamente y con profundidad a todas las preguntas | Responde bien la mayoría con algunas imprecisiones | Respuestas básicas correctas | No puede responder preguntas básicas |

**Preguntas de ejemplo**:
- ¿Por qué elegiste esas relaciones entre entidades?
- ¿Cómo funciona el flujo de autenticación JWT?
- ¿Qué ventajas tiene usar Docker en este proyecto?
- ¿Cómo garantizas la seguridad de los datos sensibles?

---

## 💻 Evidencias de Desempeño (30 puntos)

### 1. Arquitectura en Capas (10 puntos)

| Criterio | Excelente (10) | Bueno (7-8) | Suficiente (5-6) | Insuficiente (0-4) |
|----------|----------------|-------------|------------------|-------------------|
| **Separación de responsabilidades** | Capas perfectamente separadas, cada clase tiene una única responsabilidad | Buena separación con algunas mejoras posibles | Separación básica pero funcional | Lógica mezclada entre capas |
| **DTOs y mappers** | DTOs bien diseñados, mappers eficientes | DTOs correctos con algunos detalles | DTOs básicos | No usa DTOs o los usa incorrectamente |

### 2. Implementación de Seguridad (10 puntos)

| Criterio | Excelente (10) | Bueno (7-8) | Suficiente (5-6) | Insuficiente (0-4) |
|----------|----------------|-------------|------------------|-------------------|
| **JWT implementado** | JWT completo con refresh token, expiración, validación | JWT funcional con registro y login | JWT básico funcional | JWT no funciona o es inseguro |
| **Autorización** | Roles bien definidos, protección granular de endpoints | Protección de endpoints por roles | Protección básica | Sin autorización o incorrecta |

### 3. Manejo de Errores (5 puntos)

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| **Global Exception Handler** | Manejo completo de excepciones, mensajes claros, códigos HTTP correctos | Buen manejo con algunos casos no cubiertos | Manejo básico de excepciones | Sin manejo global o incorrecto |

### 4. Calidad del Código (5 puntos)

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| **Código limpio** | Nomenclatura consistente, código bien organizado, sin duplicación | Código legible con algunas mejoras | Código funcional pero mejorable | Código difícil de leer/mantener |

---

## 📦 Evidencias de Producto (50 puntos)

### 1. Entidades y Relaciones (12 puntos)

| Criterio | Excelente (12) | Bueno (9-10) | Suficiente (6-8) | Insuficiente (0-5) |
|----------|----------------|--------------|------------------|-------------------|
| **Mínimo 3 entidades** | 3+ entidades bien diseñadas con relaciones complejas | 3 entidades con relaciones correctas | 3 entidades con relaciones básicas | Menos de 3 entidades o sin relaciones |
| **Relaciones JPA** | @OneToMany, @ManyToOne, @ManyToMany correctamente implementadas | Relaciones correctas con LAZY/EAGER apropiado | Relaciones básicas funcionales | Relaciones incorrectas o no funcionan |

### 2. Endpoints REST (10 puntos)

| Criterio | Excelente (10) | Bueno (7-8) | Suficiente (5-6) | Insuficiente (0-4) |
|----------|----------------|-------------|------------------|-------------------|
| **CRUD completo** | CRUD para todas las entidades + endpoints adicionales | CRUD completo para principales entidades | CRUD básico funcional | CRUD incompleto |
| **Paginación** | Paginación con ordenamiento en todos los listados | Paginación en principales listados | Paginación básica | Sin paginación |

### 3. Testing (10 puntos)

| Criterio | Excelente (10) | Bueno (7-8) | Suficiente (5-6) | Insuficiente (0-4) |
|----------|----------------|-------------|------------------|-------------------|
| **Cobertura ≥70%** | Cobertura >80% con tests significativos | Cobertura 70-80% | Cobertura 60-70% | Cobertura <60% |
| **Tests de integración** | TestContainers funcionando, tests de auth | Tests de integración básicos | Tests unitarios suficientes | Tests insuficientes |

### 4. Docker (10 puntos)

| Criterio | Excelente (10) | Bueno (7-8) | Suficiente (5-6) | Insuficiente (0-4) |
|----------|----------------|-------------|------------------|-------------------|
| **docker-compose funcional** | `docker-compose up` funciona perfectamente | Funciona con ajustes menores | Funciona parcialmente | No funciona |
| **Multi-stage build** | Dockerfile optimizado, imagen <200MB | Dockerfile funcional con optimizaciones | Dockerfile básico funcional | Dockerfile no funciona |
| **Healthchecks** | Healthchecks en todos los servicios | Healthchecks en servicios principales | Healthcheck básico | Sin healthchecks |

### 5. Documentación (8 puntos)

| Criterio | Excelente (8) | Bueno (6) | Suficiente (4) | Insuficiente (0-3) |
|----------|---------------|-----------|----------------|-------------------|
| **README.md** | Instrucciones completas, arquitectura, ejemplos | Instrucciones claras de uso | Instrucciones básicas | README incompleto |
| **Swagger** | Todos los endpoints documentados con ejemplos | Endpoints principales documentados | Documentación básica | Sin documentación Swagger |

---

## 📋 Criterios de Aprobación

### Requisitos Mínimos para Aprobar

- [ ] Proyecto ejecuta con `docker-compose up`
- [ ] Mínimo 3 entidades relacionadas
- [ ] Autenticación JWT funcional
- [ ] Cobertura de tests ≥60%
- [ ] Documentación Swagger accesible
- [ ] Defensa del proyecto realizada

### Escala de Calificación

| Rango | Calificación | Descripción |
|-------|--------------|-------------|
| 90-100 | A | Excelente - Supera expectativas |
| 80-89 | B | Bueno - Cumple todas las expectativas |
| 70-79 | C | Satisfactorio - Cumple requisitos mínimos |
| 60-69 | D | Suficiente - Requiere mejoras |
| 0-59 | F | Insuficiente - No aprueba |

---

## 🎯 Bonus (hasta +10 puntos extra)

| Bonus | Puntos |
|-------|--------|
| CI/CD con GitHub Actions | +3 |
| Frontend React integrado | +3 |
| Despliegue en cloud (Render, Railway) | +2 |
| Redis para caché o sesiones | +2 |

---

## 📝 Rúbrica de Presentación

### Presentación Oral (5-10 minutos)

| Aspecto | Excelente | Bueno | Suficiente | Insuficiente |
|---------|-----------|-------|------------|--------------|
| **Claridad** | Explicación clara y estructurada | Clara con algunas imprecisiones | Entendible pero desorganizada | Confusa o incompleta |
| **Demo** | Demo fluida sin errores | Demo con errores menores | Demo parcial | Demo falla |
| **Tiempo** | Dentro del tiempo asignado | Ligeramente fuera de tiempo | Significativamente fuera | No completa |

---

## 📊 Hoja de Evaluación

```
Estudiante: _________________________ Fecha: ____________

CONOCIMIENTO (20 puntos)
├── Defensa del proyecto:     ___/12
└── Preguntas técnicas:       ___/8

DESEMPEÑO (30 puntos)
├── Arquitectura en capas:    ___/10
├── Implementación seguridad: ___/10
├── Manejo de errores:        ___/5
└── Calidad del código:       ___/5

PRODUCTO (50 puntos)
├── Entidades y relaciones:   ___/12
├── Endpoints REST:           ___/10
├── Testing:                  ___/10
├── Docker:                   ___/10
└── Documentación:            ___/8

SUBTOTAL:                     ___/100

BONUS:                        +___

TOTAL FINAL:                  ___

Observaciones:
_____________________________________________
_____________________________________________
_____________________________________________

Evaluador: _________________________
```
