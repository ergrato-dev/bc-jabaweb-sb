# Arquitectura Web y Protocolo HTTP

## 🎯 Objetivos de Aprendizaje

- Comprender el modelo cliente-servidor
- Entender el funcionamiento del protocolo HTTP
- Conocer los métodos HTTP y sus usos
- Interpretar códigos de estado HTTP
- Entender los headers y el body de las peticiones

---

## 1. Arquitectura Cliente-Servidor

### 1.1 El Modelo Básico

```
┌─────────────────┐                      ┌─────────────────┐
│     CLIENTE     │                      │     SERVIDOR    │
│                 │                      │                 │
│  ┌───────────┐  │    1. Request        │  ┌───────────┐  │
│  │  Browser  │  │  ─────────────────►  │  │    API    │  │
│  │  Mobile   │  │                      │  │  Backend  │  │
│  │  Desktop  │  │    2. Response       │  │           │  │
│  │           │  │  ◄─────────────────  │  │           │  │
│  └───────────┘  │                      │  └───────────┘  │
│                 │                      │        │        │
└─────────────────┘                      │        ▼        │
                                         │  ┌───────────┐  │
                                         │  │  Database │  │
                                         │  └───────────┘  │
                                         └─────────────────┘
```

### 1.2 Características del Modelo

| Cliente | Servidor |
|---------|----------|
| Inicia la comunicación | Espera peticiones |
| Envía requests | Procesa y responde |
| Renderiza respuestas | Accede a datos |
| Múltiples clientes | Generalmente uno (escalable) |

### 1.3 Tipos de Clientes

```
┌─────────────────────────────────────────────────────────┐
│                      CLIENTES                            │
├─────────────┬─────────────┬─────────────┬──────────────┤
│   Browser   │   Mobile    │   Desktop   │    CLI       │
│             │     App     │     App     │              │
├─────────────┼─────────────┼─────────────┼──────────────┤
│  Chrome     │   iOS App   │  Electron   │   curl       │
│  Firefox    │ Android App │    Java     │   HTTPie     │
│  Safari     │   Flutter   │   Python    │   Postman    │
└─────────────┴─────────────┴─────────────┴──────────────┘
```

---

## 2. Protocolo HTTP

### 2.1 ¿Qué es HTTP?

**HTTP** (HyperText Transfer Protocol) es el protocolo de comunicación para la web.

```
HTTP = Conjunto de reglas para intercambiar información
       entre cliente y servidor
```

**Características**:
- **Stateless**: Cada petición es independiente
- **Text-based**: Mensajes legibles
- **Request-Response**: Siempre hay pregunta y respuesta
- **Extensible**: Headers personalizables

### 2.2 HTTPS vs HTTP

```
HTTP:  Los datos viajan en texto plano (inseguro)
       http://example.com

HTTPS: Los datos viajan encriptados (seguro)
       https://example.com
       └── TLS/SSL encryption
```

> ⚠️ **SIEMPRE** usa HTTPS en producción

### 2.3 URL - Anatomía

```
https://api.example.com:8080/users/123?active=true#section1
└─┬──┘ └──────┬───────┘└─┬─┘└────┬───┘└────┬─────┘└───┬───┘
  │           │          │       │         │          │
Scheme      Host       Port    Path      Query     Fragment
(protocolo) (dominio)         (recurso)  (filtros) (ancla)
```

| Componente | Descripción | Ejemplo |
|------------|-------------|---------|
| **Scheme** | Protocolo | `https` |
| **Host** | Dominio o IP | `api.example.com` |
| **Port** | Puerto (opcional) | `8080` |
| **Path** | Ruta al recurso | `/users/123` |
| **Query** | Parámetros | `?active=true` |
| **Fragment** | Sección (cliente) | `#section1` |

---

## 3. Métodos HTTP

### 3.1 Métodos Principales (CRUD)

```
┌──────────┬───────────────┬─────────────────────────────────┐
│  Método  │   Operación   │           Descripción           │
├──────────┼───────────────┼─────────────────────────────────┤
│   GET    │     READ      │  Obtener recurso(s)             │
│   POST   │    CREATE     │  Crear nuevo recurso            │
│   PUT    │    UPDATE     │  Reemplazar recurso completo    │
│  PATCH   │    UPDATE     │  Modificar parcialmente         │
│  DELETE  │    DELETE     │  Eliminar recurso               │
└──────────┴───────────────┴─────────────────────────────────┘
```

