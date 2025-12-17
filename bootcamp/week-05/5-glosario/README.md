# 📖 Glosario - Semana 05

## Relaciones JPA y Redes Docker

### A

**@AssociationOverride**
: Anotación que permite sobrescribir la configuración de una asociación heredada en JPA.

### B

**Bidirectional Relationship** (Relación Bidireccional)
: Relación JPA donde ambas entidades tienen referencia una a la otra. Ejemplo: User tiene `List<Task>` y Task tiene `User user`.

**Bridge Network**
: Tipo de red Docker predeterminada que permite la comunicación entre contenedores en el mismo host. Los contenedores en la misma bridge network pueden comunicarse por nombre.

### C

**Cascade** (Cascada)
: Mecanismo JPA que propaga operaciones (persist, merge, remove) de una entidad padre a sus entidades relacionadas.

**CascadeType.ALL**
: Incluye todas las operaciones de cascada: PERSIST, MERGE, REMOVE, REFRESH, DETACH.

**CascadeType.PERSIST**
: Propaga la operación de persistir a entidades relacionadas.

**CascadeType.MERGE**
: Propaga la operación de actualizar a entidades relacionadas.

**CascadeType.REMOVE**
: Propaga la operación de eliminar a entidades relacionadas.

### D

**DNS (Docker)**
: Sistema de resolución de nombres interno de Docker que permite a los contenedores comunicarse usando nombres de servicio en lugar de IPs.

### E

**Eager Loading** (Carga Ansiosa)
: Estrategia de fetch donde las entidades relacionadas se cargan inmediatamente junto con la entidad principal.

**Entity Graph**
: Alternativa a JOIN FETCH que permite definir qué atributos cargar de forma declarativa.

### F

**Fetch Type**
: Estrategia que determina cuándo cargar las entidades relacionadas (LAZY o EAGER).

**FetchType.LAZY**
: Carga diferida - las relaciones se cargan solo cuando se acceden por primera vez.

**FetchType.EAGER**
: Carga inmediata - las relaciones se cargan junto con la entidad principal.

### H

**Healthcheck**
: Mecanismo de Docker para verificar si un contenedor está funcionando correctamente.

### I

**Inverse Side** (Lado Inverso)
: En una relación bidireccional, el lado que tiene el atributo `mappedBy` y no es propietario de la relación.

### J

**JOIN FETCH**
: Cláusula JPQL que fuerza la carga de relaciones en una sola consulta SQL, evitando el problema N+1.

**@JoinColumn**
: Anotación que especifica la columna de clave foránea en una relación JPA.

**@JoinTable**
: Anotación que configura la tabla intermedia en relaciones ManyToMany.

**JPQL (Java Persistence Query Language)**
: Lenguaje de consultas orientado a objetos similar a SQL pero que opera sobre entidades JPA.

### L

**Lazy Loading** (Carga Perezosa)
: Estrategia donde las relaciones se cargan bajo demanda, cuando se acceden por primera vez.

**LazyInitializationException**
: Excepción que ocurre al acceder a una relación LAZY fuera del contexto de persistencia (sesión cerrada).

### M

**@ManyToMany**
: Anotación JPA para relaciones donde múltiples instancias de una entidad se relacionan con múltiples instancias de otra.

**@ManyToOne**
: Anotación JPA para el lado "muchos" de una relación uno-a-muchos.

**mappedBy**
: Atributo que indica qué campo de la entidad relacionada es el propietario de la relación bidireccional.

### N

**N+1 Problem** (Problema N+1)
: Problema de rendimiento donde una consulta inicial genera N consultas adicionales para cargar relaciones (1 consulta principal + N consultas de relaciones).

**Network Driver**
: Tipo de red en Docker (bridge, host, overlay, macvlan, none).

### O

**@OneToMany**
: Anotación JPA para el lado "uno" de una relación uno-a-muchos. El campo es típicamente una colección.

**@OneToOne**
: Anotación JPA para relaciones donde una instancia de una entidad se relaciona con exactamente una instancia de otra.

**OrphanRemoval**
: Atributo que elimina automáticamente entidades "huérfanas" cuando se remueven de una colección.

**Owning Side** (Lado Propietario)
: En una relación bidireccional, el lado que contiene la clave foránea y no tiene `mappedBy`.

### P

**Persistence Context**
: Contexto de JPA que mantiene las entidades gestionadas durante una transacción.

**Proxy**
: Objeto sustituto generado por Hibernate para implementar lazy loading.

### S

**Service Discovery**
: En Docker, capacidad de los contenedores para encontrar otros servicios por nombre.

### T

**@Transactional**
: Anotación Spring que define los límites de una transacción de base de datos.

### U

**Unidirectional Relationship** (Relación Unidireccional)
: Relación JPA donde solo una entidad tiene referencia a la otra.

---

## Comparativa Rápida

### Tipos de Relación

| Relación | Cardinalidad | Ejemplo |
|----------|--------------|---------|
| @OneToOne | 1:1 | User ↔ Profile |
| @OneToMany | 1:N | User → Tasks |
| @ManyToOne | N:1 | Task → User |
| @ManyToMany | N:M | Task ↔ Categories |

### Fetch Types

| Tipo | Cuándo Carga | Uso Típico |
|------|--------------|------------|
| LAZY | Bajo demanda | Colecciones (@OneToMany, @ManyToMany) |
| EAGER | Inmediatamente | Referencias simples (@ManyToOne, @OneToOne) |

### Cascade Types

| Tipo | Operación Propagada |
|------|---------------------|
| PERSIST | Guardar nuevas entidades |
| MERGE | Actualizar entidades |
| REMOVE | Eliminar entidades |
| REFRESH | Recargar desde BD |
| DETACH | Desasociar del contexto |
| ALL | Todas las anteriores |

### Docker Networks

| Tipo | Característica | Uso |
|------|----------------|-----|
| bridge | Red por defecto, aislada | Contenedores en un host |
| host | Usa red del host | Máximo rendimiento |
| overlay | Multi-host | Docker Swarm |
| none | Sin red | Contenedores aislados |

---

> 📌 **Referencia**: Este glosario cubre los términos más importantes de la Semana 05. Consulta la documentación oficial para definiciones más detalladas.
