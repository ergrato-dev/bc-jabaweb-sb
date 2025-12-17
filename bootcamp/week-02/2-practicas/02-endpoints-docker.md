# Práctica 02: Endpoints REST y Docker

## 🎯 Objetivo

Implementar un controlador REST completo con múltiples endpoints y configurar Docker Compose para desarrollo profesional.

**Duración estimada**: 60 minutos

---

## 📋 Requisitos Previos

- Haber completado la Práctica 01
- Proyecto Spring Boot funcionando en Docker

---

## Parte 1: Controlador de Productos

### Paso 1.1: Crear el Modelo

Crea `src/main/java/com/bootcamp/demo/model/Product.java`:

```java
package com.bootcamp.demo.model;

public class Product {
    private Long id;
    private String name;
    private Double price;

    // Constructor vacío (requerido por Jackson)
    public Product() {}

    // Constructor con parámetros
    public Product(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
```

### Paso 1.2: Crear ProductController

Crea `src/main/java/com/bootcamp/demo/controller/ProductController.java`:

```java
package com.bootcamp.demo.controller;

import com.bootcamp.demo.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Lista en memoria (simulando base de datos)
    private List<Product> products = new ArrayList<>(List.of(
        new Product(1L, "Laptop", 999.99),
        new Product(2L, "Mouse", 29.99),
        new Product(3L, "Teclado", 79.99)
    ));

    // GET /api/products - Listar todos
    @GetMapping
    public List<Product> getAll() {
        return products;
    }

    // GET /api/products/{id} - Obtener uno
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return products.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    // TODO: Implementar los siguientes endpoints
}
```

### Paso 1.3: Probar Endpoints GET

```bash
# Listar todos
curl http://localhost:8080/api/products

# Obtener uno específico
curl http://localhost:8080/api/products/1
```

**Respuesta esperada (GET /api/products)**:
```json
[
  {"id":1,"name":"Laptop","price":999.99},
  {"id":2,"name":"Mouse","price":29.99},
  {"id":3,"name":"Teclado","price":79.99}
]
```

---

## Parte 2: Implementar POST

### Ejercicio 2.1: Crear Producto

Agrega el siguiente método al `ProductController`:

```java
// TODO: Implementar POST /api/products
// Debe recibir un Product en el body y agregarlo a la lista
// Debe retornar el producto creado
// Pistas:
// - Usa @PostMapping
// - Usa @RequestBody para recibir el JSON
// - Genera un nuevo ID (products.size() + 1)

@PostMapping
public Product create(@RequestBody Product product) {
    // TODO: Tu implementación aquí
    return null;
}
```

### Ejercicio 2.2: Probar POST

```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Monitor","price":299.99}'
```

**Respuesta esperada**:
```json
{"id":4,"name":"Monitor","price":299.99}
```

---

## Parte 3: Query Parameters

### Ejercicio 3.1: Buscar por Nombre

```java
// TODO: Implementar GET /api/products/search?name=xxx
// Debe buscar productos cuyo nombre contenga el texto (case insensitive)
// Ejemplo: /api/products/search?name=lap debe retornar Laptop
// Pistas:
// - Usa @GetMapping("/search")
// - Usa @RequestParam String name
// - Usa stream().filter() con contains()

@GetMapping("/search")
public List<Product> search(@RequestParam String name) {
    // TODO: Tu implementación aquí
    return null;
}
```

### Ejercicio 3.2: Filtrar por Precio

```java
// TODO: Implementar GET /api/products/filter?minPrice=x&maxPrice=y
// Debe retornar productos dentro del rango de precios
// Ejemplo: /api/products/filter?minPrice=50&maxPrice=100
// Los parámetros deben ser opcionales:
// - Si no se envía minPrice, usar 0
// - Si no se envía maxPrice, usar Double.MAX_VALUE
// Pista: Usa @RequestParam(required = false, defaultValue = "0")

@GetMapping("/filter")
public List<Product> filterByPrice(
        @RequestParam(required = false, defaultValue = "0") Double minPrice,
        @RequestParam(required = false, defaultValue = "999999") Double maxPrice) {
    // TODO: Tu implementación aquí
    return null;
}
```