### 3.2 Características de los Métodos

| Método | Safe | Idempotente | Body |
|--------|------|-------------|------|
| GET | ✅ Sí | ✅ Sí | ❌ No |
| POST | ❌ No | ❌ No | ✅ Sí |
| PUT | ❌ No | ✅ Sí | ✅ Sí |
| PATCH | ❌ No | ❌ No | ✅ Sí |
| DELETE | ❌ No | ✅ Sí | ❌ No |

**Definiciones**:
- **Safe (Seguro)**: No modifica recursos en el servidor
- **Idempotente**: Múltiples llamadas = mismo resultado

### 3.3 Ejemplos de Uso

```bash
# GET - Obtener todos los usuarios
GET /api/users

# GET - Obtener usuario específico
GET /api/users/123

# POST - Crear usuario (body con datos)
POST /api/users
Content-Type: application/json
{
  "name": "Juan",
  "email": "juan@example.com"
}

# PUT - Reemplazar usuario completo
PUT /api/users/123
Content-Type: application/json
{
  "name": "Juan Pérez",
  "email": "juan.perez@example.com"
}

# PATCH - Actualizar solo email
PATCH /api/users/123
Content-Type: application/json
{
  "email": "nuevo@example.com"
}

# DELETE - Eliminar usuario
DELETE /api/users/123
```

---

## 4. Estructura de Mensajes HTTP

### 4.1 Request (Petición)

```
┌─────────────────────────────────────────────────────────┐
│                    HTTP REQUEST                          │
├─────────────────────────────────────────────────────────┤
│  POST /api/users HTTP/1.1          ← Request Line       │
├─────────────────────────────────────────────────────────┤
│  Host: api.example.com             ← Headers            │
│  Content-Type: application/json                          │
│  Authorization: Bearer token123                          │
│  Accept: application/json                                │
├─────────────────────────────────────────────────────────┤
│                                    ← Línea vacía        │
├─────────────────────────────────────────────────────────┤
│  {                                 ← Body (opcional)    │
│    "name": "Juan",                                       │
│    "email": "juan@example.com"                          │
│  }                                                       │
└─────────────────────────────────────────────────────────┘
```

### 4.2 Response (Respuesta)

```
┌─────────────────────────────────────────────────────────┐
│                    HTTP RESPONSE                         │
├─────────────────────────────────────────────────────────┤
│  HTTP/1.1 201 Created              ← Status Line        │
├─────────────────────────────────────────────────────────┤
│  Content-Type: application/json    ← Headers            │
│  Location: /api/users/123                                │
│  Date: Tue, 17 Dec 2024 10:30:00 GMT                    │
├─────────────────────────────────────────────────────────┤
│                                    ← Línea vacía        │
├─────────────────────────────────────────────────────────┤
│  {                                 ← Body               │
│    "id": 123,                                            │
│    "name": "Juan",                                       │
│    "email": "juan@example.com"                          │
│  }                                                       │
└─────────────────────────────────────────────────────────┘
```

---

## 5. Códigos de Estado HTTP

### 5.1 Categorías

```
1xx → Informativo     (procesando...)
2xx → Éxito          (todo bien ✅)
3xx → Redirección    (ve a otro lugar)
4xx → Error cliente  (tu error ❌)
5xx → Error servidor (nuestro error 💥)
```

### 5.2 Códigos Más Comunes

#### 2xx - Éxito

| Código | Nombre | Uso |
|--------|--------|-----|
| **200** | OK | GET exitoso, respuesta con datos |
| **201** | Created | POST exitoso, recurso creado |
| **204** | No Content | DELETE exitoso, sin body |

#### 3xx - Redirección

| Código | Nombre | Uso |
|--------|--------|-----|
| **301** | Moved Permanently | URL cambió permanentemente |
| **302** | Found | Redirección temporal |
| **304** | Not Modified | Cache válida, no enviar datos |

#### 4xx - Error del Cliente

| Código | Nombre | Uso |
|--------|--------|-----|
| **400** | Bad Request | JSON mal formado, datos inválidos |
| **401** | Unauthorized | No autenticado |
| **403** | Forbidden | Autenticado pero sin permiso |
| **404** | Not Found | Recurso no existe |
| **405** | Method Not Allowed | Método no soportado |
| **409** | Conflict | Conflicto (ej: duplicado) |
| **422** | Unprocessable Entity | Validación fallida |

