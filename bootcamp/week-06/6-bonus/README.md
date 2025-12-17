# 🎁 Bonus Week 06: CORS Basics

## Objetivo

Aprender a consumir tu API REST desde un navegador web usando JavaScript vanilla y entender cómo funciona CORS.

**Duración estimada**: 30-45 minutos

> 💡 **Este es contenido bonus** - Diseñado para estudiantes que deseen profundizar en la integración frontend-backend. No es requisito para completar el bootcamp.

---

## ¿Qué es CORS?

**CORS** (Cross-Origin Resource Sharing) es un mecanismo de seguridad del navegador que bloquea peticiones a dominios diferentes al de la página actual.

```
Tu página:     http://localhost:5500 (Live Server)
Tu API:        http://localhost:8080 (Spring Boot)
                    ↓
          ¡CORS bloquea la petición!
```

### ¿Por qué existe?

Protege a los usuarios de ataques donde una página maliciosa intenta acceder a APIs con las credenciales del usuario.

---

## Parte 1: Configurar CORS en Spring Boot

### 1.1 Opción A: @CrossOrigin en Controller

```java
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5500") // Permitir este origen
public class ProductController {
    // ...
}
```

### 1.2 Opción B: Configuración Global (Recomendada)

Crea una clase de configuración:

```java
package com.bootcamp.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos (en desarrollo, permitir localhost)
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:5500",  // Live Server
            "http://localhost:5173",  // Vite dev server
            "http://localhost:3000",  // React dev server
            "http://127.0.0.1:5500"
        ));

        // Métodos HTTP permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Headers permitidos
        config.setAllowedHeaders(Arrays.asList("*"));

        // Permitir envío de cookies/credenciales
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);

        return new CorsFilter(source);
    }
}
```

---

## Parte 2: Cliente JavaScript

### 2.1 Crear index.html

Crea el archivo `index.html` en esta carpeta (`6-bonus/`):

```html
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>API Client - CORS Demo</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background: #1e1e1e;
            color: #d4d4d4;
        }
        h1 { color: #4fc3f7; }
        button {
            background: #0d47a1;
            color: white;
            border: none;
            padding: 10px 20px;
            margin: 5px;
            cursor: pointer;
            border-radius: 4px;
        }
        button:hover { background: #1565c0; }
        #output {
            background: #2d2d2d;
            padding: 20px;
            border-radius: 8px;
            margin-top: 20px;
            white-space: pre-wrap;
            font-family: 'Consolas', monospace;
        }
        .success { color: #4caf50; }
        .error { color: #f44336; }
        input {
            padding: 10px;
            margin: 5px;
            border: 1px solid #444;
            border-radius: 4px;
            background: #2d2d2d;
            color: #d4d4d4;
        }
    </style>
</head>
<body>
    <h1>🌐 API Client - CORS Demo</h1>

    <div>
        <h3>Listar Productos</h3>
        <button onclick="getProducts()">GET /api/products</button>
    </div>

    <div>
        <h3>Crear Producto</h3>
        <input type="text" id="productName" placeholder="Nombre">
        <input type="number" id="productPrice" placeholder="Precio">
        <button onclick="createProduct()">POST /api/products</button>
    </div>

    <div id="output">
        // Los resultados aparecerán aquí...
    </div>

    <script src="api-client.js"></script>
</body>
</html>
```

### 2.2 Crear api-client.js

Crea el archivo `api-client.js` en esta carpeta:

```javascript
// Configuración de la API
const API_BASE_URL = 'http://localhost:8080/api';

// Elemento donde mostrar resultados
const output = document.getElementById('output');

// Helper para mostrar resultados
function showResult(data, isError = false) {
    output.className = isError ? 'error' : 'success';
    output.textContent = JSON.stringify(data, null, 2);
}

// GET - Listar productos
async function getProducts() {
    try {
        output.textContent = 'Cargando...';

        const response = await fetch(`${API_BASE_URL}/products`);

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }

        const data = await response.json();
        showResult(data);

    } catch (error) {
        showResult({ error: error.message }, true);
        console.error('Error:', error);
    }
}

// POST - Crear producto
async function createProduct() {
    const name = document.getElementById('productName').value;
    const price = document.getElementById('productPrice').value;

    if (!name || !price) {
        showResult({ error: 'Nombre y precio son requeridos' }, true);
        return;
    }

    try {
        output.textContent = 'Creando producto...';

        const response = await fetch(`${API_BASE_URL}/products`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: name,
                price: parseFloat(price)
            })
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }

        const data = await response.json();
        showResult(data);

        // Limpiar inputs
        document.getElementById('productName').value = '';
        document.getElementById('productPrice').value = '';

    } catch (error) {
        showResult({ error: error.message }, true);
        console.error('Error:', error);
    }
}

// Log inicial
console.log('API Client cargado. URL base:', API_BASE_URL);
```

---

## Parte 3: Ejecutar y Probar

### 3.1 Iniciar tu API

```bash
cd ../3-proyecto
docker compose up
```

Verifica que funciona: http://localhost:8080/api/products

### 3.2 Iniciar el cliente HTML

**Opción A: VS Code Live Server**
1. Instala la extensión "Live Server"
2. Click derecho en `index.html` → "Open with Live Server"
3. Se abrirá en http://localhost:5500

**Opción B: Python HTTP Server**
```bash
python -m http.server 5500
```

### 3.3 Probar

1. Abre http://localhost:5500
2. Click en "GET /api/products"
3. Deberías ver los productos en formato JSON

---

## Troubleshooting

### Error: "CORS policy blocked"

```
Access to fetch at 'http://localhost:8080/api/products' from origin
'http://localhost:5500' has been blocked by CORS policy
```

**Solución**: Verifica que:
1. `CorsConfig.java` está creado y en el paquete correcto
2. El origen en `setAllowedOrigins` coincide exactamente con tu URL
3. Reinicia la aplicación Spring Boot

### Error: "Failed to fetch"

**Posibles causas**:
1. API no está corriendo → `docker compose up`
2. Puerto incorrecto → Verifica `API_BASE_URL` en `api-client.js`
3. Firewall bloqueando → Verifica configuración de red

---

## ✅ Checklist

- [ ] CORS configurado en Spring Boot
- [ ] `index.html` creado
- [ ] `api-client.js` creado
- [ ] GET funciona desde el navegador
- [ ] POST funciona desde el navegador
- [ ] Entiendo qué es CORS y por qué existe

---

## 📚 Recursos

- [MDN: CORS](https://developer.mozilla.org/es/docs/Web/HTTP/CORS)
- [Spring: CORS Support](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html#mvc-cors)
- [Fetch API](https://developer.mozilla.org/es/docs/Web/API/Fetch_API)

---

## ➡️ Siguiente

Si tienes conocimientos de React, continúa con el bonus de la **Semana 07** para crear formularios de Login y Registro.
