# Instrucciones para GitHub Copilot - Bootcamp Java Web con Spring Boot

## Contexto del Proyecto

Este es un bootcamp de **9 semanas** de duración con **1 sesión de 5 horas por semana** (45 horas totales) enfocado en **Desarrollo de APIs REST con Java Web usando Spring Boot**.

## Objetivo General

Desarrollar competencias en diseño, implementación y despliegue de APIs RESTful utilizando Java 21, Spring Boot, bases de datos relacionales (SQLite para pruebas, PostgreSQL para producción), documentación con Swagger/OpenAPI, y contenedorización con Docker.

---

## Estructura del Bootcamp

### Organización por Semanas

Cada semana debe tener la siguiente estructura de carpetas:

```
bootcamp/
  week-XX/
    ├── README.md
    ├── rubrica-evaluacion.md
    ├── 0-assets/
    ├── 1-teoria/
    ├── 2-practicas/
    ├── 3-proyecto/
    ├── 4-recursos/
    │   ├── ebooks-free/
    │   ├── videografia/
    │   └── webgrafia/
    └── 5-glosario/
```

### Componentes de Cada Semana

#### 1. `README.md`

- Descripción general de la semana
- Objetivos de aprendizaje
- Requisitos previos
- Índice de contenidos
- Duración estimada por sección

#### 2. `rubrica-evaluacion.md`

- Contiene los criterios de evaluación específicos de la semana
- Incluye tres tipos de evidencias:
  - **Conocimiento**: Cuestionarios, exámenes, preguntas escritas
  - **Desempeño**: Ejercicios en clase, talleres prácticos, implementaciones
  - **Producto**: Entregables (código, documentación, endpoints funcionales)

#### 3. `0-assets/`

- Imágenes y diagramas de la semana
- Archivos de configuración de ejemplo
- Recursos visuales (SVG, PNG). NUNCA ascii-art

#### 4. `1-teoria/`

- Material teórico de la semana
- Presentaciones, apuntes, diagramas conceptuales
- Referencias a documentación oficial
- Videos y recursos externos

#### 5. `2-practicas/`

- Ejercicios prácticos guiados
- Talleres paso a paso
- Ejemplos de código
- Mini-proyectos semanales

#### 6. `3-proyecto/`

- Proyecto integrador de la semana
- Código fuente del proyecto
- Archivos de configuración (application.properties, docker-compose.yml)
- Tests unitarios e integración

#### 7. `4-recursos/`

- `ebooks-free/`: Libros electrónicos gratuitos recomendados
- `videografia/`: Videos y tutoriales recomendados
- `webgrafia/`: Enlaces a artículos y documentación

#### 8. `5-glosario/`

- Términos y conceptos clave de la semana
- Definiciones técnicas
- Referencias cruzadas

---

**SIEMPRE** usar tema dark, sin degradados para la generación de imágenes en SVG, fuentes sin serifas

---

## Temario del Bootcamp (9 Semanas)

> ⚠️ **NOTA IMPORTANTE**: Docker se introduce desde la Semana 1 como herramienta fundamental para garantizar entornos de desarrollo consistentes y reproducibles. Esto resuelve el problema común de inestabilidad en ambientes de formación compartidos donde múltiples cohortes utilizan los mismos equipos.

### **Semana 1 – Entorno de Desarrollo con Docker y Fundamentos REST**

**Duración**: 5 horas

**Temas**:

- **Docker como solución a entornos inestables**
  - ¿Por qué Docker? Problema de "en mi máquina funciona"
  - Instalación de Docker Desktop
  - Conceptos básicos: imágenes, contenedores, volúmenes
  - Primer contenedor: `docker run hello-world`
  - Ejecutar JDK 21 en contenedor
- **Fundamentos de arquitectura web**
  - Arquitectura cliente-servidor
  - Protocolo HTTP: métodos, códigos de estado, headers
  - Principios REST y RESTful APIs
  - Introducción a JSON
- **Configuración del entorno containerizado**
  - docker-compose.yml básico para desarrollo Java
  - VS Code con Dev Containers (opcional)

