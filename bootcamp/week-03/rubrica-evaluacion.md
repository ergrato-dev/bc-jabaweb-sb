# 📋 Rúbrica de Evaluación - Semana 03

## Arquitectura en Capas y Configuración Avanzada

---

## 📊 Resumen de Evaluación

| Tipo de Evidencia | Peso | Puntos Máximos |
|-------------------|------|----------------|
| Conocimiento | 20% | 20 pts |
| Desempeño | 30% | 30 pts |
| Producto | 50% | 50 pts |
| **Total** | **100%** | **100 pts** |

---

## 📝 Evidencia de Conocimiento (20 pts)

### Cuestionario: Arquitectura en Capas y Configuración

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| **Arquitectura en Capas** | Explica correctamente las 3 capas y sus responsabilidades | Explica las capas con pequeños errores | Conoce las capas pero confunde responsabilidades | No distingue las capas |
| **Inyección de Dependencias** | Comprende IoC, DI y las anotaciones @Autowired, @Component, @Service | Entiende DI pero confunde algunas anotaciones | Conocimiento básico de DI | No comprende el concepto |
| **Bean Validation** | Domina @Valid, @NotNull, @Size y validaciones custom | Conoce las anotaciones principales | Uso básico de validaciones | No sabe implementar validaciones |
| **Manejo de Excepciones** | Explica @ControllerAdvice, @ExceptionHandler y ResponseEntity | Conoce el manejo global con pequeños errores | Sabe capturar excepciones básicas | No maneja excepciones correctamente |

**Puntos máximos: 20**

---

## 🔧 Evidencia de Desempeño (30 pts)

### Ejercicios Prácticos en Clase

#### Ejercicio 1: Refactorización en Capas (10 pts)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| **Separación de capas** | Controller, Service y Repository correctamente separados con responsabilidades claras | Capas separadas con pequeñas mezclas de responsabilidades | Capas parcialmente separadas | Todo en el Controller |

#### Ejercicio 2: DTOs y Validación (10 pts)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| **Implementación DTOs** | DTOs de Request/Response con mappers y validaciones completas | DTOs implementados con validaciones básicas | DTOs sin validaciones | Usa entidades directamente |

#### Ejercicio 3: Configuración y Perfiles (10 pts)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| **Perfiles Docker** | application-{profile}.yml, .env y docker-compose con variables correctos | Perfiles configurados con pequeños errores | Configuración básica sin perfiles | Configuración hardcodeada |

**Puntos máximos: 30**

---

## 📦 Evidencia de Producto (50 pts)

### Proyecto: API Task Manager Refactorizada

#### 1. Estructura del Proyecto (15 pts)

| Criterio | Excelente (15) | Bueno (11-14) | Suficiente (6-10) | Insuficiente (0-5) |
|----------|----------------|---------------|-------------------|-------------------|
| **Organización** | Paquetes bien organizados: controller, service, repository, dto, exception, config | Estructura correcta con pequeñas inconsistencias | Estructura parcial | Sin estructura clara |

```
Estructura esperada:
src/main/java/com/bootcamp/taskmanager/
├── TaskManagerApplication.java
├── controller/
│   └── TaskController.java
├── service/
│   ├── TaskService.java
│   └── impl/
│       └── TaskServiceImpl.java
├── repository/
│   └── TaskRepository.java
├── model/
│   └── Task.java
├── dto/
│   ├── TaskRequest.java
│   └── TaskResponse.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── ErrorResponse.java
└── config/
    └── AppConfig.java
```

#### 2. Capa de Servicio (10 pts)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| **Implementación** | Interface + Implementación, lógica de negocio en Service, inyección correcta | Service funcional con pequeñas mezclas | Service básico | Sin capa de servicio |

#### 3. DTOs y Validación (10 pts)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| **DTOs** | Request/Response DTOs con validaciones (@NotBlank, @Size, @Valid), mappers implementados | DTOs con validaciones básicas | DTOs sin validaciones | Sin DTOs |

#### 4. Manejo de Excepciones (10 pts)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| **Excepciones** | @ControllerAdvice, excepciones custom, ErrorResponse estándar, códigos HTTP correctos | Manejo global con pequeños errores | Manejo básico de excepciones | Sin manejo de excepciones |

#### 5. Configuración Docker (5 pts)

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| **Docker Compose** | Perfiles dev/prod, .env, variables de entorno, healthcheck | Configuración correcta sin perfiles | Configuración básica | No funciona |

**Puntos máximos: 50**

---

## 📈 Escala de Calificación

| Rango | Calificación | Descripción |
|-------|--------------|-------------|
| 90-100 | Excelente | Dominio completo de arquitectura en capas |
| 80-89 | Bueno | Buen manejo con oportunidades de mejora |
| 70-79 | Suficiente | Cumple requisitos mínimos |
| 60-69 | En desarrollo | Necesita reforzar conceptos |
| 0-59 | Insuficiente | No cumple los objetivos mínimos |

---

## ✅ Checklist de Entrega

### Estructura
- [ ] Paquetes organizados según arquitectura en capas
- [ ] Nomenclatura en inglés para código

### Controller
- [ ] Solo maneja requests HTTP
- [ ] Usa DTOs para entrada/salida
- [ ] Aplica @Valid en parámetros

### Service
- [ ] Interface definida
- [ ] Implementación con @Service
- [ ] Contiene lógica de negocio

### Repository
- [ ] Anotado con @Repository
- [ ] Operaciones CRUD implementadas

### DTOs
- [ ] TaskRequest con validaciones
- [ ] TaskResponse para respuestas
- [ ] Mapper (manual o con librería)

### Excepciones
- [ ] GlobalExceptionHandler con @ControllerAdvice
- [ ] ResourceNotFoundException custom
- [ ] ErrorResponse estándar

### Configuración
- [ ] application.yml con perfiles
- [ ] .env.example documentado
- [ ] docker-compose.yml con variables

### Docker
- [ ] `docker compose up` funciona
- [ ] Perfiles configurados
- [ ] Variables de entorno aplicadas

---

## 🎯 Competencias Evaluadas

1. **CE1**: Implementar arquitectura en capas siguiendo principios SOLID
2. **CE2**: Aplicar validaciones en datos de entrada
3. **CE3**: Manejar excepciones de forma global y consistente
4. **CE4**: Configurar aplicaciones para múltiples entornos
5. **CE5**: Utilizar Docker para desarrollo con configuración externalizada
