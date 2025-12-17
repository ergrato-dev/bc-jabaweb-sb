# 📖 Glosario - Semana 9: Proyecto Final Integrador

Este glosario recopila los términos más importantes del bootcamp, integrando conceptos de todas las semanas.

---

## A

### API (Application Programming Interface)
Interfaz que permite la comunicación entre diferentes aplicaciones. En el contexto de este bootcamp, desarrollamos APIs REST.

### API REST
Arquitectura de servicios web que utiliza HTTP para operaciones CRUD sobre recursos identificados por URIs.

### Arquitectura en Capas
Patrón de diseño que separa la aplicación en capas con responsabilidades específicas (Controller, Service, Repository).

### Authentication (Autenticación)
Proceso de verificar la identidad de un usuario, típicamente mediante credenciales como email/password.

### Authorization (Autorización)
Proceso de determinar qué recursos y operaciones puede acceder un usuario autenticado.

### @Autowired
Anotación de Spring que habilita la inyección automática de dependencias.

---

## B

### Bean
Objeto gestionado por el contenedor de Spring. Se crean mediante anotaciones como `@Component`, `@Service`, `@Repository`.

### Bean Validation
Framework para validar objetos Java usando anotaciones como `@NotNull`, `@Size`, `@Email`.

### BCrypt
Algoritmo de hash para passwords que incluye salting automático. Usado en Spring Security.

### Bearer Token
Esquema de autenticación donde el token JWT se envía en el header `Authorization: Bearer <token>`.

---

## C

### Cascade
Estrategia JPA para propagar operaciones de una entidad padre a sus entidades hijas.

### CORS (Cross-Origin Resource Sharing)
Mecanismo de seguridad del navegador que controla solicitudes entre diferentes dominios.

### Container (Contenedor Docker)
Instancia ejecutable de una imagen Docker, aislada y portátil.

### Controller
Componente que maneja las solicitudes HTTP entrantes y las delega a los servicios.

### @ControllerAdvice
Anotación para crear manejadores globales de excepciones.

---

## D

### DAO (Data Access Object)
Patrón que encapsula el acceso a datos. En Spring, los Repository cumplen este rol.

### DI (Dependency Injection)
Patrón donde las dependencias de un objeto son proporcionadas externamente en lugar de crearse internamente.

### Docker
Plataforma de contenedorización que empaqueta aplicaciones con sus dependencias.

### Docker Compose
Herramienta para definir y ejecutar aplicaciones Docker multi-contenedor.

### Dockerfile
Archivo de texto con instrucciones para construir una imagen Docker.

### DTO (Data Transfer Object)
Objeto que transporta datos entre procesos, separando la representación interna de la externa.

---

## E

### Endpoint
URL específica de una API que acepta solicitudes y retorna respuestas.

### Entity (Entidad)
Clase Java mapeada a una tabla de base de datos mediante JPA.

### @ExceptionHandler
Anotación para definir métodos que manejan excepciones específicas.

---

## F

### FetchType
Estrategia JPA para cargar relaciones: LAZY (bajo demanda) o EAGER (inmediatamente).

### Filter
Componente que intercepta solicitudes HTTP antes de llegar al controller.

---

## G

### @GeneratedValue
Anotación JPA que indica que el valor de un campo es generado automáticamente.

---

## H

### Healthcheck
Endpoint o mecanismo para verificar que un servicio está funcionando correctamente.

### Hibernate
Implementación de JPA más popular, usada por defecto en Spring Data JPA.

### HTTP Methods
Verbos que definen la operación: GET (leer), POST (crear), PUT (actualizar), DELETE (eliminar).

### HTTP Status Codes
Códigos numéricos que indican el resultado: 2xx (éxito), 4xx (error cliente), 5xx (error servidor).

---

## I

### Image (Imagen Docker)
Plantilla de solo lectura con instrucciones para crear un contenedor.

### IoC (Inversion of Control)
Principio donde el framework controla el flujo del programa en lugar del programador.

---

## J

### JaCoCo
Herramienta para medir la cobertura de código en tests Java.

### JPA (Java Persistence API)
Especificación estándar para mapeo objeto-relacional (ORM) en Java.

### JPQL
Lenguaje de consultas orientado a objetos similar a SQL pero que opera sobre entidades JPA.

### JSON (JavaScript Object Notation)
Formato ligero de intercambio de datos, estándar en APIs REST.

### JUnit
Framework de testing unitario para Java.

### JWT (JSON Web Token)
Estándar para crear tokens de acceso que contienen claims firmados digitalmente.