**Evidencias**:

- **Conocimiento**: Cuestionario sobre Docker básico y arquitectura REST
- **Desempeño**: Ejecutar aplicación Java "Hola Mundo" en contenedor Docker
- **Producto**: docker-compose.yml funcional con JDK 21 y documento sobre REST

**Estrategias**: Clase invertida, codificación en vivo, troubleshooting guiado

---

### **Semana 2 – Spring Boot en Docker: Primeros Pasos**

**Duración**: 5 horas

**Temas**:

- **Proyecto Spring Boot con Maven**
  - Estructura de proyecto Spring Boot
  - pom.xml: dependencias y plugins
  - Spring Initializr (start.spring.io)
- **Dockerfile para Spring Boot**
  - Dockerfile básico con JDK 21
  - Construcción de imagen: `docker build`
  - Ejecución: `docker run` con puertos
- **Primeros endpoints REST**
  - @SpringBootApplication, @RestController, @RequestMapping
  - Endpoints GET y POST básicos
  - Path variables y Query parameters
- **Docker Compose para desarrollo**
  - Servicio de aplicación Spring Boot
  - Hot reload con volúmenes (desarrollo)
  - Logs y debugging en contenedores

**Evidencias**:

- **Conocimiento**: Examen sobre estructura Spring Boot y Dockerfile
- **Desempeño**: Crear API básica corriendo en Docker
- **Producto**: Proyecto Spring Boot con Dockerfile y docker-compose.yml funcional

**Estrategias**: Talleres prácticos guiados, pair programming

---

### **Semana 3 – Arquitectura en Capas y Configuración Avanzada**

**Duración**: 5 horas

**Temas**:

- **Arquitectura en capas**
  - Controller, Service, Repository
  - Patrón MVC adaptado a APIs REST
  - Inyección de dependencias (@Autowired, @Component, @Service)
- **DTOs y Validación**
  - Data Transfer Objects y Mappers
  - Bean Validation (@Valid, @NotNull, @Size)
  - Manejo de excepciones (@ControllerAdvice, @ExceptionHandler)
- **Configuración y perfiles**
  - application.properties vs application.yml
  - Perfiles: dev, test, prod
  - Variables de entorno en Docker
  - docker-compose con perfiles y .env

**Evidencias**:

- **Conocimiento**: Preguntas sobre arquitectura en capas y configuración
- **Desempeño**: Refactorizar proyecto aplicando capas y perfiles Docker
- **Producto**: API con arquitectura limpia, DTOs y configuración por entorno

**Estrategias**: Aprendizaje basado en problemas, refactoring en vivo

---

### **Semana 4 – Persistencia con JPA y PostgreSQL en Docker**

**Duración**: 5 horas

**Temas**:

- **PostgreSQL containerizado**
  - Imagen oficial de PostgreSQL
  - docker-compose con servicio de base de datos
  - Volúmenes para persistencia de datos
  - pgAdmin en contenedor (opcional)
- **Spring Data JPA**
  - Introducción a JPA e Hibernate
  - Configuración de conexión a PostgreSQL
  - Entidades: @Entity, @Id, @GeneratedValue, @Column
  - JpaRepository y operaciones CRUD
- **Multi-stage builds**
  - Optimización de imágenes Docker
  - Build de Maven en contenedor
  - Imagen final ligera

**Evidencias**:

- **Conocimiento**: Cuestionario sobre JPA y Docker multi-stage
- **Desempeño**: Configurar PostgreSQL en Docker y conectar con Spring
- **Producto**: API con persistencia en PostgreSQL containerizado

**Estrategias**: Aprendizaje basado en proyectos, estudio de casos

---

### **Semana 5 – Relaciones JPA y Redes Docker**

**Duración**: 5 horas

**Temas**:

- **Relaciones entre entidades**
  - @OneToOne, @OneToMany, @ManyToOne, @ManyToMany
  - Estrategias de carga: LAZY vs EAGER
  - Cascade types y orphan removal
