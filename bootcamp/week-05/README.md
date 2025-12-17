# 📅 Semana 05: Relaciones JPA y Redes Docker

## Descripción General

En esta semana aprenderás a modelar relaciones entre entidades usando JPA y a configurar redes Docker para comunicación entre contenedores. Expandiremos el proyecto Task Manager agregando usuarios y categorías.

---

## Objetivos de Aprendizaje

Al finalizar esta semana, serás capaz de:

1. ✅ Implementar relaciones @OneToMany y @ManyToOne entre entidades
2. ✅ Implementar relaciones @ManyToMany con tablas intermedias
3. ✅ Configurar estrategias de carga LAZY vs EAGER
4. ✅ Usar cascade types y orphan removal correctamente
5. ✅ Crear consultas avanzadas con JPQL y Query Methods
6. ✅ Implementar paginación y ordenamiento
7. ✅ Configurar redes Docker custom para comunicación entre servicios

---

## Contenido

### 📚 Teoría (2 horas)

| # | Tema | Archivo | Duración |
|---|------|---------|----------|
| 1 | Relaciones en JPA | [01-relaciones-jpa.md](1-teoria/01-relaciones-jpa.md) | 30 min |
| 2 | @OneToMany y @ManyToOne | [02-one-to-many.md](1-teoria/02-one-to-many.md) | 25 min |
| 3 | @ManyToMany | [03-many-to-many.md](1-teoria/03-many-to-many.md) | 20 min |
| 4 | Consultas Avanzadas | [04-consultas-avanzadas.md](1-teoria/04-consultas-avanzadas.md) | 25 min |
| 5 | Redes Docker | [05-redes-docker.md](1-teoria/05-redes-docker.md) | 20 min |

### 🔧 Prácticas (2.5 horas)

| # | Práctica | Archivo | Duración |
|---|----------|---------|----------|
| 1 | Crear Entidad User | [01-crear-entidad-user.md](2-practicas/01-crear-entidad-user.md) | 30 min |
| 2 | Relación User-Task | [02-relacion-user-task.md](2-practicas/02-relacion-user-task.md) | 30 min |
| 3 | Crear Entidad Category | [03-crear-entidad-category.md](2-practicas/03-crear-entidad-category.md) | 25 min |
| 4 | Relación ManyToMany | [04-relacion-many-to-many.md](2-practicas/04-relacion-many-to-many.md) | 30 min |
| 5 | Redes Docker Custom | [05-redes-docker-custom.md](2-practicas/05-redes-docker-custom.md) | 25 min |

### 🚀 Proyecto (30 min)

Expandir Task Manager con:
- Entidad User (propietario de tareas)
- Entidad Category (categorías de tareas)
- Relación @OneToMany User → Tasks
- Relación @ManyToMany Task ↔ Category
- Red Docker custom con healthchecks

📁 [Ir al proyecto](3-proyecto/)

---

## Modelo de Datos

```
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│    User     │       │    Task     │       │  Category   │
├─────────────┤       ├─────────────┤       ├─────────────┤
│ id (UUID)   │       │ id (UUID)   │       │ id (UUID)   │
│ username    │──1:N──│ user_id (FK)│       │ name        │
│ email       │       │ title       │──N:M──│ description │
│ createdAt   │       │ description │       │ color       │
└─────────────┘       │ completed   │       └─────────────┘
                      │ createdAt   │
                      └─────────────┘
```

---

## Relaciones a Implementar

| Relación | Tipo | Descripción |
|----------|------|-------------|
| User → Task | @OneToMany | Un usuario tiene muchas tareas |
| Task → User | @ManyToOne | Una tarea pertenece a un usuario |
| Task ↔ Category | @ManyToMany | Una tarea puede tener muchas categorías y viceversa |

---

## Stack Tecnológico

| Tecnología | Versión | Uso en esta semana |
|------------|---------|-------------------|
| Spring Data JPA | 3.2+ | Relaciones entre entidades |
| PostgreSQL | 16 | Base de datos relacional |
| Docker Networks | - | Comunicación entre contenedores |
| JPQL | - | Consultas avanzadas |

---

## Estructura de Archivos

```
week-05/
├── README.md
├── rubrica-evaluacion.md
├── 0-assets/
│   ├── 01-relaciones-jpa.svg
│   ├── 02-one-to-many.svg
│   ├── 03-many-to-many.svg
│   ├── 04-fetch-types.svg
│   └── 05-docker-networks.svg
├── 1-teoria/
│   ├── 01-relaciones-jpa.md
│   ├── 02-one-to-many.md
│   ├── 03-many-to-many.md
│   ├── 04-consultas-avanzadas.md
│   └── 05-redes-docker.md
├── 2-practicas/
│   ├── 01-crear-entidad-user.md
│   ├── 02-relacion-user-task.md
│   ├── 03-crear-entidad-category.md
│   ├── 04-relacion-many-to-many.md
│   └── 05-redes-docker-custom.md
├── 3-proyecto/
│   ├── docker-compose.yml
│   ├── Dockerfile
│   ├── pom.xml
│   ├── README.md
│   ├── docs/
│   └── src/
├── 4-recursos/
│   ├── ebooks-free/
│   ├── videografia/
│   └── webgrafia/
└── 5-glosario/
    └── README.md
```

---

## Evaluación

Ver [rubrica-evaluacion.md](rubrica-evaluacion.md) para criterios detallados.

| Componente | Peso |
|------------|------|
| Conocimiento (cuestionario) | 20% |
| Desempeño (prácticas) | 40% |
| Producto (proyecto) | 40% |

---

## Recursos Adicionales

- 📖 [4-recursos/ebooks-free/](4-recursos/ebooks-free/)
- 🎬 [4-recursos/videografia/](4-recursos/videografia/)
- 🌐 [4-recursos/webgrafia/](4-recursos/webgrafia/)
- 📖 [5-glosario/](5-glosario/)

---

## Navegación

| ⬅️ Anterior | 🏠 Inicio | Siguiente ➡️ |
|-------------|-----------|---------------|
| [Semana 04](../week-04/) | [Bootcamp](../../) | [Semana 06](../week-06/) |
