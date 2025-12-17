# 📋 Rúbrica de Evaluación - Semana 04

## Persistencia con JPA y PostgreSQL en Docker

**Duración total:** 5 horas
**Fecha:** Semana 04 del Bootcamp

---

## 🎯 Competencias a Evaluar

| Competencia | Peso |
|-------------|------|
| Configuración de PostgreSQL en Docker | 20% |
| Implementación de entidades JPA | 25% |
| Uso de Spring Data JPA | 25% |
| Optimización con Multi-stage builds | 15% |
| Integración y funcionamiento completo | 15% |

---

## 📝 Evidencias de Conocimiento (30%)

### Cuestionario Teórico

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|---------------------|
| **JPA vs Hibernate** | Explica claramente que JPA es especificación y Hibernate implementación | Diferencia ambos conceptos con alguna imprecisión | Conoce los términos pero confunde roles | No diferencia los conceptos |
| **Anotaciones JPA** | Explica @Entity, @Id, @GeneratedValue, @Column con ejemplos | Conoce las anotaciones principales | Conoce algunas anotaciones | No conoce las anotaciones |
| **Spring Data JPA** | Explica query methods, JpaRepository y su funcionamiento | Conoce JpaRepository y métodos básicos | Sabe que existe pero no cómo funciona | No conoce Spring Data JPA |
| **Docker multi-stage** | Explica ventajas y cómo reduce tamaño de imagen | Entiende el concepto de etapas | Conoce el término | No conoce multi-stage |
| **Volúmenes Docker** | Explica persistencia de datos entre reinicios | Sabe que mantienen datos | Conoce el concepto vagamente | No entiende volúmenes |

---

## 💻 Evidencias de Desempeño (40%)

### Práctica 1: PostgreSQL en Docker (10%)

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|---------------------|
| **docker-compose.yml** | Servicio PostgreSQL con volumen, healthcheck y red custom | PostgreSQL con volumen configurado | PostgreSQL básico funcionando | No logra configurar PostgreSQL |
| **Conexión** | Verifica conexión con psql o pgAdmin | Conecta pero sin verificar datos | Intenta conectar con errores | No logra conectar |

### Práctica 2: Configuración Spring Data JPA (10%)

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|---------------------|
| **Dependencias** | spring-boot-starter-data-jpa y postgresql correctos | Dependencias correctas con warnings | Dependencias parciales | Faltan dependencias críticas |
| **application.yml** | Conexión, dialect, ddl-auto y logging configurados | Conexión funcional básica | Configuración incompleta | No configura conexión |

### Práctica 3: Entidades JPA (10%)

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|---------------------|
| **Anotaciones** | @Entity, @Table, @Id, @GeneratedValue, @Column correctos | Anotaciones principales correctas | Algunas anotaciones faltan | Entidad no es válida |
| **Tipos de datos** | Usa tipos apropiados (UUID, LocalDateTime, etc.) | Tipos correctos pero no óptimos | Algunos tipos incorrectos | Tipos incompatibles |

### Práctica 4: Repositorios JPA (10%)

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|---------------------|
| **JpaRepository** | Extiende correctamente con tipos genéricos | Extiende con minor issues | Interfaz creada pero incompleta | No crea repositorio |
| **Query Methods** | Implementa métodos custom (findByTitle, etc.) | Usa métodos heredados correctamente | Solo métodos básicos | No usa query methods |

---

## 🏗️ Evidencias de Producto (30%)

### Proyecto Integrador: Task Manager con PostgreSQL

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (25%) |
|----------|------------------|-------------|------------------|---------------------|
| **Persistencia** | CRUD completo funciona con PostgreSQL, datos persisten | CRUD funciona, algunos datos se pierden | Operaciones parciales | No hay persistencia real |
| **Entidad Task** | Campos id, title, description, completed, createdAt, updatedAt con tipos correctos | Campos principales correctos | Faltan campos o tipos incorrectos | Entidad no válida |
| **Repository** | TaskRepository con métodos custom funcionando | Repository básico funcional | Repository incompleto | No funciona |
| **Multi-stage Dockerfile** | Build en etapa 1, runtime en etapa 2, imagen <200MB | Multi-stage funcional, imagen <400MB | Dockerfile funcional sin multi-stage | Dockerfile no funciona |
| **docker-compose.yml** | App + PostgreSQL con networks, volumes, depends_on, healthcheck | App + PostgreSQL funcionando | Servicios parcialmente configurados | No orquesta servicios |
| **Migración desde Semana 03** | Adapta todo el código de capas a JPA sin errores | Migración con minor issues | Migración incompleta | No migra código anterior |

---

## 📊 Escala de Calificación

| Rango | Calificación | Descripción |
|-------|--------------|-------------|
| 90-100% | Excelente | Domina JPA y Docker, implementación profesional |
| 75-89% | Bueno | Comprende conceptos, implementación funcional |
| 50-74% | Suficiente | Conocimientos básicos, requiere práctica adicional |
| 25-49% | Insuficiente | Conceptos confusos, necesita refuerzo |
| 0-24% | No presentado | No demuestra competencias mínimas |

---

## 🔍 Criterios de Evaluación Detallados

### PostgreSQL en Docker

```yaml
# docker-compose.yml MÍNIMO esperado
services:
  db:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: taskmanager
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: ${DB_PASSWORD}
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"

volumes:
  postgres_data:
```

### Entidad JPA MÍNIMA esperada

```java
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean completed = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // getters, setters, constructors...
}
```

### Repository MÍNIMO esperado

```java
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByCompleted(boolean completed);
    List<Task> findByTitleContainingIgnoreCase(String title);
}
```

---

## 📝 Notas del Evaluador

| Aspecto | Observaciones |
|---------|---------------|
| Fortalezas | |
| Áreas de mejora | |
| Recomendaciones | |

---

## ✍️ Firmas

| Rol | Nombre | Firma | Fecha |
|-----|--------|-------|-------|
| Estudiante | | | |
| Instructor | | | |