- **Consultas avanzadas**
  - Query methods en Spring Data
  - @Query con JPQL y SQL nativo
  - Paginación y ordenamiento (Pageable, Sort)
- **Redes en Docker Compose**
  - Networks: bridge, host, custom
  - Comunicación entre contenedores
  - DNS interno de Docker
  - Healthchecks para dependencias

**Evidencias**:

- **Conocimiento**: Evaluación sobre relaciones JPA y redes Docker
- **Desempeño**: Implementar modelo con relaciones y red Docker custom
- **Producto**: API con entidades relacionadas en ambiente Docker completo

**Estrategias**: Pair programming, debugging colaborativo

---

### **Semana 6 – Documentación con Swagger/OpenAPI y CORS**

**Duración**: 5 horas

**Temas**:

- **OpenAPI y Swagger**
  - Importancia de documentación de APIs
  - OpenAPI Specification (OAS) 3.0
  - Configuración de SpringDoc OpenAPI
  - Anotaciones: @Operation, @ApiResponse, @Schema
- **Swagger UI**
  - Interfaz interactiva de documentación
  - Testing de endpoints desde Swagger
  - Exportación de especificación OpenAPI
- **CORS (Cross-Origin Resource Sharing)**
  - ¿Qué es CORS y por qué existe?
  - Configuración de CORS en Spring Boot
  - @CrossOrigin y configuración global
  - Preparación para integración con frontend

**Evidencias**:

- **Conocimiento**: Preguntas sobre OpenAPI, CORS y documentación
- **Desempeño**: Documentar API existente con Swagger y configurar CORS
- **Producto**: API documentada con Swagger UI y CORS habilitado

**Estrategias**: Talleres prácticos guiados, revisión de APIs profesionales

**🎁 Bonus - Integración Frontend (Parte 1)**:
- Introducción a la integración frontend-backend
- Consumir API desde HTML + JavaScript (fetch)
- Verificar CORS funcionando
- Carpeta: `bonus-frontend/week-06-cors-basics/`

---

### **Semana 7 – Seguridad: Spring Security y JWT**

**Duración**: 5 horas

**Temas**:

- **Fundamentos de seguridad en APIs**
  - Autenticación vs Autorización
  - Stateless vs Stateful
  - ¿Por qué JWT para APIs REST?
- **Spring Security**
  - Configuración básica de Spring Security
  - SecurityFilterChain y filtros
  - Deshabilitar CSRF para APIs REST
  - Proteger endpoints por roles
- **JWT (JSON Web Tokens)**
  - Estructura de un JWT (header, payload, signature)
  - Generación y validación de tokens
  - Refresh tokens (conceptual)
- **Implementación de Auth**
  - Endpoint de registro (/api/auth/register)
  - Endpoint de login (/api/auth/login)
  - Endpoint de recuperación de contraseña (básico)
  - Protección de endpoints con @PreAuthorize

**Evidencias**:

- **Conocimiento**: Cuestionario sobre JWT, Spring Security y autenticación
- **Desempeño**: Implementar registro y login con JWT
- **Producto**: API con autenticación JWT funcional

**Estrategias**: Live coding, análisis de flujos de autenticación, debugging de tokens

**🎁 Bonus - Integración Frontend (Parte 2)**:
- Formularios de Login y Registro en React
- Almacenamiento de JWT (localStorage vs httpOnly cookies)
- Envío de token en headers (Authorization: Bearer)
- Carpeta: `bonus-frontend/week-07-react-auth/`

---

### **Semana 8 – Testing y Docker Avanzado**

**Duración**: 5 horas

**Temas**:

- **Testing en Spring Boot**
  - Pirámide de testing (unitarios, integración, E2E)
  - JUnit 5 y Mockito básico
  - @MockBean y @SpringBootTest
  - MockMvc para testing de controladores
  - Tests de autenticación JWT
- **TestContainers**
  - Tests de integración con contenedores reales
  - PostgreSQL en tests con TestContainers
- **Docker Compose avanzado**
  - Múltiples servicios: app + db + frontend
  - Dependencias y orden de inicio (depends_on, healthcheck)
  - Override files para diferentes entornos
  - Optimización de imágenes (multi-stage builds)

