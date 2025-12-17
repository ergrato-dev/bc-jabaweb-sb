# 📋 Rúbrica de Evaluación - Semana 01

## Entorno de Desarrollo con Docker y Fundamentos REST

**Duración**: 5 horas  
**Peso Total**: 100 puntos

---

## 📊 Distribución de Evidencias

| Tipo de Evidencia | Peso | Descripción |
|-------------------|------|-------------|
| Conocimiento | 30% | Cuestionario teórico |
| Desempeño | 40% | Ejercicios prácticos en clase |
| Producto | 30% | Entregables finales |

---

## 1. Evidencia de Conocimiento (30 puntos)

### Cuestionario sobre Docker y REST

**Instrumento**: Cuestionario de 15 preguntas (2 puntos c/u)

| Tema | Preguntas | Puntos |
|------|-----------|--------|
| Docker conceptos básicos | 5 | 10 |
| Comandos Docker | 4 | 8 |
| Arquitectura cliente-servidor | 2 | 4 |
| Protocolo HTTP | 2 | 4 |
| Principios REST | 2 | 4 |
| **Total** | **15** | **30** |

### Criterios de Evaluación

| Nivel | Puntos | Descripción |
|-------|--------|-------------|
| Excelente | 27-30 | Responde correctamente 90%+ de las preguntas |
| Bueno | 21-26 | Responde correctamente 70-89% de las preguntas |
| Suficiente | 18-20 | Responde correctamente 60-69% de las preguntas |
| Insuficiente | <18 | Responde menos del 60% correctamente |

### Preguntas de Ejemplo

1. ¿Cuál es la diferencia entre una imagen y un contenedor Docker?
2. ¿Qué comando se usa para listar contenedores en ejecución?
3. ¿Qué significa que REST sea "stateless"?
4. ¿Qué método HTTP se usa para crear un nuevo recurso?
5. ¿Qué código de estado indica que un recurso no fue encontrado?

---

## 2. Evidencia de Desempeño (40 puntos)

### Ejercicios Prácticos en Clase

**Instrumento**: Lista de cotejo de actividades

| Actividad | Puntos | Criterio |
|-----------|--------|----------|
| Instalación Docker verificada | 5 | Docker funcionando correctamente |
| Ejecutar primer contenedor | 5 | `docker run hello-world` exitoso |
| Descargar imagen Java | 5 | `docker pull eclipse-temurin:21-jdk` |
| Compilar Java en contenedor | 10 | Compilar y ejecutar HelloWorld.java |
| Usar volúmenes | 8 | Montar directorio local correctamente |
| Usar Docker Compose | 7 | Ejecutar docker-compose.yml funcional |
| **Total** | **40** | |

### Criterios de Evaluación por Actividad

#### Compilar Java en contenedor (10 puntos)

| Nivel | Puntos | Descripción |
|-------|--------|-------------|
| Excelente | 9-10 | Compila y ejecuta sin ayuda, entiende el proceso |
| Bueno | 7-8 | Compila y ejecuta con mínima ayuda |
| Suficiente | 6 | Compila y ejecuta con guía paso a paso |
| Insuficiente | <6 | No logra compilar o ejecutar |

#### Usar Docker Compose (7 puntos)

| Nivel | Puntos | Descripción |
|-------|--------|-------------|
| Excelente | 7 | Crea y ejecuta docker-compose.yml propio |
| Bueno | 5-6 | Modifica docker-compose.yml proporcionado |
| Suficiente | 4 | Ejecuta docker-compose.yml sin modificar |
| Insuficiente | <4 | No logra usar Docker Compose |

---

## 3. Evidencia de Producto (30 puntos)

### Entregables

| Entregable | Puntos | Descripción |
|------------|--------|-------------|
| docker-compose.yml | 12 | Archivo funcional con JDK 21 |
| Programa Java | 10 | HelloWorld que muestre info del sistema |
| Documento REST | 8 | Resumen de principios REST |
| **Total** | **30** | |

### Rúbrica: docker-compose.yml (12 puntos)

| Criterio | Excelente (12) | Bueno (9-11) | Suficiente (7-8) | Insuficiente (<7) |
|----------|----------------|--------------|------------------|-------------------|
| Sintaxis | YAML válido sin errores | 1-2 errores menores | Errores que requieren corrección | Sintaxis incorrecta |
| Servicios | Define servicios correctamente | Servicios funcionales con mejoras posibles | Servicios básicos | No define servicios |
| Volúmenes | Usa volúmenes para código fuente | Volúmenes configurados | Volúmenes básicos | Sin volúmenes |
| Variables | Usa .env o environment | Variables definidas | Variables básicas | Sin variables |
| Ejecutable | `docker compose up` funciona | Funciona con ajustes menores | Funciona con ayuda | No funciona |

### Rúbrica: Programa Java (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (5-6) | Insuficiente (<5) |
|----------|----------------|-------------|------------------|-------------------|
| Compila | Sin errores | Warnings menores | Requiere correcciones | No compila |
| Ejecuta | Muestra información del sistema | Ejecuta correctamente | Ejecuta con errores menores | No ejecuta |
| Código | Limpio, comentado, bien estructurado | Legible con mejoras | Funcional pero desordenado | Difícil de leer |
| Funcionalidad | Lee variables de entorno + argumentos | Lee variables de entorno | Muestra info básica | Solo "Hello World" |

### Rúbrica: Documento REST (8 puntos)

| Criterio | Excelente (8) | Bueno (6-7) | Suficiente (4-5) | Insuficiente (<4) |
|----------|---------------|-------------|------------------|-------------------|
| Contenido | Cubre los 6 principios REST | Cubre principios principales | Cubre conceptos básicos | Incompleto |
| Claridad | Explicaciones claras con ejemplos | Explicaciones claras | Explicaciones básicas | Confuso |
| Formato | Bien estructurado con tablas/listas | Buena estructura | Estructura básica | Sin estructura |
| Ejemplos | Incluye ejemplos de endpoints | Algunos ejemplos | Pocos ejemplos | Sin ejemplos |

---

## 📝 Formato de Entrega

### Estructura esperada
```
entrega-semana01/
├── docker-compose.yml
├── .env
├── src/
│   └── Main.java
└── docs/
    └── REST-PRINCIPLES.md
```

### Método de entrega
- Repositorio Git (GitHub/GitLab)
- O carpeta comprimida (.zip)

### Fecha límite
- Al final de la sesión o según indique el instructor

---

## 🎯 Criterios de Aprobación

| Calificación | Puntos | Porcentaje |
|--------------|--------|------------|
| **Aprobado** | ≥60 | ≥60% |
| No aprobado | <60 | <60% |

### Distribución mínima para aprobar
- Conocimiento: ≥18/30 puntos
- Desempeño: ≥24/40 puntos
- Producto: ≥18/30 puntos

---

## 📌 Observaciones

1. **Trabajo colaborativo**: Se permite trabajo en parejas para desempeño
2. **Producto individual**: Los entregables deben ser individuales
3. **Retroalimentación**: Se proporcionará feedback en cada evidencia
4. **Recuperación**: Posibilidad de mejorar producto hasta siguiente sesión

---

## ✅ Checklist del Estudiante

Antes de entregar, verifica:

- [ ] Docker Desktop instalado y funcionando
- [ ] `docker compose up` ejecuta sin errores
- [ ] Programa Java compila y muestra información
- [ ] Documento REST incluye los principios principales
- [ ] Archivos organizados según estructura solicitada
- [ ] Código con nomenclatura en inglés

---

*Semana 01 - Bootcamp Java Web con Spring Boot*
