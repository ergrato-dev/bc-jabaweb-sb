# 📖 Glosario - Semana 06: Swagger/OpenAPI y CORS

## A

### API (Application Programming Interface)
Interfaz de Programación de Aplicaciones. Conjunto de definiciones y protocolos para construir e integrar software de aplicaciones. En el contexto REST, define los endpoints y operaciones disponibles.

### Access-Control-Allow-Origin
Header HTTP de respuesta que indica qué orígenes pueden acceder al recurso. Es el header principal de CORS.

```http
Access-Control-Allow-Origin: https://example.com
Access-Control-Allow-Origin: *
```

### @ApiResponse
Anotación de OpenAPI para documentar una respuesta específica de un endpoint, incluyendo código de estado, descripción y esquema del contenido.

```java
@ApiResponse(responseCode = "200", description = "Operación exitosa")
```

---

## C

### CORS (Cross-Origin Resource Sharing)
Mecanismo de seguridad que permite o restringe solicitudes de recursos desde un dominio diferente al del servidor. Utiliza headers HTTP para definir las políticas de acceso.

### Content Negotiation
Proceso por el cual cliente y servidor acuerdan el formato de intercambio de datos (JSON, XML, etc.) mediante headers como `Accept` y `Content-Type`.

### @CrossOrigin
Anotación de Spring para habilitar CORS a nivel de controlador o método específico.

```java
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MyController { }
```

---

## E

### Endpoint
URL específica de una API que representa un recurso o acción. Combinación de path + método HTTP.

```
GET /api/users      → Endpoint para obtener usuarios
POST /api/users     → Endpoint para crear usuario
```

---

## H

### Header HTTP
Metadatos enviados en las solicitudes y respuestas HTTP. Importantes para CORS: `Origin`, `Access-Control-*`.

---

## I

### Info Object (OpenAPI)
Objeto en la especificación OpenAPI que contiene metadatos de la API: título, versión, descripción, contacto, licencia.

```yaml
info:
  title: Mi API
  version: 1.0.0
  description: API de ejemplo
```

---

## O

### OAS (OpenAPI Specification)
Especificación estándar, agnóstica al lenguaje, para describir APIs RESTful. Anteriormente conocida como Swagger Specification.

### OpenAPI
Especificación para describir APIs REST de manera estandarizada. La versión actual es 3.1.0. Permite generar documentación, clientes y validadores.

### @Operation
Anotación de OpenAPI para documentar un endpoint específico con summary, description, tags, etc.

```java
@Operation(summary = "Obtener usuario", description = "Busca un usuario por ID")
```

### Origin
Header HTTP que indica el origen (protocolo + dominio + puerto) de una solicitud. Fundamental para CORS.

```http
Origin: http://localhost:3000
```

---

## P

### @Parameter
Anotación de OpenAPI para documentar un parámetro de endpoint (path variable, query param, header).

```java
@Parameter(description = "ID del usuario", required = true)
```

### Path Object (OpenAPI)
En la especificación OpenAPI, define las operaciones disponibles en un path específico (`/users`, `/users/{id}`).

### Preflight Request
Solicitud OPTIONS enviada automáticamente por el navegador antes de ciertas solicitudes cross-origin para verificar si el servidor permite la operación.

---

## R

### @RequestBody (Swagger)
Anotación de OpenAPI (`io.swagger.v3.oas.annotations.parameters.RequestBody`) para documentar el cuerpo de una solicitud.

```java
@io.swagger.v3.oas.annotations.parameters.RequestBody(
    description = "Datos del usuario",
    required = true
)
```

---

## S

### Same-Origin Policy
Política de seguridad de los navegadores que restringe cómo un documento o script de un origen puede interactuar con recursos de otro origen.

### Schema Object (OpenAPI)
Define la estructura de un objeto en OpenAPI. Describe tipos de datos, propiedades, validaciones y ejemplos.