**Evidencias**:

- **Conocimiento**: Cuestionario sobre testing y Docker avanzado
- **Desempeño**: Escribir tests de auth y configurar compose multi-servicio
- **Producto**: API con tests + docker-compose con frontend integrado

**Estrategias**: TDD, code review, análisis de arquitecturas reales

**🎁 Bonus - Integración Frontend (Parte 3 - Completo)**:
- Stack completo: Spring Boot + PostgreSQL + React
- docker-compose.yml con 3 servicios
- Flujo completo: Registro → Login → Acceso a recursos protegidos
- Ejercicio adaptable al proyecto formativo
- Carpeta: `bonus-frontend/week-08-full-stack-auth/`

---

### **Semana 9 – Proyecto Final Integrador**

**Duración**: 5 horas

**Temas**:

- **Desarrollo de API REST completa**
  - Integración de todos los conceptos del bootcamp
  - Mínimo 3 entidades relacionadas
  - Arquitectura en capas completa
- **Stack Docker completo**
  - Spring Boot + PostgreSQL + (opcional: Redis/Nginx)
  - docker-compose.yml de producción
  - Scripts de inicialización
- **Documentación y Testing**
  - Swagger UI completo
  - Suite de tests con TestContainers
  - README con instrucciones de despliegue
- **Presentación**
  - Defensa del proyecto
  - Demo en vivo con Docker

**Evidencias**:

- **Conocimiento**: Presentación oral sobre arquitectura y decisiones técnicas
- **Desempeño**: Desarrollo completo de API REST con Docker
- **Producto**: Proyecto final desplegable con `docker-compose up`

**Estrategias**: Aprendizaje basado en proyectos, evaluación integral, presentaciones

---

## Stack Tecnológico

### Tecnologías Principales

| Tecnología        | Versión | Propósito                                     |
| ----------------- | ------- | --------------------------------------------- |
| Docker            | 24+     | **Entorno de desarrollo containerizado**      |
| Docker Compose    | 2.x     | Orquestación de servicios                     |
| Java              | JDK 21  | Lenguaje de programación (en contenedor)      |
| Spring Boot       | 3.2+    | Framework web                                 |
| Spring Data JPA   | 3.2+    | Persistencia de datos                         |
| PostgreSQL        | 16+     | Base de datos (containerizada)                |
| SQLite            | 3.x     | Base de datos para pruebas locales rápidas    |
| SpringDoc OpenAPI | 2.x     | Documentación Swagger                         |
| JUnit 5           | 5.10+   | Testing unitario                              |
| Mockito           | 5.x     | Mocking para tests                            |
| TestContainers    | 1.19+   | Testing de integración con contenedores       |
| Maven             | 3.9+    | Gestión de dependencias (build en contenedor) |

### Dependencias Maven Recomendadas

```xml
<!-- Spring Boot Starter Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- SQLite JDBC -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
</dependency>

<!-- Hibernate SQLite Dialect -->
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-community-dialects</artifactId>
</dependency>

<!-- PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- SpringDoc OpenAPI (Swagger) -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>

<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT (JSON Web Tokens) -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
    <scope>runtime</scope>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Spring Security Test -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- TestContainers -->
<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>junit-jupiter</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.testcontainers</groupId>
    <artifactId>postgresql</artifactId>
    <scope>test</scope>
</dependency>
```

---

## Saberes de Conceptos y Principios

Al generar contenido, considera estos conceptos fundamentales:

1. **Arquitectura REST**: recursos, URIs, métodos HTTP, códigos de estado, HATEOAS
2. **Spring Framework**: IoC, DI, AOP, contexto de aplicación
3. **Spring Boot**: autoconfiguración, starters, actuators, profiles
4. **JPA/Hibernate**: entidades, relaciones, ciclo de vida, transacciones
5. **Bases de Datos**: SQL, normalización, índices, migraciones
6. **Docker**: imágenes, contenedores, volúmenes, redes, compose
7. **Testing**: unitario, integración, mocking, TDD
8. **API Design**: versionado, paginación, filtrado, CORS, seguridad básica
9. **Documentación**: OpenAPI 3.0, Swagger UI, contratos de API
10. **Seguridad**: validación de inputs, sanitización, OWASP Top 10, defensa en profundidad

