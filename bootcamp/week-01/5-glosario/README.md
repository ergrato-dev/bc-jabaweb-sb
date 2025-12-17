# 📖 Glosario - Semana 01

## A

### API (Application Programming Interface)
Interfaz de programación de aplicaciones. Conjunto de reglas y protocolos que permiten que diferentes aplicaciones se comuniquen entre sí.
- **Ejemplo**: La API de Twitter permite a otras aplicaciones publicar tweets.
- **Relacionado**: REST, Endpoint, HTTP

### Alpine Linux
Distribución Linux minimalista (~5MB) usada frecuentemente como base para imágenes Docker.
- **Ejemplo**: `eclipse-temurin:21-jdk-alpine`
- **Ventaja**: Imágenes más pequeñas y seguras

---

## B

### Bind Mount
Tipo de volumen en Docker que mapea un directorio del host al contenedor.
- **Ejemplo**: `-v /home/user/src:/app/src`
- **Uso**: Desarrollo local con hot-reload

### Body (HTTP)
Cuerpo de un mensaje HTTP. Contiene los datos enviados en requests POST/PUT o las respuestas del servidor.
- **Formato común**: JSON, XML, form-data

---

## C

### Cliente
Aplicación que solicita servicios o recursos a un servidor.
- **Ejemplos**: Navegador web, aplicación móvil, Postman

### Contenedor (Container)
Instancia ejecutable de una imagen Docker. Proceso aislado con su propio sistema de archivos, red y recursos.
- **Analogía**: Si la imagen es una clase, el contenedor es un objeto/instancia

### CRUD
Acrónimo para las cuatro operaciones básicas de persistencia:
- **C**reate (Crear) → POST
- **R**ead (Leer) → GET
- **U**pdate (Actualizar) → PUT/PATCH
- **D**elete (Eliminar) → DELETE

---

## D

### Docker
Plataforma de contenedorización que permite empaquetar aplicaciones con todas sus dependencias.
- **Beneficio**: "Si funciona en mi máquina, funciona en todas"
- **Componentes**: Docker Engine, Docker CLI, Docker Hub

### Docker Compose
Herramienta para definir y ejecutar aplicaciones multi-contenedor usando archivos YAML.
- **Archivo**: `docker-compose.yml`
- **Comando**: `docker compose up`

### Docker Hub
Registro público de imágenes Docker. Repositorio donde se almacenan y comparten imágenes.
- **URL**: https://hub.docker.com/

### Dockerfile
Archivo de texto con instrucciones para construir una imagen Docker.
- **Comandos comunes**: FROM, RUN, COPY, WORKDIR, CMD, EXPOSE

---

## E

### Endpoint
URL específica que expone un recurso o funcionalidad de una API.
- **Ejemplo**: `GET /api/users/123`
- **Componentes**: Método HTTP + URI

### Entrypoint
Comando que se ejecuta cuando inicia un contenedor Docker.
- **Diferencia con CMD**: ENTRYPOINT define el ejecutable, CMD define argumentos por defecto

---

## G

### GET
Método HTTP para obtener/leer recursos. Debe ser idempotente y seguro (no modifica datos).
- **Ejemplo**: `GET /api/productos` → Lista de productos

---

## H

### Header (HTTP)
Metadatos enviados en requests/responses HTTP.
- **Ejemplos**: `Content-Type`, `Authorization`, `Accept`
- **Uso**: Configurar formato de datos, autenticación, caché

### Host
Máquina física o virtual donde se ejecutan los contenedores Docker.

### HTTP (HyperText Transfer Protocol)
Protocolo de comunicación cliente-servidor para la web.
- **Puerto por defecto**: 80 (HTTP), 443 (HTTPS)
- **Versiones**: HTTP/1.1, HTTP/2, HTTP/3

### HTTPS
HTTP sobre TLS/SSL. Versión segura y encriptada de HTTP.

---

## I

### Idempotente
Operación que produce el mismo resultado sin importar cuántas veces se ejecute.
- **Métodos idempotentes**: GET, PUT, DELETE
- **No idempotente**: POST

### Imagen (Docker Image)
Plantilla de solo lectura con instrucciones para crear un contenedor.
- **Capas**: Las imágenes se construyen en capas reutilizables
- **Ejemplo**: `eclipse-temurin:21-jdk`

---

## J

### Java
Lenguaje de programación orientado a objetos, tipado estáticamente.
- **Versión del bootcamp**: JDK 21
- **Características**: Write Once, Run Anywhere (WORA)

