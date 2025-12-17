# 🛠️ Práctica 01: Configurar SpringDoc OpenAPI

## 🎯 Objetivo

Configurar SpringDoc OpenAPI en un proyecto Spring Boot existente para generar documentación automática.

---

## 📋 Requisitos Previos

- Proyecto Spring Boot 3.x funcionando
- Maven configurado
- Conocimientos básicos de Spring Boot

---

## 📝 Ejercicio

### Paso 1: Agregar Dependencia

Edita `pom.xml` y agrega la dependencia de SpringDoc:

```xml
<!-- SpringDoc OpenAPI (Swagger) -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

### Paso 2: Verificar Auto-configuración

Sin ninguna configuración adicional, inicia la aplicación:

```bash
./mvnw spring-boot:run
```

Accede a:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

### Paso 3: Crear Configuración OpenAPI

Crea el archivo `src/main/java/com/bootcamp/config/OpenApiConfig.java`:

```java
package com.bootcamp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    // TODO 1: Crear Bean que retorne OpenAPI configurado
    // Debe incluir:
    // - Info con title, version, description, contact, license
    // - Servers con URLs de desarrollo y producción

}
```

### Paso 4: Configurar Propiedades

Agrega en `application.properties`:

```properties
# SpringDoc Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=alpha
springdoc.swagger-ui.tagsSorter=alpha
springdoc.swagger-ui.tryItOutEnabled=true
```

### Paso 5: Verificar Resultado

1. Reinicia la aplicación
2. Accede a Swagger UI
3. Verifica que aparezca:
   - Título de la API
   - Versión
   - Descripción
   - Servidores disponibles

---

## ✅ Criterios de Éxito

- [ ] Swagger UI accesible en `/swagger-ui.html`
- [ ] Info de la API visible (título, versión, descripción)
- [ ] Contact y License configurados
- [ ] Al menos 2 servers configurados
- [ ] Endpoints ordenados alfabéticamente

---

## 💡 Pistas

<details>
<summary>Ver pista para TODO 1</summary>

```java
@Bean
public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("...")
            .version("...")
            .description("...")
            .contact(new Contact()
                .name("...")
                .email("..."))
            .license(new License()
                .name("MIT")
                .url("...")))
        .servers(List.of(
            new Server().url("http://localhost:8080").description("Dev")
        ));
}
```

</details>

---

## 🔗 Siguiente

Continúa con [02-documentar-endpoints.md](02-documentar-endpoints.md)