---

## Saberes de Proceso

Los estudiantes deben ser capaces de:

1. Diseñar APIs REST siguiendo mejores prácticas y estándares
2. Implementar arquitectura en capas (Controller-Service-Repository)
3. Configurar y gestionar bases de datos con JPA
4. Documentar APIs con OpenAPI/Swagger
5. Escribir tests unitarios y de integración
6. Contenedorizar aplicaciones con Docker
7. Orquestar servicios con Docker Compose
8. Depurar y solucionar problemas en APIs REST
9. Versionar código con Git siguiendo convenciones
10. **Aplicar validaciones de seguridad desde el diseño** (Security by Design)
11. **Identificar y mitigar vulnerabilidades comunes** (SQL Injection, XSS, etc.)

---

## Criterios de Evaluación

Al crear contenido evaluativo, verifica que:

1. Se diseñen endpoints REST siguiendo convenciones de nombrado y métodos HTTP
2. Se implemente correctamente la arquitectura en capas
3. Se utilicen DTOs para transferencia de datos
4. Se manejen excepciones de forma global y consistente
5. Se configuren correctamente las entidades JPA y sus relaciones
6. Se documenten los endpoints con OpenAPI/Swagger
7. Se escriban tests con cobertura adecuada
8. Se contenericen las aplicaciones correctamente con Docker
9. Se configure Docker Compose para desarrollo local
10. **Se implementen validaciones en TODOS los inputs** (@Valid, @NotNull, etc.)
11. **No se expongan datos sensibles en respuestas de error**
12. **Se usen variables de entorno para credenciales** (nunca hardcodeadas)

---

## Estrategias Didácticas Activas

Al generar actividades, utiliza estas estrategias:

1. **Aprendizaje Basado en Proyectos (ABP)**: construcción progresiva de API REST
2. **Aprendizaje Basado en Problemas**: casos prácticos del mundo real
3. **Clase invertida (Flipped Classroom)**: teoría previa, práctica en clase
4. **Codificación colaborativa (Pair Programming)**: trabajo en parejas
5. **Code Review**: revisión de código entre pares
6. **TDD (Test-Driven Development)**: tests primero, código después
7. **Live Coding**: demostración en tiempo real
8. **Debugging colaborativo**: resolución de problemas en grupo
9. **API First Design**: diseño de contrato antes de implementación
10. **Talleres prácticos guiados**: codificación paso a paso

---

## Herramientas y Recursos

### Software y Herramientas

- **JDK 21**: Java Development Kit (Temurin)
- **IDEs**: IntelliJ IDEA, VS Code con Extension Pack for Java
- **Build Tools**: Maven
- **Git y GitHub**: control de versiones y trabajo colaborativo
- **Docker Desktop**: contenedorización local
- **Postman/Insomnia**: testing de APIs
- **DBeaver/DataGrip**: gestión de bases de datos
- **HTTPie/curl**: testing desde terminal

### Recursos Digitales

