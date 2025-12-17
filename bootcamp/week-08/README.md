# Semana 8: Testing y Docker Avanzado

## 📋 Información General

| Aspecto | Detalle |
|---------|---------|
| **Duración** | 5 horas |
| **Nivel** | Avanzado |
| **Prerrequisitos** | Semanas 1-7 completadas |
| **Proyecto** | API con tests completos y Docker multi-servicio |

## 🎯 Objetivos de Aprendizaje

Al finalizar esta semana, serás capaz de:

1. **Comprender** la pirámide de testing y su aplicación en Spring Boot
2. **Implementar** tests unitarios con JUnit 5 y Mockito
3. **Crear** tests de integración con MockMvc y TestContainers
4. **Configurar** tests de seguridad para endpoints protegidos con JWT
5. **Diseñar** arquitecturas Docker multi-servicio con compose avanzado
6. **Optimizar** imágenes Docker con multi-stage builds
7. **Gestionar** dependencias entre servicios con healthchecks

## 📚 Índice de Contenidos

### 1. Teoría (`1-teoria/`)

| Archivo | Tema | Duración |
|---------|------|----------|
| [01-piramide-testing.md](1-teoria/01-piramide-testing.md) | Pirámide de Testing | 20 min |
| [02-junit5-mockito.md](1-teoria/02-junit5-mockito.md) | JUnit 5 y Mockito | 30 min |
| [03-testing-spring-boot.md](1-teoria/03-testing-spring-boot.md) | Testing en Spring Boot | 30 min |
| [04-testcontainers.md](1-teoria/04-testcontainers.md) | TestContainers | 25 min |
| [05-docker-compose-avanzado.md](1-teoria/05-docker-compose-avanzado.md) | Docker Compose Avanzado | 25 min |

### 2. Prácticas (`2-practicas/`)

| Archivo | Ejercicio | Duración |
|---------|-----------|----------|
| [01-tests-unitarios-service.md](2-practicas/01-tests-unitarios-service.md) | Tests Unitarios de Services | 40 min |
| [02-tests-controladores-mockmvc.md](2-practicas/02-tests-controladores-mockmvc.md) | Tests de Controllers con MockMvc | 40 min |
| [03-tests-seguridad-jwt.md](2-practicas/03-tests-seguridad-jwt.md) | Tests de Seguridad JWT | 35 min |
| [04-testcontainers-postgresql.md](2-practicas/04-testcontainers-postgresql.md) | TestContainers con PostgreSQL | 35 min |
| [05-docker-compose-multiservicio.md](2-practicas/05-docker-compose-multiservicio.md) | Docker Compose Multi-servicio | 30 min |

### 3. Proyecto (`3-proyecto/`)

API REST con suite de tests completa:

```
3-proyecto/
├── docker-compose.yml          # Producción: app + db + pgadmin
├── docker-compose.override.yml # Desarrollo: hot reload
├── docker-compose.test.yml     # Testing: TestContainers config
├── Dockerfile                  # Multi-stage optimizado
├── pom.xml
├── src/
│   ├── main/java/com/bootcamp/
│   │   ├── config/
│   │   ├── product/            # Dominio de ejemplo
│   │   └── user/               # Integración con Week-07
│   └── test/java/com/bootcamp/
│       ├── unit/               # Tests unitarios
│       ├── integration/        # Tests de integración
│       └── e2e/                # Tests end-to-end
└── docs/
    └── SOLUCIONES.md
```

### 4. Recursos (`4-recursos/`)

- Documentación oficial de testing
- Libros y tutoriales recomendados
- Herramientas de cobertura

### 5. Glosario (`5-glosario/`)

- Términos de testing
- Conceptos de Docker avanzado

---

## 🔧 Stack Tecnológico

| Tecnología | Versión | Uso |
|------------|---------|-----|
| JUnit 5 | 5.10+ | Framework de testing |
| Mockito | 5.x | Mocking de dependencias |
| MockMvc | 6.x | Testing de controllers |
| TestContainers | 1.19+ | Contenedores para tests |
| JaCoCo | 0.8.x | Cobertura de código |
| Docker Compose | 2.x | Orquestación multi-servicio |

