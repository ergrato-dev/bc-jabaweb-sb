# Principios REST y Diseño de APIs RESTful

## 🎯 Objetivos de Aprendizaje

- Comprender qué es REST y su origen
- Conocer los 6 principios/restricciones de REST
- Diseñar URIs siguiendo convenciones RESTful
- Aplicar buenas prácticas en diseño de APIs

---

## 1. ¿Qué es REST?

### 1.1 Definición

**REST** (Representational State Transfer) es un **estilo arquitectónico** para diseñar sistemas distribuidos, propuesto por Roy Fielding en su tesis doctoral (2000).

> REST no es un protocolo ni un estándar, es un conjunto de **restricciones arquitectónicas**.

### 1.2 REST vs RESTful

| Término | Significado |
|---------|-------------|
| **REST** | El estilo arquitectónico (teoría) |
| **RESTful** | Una API que sigue los principios REST (implementación) |
| **API REST** | Sinónimo de API RESTful |

### 1.3 ¿Por qué REST?

- ✅ **Simple**: Usa HTTP estándar
- ✅ **Escalable**: Sin estado = fácil de escalar horizontalmente
- ✅ **Flexible**: Múltiples formatos (JSON, XML, etc.)
- ✅ **Desacoplado**: Cliente y servidor independientes
- ✅ **Cacheable**: Mejora rendimiento
- ✅ **Universal**: Cualquier lenguaje/plataforma puede consumirla

---

## 2. Los 6 Principios de REST

### 2.1 Cliente-Servidor (Client-Server)

Separación de responsabilidades entre cliente y servidor.

```
CLIENTE                          SERVIDOR
┌─────────────────┐              ┌─────────────────┐
│ - UI/UX         │              │ - Lógica        │
│ - Presentación  │  ◄────────►  │ - Datos         │
│ - Interacción   │              │ - Seguridad     │
└─────────────────┘              └─────────────────┘
```

**Beneficios**:
- El cliente puede evolucionar sin afectar al servidor
- El servidor puede escalar independientemente
- Diferentes clientes (web, mobile, CLI) pueden usar la misma API

### 2.2 Sin Estado (Stateless)

Cada petición debe contener **toda la información necesaria** para ser procesada. El servidor no almacena contexto entre peticiones.

```
❌ CON ESTADO (mal):
Request 1: POST /login → Servidor guarda sesión
Request 2: GET /profile → Servidor busca sesión guardada

✅ SIN ESTADO (bien):
Request 1: POST /login → Devuelve token
Request 2: GET /profile + Authorization: Bearer <token>
           └── Cada request es auto-contenida
```

**Beneficios**:
- Escalabilidad: cualquier servidor puede atender cualquier petición
- Fiabilidad: fallo de un servidor no pierde estado
- Simplicidad: no hay que sincronizar sesiones

### 2.3 Cacheable

Las respuestas deben indicar si pueden ser cacheadas.

```http
# Respuesta cacheable
HTTP/1.1 200 OK
Cache-Control: max-age=3600
ETag: "abc123"

# Respuesta no cacheable
HTTP/1.1 200 OK
Cache-Control: no-store
```

**Beneficios**:
- Reduce carga del servidor
- Mejora tiempos de respuesta
- Reduce uso de red

### 2.4 Interfaz Uniforme (Uniform Interface)

La **restricción más importante**. Define cómo cliente y servidor se comunican.

#### 4 Sub-restricciones:

**1. Identificación de recursos (URIs)**
```
Cada recurso tiene una URI única:
/users/123        → Usuario con ID 123
/products/456     → Producto con ID 456
/orders/789/items → Items del pedido 789
```

**2. Manipulación mediante representaciones**
```
El cliente manipula recursos a través de representaciones (JSON, XML):

GET /users/123 → Obtiene representación JSON del usuario
PUT /users/123 + JSON → Actualiza usuario con la representación enviada
```

**3. Mensajes auto-descriptivos**
```http
POST /api/users HTTP/1.1
Content-Type: application/json    ← Indica formato del body
Accept: application/json          ← Indica formato esperado
Authorization: Bearer token123    ← Autenticación

{"name": "Juan"}                  ← Datos
```

**4. HATEOAS (Hypermedia as the Engine of Application State)**
```json
{
  "id": 123,
  "name": "Juan",
  "links": [
    {"rel": "self", "href": "/users/123"},
    {"rel": "orders", "href": "/users/123/orders"},
    {"rel": "update", "href": "/users/123", "method": "PUT"},
    {"rel": "delete", "href": "/users/123", "method": "DELETE"}
  ]
}
```

> 💡 HATEOAS es el nivel más avanzado de REST. Muchas APIs no lo implementan completamente.

### 2.5 Sistema en Capas (Layered System)

El cliente no sabe si está conectado directamente al servidor o a un intermediario.

```
Cliente → Load Balancer → API Gateway → Servidor → Base de Datos
          └── Cache ──┘   └── Auth ──┘
```

**Beneficios**:
- Seguridad: capas de autenticación/autorización
- Escalabilidad: load balancers, caches
- Flexibilidad: cambiar componentes sin afectar al cliente

### 2.6 Código Bajo Demanda (Code on Demand) - Opcional

El servidor puede enviar código ejecutable al cliente (JavaScript, applets).

```html
<!-- El servidor envía código que el cliente ejecuta -->
<script src="https://api.example.com/widget.js"></script>
```

> ⚠️ Esta restricción es **opcional** y raramente se usa en APIs REST modernas.

---

## 3. Diseño de URIs RESTful

### 3.1 Reglas Fundamentales