- [Documentación oficial de Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Guides](https://spring.io/guides)
- [Baeldung](https://www.baeldung.com/) - Tutoriales Spring
- [Docker Documentation](https://docs.docker.com/)
- [OpenAPI Specification](https://spec.openapis.org/oas/latest.html)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

### Material de Apoyo

- Guías de instalación y configuración del entorno
- Plantillas de proyectos Spring Boot
- Dockerfile y docker-compose.yml de referencia
- Colecciones de Postman para testing
- Glosario de términos de desarrollo web

### Herramientas Didácticas

- Presentaciones (PowerPoint/Canva/Reveal.js)
- Diagramas de arquitectura (draw.io, Excalidraw)
- Cuestionarios en línea (Kahoot, Quizizz)
- Foros/grupos (Teams, Slack, Discord)

---

## Guías para Generar Contenido

### Al crear material teórico (`1-teoria/`):

- Usa ejemplos claros y del mundo real
- Incluye diagramas de arquitectura y flujo
- Proporciona analogías para conceptos complejos
- Enlaza con documentación oficial de Spring
- Estructura: introducción, desarrollo, ejemplos de código, conclusión
- Incluye snippets de código funcionales y comentados

### Al crear prácticas (`2-practicas/`):

- Ejercicios progresivos (de simple a complejo)
- Incluye código de inicio (boilerplate) con TODOs
- Proporciona casos de prueba y expected outputs
- Agrega soluciones comentadas en carpeta separada
- Plantea desafíos opcionales para estudiantes avanzados
- Incluye comandos curl/httpie para probar endpoints

### Al crear proyectos (`3-proyecto/`):

- Estructura Maven completa
- Archivos de configuración (application.properties, application.yml)
- Dockerfile y docker-compose.yml cuando aplique
- README con instrucciones de ejecución
- Tests unitarios e integración
- Colección Postman para testing manual

### Al crear recursos (`4-recursos/`):

- `ebooks-free/`: Lista de libros gratuitos con enlaces
- `videografia/`: Videos de YouTube, cursos gratuitos
- `webgrafia/`: Artículos, tutoriales, documentación oficial

### Al crear glosarios (`5-glosario/`):

- Define términos en lenguaje claro
- Usa ejemplos cortos de código cuando sea relevante
- Incluye sinónimos y términos relacionados en inglés
- Ordena alfabéticamente
- Relaciona términos con los temas de la semana

### Al crear rúbricas (`rubrica-evaluacion.md`):

- Define niveles claros: Excelente, Bueno, Suficiente, Insuficiente
- Especifica criterios medibles
- Incluye pesos/ponderaciones
- Alinea con los criterios de evaluación del curso
- Diferencia entre evidencias de conocimiento, desempeño y producto

---

## Consideraciones Técnicas

### Estilo de Código

- Sigue las convenciones de Java (camelCase, PascalCase para clases)
- **Nomenclatura técnica en inglés (OBLIGATORIO)**:
  - Nombres de clases: `UserService`, `OrderController`, `ProductRepository`
  - Nombres de métodos: `findById()`, `createUser()`, `deleteOrder()`
  - Nombres de variables: `userName`, `totalAmount`, `isActive`
  - Nombres de paquetes: `com.bootcamp.week01.service`
  - Constantes: `MAX_RETRY_COUNT`, `DEFAULT_PAGE_SIZE`
- Documentación y comentarios pueden ser en español
- Incluye comentarios Javadoc en métodos públicos
- Implementa validaciones con Bean Validation
- Maneja excepciones con @ControllerAdvice
- Usa Lombok opcionalmente para reducir boilerplate

### Nivel de Complejidad

- **Semana 1**: Docker básico + Fundamentos REST (entorno estable desde el inicio)
- **Semana 2**: Spring Boot en Docker + Dockerfile básico
- **Semana 3**: Arquitectura en capas + Docker Compose con perfiles
- **Semana 4**: JPA + PostgreSQL containerizado + Multi-stage builds
- **Semana 5**: Relaciones JPA + Redes Docker
- **Semana 6**: Documentación Swagger
- **Semana 7**: Testing con TestContainers
- **Semana 8**: Docker avanzado y producción
- **Semana 9**: Proyecto final integrador

### Buenas Prácticas

- Código limpio y legible (Clean Code)
- Separación de responsabilidades (SoC)
- Principios SOLID aplicados a servicios
- API RESTful con recursos bien definidos
- Manejo consistente de errores (Problem Details RFC 7807)
- Logging apropiado con SLF4J
- Configuración externalizada con profiles

### 🔐 Seguridad Primero (Security First)

> **Mentalidad de seguridad**: "Ya nos atacaron, ¿qué vamos a hacer?" en lugar de "¿Nos podrían atacar? Después vemos..."

La seguridad NO es una característica opcional que se agrega al final. Debe estar presente desde la primera línea de código.

#### Principios Fundamentales

1. **Nunca confiar en el input del usuario** - Todo dato externo es potencialmente malicioso
2. **Validar siempre, sanitizar cuando sea necesario** - Bean Validation en todas las capas
3. **Fallar de forma segura** - Si algo sale mal, denegar acceso por defecto
4. **Principio de mínimo privilegio** - Solo los permisos estrictamente necesarios
5. **Defensa en profundidad** - Múltiples capas de protección

#### Validaciones Obligatorias

```java
// ✅ CORRECTO: Validación exhaustiva
@PostMapping("/users")
public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
    // Bean Validation ya validó el input
    return ResponseEntity.ok(userService.create(request));
}

// DTO con validaciones
public record CreateUserRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 2, max = 100, message = "Nombre debe tener entre 2 y 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "Nombre solo puede contener letras")
    String nombre,

    @NotBlank(message = "El email es requerido")
    @Email(message = "Email debe ser válido")
    String email,

    @NotNull(message = "La edad es requerida")
    @Min(value = 18, message = "Debe ser mayor de edad")
    @Max(value = 120, message = "Edad no válida")
    Integer edad
) {}

// ❌ INCORRECTO: Sin validación
@PostMapping("/users")
public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.ok(userRepository.save(user)); // ¡PELIGROSO!
}
```

#### Checklist de Seguridad por Capa

**Controller**:

- [ ] `@Valid` en todos los `@RequestBody`
- [ ] Validación de path variables y query params
- [ ] No exponer información sensible en respuestas de error
- [ ] Rate limiting (cuando aplique)

**Service**:

- [ ] Validación de reglas de negocio
- [ ] Verificación de permisos/autorización
- [ ] No confiar en datos ya "validados" - revalidar si es crítico
- [ ] Logging de operaciones sensibles (sin datos sensibles)

**Repository/Persistencia**:

- [ ] Usar consultas parametrizadas (JPA lo hace por defecto)
- [ ] Nunca concatenar strings para queries SQL
- [ ] Validar que IDs existan antes de operaciones

**Configuración**:

- [ ] Credenciales en variables de entorno, NUNCA en código
- [ ] Diferentes secretos para cada ambiente (dev, test, prod)
- [ ] `.env` en `.gitignore`
- [ ] Desactivar endpoints de debug en producción

#### Errores Comunes a Evitar

```java
// ❌ NUNCA: Exponer stack traces al usuario
@ExceptionHandler(Exception.class)
public ResponseEntity<String> handleError(Exception e) {
    return ResponseEntity.status(500).body(e.getMessage()); // Revela información interna
}

// ✅ SIEMPRE: Respuestas genéricas, logging interno
@ExceptionHandler(Exception.class)
public ResponseEntity<ErrorResponse> handleError(Exception e) {
    log.error("Error interno: ", e); // Log completo internamente
    return ResponseEntity.status(500)
        .body(new ErrorResponse("Error interno del servidor", "ERR-500"));
}

// ❌ NUNCA: SQL dinámico
String query = "SELECT * FROM users WHERE name = '" + userName + "'"; // SQL Injection!

// ✅ SIEMPRE: Queries parametrizadas
@Query("SELECT u FROM User u WHERE u.name = :name")
List<User> findByName(@Param("name") String name);

// ❌ NUNCA: Credenciales hardcodeadas
spring.datasource.password=mi_password_secreto

// ✅ SIEMPRE: Variables de entorno
spring.datasource.password=${DB_PASSWORD}
```

#### Headers de Seguridad Recomendados

```java
@Configuration
public class SecurityHeadersConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityHeadersInterceptor());
    }
}

// En cada respuesta:
// X-Content-Type-Options: nosniff
// X-Frame-Options: DENY
// X-XSS-Protection: 1; mode=block
// Content-Security-Policy: default-src 'self'
```

### Configuración de Bases de Datos

**PostgreSQL en Docker (desarrollo - recomendado)**:

```yaml
# docker-compose.yml
services:
  db:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: bootcamp
      POSTGRES_USER: dev
      POSTGRES_PASSWORD: dev123
    ports:
      - '5432:5432'
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

```properties
# application-dev.properties
spring.datasource.url=jdbc:postgresql://db:5432/bootcamp
spring.datasource.username=dev
spring.datasource.password=dev123
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

**SQLite (pruebas locales rápidas sin Docker)**:

```properties
# application-local.properties
spring.datasource.url=jdbc:sqlite:./data/app.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
```

**PostgreSQL (producción)**:

```properties
# application-prod.properties
spring.datasource.url=jdbc:postgresql://${DB_HOST}:5432/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=validate
```

---

## Formato de Archivos

### Markdown

- Usa headers apropiados (H1, H2, H3)
- Bloques de código con sintaxis highlighting: `java, `yaml, ```bash
- Listas y tablas cuando sea necesario
- Enlaces a recursos externos
- Emojis para mejorar legibilidad (📌, ✅, ⚠️, 💡)

### Código Java

- Archivos `.java` con estructura completa de paquetes
- Package naming: `com.bootcamp.weekXX.feature`
- Comentarios de autor y descripción con Javadoc
- Ejemplos de uso en comentarios o clase Main
- Tests en carpeta `src/test/java` con misma estructura

### Archivos de Configuración

- `application.properties` o `application.yml` bien documentados
- `Dockerfile` con multi-stage builds
- `docker-compose.yml` con servicios necesarios
- `.env.example` para variables de entorno

---

## Progresión del Aprendizaje

Asegúrate de que cada semana:

1. **Construya sobre conocimientos previos**
2. **Introduzca 1-2 conceptos nuevos máximo**
3. **Incluya tiempo para práctica (mínimo 60% del tiempo)**
4. **Proporcione feedback inmediato**
5. **Prepare para la semana siguiente**
6. **Incluya proyecto práctico aplicable**

---

## Notas Finales

- **Enfoque práctico**: El 60% del tiempo debe ser práctica, 40% teoría
- **Contexto laboral**: Formación orientada a competencias del mercado
- **Evaluación continua**: Cada semana incluye evidencias evaluables
- **Proyecto integrador**: La semana 9 integra todos los conocimientos
- **Trabajo colaborativo**: Fomentar trabajo en equipo y code review entre pares
- **Documentación**: Enfatizar la importancia de documentar APIs

---

## Estructura de Referencia

```
bootcamp/
  week-01/ → Entorno de Desarrollo con Docker y Fundamentos REST
  week-02/ → Spring Boot en Docker: Primeros Pasos
  week-03/ → Arquitectura en Capas y Configuración Avanzada
  week-04/ → Persistencia con JPA y PostgreSQL en Docker
  week-05/ → Relaciones JPA y Redes Docker
  week-06/ → Documentación con Swagger/OpenAPI
  week-07/ → Testing con TestContainers
  week-08/ → Docker Avanzado y Preparación para Producción
  week-09/ → Proyecto Final Integrador
```

---

## Proyecto Final Esperado

El proyecto final debe incluir:

1. **Stack Docker completo** ejecutable con `docker-compose up`
2. **API REST completa** con mínimo 3 entidades relacionadas
3. **Arquitectura en capas** (Controller, Service, Repository)
4. **PostgreSQL containerizado** con volúmenes persistentes
5. **Documentación Swagger** completa y funcional
6. **Suite de tests** con TestContainers (cobertura mínima 70%)
7. **Dockerfile optimizado** con multi-stage build
8. **docker-compose.yml** con servicios: app, db, (opcional: pgadmin)
9. **README** con instrucciones de despliegue Docker
10. **Colección Postman** para testing manual

---

**Recuerda**: El objetivo es formar desarrolladores Java competentes en desarrollo de APIs REST profesionales, capaces de diseñar, implementar, documentar, testear y desplegar aplicaciones web modernas usando Spring Boot y Docker. **Docker desde el día 1 garantiza entornos reproducibles y elimina el problema de "en mi máquina funciona"**.
