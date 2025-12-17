# 📖 Glosario - Semana 04

## JPA y Persistencia

| Término | Definición |
|---------|------------|
| **JPA** | Java Persistence API. Especificación estándar para ORM en Java. |
| **ORM** | Object-Relational Mapping. Técnica para mapear objetos a tablas. |
| **Hibernate** | Implementación más popular de JPA. |
| **Entity** | Clase Java que representa una tabla de base de datos. |
| **EntityManager** | Interfaz para operaciones de persistencia. |
| **Persistence Context** | Caché de primer nivel que gestiona entidades. |

## Anotaciones JPA

| Anotación | Descripción |
|-----------|-------------|
| `@Entity` | Marca una clase como entidad JPA. |
| `@Table` | Configura el nombre de la tabla. |
| `@Id` | Define la clave primaria. |
| `@GeneratedValue` | Configura generación automática de ID. |
| `@Column` | Configura propiedades de columna. |
| `@PrePersist` | Callback antes de INSERT. |
| `@PreUpdate` | Callback antes de UPDATE. |

## Spring Data JPA

| Término | Definición |
|---------|------------|
| **JpaRepository** | Interfaz que proporciona métodos CRUD. |
| **Query Methods** | Consultas generadas por nombre de método. |
| **@Query** | Anotación para consultas JPQL o SQL. |
| **Pageable** | Interfaz para paginación. |
| **@Transactional** | Gestión declarativa de transacciones. |

## Docker

| Término | Definición |
|---------|------------|
| **Volume** | Almacenamiento persistente para contenedores. |
| **Multi-stage build** | Dockerfile con múltiples FROM para optimizar. |
| **Healthcheck** | Verificación de salud del contenedor. |
| **Network** | Red virtual para comunicación entre contenedores. |

## Base de Datos

| Término | Definición |
|---------|------------|
| **PostgreSQL** | Sistema de base de datos relacional open source. |
| **DDL** | Data Definition Language (CREATE, ALTER, DROP). |
| **DML** | Data Manipulation Language (SELECT, INSERT, UPDATE, DELETE). |
| **ddl-auto** | Configuración de Hibernate para gestión de esquema. |