---

## 📊 Pirámide de Testing

```
        ╱╲
       ╱  ╲
      ╱ E2E╲         ← Pocos, lentos, costosos
     ╱──────╲
    ╱        ╲
   ╱Integration╲     ← Moderados, balance
  ╱────────────╲
 ╱              ╲
╱   Unit Tests   ╲   ← Muchos, rápidos, baratos
╱────────────────╲
```

**Distribución recomendada:**
- 70% Tests Unitarios
- 20% Tests de Integración
- 10% Tests E2E

---

## 🐳 Arquitectura Docker Multi-servicio

```
┌─────────────────────────────────────────────────────┐
│                   docker-compose                     │
├─────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  │
│  │   app       │  │    db       │  │  pgadmin    │  │
│  │ Spring Boot │──│ PostgreSQL  │──│   Admin     │  │
│  │  :8080      │  │   :5432     │  │   :5050     │  │
│  └─────────────┘  └─────────────┘  └─────────────┘  │
│         │                │                │         │
│         └────────────────┼────────────────┘         │
│                          │                          │
│              ┌───────────┴───────────┐              │
│              │   bootcamp-network    │              │
│              └───────────────────────┘              │
└─────────────────────────────────────────────────────┘
```

---

## ✅ Checklist de la Semana

### Teoría
- [ ] Entender la pirámide de testing
- [ ] Conocer JUnit 5: @Test, @BeforeEach, @DisplayName
- [ ] Dominar Mockito: @Mock, @InjectMocks, when(), verify()
- [ ] Comprender @SpringBootTest vs @WebMvcTest vs @DataJpaTest
- [ ] Aprender TestContainers para tests con BD real
- [ ] Configurar Docker Compose multi-servicio

### Práctica
- [ ] Escribir tests unitarios para services
- [ ] Crear tests de controllers con MockMvc
- [ ] Implementar tests de autenticación JWT
- [ ] Configurar TestContainers con PostgreSQL
- [ ] Diseñar docker-compose con healthchecks

### Proyecto
- [ ] Suite de tests con cobertura >70%
- [ ] Tests de integración con TestContainers
- [ ] docker-compose.yml de producción
- [ ] docker-compose.override.yml para desarrollo
- [ ] Dockerfile multi-stage optimizado

---

## 📈 Métricas de Cobertura Esperadas

| Capa | Cobertura Mínima |
|------|------------------|
| Services | 80% |
| Controllers | 70% |
| Repositories | 60% |
| **Total** | **70%** |

---

## 🔗 Dependencias con Semanas Anteriores

| Semana | Concepto Utilizado |
|--------|-------------------|
| Week-01 | Docker básico |
| Week-02 | Spring Boot, Maven |
| Week-03 | Arquitectura capas, DTOs |
| Week-04 | JPA, PostgreSQL |
| Week-05 | Relaciones, Redes Docker |
| Week-06 | Swagger (documentación de tests) |
| Week-07 | Spring Security, JWT (tests de auth) |

---

## ⏱️ Distribución del Tiempo (5 horas)

| Actividad | Tiempo | Porcentaje |
|-----------|--------|------------|
| Teoría Testing | 45 min | 15% |
| Teoría Docker | 25 min | 8% |
| Práctica Tests | 2h 30min | 50% |
| Práctica Docker | 30 min | 10% |
| Proyecto | 50 min | 17% |

---

## 📝 Evaluación

Ver [rubrica-evaluacion.md](rubrica-evaluacion.md) para criterios detallados.

### Evidencias Requeridas

1. **Conocimiento**: Cuestionario sobre testing y Docker avanzado
2. **Desempeño**: Tests funcionales ejecutándose
3. **Producto**: Proyecto con suite de tests y docker-compose multi-servicio

---

## 🎁 Bonus

Ver carpeta `6-bonus/` para contenido adicional de integración full-stack.

---

## 🚀 Próxima Semana

**Week-09**: Proyecto Final Integrador - Aplicación de todos los conceptos del bootcamp en un proyecto completo desplegable con `docker-compose up`.
