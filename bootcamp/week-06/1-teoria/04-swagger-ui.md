# 🖥️ Swagger UI - Interfaz Interactiva

## 🎯 Objetivos de Aprendizaje

- Navegar y usar Swagger UI efectivamente
- Probar endpoints directamente desde el navegador
- Autenticarse con JWT en Swagger UI
- Exportar especificaciones OpenAPI

---

## 📊 Diagrama: Swagger UI

![Swagger UI Interface](../0-assets/04-swagger-ui.svg)

---

## 🌐 Acceso a Swagger UI

### URLs por Defecto

| Recurso | URL |
|---------|-----|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| API Docs JSON | http://localhost:8080/v3/api-docs |
| API Docs YAML | http://localhost:8080/v3/api-docs.yaml |

### URLs Personalizadas

```properties
# application.properties
springdoc.swagger-ui.path=/docs
springdoc.api-docs.path=/api-docs
```

Con esta configuración:
- Swagger UI: http://localhost:8080/docs
- API Docs: http://localhost:8080/api-docs

---

## 🧭 Navegación en Swagger UI

### Estructura de la Interfaz

```
┌─────────────────────────────────────────────────────┐
│  📌 Header                                          │
│  - Título de la API                                 │
│  - Versión                                          │
│  - Botón "Authorize" (si hay seguridad)            │
├─────────────────────────────────────────────────────┤
│  🏷️ Tags (Grupos)                                   │
│  ├─ tasks                                           │
│  │   ├─ GET    /api/v1/tasks                       │
│  │   ├─ POST   /api/v1/tasks                       │
│  │   ├─ GET    /api/v1/tasks/{id}                  │
│  │   ├─ PUT    /api/v1/tasks/{id}                  │
│  │   └─ DELETE /api/v1/tasks/{id}                  │
│  └─ users                                           │
│      ├─ GET    /api/v1/users                       │
│      └─ ...                                         │
├─────────────────────────────────────────────────────┤
│  📋 Schemas                                         │
│  - TaskDTO                                          │
│  - CreateTaskRequest                                │
│  - ErrorResponse                                    │
└─────────────────────────────────────────────────────┘
```

### Códigos de Color por Método HTTP

| Método | Color | Uso |
|--------|-------|-----|
| GET | 🟢 Verde | Obtener recursos |
| POST | 🟡 Amarillo | Crear recursos |
| PUT | 🔵 Azul | Actualizar recursos |
| DELETE | 🔴 Rojo | Eliminar recursos |
| PATCH | 🟣 Púrpura | Actualización parcial |

---

## 🧪 Probar Endpoints

### Paso 1: Expandir Endpoint

Haz clic en cualquier endpoint para expandirlo y ver:

- **Summary**: Descripción corta
- **Description**: Descripción detallada
- **Parameters**: Parámetros requeridos
- **Request body**: Cuerpo de la petición (si aplica)
- **Responses**: Respuestas posibles

### Paso 2: Try it out

1. Clic en el botón **"Try it out"**
2. Completa los parámetros requeridos
3. Modifica el request body si es necesario
4. Clic en **"Execute"**

### Paso 3: Ver Respuesta

```
┌─────────────────────────────────────────────────────┐
│  Server response                                    │
├─────────────────────────────────────────────────────┤
│  Code: 200                                          │
│  Response body:                                     │
│  {                                                  │
│    "id": "550e8400-e29b-41d4-a716-446655440000",   │
│    "title": "Mi tarea",                            │
│    "completed": false                               │
│  }                                                  │
├─────────────────────────────────────────────────────┤
│  Response headers:                                  │
│  content-type: application/json                     │
│  date: Mon, 15 Jan 2024 10:30:00 GMT               │
├─────────────────────────────────────────────────────┤
│  Curl:                                              │
│  curl -X 'GET' \                                    │
│    'http://localhost:8080/api/v1/tasks' \          │
│    -H 'accept: application/json'                    │
└─────────────────────────────────────────────────────┘
```

---

## 🔐 Autenticación en Swagger UI

### Configurar JWT

1. Clic en el botón **"Authorize"** (🔒)
2. En el campo `bearerAuth`:
   - Ingresa solo el token (sin "Bearer ")
   - Ejemplo: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`
3. Clic en **"Authorize"**
4. Clic en **"Close"**

### Estado de Autorización

- 🔓 **Sin candado**: Endpoint público
- 🔒 **Con candado**: Requiere autenticación
- ✅ **Candado cerrado**: Autenticado

### Ejemplo de Flujo

```
1. POST /api/auth/login
   Body: {"email": "user@test.com", "password": "123456"}
   Response: {"token": "eyJhbG..."}

