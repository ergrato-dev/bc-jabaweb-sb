# 🔧 Práctica 03: Manejo Global de Excepciones

## Objetivo

Implementar manejo centralizado de excepciones con `@RestControllerAdvice` para respuestas de error consistentes y código limpio.

---

## 📋 Requisitos Previos

- Práctica 02 completada (DTOs y Validación)
- Entender códigos HTTP de error

---

## Parte 1: Crear Estructura de Excepciones

### 1.1 Crear el paquete exception

```
src/main/java/com/bootcamp/taskmanager/
└── exception/
    ├── GlobalExceptionHandler.java
    ├── ErrorResponse.java
    ├── ResourceNotFoundException.java
    └── BadRequestException.java
```

---

## Parte 2: Crear Excepciones Personalizadas

### 2.1 ResourceNotFoundException (404)

```java
package com.bootcamp.taskmanager.exception;

/**
 * Excepción para recursos no encontrados
 * Se traduce a HTTP 404 Not Found
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final String fieldName;
    private final Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // Constructor simple
    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceName = null;
        this.fieldName = null;
        this.fieldValue = null;
    }

    public String getResourceName() { return resourceName; }
    public String getFieldName() { return fieldName; }
    public Object getFieldValue() { return fieldValue; }
}
```

### 2.2 BadRequestException (400)

```java
package com.bootcamp.taskmanager.exception;

/**
 * Excepción para solicitudes inválidas
 * Se traduce a HTTP 400 Bad Request
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

---

## Parte 3: Crear ErrorResponse DTO

### 3.1 ErrorResponse

```java
package com.bootcamp.taskmanager.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO estándar para respuestas de error
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // No incluir campos null
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> errors; // Para errores de validación

    // Constructor básico
    public ErrorResponse(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    // Constructor con errores de validación
    public ErrorResponse(int status, String error, String message, String path,
                         Map<String, String> errors) {
        this(status, error, message, path);
        this.errors = errors;
    }

    // Getters
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Map<String, String> getErrors() { return errors; }
}
```

---

## Parte 4: Crear GlobalExceptionHandler

### 4.1 GlobalExceptionHandler

```java
package com.bootcamp.taskmanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones
 * Centraliza el manejo de errores para toda la aplicación
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja ResourceNotFoundException → HTTP 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja BadRequestException → HTTP 400
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja errores de validación → HTTP 400
     * TODO: Captura @Valid que fallan
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        // Extraer errores de cada campo
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Validation Error",
            "Error de validación en los datos enviados",
            request.getRequestURI(),
            fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Maneja IllegalArgumentException → HTTP 400
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            ex.getMessage(),
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    /**
     * Catch-all: Maneja cualquier otra excepción → HTTP 500
     * TODO: Importante para seguridad - no exponer detalles internos
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex,
            HttpServletRequest request) {

        // Log interno (en producción usar un logger como SLF4J)
        System.err.println("Error interno: " + ex.getMessage());
        ex.printStackTrace();

        // Respuesta genérica al cliente (no exponer detalles)
        ErrorResponse error = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal Server Error",
            "Ha ocurrido un error interno. Por favor, intente más tarde.",
            request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

---

## Parte 5: Actualizar Service para Usar Excepciones

### 5.1 Modificar TaskServiceImpl

```java
// En TaskServiceImpl, cambiar RuntimeException por ResourceNotFoundException

@Override
public TaskResponse getTaskById(Long id) {
    Task task = taskRepository.findById(id)
        // TODO: Usar excepción personalizada
        .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    return taskMapper.toResponse(task);
}

@Override
public TaskResponse updateTask(Long id, TaskRequest request) {
    Task existingTask = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

    // ... resto del código
}

@Override
public void deleteTask(Long id) {
    if (!taskRepository.existsById(id)) {
        throw new ResourceNotFoundException("Task", "id", id);
    }
    taskRepository.deleteById(id);
}

@Override
public TaskResponse completeTask(Long id) {
    Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

    // ... resto del código
}
```

---

## Parte 6: Probar

### 6.1 Levantar aplicación

```bash
docker compose up --build
```

### 6.2 Probar error 404

```bash
# Buscar tarea que no existe
curl -v http://localhost:8080/api/tasks/999
```

**Respuesta esperada:**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Task not found with id: '999'",
  "path": "/api/tasks/999",
  "timestamp": "2024-01-15T10:30:00"
}
```

### 6.3 Probar error de validación

```bash
# Título vacío
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "", "description": "Test"}'
```

**Respuesta esperada:**
```json
{
  "status": 400,
  "error": "Validation Error",
  "message": "Error de validación en los datos enviados",
  "path": "/api/tasks",
  "timestamp": "2024-01-15T10:30:00",
  "errors": {
    "title": "El título es requerido"
  }
}
```

### 6.4 Probar operación exitosa

```bash
# Crear tarea válida
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title": "Tarea válida", "description": "Funciona"}'
```

---

## ✅ Checklist de Verificación

- [ ] Paquete `exception` creado
- [ ] `ResourceNotFoundException` implementada
- [ ] `BadRequestException` implementada
- [ ] `ErrorResponse` DTO creado
- [ ] `GlobalExceptionHandler` con `@RestControllerAdvice`
- [ ] Service usa excepciones personalizadas
- [ ] HTTP 404 para recursos no encontrados
- [ ] HTTP 400 para errores de validación
- [ ] HTTP 500 no expone detalles internos

---

## 🎯 Reto Adicional

1. Crear una `ConflictException` para HTTP 409 (ej: tarea duplicada)
2. Agregar un campo `code` al ErrorResponse para identificar errores específicos
3. Implementar logging con SLF4J en lugar de `System.err`

---

## 📚 Recursos

- [Spring Exception Handling](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc)
- [RFC 7807 - Problem Details](https://datatracker.ietf.org/doc/html/rfc7807)
