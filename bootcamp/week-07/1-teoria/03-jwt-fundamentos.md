# 🎫 Fundamentos de JWT (JSON Web Token)

## Introducción

**JWT (JSON Web Token)** es un estándar abierto (RFC 7519) que define un formato compacto y autocontenido para transmitir información de forma segura entre partes como un objeto JSON. Esta información puede ser verificada y confiable porque está **firmada digitalmente**.

![Estructura JWT](../0-assets/03-jwt-structure.svg)

---

## 1. ¿Por qué JWT?

### Problemas con Sesiones Tradicionales

```
❌ Sesiones en servidor:
   - Almacenamiento en memoria/BD
   - Difícil escalar horizontalmente
   - Requiere "sticky sessions"
   - Problemas con múltiples servidores
```

### Solución con JWT

```
✅ JWT (Stateless):
   - Token autocontenido
   - Cualquier servidor puede validar
   - No requiere almacenamiento
   - Escalabilidad horizontal
   - Perfecto para microservicios
```

---

## 2. Estructura de un JWT

Un JWT consiste en **tres partes** separadas por puntos (`.`):

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huZG9lIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE3MDI4MDAwMDAsImV4cCI6MTcwMjg4NjQwMH0.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

### 2.1 Header (Encabezado)

Contiene metadatos sobre el token:

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

| Campo | Descripción |
|-------|-------------|
| `alg` | Algoritmo de firma (HS256, RS256, etc.) |
| `typ` | Tipo de token (siempre "JWT") |

**Codificado en Base64Url:**
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
```

### 2.2 Payload (Carga Útil)

Contiene los **claims** (declaraciones) sobre el usuario:

```json
{
  "sub": "johndoe",
  "roles": ["USER"],
  "email": "john@example.com",
  "iat": 1702800000,
  "exp": 1702886400
}
```

#### Claims Registrados (Estándar)

| Claim | Nombre | Descripción |
|-------|--------|-------------|
| `sub` | Subject | Identificador del usuario |
| `iat` | Issued At | Timestamp de creación |
| `exp` | Expiration | Timestamp de expiración |
| `nbf` | Not Before | No válido antes de... |
| `iss` | Issuer | Emisor del token |
| `aud` | Audience | Destinatario del token |
| `jti` | JWT ID | Identificador único |

#### Claims Personalizados

```json
{
  "sub": "johndoe",
  "roles": ["USER", "ADMIN"],    // Custom
  "email": "john@example.com",    // Custom
  "department": "Engineering"     // Custom
}
```

**Codificado en Base64Url:**
```
eyJzdWIiOiJqb2huZG9lIiwicm9sZXMiOlsiVVNFUiJdLCJpYXQiOjE3MDI4MDAwMDAsImV4cCI6MTcwMjg4NjQwMH0
```

### 2.3 Signature (Firma)

Garantiza que el token no ha sido alterado:

```
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  SECRET_KEY
)
```

**Resultado:**
```
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
```

---

## 3. Flujo de Autenticación

![Flujo JWT](../0-assets/04-jwt-flow.svg)

### Paso a Paso:

```
1️⃣ LOGIN
   Cliente: POST /api/auth/login
            { "username": "john", "password": "secret123" }

   Servidor: Valida credenciales contra BD
             Si válidas → Genera JWT

   Respuesta: { "accessToken": "eyJhbG...", "expiresIn": 86400 }

2️⃣ ALMACENAR TOKEN
   Cliente: Guardar token (localStorage, memoria, etc.)

3️⃣ REQUEST CON TOKEN
   Cliente: GET /api/tasks
            Header: Authorization: Bearer eyJhbG...

   Servidor: Extrae token del header
             Valida firma con SECRET_KEY
             Verifica expiración
             Extrae claims (usuario, roles)

   Si válido: Procesa request → Retorna datos
   Si inválido: 401 Unauthorized

4️⃣ TOKEN EXPIRADO
   Servidor: 401 Unauthorized (token expired)
   Cliente: Usar refresh token o re-login
```

---

## 4. Algoritmos de Firma

### Simétricos (Clave Compartida)

| Algoritmo | Descripción | Uso |
|-----------|-------------|-----|
| **HS256** | HMAC con SHA-256 | Más común, simple |
| **HS384** | HMAC con SHA-384 | Mayor seguridad |
| **HS512** | HMAC con SHA-512 | Máxima seguridad |

```java
// Misma clave para firmar y verificar
String SECRET = "miClaveSecreta256bits";

// Firmar
Jwts.builder()
    .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), Jwts.SIG.HS256)
    .compact();

// Verificar
Jwts.parser()
    .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
    .build()
    .parseSignedClaims(token);
