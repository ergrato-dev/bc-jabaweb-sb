# 📅 Semana 04 - Persistencia con JPA y PostgreSQL en Docker

## 🎯 Objetivos de Aprendizaje

Al finalizar esta semana, serás capaz de:

1. ✅ Configurar PostgreSQL containerizado con Docker Compose
2. ✅ Comprender los fundamentos de JPA e Hibernate
3. ✅ Configurar Spring Data JPA para conectar con PostgreSQL
4. ✅ Crear entidades con anotaciones JPA (@Entity, @Id, @Column)
5. ✅ Implementar repositorios con JpaRepository
6. ✅ Aplicar operaciones CRUD con persistencia real
7. ✅ Optimizar imágenes Docker con multi-stage builds

---

## 📚 Contenido

### 1. Teoría (1.5 horas)

| Archivo | Tema | Duración |
|---------|------|----------|
| [01-postgresql-docker.md](1-teoria/01-postgresql-docker.md) | PostgreSQL containerizado | 20 min |
| [02-introduccion-jpa-hibernate.md](1-teoria/02-introduccion-jpa-hibernate.md) | Fundamentos de JPA e Hibernate | 25 min |
| [03-entidades-jpa.md](1-teoria/03-entidades-jpa.md) | Entidades y anotaciones JPA | 25 min |
| [04-spring-data-jpa.md](1-teoria/04-spring-data-jpa.md) | Spring Data JPA y JpaRepository | 20 min |
| [05-multi-stage-builds.md](1-teoria/05-multi-stage-builds.md) | Optimización de imágenes Docker | 15 min |

### 2. Prácticas (2.5 horas)

| Archivo | Tema | Duración |
|---------|------|----------|
| [01-postgresql-docker-compose.md](2-practicas/01-postgresql-docker-compose.md) | Configurar PostgreSQL en Docker | 30 min |
| [02-configurar-spring-data-jpa.md](2-practicas/02-configurar-spring-data-jpa.md) | Conectar Spring Boot con PostgreSQL | 30 min |
| [03-crear-entidades.md](2-practicas/03-crear-entidades.md) | Crear entidades JPA | 40 min |
| [04-implementar-repositorios.md](2-practicas/04-implementar-repositorios.md) | Implementar JpaRepository | 30 min |
| [05-multi-stage-dockerfile.md](2-practicas/05-multi-stage-dockerfile.md) | Optimizar Dockerfile | 20 min |

### 3. Proyecto (1 hora)

Evolución del Task Manager API con persistencia real en PostgreSQL.

📁 [Ver proyecto](3-proyecto/)

---

## 🔧 Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| PostgreSQL | 16-alpine | Base de datos relacional |
| Spring Data JPA | 3.2+ | Abstracción de persistencia |
| Hibernate | 6.x | Implementación JPA (ORM) |
| Docker Compose | 2.x | Orquestación de servicios |
| pgAdmin 4 | Latest | Administración de BD (opcional) |

---

## 📋 Requisitos Previos

Antes de comenzar, asegúrate de:

- [ ] Completar la Semana 03 (Arquitectura en Capas)
- [ ] Tener Docker Desktop funcionando
- [ ] Entender el patrón Repository
- [ ] Conocer SQL básico (SELECT, INSERT, UPDATE, DELETE)

---

## 🗂️ Estructura de la Semana

```
week-04/
├── README.md
├── rubrica-evaluacion.md
├── 0-assets/
│   ├── 01-postgresql-docker.svg
│   ├── 02-jpa-architecture.svg
│   ├── 03-entity-mapping.svg
│   ├── 04-spring-data-jpa.svg
│   └── 05-multi-stage-build.svg
├── 1-teoria/
│   ├── 01-postgresql-docker.md
│   ├── 02-introduccion-jpa-hibernate.md
│   ├── 03-entidades-jpa.md
│   ├── 04-spring-data-jpa.md
│   └── 05-multi-stage-builds.md
├── 2-practicas/
│   ├── 01-postgresql-docker-compose.md
│   ├── 02-configurar-spring-data-jpa.md
│   ├── 03-crear-entidades.md
│   ├── 04-implementar-repositorios.md
│   └── 05-multi-stage-dockerfile.md
├── 3-proyecto/
│   ├── docker-compose.yml
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
├── 4-recursos/
│   ├── ebooks-free/
│   ├── videografia/
│   └── webgrafia/
└── 5-glosario/
```

---

## 🎓 Conceptos Clave

### JPA vs Hibernate vs Spring Data JPA

```
┌─────────────────────────────────────────────────────────────┐
│                      Tu Aplicación                          │
├─────────────────────────────────────────────────────────────┤
│              Spring Data JPA (Abstracción)                  │
│         - JpaRepository, CrudRepository                     │
│         - Query Methods automáticos                         │
├─────────────────────────────────────────────────────────────┤
│                JPA (Especificación)                         │
│         - @Entity, @Id, @Column                             │
│         - EntityManager, JPQL                               │
├─────────────────────────────────────────────────────────────┤
│              Hibernate (Implementación)                     │
│         - ORM real que ejecuta las operaciones              │
│         - Genera SQL, maneja caché, transacciones           │
├─────────────────────────────────────────────────────────────┤
│                    JDBC Driver                              │
│         - Comunicación con la base de datos                 │
├─────────────────────────────────────────────────────────────┤
│                    PostgreSQL                               │
└─────────────────────────────────────────────────────────────┘
```

### Flujo de Datos con JPA

```
Controller → Service → Repository (JPA) → Hibernate → JDBC → PostgreSQL
     │           │           │
  TaskDTO    Task Entity   Task Entity
```

---

## ⚠️ Errores Comunes

| Error | Causa | Solución |
|-------|-------|----------|
| `Connection refused` | PostgreSQL no iniciado | Verificar `docker-compose up` |
| `Table doesn't exist` | ddl-auto no configurado | Usar `spring.jpa.hibernate.ddl-auto=update` |
| `No identifier specified` | Falta @Id en entidad | Agregar @Id al campo identificador |
| `LazyInitializationException` | Acceso fuera de transacción | Usar `@Transactional` o EAGER fetch |

---

## 📖 Referencias

- [Spring Data JPA Reference](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Hibernate ORM Documentation](https://hibernate.org/orm/documentation/)
- [PostgreSQL Docker Hub](https://hub.docker.com/_/postgres)
- [JPA Specification](https://jakarta.ee/specifications/persistence/)

---

## ✅ Checklist de la Semana

- [ ] PostgreSQL corriendo en Docker
- [ ] Spring Boot conectado a PostgreSQL
- [ ] Entidad Task con anotaciones JPA
- [ ] TaskRepository extendiendo JpaRepository
- [ ] CRUD funcionando con persistencia real
- [ ] Dockerfile optimizado con multi-stage build
- [ ] Datos persistentes entre reinicios (volumen Docker)