| Regla | ✅ Correcto | ❌ Incorrecto |
|-------|-------------|---------------|
| Usar sustantivos, no verbos | `/users` | `/getUsers` |
| Plural para colecciones | `/products` | `/product` |
| Minúsculas | `/user-profiles` | `/UserProfiles` |
| Guiones para separar palabras | `/user-profiles` | `/user_profiles` |
| Sin extensiones | `/users/123` | `/users/123.json` |
| Sin trailing slash | `/users` | `/users/` |

### 3.2 Estructura de URIs

```
/recursos                    → Colección
/recursos/{id}               → Recurso específico
/recursos/{id}/sub-recursos  → Recursos anidados

Ejemplos:
GET    /users                → Lista usuarios
GET    /users/123            → Usuario 123
GET    /users/123/orders     → Pedidos del usuario 123
GET    /users/123/orders/456 → Pedido 456 del usuario 123
POST   /users                → Crear usuario
PUT    /users/123            → Actualizar usuario 123
DELETE /users/123            → Eliminar usuario 123
```

### 3.3 Query Parameters para Filtros

```
# Filtrado
GET /products?category=electronics&brand=sony

# Ordenamiento
GET /products?sort=price&order=desc

# Paginación
GET /products?page=2&limit=20

# Búsqueda
GET /products?search=laptop

# Combinado
GET /products?category=electronics&sort=price&page=1&limit=10
```

### 3.4 Versionado de APIs

```
# En la URI (más común)
GET /api/v1/users
GET /api/v2/users

# En el header (más "RESTful")
GET /api/users
Accept: application/vnd.myapi.v1+json

# En query parameter
GET /api/users?version=1
```

---

## 4. Respuestas RESTful

### 4.1 Estructura de Respuesta para Colecciones

```json
{
  "data": [
    {"id": 1, "name": "Producto A", "price": 100},
    {"id": 2, "name": "Producto B", "price": 200}
  ],
  "meta": {
    "total": 150,
    "page": 1,
    "limit": 20,
    "totalPages": 8
  },
  "links": {
    "self": "/api/products?page=1",
    "next": "/api/products?page=2",
    "last": "/api/products?page=8"
  }
}
```

### 4.2 Estructura de Respuesta para Recurso Individual

```json
{
  "data": {
    "id": 123,
    "name": "Juan Pérez",
    "email": "juan@example.com",
    "createdAt": "2024-12-17T10:30:00Z"
  },
  "links": {
    "self": "/api/users/123",
    "orders": "/api/users/123/orders"
  }
}
```

### 4.3 Estructura de Respuesta de Error

```json
{
  "error": {
    "status": 400,
    "code": "VALIDATION_ERROR",
    "message": "Los datos proporcionados no son válidos",
    "details": [
      {
        "field": "email",
        "message": "El email no tiene un formato válido"
      },
      {
        "field": "age",
        "message": "La edad debe ser mayor a 18"
      }
    ],
    "timestamp": "2024-12-17T10:30:00Z",
    "path": "/api/users"
  }
}
```

---

## 5. Mapeo HTTP ↔ CRUD ↔ SQL

| Operación | Método HTTP | URI | SQL |
|-----------|-------------|-----|-----|
| Listar | GET | /users | SELECT * FROM users |
| Obtener | GET | /users/123 | SELECT * FROM users WHERE id=123 |
| Crear | POST | /users | INSERT INTO users |
| Reemplazar | PUT | /users/123 | UPDATE users SET ... WHERE id=123 |
| Actualizar | PATCH | /users/123 | UPDATE users SET campo=valor WHERE id=123 |
| Eliminar | DELETE | /users/123 | DELETE FROM users WHERE id=123 |

---

## 6. Niveles de Madurez REST (Richardson Maturity Model)

```
Nivel 3: HATEOAS
         └── Hypermedia controls (links en respuestas)
         
Nivel 2: Verbos HTTP
         └── GET, POST, PUT, DELETE correctamente
         
Nivel 1: Recursos
         └── URIs para diferentes recursos
         
Nivel 0: RPC sobre HTTP
         └── POST /api con acción en body (SOAP, XML-RPC)
```

**Objetivo del bootcamp**: Alcanzar **Nivel 2** consistentemente.

---

## 7. Buenas Prácticas

### 7.1 Diseño de API

- ✅ Usar sustantivos plurales para recursos
- ✅ Mantener URIs simples y predecibles
- ✅ Usar códigos de estado HTTP apropiados
- ✅ Versionar la API desde el inicio
- ✅ Documentar con OpenAPI/Swagger
- ✅ Paginar colecciones grandes

### 7.2 Seguridad

- ✅ Usar HTTPS siempre
- ✅ Validar TODOS los inputs
- ✅ No exponer IDs internos sensibles
- ✅ Implementar rate limiting
- ✅ Usar tokens para autenticación (JWT, OAuth)
- ✅ No exponer información en errores de producción

### 7.3 Rendimiento

- ✅ Implementar caché donde sea apropiado
- ✅ Permitir filtrado y paginación
- ✅ Usar compresión (gzip)
- ✅ Minimizar llamadas N+1
- ✅ Considerar campos parciales (`?fields=id,name`)

---

## 📚 Recursos Adicionales

- [Roy Fielding's Dissertation](https://www.ics.uci.edu/~fielding/pubs/dissertation/top.htm)
- [REST API Tutorial](https://restfulapi.net/)
- [Microsoft REST API Guidelines](https://github.com/microsoft/api-guidelines)
- [JSON:API Specification](https://jsonapi.org/)

---

## ✅ Checklist de la Sección

- [ ] Puedo explicar qué es REST y RESTful
- [ ] Conozco los 6 principios de REST
- [ ] Sé diseñar URIs siguiendo convenciones
- [ ] Entiendo el mapeo HTTP → CRUD
- [ ] Conozco los niveles de madurez REST
- [ ] Puedo aplicar buenas prácticas de diseño de APIs