### @Schema
Anotación para documentar la estructura de un modelo/DTO en OpenAPI.

```java
@Schema(
    description = "Nombre del usuario",
    example = "johndoe",
    maxLength = 50
)
```

### Simple Request (CORS)
Solicitud que no requiere preflight. Debe cumplir condiciones específicas: métodos GET/HEAD/POST, headers limitados, content-types específicos.

### Specification (OpenAPI)
Documento YAML o JSON que describe completamente una API según el estándar OpenAPI.

### SpringDoc
Librería para integrar OpenAPI 3.0 con Spring Boot 3.x. Reemplaza a SpringFox/swagger-springmvc.

### SpringFox
Librería legacy para Swagger en Spring. **Deprecada** para Spring Boot 3.x; usar SpringDoc en su lugar.

### Swagger
Conjunto de herramientas para diseñar, construir, documentar y consumir APIs REST. Incluye Swagger UI, Swagger Editor, Swagger Codegen.

### Swagger UI
Interfaz web interactiva que renderiza la especificación OpenAPI, permitiendo explorar y probar endpoints directamente desde el navegador.

---

## T

### @Tag
Anotación para agrupar endpoints relacionados en la documentación de Swagger UI.

```java
@Tag(name = "Usuarios", description = "Operaciones de usuarios")
@RestController
public class UserController { }
```

---

## W

### WebMvcConfigurer
Interfaz de Spring para personalizar la configuración de Spring MVC, incluyendo CORS.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000");
    }
}
```

---

## Códigos de Estado HTTP Relevantes

| Código | Nombre | Uso en APIs |
|--------|--------|-------------|
| 200 | OK | Operación exitosa |
| 201 | Created | Recurso creado |
| 204 | No Content | Eliminación exitosa |
| 400 | Bad Request | Error de validación |
| 401 | Unauthorized | Sin autenticación |
| 403 | Forbidden | Sin autorización |
| 404 | Not Found | Recurso no encontrado |
| 409 | Conflict | Conflicto (duplicado) |
| 500 | Internal Server Error | Error del servidor |

---

## Headers CORS Importantes

| Header | Dirección | Descripción |
|--------|-----------|-------------|
| `Origin` | Request | Origen de la solicitud |
| `Access-Control-Allow-Origin` | Response | Orígenes permitidos |
| `Access-Control-Allow-Methods` | Response | Métodos HTTP permitidos |
| `Access-Control-Allow-Headers` | Response | Headers permitidos |
| `Access-Control-Allow-Credentials` | Response | Permite cookies/auth |
| `Access-Control-Max-Age` | Response | Cache de preflight |
| `Access-Control-Expose-Headers` | Response | Headers expuestos al cliente |
| `Access-Control-Request-Method` | Request (Preflight) | Método que se usará |
| `Access-Control-Request-Headers` | Request (Preflight) | Headers que se usarán |

---

## Anotaciones OpenAPI Principales

| Anotación | Nivel | Propósito |
|-----------|-------|-----------|
| `@Tag` | Clase | Agrupar endpoints |
| `@Operation` | Método | Documentar operación |
| `@ApiResponse` | Método | Documentar respuesta |
| `@ApiResponses` | Método | Múltiples respuestas |
| `@Parameter` | Parámetro | Documentar parámetro |
| `@RequestBody` | Parámetro | Documentar body |
| `@Schema` | Clase/Campo | Documentar modelo |
| `@Hidden` | Clase/Método | Ocultar de docs |

---

## Referencias Cruzadas

- **Semana 02**: Endpoints REST básicos → Base para documentar con OpenAPI
- **Semana 03**: DTOs y validación → @Schema documenta los DTOs
- **Semana 03**: Manejo de excepciones → Documentar errores en @ApiResponse
- **Semana 07**: Seguridad JWT → Documentar autenticación en OpenAPI

> 📚 **Tip**: Usa `Ctrl+F` para buscar términos específicos en este glosario.