---

## Parte 4: ResponseEntity

### Paso 4.1: Mejorar getById

Modifica el método para retornar 404 si no existe:

```java
import org.springframework.http.ResponseEntity;

// Mejorar GET /api/products/{id}
@GetMapping("/{id}")
public ResponseEntity<Product> getById(@PathVariable Long id) {
    return products.stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .map(ResponseEntity::ok)                    // 200 OK si existe
        .orElse(ResponseEntity.notFound().build()); // 404 si no existe
}
```

### Ejercicio 4.2: Mejorar POST con 201 Created

```java
import org.springframework.http.HttpStatus;

// TODO: Modificar el método create para retornar:
// - HTTP 201 Created (no 200 OK)
// - El producto creado en el body
// Pista: ResponseEntity.status(HttpStatus.CREATED).body(product)
```

---

## Parte 5: Docker Compose Mejorado

### Paso 5.1: Agregar Configuración de Ambiente

Actualiza `docker-compose.yml`:

```yaml
services:
  app:
    image: eclipse-temurin:21-jdk
    container_name: spring-boot-demo
    working_dir: /app
    volumes:
      - .:/app
      - maven-cache:/root/.m2
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8
    command: ./mvnw spring-boot:run
    tty: true
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 60s

volumes:
  maven-cache:
```

### Paso 5.2: Agregar Actuator para Health Check

Agrega en `pom.xml` dentro de `<dependencies>`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Paso 5.3: Configurar Actuator

En `src/main/resources/application.properties`:

```properties
# Habilitar endpoint de health
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

### Paso 5.4: Reiniciar y Verificar

```bash
docker compose down
docker compose up --build
```

Verifica el health check:
```bash
curl http://localhost:8080/actuator/health
```

**Respuesta esperada**:
```json
{"status":"UP"}
```

---

## Parte 6: Desafío Extra

### Ejercicio 6.1: Implementar PUT y DELETE

```java
// TODO: Implementar PUT /api/products/{id}
// Debe actualizar un producto existente
// Si no existe, retornar 404

// TODO: Implementar DELETE /api/products/{id}
// Debe eliminar un producto
// Si no existe, retornar 404
// Si se elimina correctamente, retornar 204 No Content
```

### Ejercicio 6.2: Paginación Simple

```java
// TODO: Modificar GET /api/products para soportar paginación
// GET /api/products?page=0&size=2
// Debe retornar los primeros 2 productos
// Pistas:
// - page: número de página (empieza en 0)
// - size: cantidad de elementos por página
// - Usa subList() para obtener el rango correcto
```

---

## ✅ Checklist de Verificación

- [ ] Modelo `Product` creado
- [ ] `ProductController` con `@RequestMapping("/api/products")`
- [ ] GET `/api/products` - Lista todos
- [ ] GET `/api/products/{id}` - Retorna uno o 404
- [ ] POST `/api/products` - Crea con 201 Created
- [ ] GET `/api/products/search?name=x` - Busca por nombre
- [ ] GET `/api/products/filter?minPrice=x&maxPrice=y` - Filtra por precio
- [ ] Actuator configurado y health check funcionando
- [ ] (Extra) PUT y DELETE implementados
- [ ] (Extra) Paginación implementada

---

## 🔧 Troubleshooting

### Error 415 Unsupported Media Type

Asegúrate de enviar el header:
```bash
-H "Content-Type: application/json"
```

### JSON malformado en respuesta

Verifica que tu modelo tenga:
- Constructor vacío
- Getters para todas las propiedades

### NullPointerException al crear

Verifica que `products` esté inicializado como `ArrayList` (no `List.of()` que es inmutable).

---

## 📚 Recursos

- [Spring @RequestMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html)
- [ResponseEntity](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html)
- [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)

---

## ➡️ Siguiente

Continúa con el [Proyecto de la Semana 02](../3-proyecto/README.md)
