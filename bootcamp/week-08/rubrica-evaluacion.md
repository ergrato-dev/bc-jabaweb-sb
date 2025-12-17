# Rúbrica de Evaluación - Semana 8

## Testing y Docker Avanzado

---

## 📊 Distribución de Puntaje

| Tipo de Evidencia | Peso | Puntaje Máximo |
|-------------------|------|----------------|
| Conocimiento | 25% | 25 puntos |
| Desempeño | 35% | 35 puntos |
| Producto | 40% | 40 puntos |
| **Total** | **100%** | **100 puntos** |

---

## 📝 Evidencia de Conocimiento (25 puntos)

### Cuestionario Teórico

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| Pirámide de testing | Explica distribución 70/20/10 y justifica | Conoce niveles pero no proporciones | Solo nombra tipos de tests | No distingue tipos |
| JUnit 5 anotaciones | Domina @Test, @BeforeEach, @DisplayName, @Nested | Conoce anotaciones básicas | Solo usa @Test | No conoce anotaciones |
| Mockito | Explica @Mock, @InjectMocks, when/verify | Usa mocks correctamente | Confunde mock con spy | No entiende mocking |
| TestContainers | Comprende lifecycle y configuración | Sabe qué hace pero no configura | Conoce el concepto | Desconoce TestContainers |
| Docker Compose avanzado | Domina healthchecks, depends_on, networks | Configura multi-servicio básico | Solo un servicio | No usa compose |

**Puntaje Conocimiento: ___ / 25**

---

## 💻 Evidencia de Desempeño (35 puntos)

### Tests Unitarios (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| Cobertura de services | >80% cobertura, casos edge | 70-80% cobertura | 50-70% cobertura | <50% cobertura |
| Uso de Mockito | Mocks correctos, verify assertions | Mocks funcionales | Mocks incompletos | Sin mocking |
| Nomenclatura | given_when_then o similar | Nombres descriptivos | Nombres genéricos | test1, test2... |

### Tests de Integración (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| MockMvc | Tests completos de controllers | Tests básicos GET/POST | Solo happy path | Sin tests de controller |
| TestContainers | BD real en tests, limpieza | Configuración funcional | Errores de conexión | Sin TestContainers |
| Contexto Spring | @SpringBootTest correcto | Slice tests funcionan | Contexto lento/pesado | No levanta contexto |

### Tests de Seguridad (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (4-6) | Insuficiente (0-3) |
|----------|----------------|-------------|------------------|-------------------|
| Autenticación | Tests login/register completos | Tests de happy path | Solo verifica 401 | Sin tests de auth |
| Autorización | Tests por rol (ADMIN/USER) | Verifica acceso básico | No verifica roles | Sin tests de roles |
| JWT en tests | @WithMockUser, tokens válidos | Mock de seguridad | Desactiva seguridad | Tests fallan por auth |

### Docker Avanzado (5 puntos)

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| Multi-servicio | 3+ servicios, healthchecks | 2 servicios conectados | Solo app + db | Servicio único |
| Optimización | Multi-stage, <200MB imagen | Imagen funcional | Imagen >500MB | No construye |

**Puntaje Desempeño: ___ / 35**

---

## 📦 Evidencia de Producto (40 puntos)

### Suite de Tests (20 puntos)

| Criterio | Excelente (20) | Bueno (15-19) | Suficiente (10-14) | Insuficiente (0-9) |
|----------|----------------|---------------|-------------------|-------------------|
| Cobertura total | >70% con JaCoCo | 60-70% | 50-60% | <50% |
| Organización | unit/, integration/, e2e/ | Separación clara | Mezclados | Desorganizados |
| Ejecución | `mvn test` pasa 100% | >90% pasan | >70% pasan | Muchos fallos |
| Documentación | Javadoc en tests complejos | Nombres descriptivos | Algunos comentarios | Sin documentación |

### Docker Compose Producción (15 puntos)

| Criterio | Excelente (15) | Bueno (11-14) | Suficiente (7-10) | Insuficiente (0-6) |
|----------|----------------|---------------|-------------------|-------------------|
| Servicios | app + db + pgadmin + healthchecks | app + db + healthchecks | app + db básico | Incompleto |
| Redes | Network custom, aislamiento | Default network funcional | Sin red explícita | Errores de red |
| Volúmenes | Datos persistentes, named volumes | Bind mounts funcionales | Sin persistencia | Pérdida de datos |
| Variables | .env, secrets seguros | Variables en compose | Hardcodeado | Credenciales expuestas |

### Dockerfile Optimizado (5 puntos)

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (0-2) |
|----------|---------------|-----------|----------------|-------------------|
| Multi-stage | Build + Runtime separados | Multi-stage básico | Single stage | No compila |
| Tamaño | <150MB final | <300MB | <500MB | >500MB o no optimizado |

**Puntaje Producto: ___ / 40**

---

## 🔧 Penalizaciones

| Infracción | Penalización |
|------------|--------------|
| Tests que no compilan | -10 puntos |
| `docker-compose up` falla | -15 puntos |
| Sin TestContainers configurado | -10 puntos |
| Cobertura <50% | -5 puntos |
| Sin tests de seguridad | -10 puntos |
| Credenciales hardcodeadas | -5 puntos |
| Sin .gitignore para .env | -3 puntos |

---

## 🏆 Bonificaciones

| Logro | Bonificación |
|-------|--------------|
| Cobertura >85% | +5 puntos |
| Tests de mutación (PIT) | +5 puntos |
| CI/CD con GitHub Actions | +5 puntos |
| Integración con SonarQube | +3 puntos |
| Tests de performance | +3 puntos |

**Máximo con bonificaciones: 121 puntos**

---

## 📋 Checklist de Entrega

### Tests
- [ ] Tests unitarios para services (mínimo 5 tests)
- [ ] Tests de controllers con MockMvc (mínimo 3 endpoints)
- [ ] Tests de autenticación (login, register, acceso denegado)
- [ ] Tests con TestContainers (mínimo 2 tests)
- [ ] Reporte de cobertura JaCoCo

### Docker
- [ ] docker-compose.yml (producción)
- [ ] docker-compose.override.yml (desarrollo)
- [ ] Dockerfile multi-stage
- [ ] .env.example
- [ ] README con instrucciones

### Documentación
- [ ] README.md del proyecto
- [ ] Comentarios en tests complejos
- [ ] Instrucciones de ejecución

---

## 📊 Cálculo de Nota Final

```
Nota = (Conocimiento + Desempeño + Producto - Penalizaciones + Bonificaciones)

Escala:
- 90-100+: Excelente (A)
- 80-89: Muy Bueno (B)
- 70-79: Bueno (C)
- 60-69: Suficiente (D)
- <60: Insuficiente (F)
```

---

## 📝 Rúbrica Rápida

| Componente | Peso | Tu Puntaje |
|------------|------|------------|
| Conocimiento | 25 | |
| Desempeño | 35 | |
| Producto | 40 | |
| Penalizaciones | - | |
| Bonificaciones | + | |
| **TOTAL** | **100** | |

---

## 🎯 Criterios de Aprobación

**Mínimo para aprobar: 60 puntos**

**Requisitos obligatorios (no negociables):**
1. ✅ `mvn test` ejecuta sin errores críticos
2. ✅ `docker-compose up` levanta la aplicación
3. ✅ Al menos un test con TestContainers funcional
4. ✅ Tests de autenticación presentes
5. ✅ Cobertura mínima 50%
