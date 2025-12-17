# 📋 Rúbrica de Evaluación - Semana 05

## Relaciones JPA y Redes Docker

---

## Evidencia de Conocimiento (20%)

### Cuestionario Teórico

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (0%) |
|----------|------------------|-------------|------------------|-------------------|
| Tipos de relaciones JPA | Explica correctamente @OneToMany, @ManyToOne, @ManyToMany con ejemplos | Explica 2 de 3 tipos correctamente | Explica 1 tipo correctamente | No comprende las relaciones |
| LAZY vs EAGER | Diferencia claramente ambos y cuándo usar cada uno | Diferencia pero no justifica cuándo usar | Conoce los términos sin entender diferencias | No conoce los conceptos |
| Cascade types | Explica CASCADE, PERSIST, MERGE, REMOVE correctamente | Explica 2-3 tipos correctamente | Explica 1 tipo | No comprende cascade |
| Redes Docker | Diferencia bridge, host, custom networks | Conoce 2 tipos | Conoce solo bridge | No comprende redes Docker |

---

## Evidencia de Desempeño (40%)

### Prácticas en Clase

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (0%) |
|----------|------------------|-------------|------------------|-------------------|
| Entidad User | Implementa correctamente con @Entity, @Table, @Id, @Column | Implementa con errores menores | Implementa parcialmente | No implementa |
| Relación @OneToMany | Configura correctamente bidireccional con mappedBy | Configura unidireccional correctamente | Configura con errores | No implementa |
| Relación @ManyToMany | Implementa con @JoinTable correctamente | Implementa con errores en join table | Implementa unidireccional | No implementa |
| Consultas JPQL | Crea queries complejas con JOIN FETCH | Crea queries básicas con @Query | Usa solo Query Methods | No crea consultas custom |
| Red Docker custom | Configura network con healthchecks y DNS | Configura network básica | Usa network por defecto | No configura redes |

---

## Evidencia de Producto (40%)

### Proyecto: Task Manager con Relaciones

| Criterio | Excelente (100%) | Bueno (75%) | Suficiente (50%) | Insuficiente (0%) |
|----------|------------------|-------------|------------------|-------------------|
| Modelo User | Entidad completa con validaciones y timestamps | Entidad completa sin validaciones | Entidad básica | No implementa |
| Modelo Category | Entidad con relación ManyToMany correcta | Entidad con relación parcial | Entidad sin relación | No implementa |
| Relación User-Task | Bidireccional con cascade y orphanRemoval | Bidireccional sin cascade | Unidireccional | No relaciona |
| Relación Task-Category | ManyToMany con JoinTable nombrada | ManyToMany con defaults | Relación incorrecta | No relaciona |
| Repositorios | Query Methods + JPQL custom | Solo Query Methods | Solo métodos heredados | No implementa |
| DTOs actualizados | Request/Response con relaciones | DTOs parciales | DTOs sin relaciones | No actualiza |
| Docker network | Custom network con comunicación correcta | Network por defecto funcionando | Errores de conexión | No funciona |

---

## Criterios Transversales

| Criterio | Peso | Descripción |
|----------|------|-------------|
| Código limpio | 10% | Nomenclatura en inglés, formateo consistente |
| Buenas prácticas JPA | 10% | LAZY por defecto, evitar N+1, uso de DTOs |
| Documentación | 5% | Comentarios Javadoc en entidades y repositorios |
| Git | 5% | Commits descriptivos y frecuentes |

---

## Escala de Calificación

| Rango | Calificación |
|-------|--------------|
| 90-100% | Excelente |
| 75-89% | Bueno |
| 50-74% | Suficiente |
| 0-49% | Insuficiente |

---

## Entregables

1. **Código fuente** del proyecto con relaciones implementadas
2. **docker-compose.yml** con network custom
3. **Capturas** de pruebas con curl/Postman mostrando:
   - Crear usuario
   - Crear tarea asignada a usuario
   - Crear categoría
   - Asignar categorías a tarea
   - Listar tareas con usuario y categorías

---

## Fecha de Entrega

- **En clase**: Prácticas guiadas
- **Proyecto**: Final de la sesión de 5 horas