2. Clic en "Authorize"
   Ingresar: eyJhbG...

3. Ahora puedes acceder a endpoints protegidos
```

---

## 📤 Exportar Especificación

### Desde el Navegador

```bash
# JSON
curl http://localhost:8080/v3/api-docs -o openapi.json

# YAML
curl http://localhost:8080/v3/api-docs.yaml -o openapi.yaml
```

### Desde Swagger UI

1. En la parte superior, busca el enlace `/v3/api-docs`
2. Clic derecho → "Guardar como..."

### Usos de la Especificación Exportada

| Uso | Herramienta |
|-----|-------------|
| Importar en Postman | Postman |
| Generar cliente TypeScript | openapi-generator |
| Generar cliente Java | openapi-generator |
| Documentación estática | Redoc |
| Mock server | Prism |

---

## ⚙️ Personalización de Swagger UI

### Opciones de Visualización

```properties
# application.properties

# Ordenar operaciones alfabéticamente
springdoc.swagger-ui.operationsSorter=alpha

# Ordenar tags alfabéticamente
springdoc.swagger-ui.tagsSorter=alpha

# Expandir/colapsar por defecto
# none: colapsado, list: operaciones, full: todo
springdoc.swagger-ui.docExpansion=list

# Mostrar duración de requests
springdoc.swagger-ui.displayRequestDuration=true

# Habilitar filtro de búsqueda
springdoc.swagger-ui.filter=true

# Habilitar "Try it out" por defecto
springdoc.swagger-ui.tryItOutEnabled=true

# Persistir autorización
springdoc.swagger-ui.persistAuthorization=true
```

### Mostrar Solo Algunos Endpoints

```properties
# Solo mostrar endpoints que empiecen con /api/v1
springdoc.pathsToMatch=/api/v1/**

# Excluir endpoints de actuator
springdoc.pathsToExclude=/actuator/**
```

### Agrupar por Paquete

```properties
# Solo escanear paquete específico
springdoc.packagesToScan=com.bootcamp.controller
```

---

## 🔍 Schemas (Modelos)

### Ver Schemas

En la parte inferior de Swagger UI, encuentra la sección **"Schemas"**:

```
Schemas
├── TaskDTO
│   ├── id: string (uuid)
│   ├── title: string
│   ├── description: string
│   └── completed: boolean
├── CreateTaskRequest
│   ├── title*: string
│   └── description: string
└── ErrorResponse
    ├── timestamp: string
    ├── status: integer
    └── message: string
```

### Expandir Schemas en Responses

```properties
# Mostrar modelo por defecto (en lugar de ejemplo)
springdoc.swagger-ui.defaultModelRendering=model
```

---

## 🛠️ Tips y Trucos

### 1. Copiar Curl Command

Después de ejecutar un request, Swagger muestra el comando curl equivalente. Útil para:
- Compartir con compañeros
- Guardar en colecciones
- Debugging

### 2. Ver Request Headers

En la respuesta, expande "Response headers" para ver todos los headers retornados.

### 3. Cambiar Servidor

Si configuraste múltiples servers, usa el dropdown en la parte superior para cambiar entre ellos.

### 4. Descargar Response

Para responses grandes, copia el JSON directamente o usa el comando curl generado.

### 5. Validar JSON

Swagger UI valida automáticamente el JSON del request body antes de enviar.

---

## 🚨 Problemas Comunes

### Swagger UI no carga

```java
// Verificar que el endpoint esté permitido en Security
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
        .requestMatchers(
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/webjars/**"
        ).permitAll()
        // ...
    );
    return http.build();
}
```

### CORS bloqueando Swagger

```java
@Bean
public CorsFilter corsFilter() {
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOriginPattern("*");
    config.addAllowedMethod("*");
    config.addAllowedHeader("*");
    // ...
}
```

### Token JWT expira rápido

1. Genera un nuevo token
2. Clic en "Authorize"
3. Clic en "Logout" primero
4. Ingresa el nuevo token

---

## 📝 Resumen

| Acción | Cómo hacerlo |
|--------|--------------|
| Probar endpoint | Expandir → Try it out → Execute |
| Autenticarse | Authorize → Ingresar token |
| Ver schemas | Scroll abajo → Schemas |
| Exportar spec | GET /v3/api-docs |
| Copiar curl | Execute → Copiar de "Curl" |

---

## 🔗 Referencias

- [Swagger UI Documentation](https://swagger.io/tools/swagger-ui/)
- [SpringDoc Swagger UI Config](https://springdoc.org/#swagger-ui-properties)

---

> 💡 **Próximo paso**: Aprende a configurar CORS en [05-cors-spring-boot.md](05-cors-spring-boot.md)