---

## L

### LAZY Loading
Estrategia donde los datos relacionados se cargan solo cuando se acceden.

### Lombok
Librería que genera código boilerplate (getters, setters, constructores) mediante anotaciones.

---

## M

### Maven
Herramienta de gestión de proyectos y dependencias para Java.

### Mockito
Framework para crear mocks (objetos simulados) en tests.

### MockMvc
Clase de Spring Test para probar controllers sin levantar un servidor real.

### Multi-stage Build
Técnica de Dockerfile que usa múltiples etapas para optimizar imágenes.

---

## N

### Network (Docker)
Red virtual que permite la comunicación entre contenedores Docker.

---

## O

### ORM (Object-Relational Mapping)
Técnica para convertir datos entre el sistema de tipos de un lenguaje OO y bases de datos relacionales.

### OpenAPI
Especificación para describir APIs REST de forma estandarizada (antes Swagger).

---

## P

### Pageable
Interfaz de Spring Data para solicitar información de paginación.

### PostgreSQL
Sistema de gestión de bases de datos relacional, open source y potente.

### Profile (Perfil)
Configuración específica para un entorno (dev, test, prod).

---

## R

### Record (Java)
Tipo especial de clase inmutable introducido en Java 14 para transportar datos.

### Repository
Interfaz que abstrae el acceso a datos y proporciona operaciones CRUD.

### REST (Representational State Transfer)
Estilo arquitectónico para sistemas distribuidos, basado en recursos y operaciones HTTP.

### Role
Conjunto de permisos asignados a un usuario para autorización.

---

## S

### Security Filter Chain
Cadena de filtros de Spring Security que procesan cada solicitud HTTP.

### Service
Componente que contiene la lógica de negocio de la aplicación.

### Soft Delete
Técnica donde los registros no se eliminan físicamente sino que se marcan como inactivos.

### Spring Boot
Framework que simplifica la configuración y desarrollo de aplicaciones Spring.

### Spring Data JPA
Módulo que facilita la implementación de repositorios JPA.

### Spring Security
Framework de seguridad para autenticación y autorización.

### Swagger UI
Interfaz gráfica interactiva para explorar y probar APIs documentadas con OpenAPI.

---

## T

### TestContainers
Librería que permite usar contenedores Docker en tests de integración.

### Token
Cadena de caracteres que representa credenciales de autenticación.

### Transaction (Transacción)
Unidad de trabajo que debe completarse totalmente o revertirse.

### @Transactional
Anotación que marca un método o clase para ejecutarse dentro de una transacción.

---

## U

### URI (Uniform Resource Identifier)
Identificador único de un recurso en la API.

### UserDetails
Interfaz de Spring Security que representa la información de un usuario.

---

## V

### Validation (Validación)
Proceso de verificar que los datos cumplen con las reglas de negocio.

### Volume (Volumen Docker)
Mecanismo para persistir datos generados por contenedores Docker.

---

## W

### Web Container
Servidor que ejecuta aplicaciones web (Tomcat embebido en Spring Boot).

---

## Acrónimos Comunes

| Acrónimo | Significado |
|----------|-------------|
| API | Application Programming Interface |
| CORS | Cross-Origin Resource Sharing |
| CRUD | Create, Read, Update, Delete |
| DAO | Data Access Object |
| DI | Dependency Injection |
| DTO | Data Transfer Object |
| HTTP | HyperText Transfer Protocol |
| IoC | Inversion of Control |
| JPA | Java Persistence API |
| JPQL | Java Persistence Query Language |
| JSON | JavaScript Object Notation |
| JWT | JSON Web Token |
| ORM | Object-Relational Mapping |
| REST | Representational State Transfer |
| SQL | Structured Query Language |
| TDD | Test-Driven Development |
| URI | Uniform Resource Identifier |
| URL | Uniform Resource Locator |

---

## Códigos HTTP Comunes

| Código | Significado | Uso |
|--------|-------------|-----|
| 200 | OK | Solicitud exitosa |
| 201 | Created | Recurso creado |
| 204 | No Content | Éxito sin contenido (DELETE) |
| 400 | Bad Request | Error de validación |
| 401 | Unauthorized | No autenticado |
| 403 | Forbidden | No autorizado |
| 404 | Not Found | Recurso no encontrado |
| 409 | Conflict | Conflicto (ej: duplicado) |
| 500 | Internal Server Error | Error del servidor |

---

> 💡 Este glosario integra términos de todas las semanas del bootcamp. Consúltalo durante el desarrollo del proyecto final.
