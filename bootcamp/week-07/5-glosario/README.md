# 📖 Glosario - Semana 07: Seguridad

## A

### Access Token
Token de corta duración que permite acceder a recursos protegidos. Se envía en cada request en el header `Authorization: Bearer <token>`.

### Authentication (Autenticación)
Proceso de verificar la identidad de un usuario. Responde a la pregunta "¿Quién eres?".

### Authorization (Autorización)
Proceso de determinar qué acciones puede realizar un usuario autenticado. Responde a "¿Qué puedes hacer?".

### AuthenticationManager
Interfaz de Spring Security que procesa las solicitudes de autenticación.

### AuthenticationProvider
Componente que realiza la autenticación real contra una fuente de datos (BD, LDAP, etc.).

---

## B

### Base64
Esquema de codificación que representa datos binarios en formato ASCII. JWT usa Base64URL para header y payload.

### BCrypt
Algoritmo de hashing de contraseñas que incluye un salt automático y es resistente a ataques de fuerza bruta.

### Bearer Token
Tipo de token de acceso. El formato del header es `Authorization: Bearer <token>`.

---

## C

### Claims
Declaraciones sobre el usuario contenidas en el payload del JWT. Pueden ser registradas (estándar), públicas o privadas.

### CSRF (Cross-Site Request Forgery)
Ataque que fuerza a un usuario autenticado a ejecutar acciones no deseadas. Se deshabilita en APIs stateless.

---

## D

### DaoAuthenticationProvider
Implementación de AuthenticationProvider que usa un UserDetailsService para cargar datos del usuario.

---

## E

### Expiration (exp)
Claim estándar de JWT que indica cuándo expira el token (Unix timestamp).

---

## F

### Filter Chain
Cadena de filtros de Spring Security que procesan cada request HTTP.

---

## G

### GrantedAuthority
Interfaz que representa un permiso o rol otorgado a un usuario. Ej: `ROLE_ADMIN`.

---

## H

### HS256
Algoritmo de firma HMAC con SHA-256. Usa una clave secreta compartida (simétrico).

### Hashing
Proceso de convertir una contraseña en un valor irreversible. Nunca se almacenan contraseñas en texto plano.

---

## I

### Issued At (iat)
Claim estándar que indica cuándo fue emitido el token (Unix timestamp).

---

## J

### JWT (JSON Web Token)
Estándar abierto (RFC 7519) para transmitir información de forma segura entre partes como un objeto JSON firmado.

### JJWT
Biblioteca Java para crear, parsear y validar JWTs.

---

## M

### Method Security
Seguridad a nivel de método usando anotaciones como `@PreAuthorize`, `@PostAuthorize`.

---

## O

### OncePerRequestFilter
Filtro de Spring que garantiza ejecutarse solo una vez por request. Base para el filtro JWT.

---

## P

### Payload
Segunda parte del JWT que contiene los claims (información del usuario).

### PasswordEncoder
Interfaz de Spring Security para codificar/verificar contraseñas.

### PreAuthorize
Anotación para verificar autorización antes de ejecutar un método.

### Principal
Objeto que representa al usuario autenticado actualmente.

---

## R

### Refresh Token
Token de larga duración usado para obtener nuevos access tokens sin re-autenticarse.

### Role
Agrupación de permisos. En Spring Security, los roles tienen prefijo `ROLE_` (ej: `ROLE_ADMIN`).

### RS256
Algoritmo de firma RSA con SHA-256. Usa par de claves pública/privada (asimétrico).

---

## S

### Salt
Valor aleatorio agregado a la contraseña antes de hashear. BCrypt lo genera automáticamente.

### SecurityContext
Almacén de Spring Security que contiene la información de autenticación del usuario actual.

### SecurityContextHolder
Clase que proporciona acceso al SecurityContext.

### SecurityFilterChain
Bean que define las reglas de seguridad HTTP en Spring Security 6+.

### Signature
Tercera parte del JWT que garantiza que el token no ha sido alterado.

### SpEL (Spring Expression Language)
Lenguaje de expresiones usado en `@PreAuthorize`. Permite lógica compleja como `hasRole('ADMIN') or #id == principal.id`.

### Stateless
Arquitectura donde el servidor no mantiene estado de sesión. Cada request debe incluir toda la información necesaria (token).

### Subject (sub)
Claim estándar que identifica al sujeto del token (típicamente el username o ID del usuario).

---

## T

### Token
Cadena de caracteres que representa credenciales de autenticación.

---

## U

### UserDetails
Interfaz de Spring Security que representa la información del usuario (username, password, authorities).

### UserDetailsService
Interfaz para cargar datos del usuario desde una fuente (BD, LDAP, etc.).

### UsernamePasswordAuthenticationToken
Implementación de Authentication para credenciales username/password.

---

## Tabla de Códigos HTTP de Seguridad

| Código | Nombre | Significado |
|--------|--------|-------------|
| 401 | Unauthorized | No autenticado o token inválido |
| 403 | Forbidden | Autenticado pero sin permisos |
| 409 | Conflict | Recurso duplicado (username, email) |

---

## Estructura JWT

```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIn0.signature
└──────── Header ─────┘└───── Payload ──────┘└─ Firma ─┘
```

| Parte | Contenido | Codificación |
|-------|-----------|--------------|
| Header | Algoritmo y tipo | Base64URL |
| Payload | Claims del usuario | Base64URL |
| Signature | Firma criptográfica | Base64URL |
