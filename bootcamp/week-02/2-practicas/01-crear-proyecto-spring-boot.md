# Práctica 01: Crear Proyecto Spring Boot

## 🎯 Objetivo

Crear tu primer proyecto Spring Boot usando Spring Initializr y ejecutarlo en Docker.

**Duración estimada**: 45 minutos

---

## 📋 Requisitos Previos

- Docker Desktop instalado y funcionando
- Conexión a Internet (para descargar dependencias)
- Editor de código (VS Code recomendado)

---

## Parte 1: Generar Proyecto con Spring Initializr

### Paso 1.1: Acceder a Spring Initializr

1. Abre tu navegador
2. Ve a **https://start.spring.io/**

### Paso 1.2: Configurar el Proyecto

Selecciona las siguientes opciones:

| Campo | Valor |
|-------|-------|
| **Project** | Maven |
| **Language** | Java |
| **Spring Boot** | 3.2.x (última versión estable) |
| **Group** | `com.bootcamp` |
| **Artifact** | `demo` |
| **Name** | `demo` |
| **Description** | `Mi primer proyecto Spring Boot` |
| **Package name** | `com.bootcamp.demo` |
| **Packaging** | Jar |
| **Java** | 21 |

### Paso 1.3: Agregar Dependencias

Haz clic en **"Add Dependencies"** y busca:

1. **Spring Web** - Para crear APIs REST
2. **Spring Boot DevTools** - Para hot-reload en desarrollo

### Paso 1.4: Generar y Descargar

1. Haz clic en **"Generate"**
2. Se descargará un archivo `demo.zip`
3. Extrae el contenido en tu carpeta de trabajo

---

## Parte 2: Explorar la Estructura

### Paso 2.1: Abrir en VS Code

```bash
cd demo
code .
```

### Paso 2.2: Verificar Estructura

Deberías ver:

```
demo/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bootcamp/demo/
│   │   │       └── DemoApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── .gitignore
```

### Paso 2.3: Examinar DemoApplication.java

```java
package com.bootcamp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

> 💡 **Nota**: `@SpringBootApplication` habilita la autoconfiguración de Spring Boot.

---

## Parte 3: Ejecutar con Docker

### Paso 3.1: Crear docker-compose.yml

En la raíz del proyecto (`demo/`), crea el archivo `docker-compose.yml`:

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
    command: ./mvnw spring-boot:run
    tty: true

volumes:
  maven-cache:
```

### Paso 3.2: Dar Permisos al Wrapper (Linux/Mac)

```bash
chmod +x mvnw
```

### Paso 3.3: Iniciar el Contenedor

```bash
docker compose up
```

> ⏳ La primera vez tardará varios minutos descargando dependencias Maven.

### Paso 3.4: Verificar que Funciona

Busca en los logs:

```
Started DemoApplication in X.XXX seconds
```

---

## Parte 4: Tu Primer Endpoint

### Paso 4.1: Crear HelloController

Crea el archivo `src/main/java/com/bootcamp/demo/controller/HelloController.java`:

```java
package com.bootcamp.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "¡Hola desde Spring Boot en Docker!";
    }
}
```

### Paso 4.2: Probar el Endpoint

Con DevTools, el servidor se reinicia automáticamente. Prueba:

```bash
# En otra terminal
curl http://localhost:8080/hello
```

**Respuesta esperada**:
```
¡Hola desde Spring Boot en Docker!
```

### Paso 4.3: Probar en Navegador

Abre: http://localhost:8080/hello

---

## Parte 5: Agregar Más Endpoints

### Ejercicio 5.1: Endpoint de Estado

Crea un endpoint que devuelva el estado de la aplicación:

```java
// TODO: Crear método que responda a GET /status
// Debe retornar: "OK"
```

### Ejercicio 5.2: Endpoint con Nombre

Crea un endpoint que salude con un nombre:

```java
// TODO: Crear método que responda a GET /hello/{nombre}
// GET /hello/Juan debe retornar: "¡Hola, Juan!"
// Pista: Usa @PathVariable
```

### Ejercicio 5.3: Endpoint con Parámetro Opcional

```java
// TODO: Crear método que responda a GET /greet?name=xxx
// GET /greet?name=María debe retornar: "Saludos, María"
// GET /greet (sin parámetro) debe retornar: "Saludos, visitante"
// Pista: Usa @RequestParam con defaultValue
```

---

## ✅ Checklist de Verificación

- [ ] Proyecto generado desde Spring Initializr
- [ ] Estructura de carpetas correcta
- [ ] docker-compose.yml creado
- [ ] Aplicación iniciada en Docker
- [ ] Endpoint `/hello` responde correctamente
- [ ] Endpoint `/status` implementado
- [ ] Endpoint `/hello/{nombre}` implementado
- [ ] Endpoint `/greet` con parámetro opcional implementado

---

## 🔧 Troubleshooting

### Error: "Permission denied" en mvnw

```bash
chmod +x mvnw
```

### Error: Puerto 8080 en uso

```bash
# Verificar qué usa el puerto
lsof -i :8080

# O cambiar el puerto en docker-compose.yml
ports:
  - "8081:8080"
```

### Error: "Unable to access jarfile"

Asegúrate de que el volumen esté montado correctamente y que estés en el directorio correcto.

### Los cambios no se reflejan

DevTools debería recargar automáticamente. Si no funciona:
1. Guarda el archivo
2. Espera unos segundos
3. Si persiste, reinicia: `docker compose restart`

---

## 📚 Recursos

- [Spring Initializr](https://start.spring.io/)
- [Documentación Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)

---

## ➡️ Siguiente

Continúa con la [Práctica 02: Endpoints y Docker](02-endpoints-docker.md)