```

### Asimétricos (Clave Pública/Privada)

| Algoritmo | Descripción | Uso |
|-----------|-------------|-----|
| **RS256** | RSA con SHA-256 | Microservicios |
| **RS384** | RSA con SHA-384 | Mayor seguridad |
| **RS512** | RSA con SHA-512 | Máxima seguridad |
| **ES256** | ECDSA con SHA-256 | Tokens más cortos |

```java
// Clave privada para firmar
// Clave pública para verificar (puede ser distribuida)
```

> 💡 Para APIs monolíticas, **HS256** es suficiente y más simple.

---

## 5. Seguridad del JWT

### ✅ Lo que JWT Garantiza

- **Integridad**: El contenido no ha sido modificado
- **Autenticidad**: Fue emitido por quien posee la clave secreta
- **No repudio**: El emisor no puede negar haberlo creado

### ❌ Lo que JWT NO Garantiza

- **Confidencialidad**: El payload es legible (Base64, no encriptado)
- **Revocación**: No hay forma nativa de invalidar un token

### Mejores Prácticas

| Práctica | Descripción |
|----------|-------------|
| **HTTPS siempre** | Evitar intercepción del token |
| **Tokens cortos** | Expiración 15-60 minutos |
| **Refresh tokens** | Para renovar sin re-login |
| **Clave fuerte** | Mínimo 256 bits para HS256 |
| **No datos sensibles** | En el payload (es legible) |
| **Validar siempre** | Firma Y expiración |

---

## 6. Access Token vs Refresh Token

### Access Token
- **Propósito**: Acceder a recursos protegidos
- **Duración**: Corta (15 min - 1 hora)
- **Almacenamiento**: Memoria (preferido) o localStorage

### Refresh Token
- **Propósito**: Obtener nuevos access tokens
- **Duración**: Larga (7 días - 30 días)
- **Almacenamiento**: httpOnly cookie (más seguro)

```
┌─────────────────────────────────────────────────────┐
│                    TIEMPO                           │
├───────┬────────────────────────────────────┬────────┤
│ Login │      Access Token (1 hora)         │Refresh │
│   ●───┼────────────────────────────────────┼───●    │
│       │                                    │        │
│       │  Refresh Token (7 días)            │        │
│   ●───┼────────────────────────────────────┼───●    │
│       │                                           │
│       │  Access expirado → Usar refresh           │
│       │  → Nuevo access token                      │
│       │                                           │
│       │  Refresh expirado → Re-login              │
└───────┴────────────────────────────────────────────┘
```

---

## 7. Decodificar JWT (Debug)

### Online
- [jwt.io](https://jwt.io/) - Decoder oficial

### En Java

```java
// Solo decodificar (sin validar firma)
String[] parts = token.split("\\.");
String header = new String(Base64.getUrlDecoder().decode(parts[0]));
String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

System.out.println("Header: " + header);
System.out.println("Payload: " + payload);
```

### Con curl

```bash
# Extraer payload
echo "eyJzdWIiOiJqb2huIiwiZXhwIjoxNzAyODg2NDAwfQ" | base64 -d
# Output: {"sub":"john","exp":1702886400}
```

---

## 8. Errores Comunes

### Token Expirado
```json
{
  "error": "token_expired",
  "message": "El token ha expirado",
  "expiredAt": "2024-12-17T10:00:00Z"
}
```

### Firma Inválida
```json
{
  "error": "invalid_signature",
  "message": "La firma del token no es válida"
}
```

### Token Malformado
```json
{
  "error": "malformed_token",
  "message": "El formato del token no es válido"
}
```

---

## 9. Ejemplo Completo de JWT

### Token:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.
eyJzdWIiOiJqb2huZG9lIiwicm9sZXMiOlsiVVNFUiJdLCJlbWFpbCI6ImpvaG5AZXhhbXBsZS5jb20iLCJpYXQiOjE3MDI4MDAwMDAsImV4cCI6MTcwMjg4NjQwMH0.
abc123signature
```

### Header Decodificado:
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

### Payload Decodificado:
```json
{
  "sub": "johndoe",
  "roles": ["USER"],
  "email": "john@example.com",
  "iat": 1702800000,
  "exp": 1702886400
}
```

### Interpretación:
- **Usuario**: johndoe
- **Roles**: USER
- **Emitido**: 17 dic 2024, 10:00:00 UTC
- **Expira**: 18 dic 2024, 10:00:00 UTC (24h después)

---

## Resumen

| Concepto | Descripción |
|----------|-------------|
| **JWT** | Token autocontenido y firmado |
| **Header** | Metadatos (algoritmo, tipo) |
| **Payload** | Claims del usuario |
| **Signature** | Garantiza integridad |
| **Base64Url** | Codificación (no encriptación) |
| **HS256** | Algoritmo simétrico común |
| **Access Token** | Corta duración, acceso a recursos |
| **Refresh Token** | Larga duración, renovar access |

---

## Próximos Pasos

En la siguiente sección implementaremos **generación y validación de JWT** en Spring Boot.

→ [04-implementacion-jwt.md](04-implementacion-jwt.md)
