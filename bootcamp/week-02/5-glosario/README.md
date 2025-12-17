# 📖 Glosario - Semana 02

## A

### Anotación (Annotation)
Metadato que se agrega al código Java usando `@`. En Spring, las anotaciones configuran el comportamiento de clases y métodos.
```java
@RestController  // Anotación de Spring
public class MyController { }
```

### Autoconfiguración (Auto-configuration)
Característica de Spring Boot que configura automáticamente beans según las dependencias detectadas en el classpath.

### Artifact
Identificador único de un proyecto Maven. Junto con `groupId` y `version`, forma las coordenadas GAV.

---

## B

### Bean
Objeto gestionado por el contenedor de Spring. Spring crea, configura e inyecta beans automáticamente.

### Body (Request/Response)
Contenido de una petición o respuesta HTTP. En APIs REST, típicamente es JSON.

---

## C

### Classpath
Ruta donde Java busca clases y recursos. Maven gestiona el classpath automáticamente.

### Controller
Componente que recibe peticiones HTTP y retorna respuestas. En Spring: `@Controller` o `@RestController`.

### CRUD
Acrónimo para las operaciones básicas: **C**reate, **R**ead, **U**pdate, **D**elete.

---

## D

### Dependencia (Dependency)
Librería externa que tu proyecto necesita. Se declaran en `pom.xml` y Maven las descarga automáticamente.

### DTO (Data Transfer Object)
Objeto simple usado para transferir datos entre capas o sistemas. Típicamente no tiene lógica de negocio.

---

## E

### Endpoint
URL específica que expone una funcionalidad de la API.
- Ejemplo: `GET /api/users/123`

### Embedded Server
Servidor web incluido dentro de la aplicación. Spring Boot incluye Tomcat embebido por defecto.

---

## G

### GAV (Group-Artifact-Version)
Coordenadas que identifican únicamente un proyecto Maven:
- **G**roup: `com.bootcamp`
- **A**rtifact: `demo`
- **V**ersion: `0.0.1-SNAPSHOT`

### GET
Método HTTP para obtener recursos. Debe ser idempotente (mismo resultado cada vez).

---

## H

### Handler Method
Método en un controlador que procesa una petición HTTP específica.

### HTTP Status Code
Código numérico que indica el resultado de una petición HTTP:
- `200` OK
- `201` Created
- `404` Not Found
- `500` Internal Server Error

---

## I

### IoC (Inversion of Control)
Patrón donde el framework controla la creación de objetos, no el código del desarrollador. Spring implementa IoC.

### Inyección de Dependencias (Dependency Injection)
Técnica donde las dependencias se proporcionan desde fuera en lugar de crearlas internamente.

---

## J

### Jackson
Librería para serializar/deserializar objetos Java a/desde JSON. Incluida automáticamente en Spring Web.

### JAR (Java Archive)
Archivo empaquetado que contiene clases Java compiladas y recursos. Spring Boot genera JARs ejecutables.

### JSON (JavaScript Object Notation)
Formato de texto para intercambio de datos. Estándar en APIs REST.
```json
{"id": 1, "name": "Juan"}
```

---

## M

### Maven
Herramienta de gestión de proyectos Java que maneja dependencias, compilación y empaquetado.

### Maven Wrapper (mvnw)
Script que permite ejecutar Maven sin tenerlo instalado globalmente.

### Mapping
Asociación entre una URL y un método de controlador.
```java
@GetMapping("/users")  // Mapea GET /users a este método
```

---

## P

### Path Variable
Parte de la URL que se extrae como parámetro:
```
URL:     /users/42
Pattern: /users/{id}
Valor:   id = 42
```

### POJO (Plain Old Java Object)
Clase Java simple sin dependencias especiales. Los modelos suelen ser POJOs.

### POST
Método HTTP para crear recursos. No es idempotente.

### pom.xml
Archivo de configuración de Maven. Define dependencias, plugins y metadatos del proyecto.

---

## Q

### Query Parameter
Parámetro en la URL después de `?`:
```
/users?name=Juan&active=true
       └─────────┴─────────┘
       Query Parameters
```

---

## R

### Request
Petición HTTP enviada al servidor. Incluye método, URL, headers y opcionalmente body.

### RequestBody
Anotación que indica que un parámetro debe deserializarse desde el body JSON:
```java
public User create(@RequestBody User user)
```

### Response
Respuesta HTTP enviada al cliente. Incluye status code, headers y opcionalmente body.

### ResponseEntity
Clase de Spring que permite controlar completamente la respuesta HTTP (status, headers, body).

### REST (Representational State Transfer)
Estilo arquitectónico para diseñar APIs web basado en recursos y métodos HTTP.

### @RestController
Anotación que combina `@Controller` + `@ResponseBody`. Los métodos retornan datos directamente (no vistas).

---

## S

### Serialización
Proceso de convertir un objeto Java a JSON (u otro formato).

### Starter
Dependencia de Spring Boot que incluye todo lo necesario para una funcionalidad:
- `spring-boot-starter-web` → API REST + Tomcat

### Spring Framework
Framework Java para desarrollo empresarial. Base de Spring Boot.

### Spring Boot
Framework que simplifica la configuración de Spring con autoconfiguración y convenciones.

### Spring Initializr
Herramienta web para generar proyectos Spring Boot preconfigurados: https://start.spring.io/

---

## T

### Tomcat
Servidor web Java. Spring Boot lo incluye embebido por defecto.

---

## V

### Volume (Docker)
Mecanismo para persistir datos de contenedores. Ejemplo: caché de Maven.

---

## Anotaciones Clave de Spring

| Anotación | Propósito |
|-----------|-----------|
| `@SpringBootApplication` | Clase principal de Spring Boot |
| `@RestController` | Controlador REST (retorna datos) |
| `@RequestMapping` | Mapeo base de URLs |
| `@GetMapping` | Mapeo para método GET |
| `@PostMapping` | Mapeo para método POST |
| `@PutMapping` | Mapeo para método PUT |
| `@DeleteMapping` | Mapeo para método DELETE |
| `@PathVariable` | Extrae variable de la URL |
| `@RequestParam` | Extrae query parameter |
| `@RequestBody` | Deserializa body JSON |
