# 📖 Glosario - Semana 03

## A

### @Autowired
Anotación de Spring para inyección automática de dependencias. Aunque funcional, se prefiere la inyección por constructor.

### Architecture Layer
Capa de arquitectura. Nivel de abstracción en el diseño de software que agrupa componentes con responsabilidades similares.

---

## B

### Bean
Objeto gestionado por el contenedor IoC de Spring. Se crea, configura y administra automáticamente.

### Bean Validation
API estándar de Jakarta EE (anteriormente Java EE) para validación de datos mediante anotaciones como @NotNull, @Size, @Email.

### Bad Request (400)
Código HTTP que indica que el servidor no puede procesar la solicitud debido a un error del cliente (datos inválidos).

---

## C

### @Component
Anotación genérica de Spring que marca una clase como bean gestionado. Es la anotación padre de @Service, @Repository y @Controller.

### Constructor Injection
Patrón de inyección de dependencias donde las dependencias se pasan a través del constructor. Es el método recomendado.

### Controller
Capa de la arquitectura que recibe peticiones HTTP y delega la lógica al Service. Usa @RestController en APIs REST.

### @ControllerAdvice
Anotación que permite manejar excepciones de forma global para todos los controladores.

---

## D

### DAO (Data Access Object)
Patrón de diseño que encapsula el acceso a datos. En Spring se implementa con @Repository.

### Dependency Injection (DI)
Patrón de diseño donde las dependencias de un objeto son proporcionadas externamente en lugar de ser creadas internamente.

### DTO (Data Transfer Object)
Objeto simple usado para transferir datos entre capas o procesos. No contiene lógica de negocio.

---

## E

### @Entity
Anotación JPA que marca una clase como entidad persistente en base de datos (se verá en semana 4).

### @ExceptionHandler
Anotación que marca un método para manejar un tipo específico de excepción.

### ErrorResponse
DTO utilizado para estandarizar las respuestas de error de la API.

---

## F

### Field Injection
Inyección de dependencias directamente en campos con @Autowired. No recomendado por dificultar testing.

---

## G

### GlobalExceptionHandler
Clase con @RestControllerAdvice que centraliza el manejo de excepciones de toda la aplicación.

---

## I

### IoC (Inversion of Control)
Principio donde el control de la creación de objetos se invierte: el framework crea las instancias, no el programador.

### IoC Container
Contenedor de Spring que gestiona el ciclo de vida de los beans y sus dependencias.

---

## L

### Layered Architecture
Arquitectura en capas. Patrón que organiza el código en capas con responsabilidades específicas (Controller, Service, Repository).

### Loose Coupling
Acoplamiento débil. Diseño donde los componentes dependen de abstracciones (interfaces) en lugar de implementaciones concretas.

---

## M

### Mapper
Clase responsable de convertir objetos entre diferentes tipos (ej: Entity ↔ DTO).

### MethodArgumentNotValidException
Excepción lanzada por Spring cuando falla la validación de un @RequestBody anotado con @Valid.

---

## N

### @NotBlank
Anotación de validación que verifica que un String no sea null, vacío ni solo espacios.

### @NotNull
Anotación de validación que verifica que un valor no sea null.

---

## P

### Profile
Perfil de Spring que permite activar configuraciones específicas según el entorno (dev, test, prod).

### @PathVariable
Anotación que extrae valores de la URL del endpoint.

---

## R

### @Repository
Anotación que marca una clase como componente de acceso a datos. Habilita traducción de excepciones de persistencia.

### @RequestBody
Anotación que deserializa el cuerpo JSON de una petición HTTP a un objeto Java.

### @RestController
Combinación de @Controller y @ResponseBody. Indica que todos los métodos retornan datos (no vistas).

### @RestControllerAdvice
Combinación de @ControllerAdvice y @ResponseBody para manejo global de excepciones en APIs REST.

### RuntimeException
Excepción no verificada (unchecked) que no requiere ser declarada ni capturada obligatoriamente.

---

## S

### @Service
Anotación que marca una clase como servicio de negocio. Es una especialización de @Component.

### Separation of Concerns (SoC)
Principio de diseño que establece que cada capa/componente debe tener una única responsabilidad.

### @Size
Anotación de validación que verifica que el tamaño de un String o colección esté dentro de un rango.

### Single Responsibility Principle (SRP)
Principio SOLID que establece que una clase debe tener una única razón para cambiar.

### Spring Profiles
Mecanismo de Spring para activar configuraciones específicas según el entorno de ejecución.

---

## T

### Three-Tier Architecture
Arquitectura de tres capas: Presentación (Controller), Lógica de Negocio (Service), Datos (Repository).

---

## V

### @Valid
Anotación que activa la validación de Bean Validation en un parámetro de método.

### Validation
Proceso de verificar que los datos cumplen con reglas definidas antes de procesarlos.

---

## Variables de Entorno

### SPRING_PROFILES_ACTIVE
Variable de entorno que define el perfil activo de Spring Boot.

### DATABASE_URL
Variable de entorno típica para la URL de conexión a base de datos.

---

## Códigos HTTP Relevantes

| Código | Nombre | Uso |
|--------|--------|-----|
| 200 | OK | Operación exitosa |
| 201 | Created | Recurso creado |
| 204 | No Content | Eliminación exitosa |
| 400 | Bad Request | Error de validación |
| 404 | Not Found | Recurso no encontrado |
| 500 | Internal Server Error | Error del servidor |
