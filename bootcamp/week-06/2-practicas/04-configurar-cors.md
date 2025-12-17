# 🛠️ Práctica 04: Configurar CORS

## 🎯 Objetivo

Configurar CORS en Spring Boot para permitir consumo de la API desde aplicaciones frontend.

---

## 📋 Requisitos Previos

- API REST funcionando
- Conocimiento de headers HTTP

---

## 📝 Ejercicio

### Paso 1: Configuración Global con WebMvcConfigurer

Crea `src/main/java/com/bootcamp/config/CorsConfig.java`:

```java
package com.bootcamp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // TODO 1: Implementar addCorsMappings
    // - Permitir /api/**
    // - Orígenes: http://localhost:3000, http://localhost:5173
    // - Métodos: GET, POST, PUT, DELETE, OPTIONS
    // - Headers: todos (*)
    // - Credentials: true
    // - Max age: 3600

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO: Implementar configuración
    }
}
```

### Paso 2: Probar CORS con curl

```bash
# Preflight request
curl -X OPTIONS http://localhost:8080/api/v1/tasks \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: Content-Type" \
  -v 2>&1 | grep -i "access-control"
```

Debes ver:
```
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
Access-Control-Allow-Headers: *
Access-Control-Allow-Credentials: true
Access-Control-Max-Age: 3600
```

### Paso 3: Crear HTML de Prueba

Crea un archivo `test-cors.html` en tu escritorio:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Test CORS</title>
</head>
<body>
    <h1>Test CORS</h1>
    <button onclick="testGet()">Test GET</button>
    <button onclick="testPost()">Test POST</button>
    <pre id="result"></pre>

    <script>
        const API_URL = 'http://localhost:8080/api/v1/tasks';

        async function testGet() {
            try {
                const res = await fetch(API_URL);
                const data = await res.json();
                document.getElementById('result').textContent =
                    JSON.stringify(data, null, 2);
            } catch (err) {
                document.getElementById('result').textContent =
                    'CORS Error: ' + err.message;
            }
        }

        async function testPost() {
            try {
                const res = await fetch(API_URL, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        title: 'Test CORS',
                        userId: '550e8400-e29b-41d4-a716-446655440000'
                    })
                });
                const data = await res.json();
                document.getElementById('result').textContent =
                    JSON.stringify(data, null, 2);
            } catch (err) {
                document.getElementById('result').textContent =
                    'CORS Error: ' + err.message;
            }
        }
    </script>
</body>
</html>
```

Abre con un servidor local:
```bash
# Python
python -m http.server 3000

# Node.js
npx serve -p 3000
```

### Paso 4: Configuración por Ambiente

Modifica la configuración para usar propiedades:

```properties
# application-dev.properties
cors.allowed-origins=http://localhost:3000,http://localhost:5173

# application-prod.properties
cors.allowed-origins=https://myapp.com
```

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:http://localhost:3000}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(allowedOrigins)
            // ... resto de configuración
    }
}
```

### Paso 5: Permitir Swagger UI (opcional)

Si Swagger UI está en un origen diferente:

```java
@Override
public void addCorsMappings(CorsRegistry registry) {
    // API endpoints
    registry.addMapping("/api/**")
        .allowedOrigins(allowedOrigins)
        .allowedMethods("*")
        .allowedHeaders("*")
        .allowCredentials(true);

    // Swagger UI
    registry.addMapping("/swagger-ui/**")
        .allowedOrigins("*")
        .allowedMethods("GET");

    registry.addMapping("/v3/api-docs/**")
        .allowedOrigins("*")
        .allowedMethods("GET");
}
```

---

## ✅ Criterios de Éxito

- [ ] CORS configurado globalmente
- [ ] Preflight requests funcionan (OPTIONS)
- [ ] GET requests funcionan desde otro origen
- [ ] POST requests funcionan desde otro origen
- [ ] Headers CORS visibles en respuestas
- [ ] Configuración diferente por ambiente

---

## 💡 Pistas

<details>
<summary>Ver solución completa</summary>

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:http://localhost:3000}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(allowedOrigins)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            .allowedHeaders("*")
            .exposedHeaders("Authorization", "X-Total-Count")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
```

</details>

---

## 🔗 Siguiente

Continúa con [05-proyecto-integrador.md](05-proyecto-integrador.md)