### JDK (Java Development Kit)
Kit de desarrollo de Java. Incluye compilador (javac), JVM, y herramientas.
- **Imagen Docker**: `eclipse-temurin:21-jdk`

### JSON (JavaScript Object Notation)
Formato ligero de intercambio de datos basado en texto.
```json
{
  "nombre": "Juan",
  "edad": 25,
  "activo": true
}
```

### JVM (Java Virtual Machine)
Máquina virtual que ejecuta bytecode Java. Permite la portabilidad del código.

---

## M

### Método HTTP
Verbo que indica la acción a realizar sobre un recurso.
- **Principales**: GET, POST, PUT, PATCH, DELETE
- **Otros**: HEAD, OPTIONS, TRACE

---

## N

### Named Volume
Volumen Docker con nombre, gestionado por Docker.
- **Ejemplo**: `docker volume create mi-datos`
- **Uso**: Persistencia de datos entre contenedores

---

## P

### Path Variable
Variable en la URL que identifica un recurso específico.
- **Ejemplo**: `/users/{id}` → `/users/123`

### POST
Método HTTP para crear nuevos recursos.
- **Ejemplo**: `POST /api/usuarios` con body JSON
- **Respuesta típica**: 201 Created

### PUT
Método HTTP para actualizar/reemplazar un recurso completo.
- **Idempotente**: Sí
- **Ejemplo**: `PUT /api/usuarios/123`

---

## Q

### Query Parameter
Parámetro en la URL para filtrar o modificar la respuesta.
- **Sintaxis**: `?key=value&key2=value2`
- **Ejemplo**: `/api/productos?categoria=electronica&precio_max=100`

---

## R

### REST (Representational State Transfer)
Estilo arquitectónico para diseñar APIs web.
- **Principios**: Stateless, Cacheable, Uniform Interface
- **Creador**: Roy Fielding (2000)

### RESTful
API que sigue los principios REST.

### Recurso (Resource)
Entidad o dato expuesto por una API REST.
- **Ejemplos**: Usuario, Producto, Orden
- **Representación**: JSON, XML

---

## S

### Servidor
Sistema que proporciona servicios o recursos a clientes.
- **Ejemplos**: Apache, Nginx, Tomcat

### Stateless (Sin Estado)
Principio REST donde cada request contiene toda la información necesaria.
- **Beneficio**: Escalabilidad horizontal

### Status Code
Código numérico HTTP que indica el resultado de una operación.
- **2xx**: Éxito (200, 201, 204)
- **4xx**: Error del cliente (400, 401, 404)
- **5xx**: Error del servidor (500, 502, 503)

---

## T

### Tag (Docker)
Etiqueta que identifica una versión específica de una imagen.
- **Formato**: `imagen:tag`
- **Ejemplos**: `eclipse-temurin:21-jdk`, `postgres:16-alpine`

### Temurin (Eclipse)
Distribución open source de OpenJDK mantenida por la fundación Eclipse.
- **URL**: https://adoptium.net/

---

## U

### URI (Uniform Resource Identifier)
Identificador único de un recurso.
- **Ejemplo**: `/api/v1/usuarios/123`

### URL (Uniform Resource Locator)
URI completa con protocolo y host.
- **Ejemplo**: `https://api.ejemplo.com/usuarios/123`

---

## V

### Volumen (Docker Volume)
Mecanismo para persistir datos generados por contenedores.
- **Tipos**: Named volumes, Bind mounts, tmpfs
- **Comando**: `docker volume create`

---

## Y

### YAML (YAML Ain't Markup Language)
Formato de serialización de datos usado en docker-compose.yml.
```yaml
services:
  app:
    image: eclipse-temurin:21-jdk
    ports:
      - "8080:8080"
```

---

## Símbolos y Números

### 200 OK
Código de estado HTTP que indica éxito en la operación GET.

### 201 Created
Código de estado HTTP que indica que se creó un recurso exitosamente.

### 204 No Content
Código de estado HTTP para operaciones exitosas sin contenido de respuesta.

### 400 Bad Request
Error del cliente: la solicitud tiene sintaxis incorrecta.

### 401 Unauthorized
Error de autenticación: se requieren credenciales.

### 403 Forbidden
Error de autorización: no tiene permisos para el recurso.

### 404 Not Found
Error: el recurso solicitado no existe.

### 500 Internal Server Error
Error del servidor: algo salió mal en el backend.

---

*Este glosario se actualiza semanalmente con los nuevos términos del bootcamp.*