#### 5xx - Error del Servidor

| Código | Nombre | Uso |
|--------|--------|-----|
| **500** | Internal Server Error | Error genérico del servidor |
| **502** | Bad Gateway | Proxy recibió respuesta inválida |
| **503** | Service Unavailable | Servidor temporalmente caído |

### 5.3 Flujo de Decisión

```
¿Operación exitosa?
├── SÍ → 2xx
│   ├── ¿GET con datos? → 200 OK
│   ├── ¿POST creó recurso? → 201 Created
│   └── ¿DELETE sin body? → 204 No Content
│
└── NO → ¿Quién falló?
    ├── Cliente → 4xx
    │   ├── ¿No autenticado? → 401
    │   ├── ¿Sin permiso? → 403
    │   ├── ¿No existe? → 404
    │   └── ¿Datos inválidos? → 400/422
    │
    └── Servidor → 5xx
        └── Error interno → 500
```

---

## 6. Headers HTTP

### 6.1 Headers Comunes en Requests

| Header | Descripción | Ejemplo |
|--------|-------------|---------|
| `Content-Type` | Formato del body | `application/json` |
| `Accept` | Formato esperado | `application/json` |
| `Authorization` | Credenciales | `Bearer token123` |
| `User-Agent` | Info del cliente | `Mozilla/5.0...` |

### 6.2 Headers Comunes en Responses

| Header | Descripción | Ejemplo |
|--------|-------------|---------|
| `Content-Type` | Formato del body | `application/json` |
| `Content-Length` | Tamaño en bytes | `1234` |
| `Location` | URL del recurso creado | `/api/users/123` |
| `Cache-Control` | Política de cache | `max-age=3600` |

### 6.3 Content-Type Comunes

```
application/json        → JSON (APIs REST)
application/xml         → XML
text/html              → HTML
text/plain             → Texto plano
multipart/form-data    → Archivos
application/x-www-form-urlencoded → Formularios
```

---

## 7. Herramientas para HTTP

### 7.1 curl (Terminal)

```bash
# GET
curl https://api.example.com/users

# GET con headers
curl -H "Accept: application/json" https://api.example.com/users

# POST con JSON
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"name":"Juan"}' \
  https://api.example.com/users

# Ver headers de respuesta
curl -i https://api.example.com/users

# Solo headers
curl -I https://api.example.com/users
```

### 7.2 HTTPie (Terminal, más amigable)

```bash
# Instalar
pip install httpie

# GET
http https://api.example.com/users

# POST
http POST https://api.example.com/users name=Juan email=juan@example.com

# Con autenticación
http -A bearer -a token123 https://api.example.com/users
```

### 7.3 Postman / Insomnia (GUI)

- Interfaz gráfica para probar APIs
- Colecciones de requests
- Variables de entorno
- Tests automatizados

---

## 8. JSON - Formato de Datos

### 8.1 Sintaxis Básica

```json
{
  "string": "texto",
  "number": 42,
  "decimal": 3.14,
  "boolean": true,
  "null": null,
  "array": [1, 2, 3],
  "object": {
    "nested": "value"
  }
}
```

### 8.2 Ejemplo de API Response

```json
{
  "id": 123,
  "name": "Juan Pérez",
  "email": "juan@example.com",
  "active": true,
  "roles": ["user", "admin"],
  "profile": {
    "avatar": "https://example.com/avatar.png",
    "bio": "Developer"
  },
  "createdAt": "2024-12-17T10:30:00Z"
}
```

### 8.3 JSON vs XML

```
JSON (más usado en APIs modernas):
{
  "user": {
    "name": "Juan",
    "age": 30
  }
}

XML (legacy):
<user>
  <name>Juan</name>
  <age>30</age>
</user>
```

---

## 📚 Recursos Adicionales

- [MDN - HTTP](https://developer.mozilla.org/es/docs/Web/HTTP)
- [HTTP Status Codes](https://httpstatuses.com/)
- [JSON.org](https://www.json.org/)

---

## ✅ Checklist de la Sección

- [ ] Entiendo el modelo cliente-servidor
- [ ] Conozco los métodos HTTP principales (GET, POST, PUT, DELETE)
- [ ] Sé interpretar códigos de estado HTTP
- [ ] Puedo identificar las partes de una URL
- [ ] Entiendo la estructura de request y response
- [ ] Sé qué es JSON y su sintaxis básica
