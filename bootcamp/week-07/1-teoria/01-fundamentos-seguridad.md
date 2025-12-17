# 🔐 Fundamentos de Seguridad en APIs REST

## Introducción

La seguridad es un aspecto **crítico** en el desarrollo de APIs. Una API mal protegida puede exponer datos sensibles, permitir acceso no autorizado y causar daños irreparables. En esta sección aprenderemos los conceptos fundamentales que debemos dominar antes de implementar seguridad.

---

## 1. Autenticación vs Autorización

Estos dos conceptos son la base de cualquier sistema de seguridad:

![Autenticación vs Autorización](../0-assets/01-auth-vs-authz.svg)

### 🔐 Autenticación (Authentication)

**"¿Quién eres?"**

Es el proceso de **verificar la identidad** de un usuario o sistema. Responde a la pregunta de si el usuario es quien dice ser.

**Ejemplos de autenticación:**
- Usuario + Contraseña
- Token JWT
- OAuth 2.0 (Login con Google)
- Certificados digitales
- Biometría (huella, facial)

```java
// Ejemplo: Verificar credenciales
public Authentication authenticate(String username, String password) {
    User user = userRepository.findByUsername(username);

    if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
        throw new BadCredentialsException("Credenciales inválidas");
    }

    return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
}
```

### 🛡️ Autorización (Authorization)

**"¿Qué puedes hacer?"**

Es el proceso de determinar **qué recursos y operaciones** puede acceder un usuario autenticado. Ocurre **después** de la autenticación.

**Ejemplos de autorización:**
- Roles (USER, ADMIN, MODERATOR)
- Permisos específicos (READ, WRITE, DELETE)
- Propiedad del recurso (solo acceder a tus propios datos)

```java
// Ejemplo: Verificar autorización por rol
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/users")
public List<User> getAllUsers() {
    return userService.findAll();
}

// Ejemplo: Verificar propiedad del recurso
@PreAuthorize("hasRole('ADMIN') or @taskService.isOwner(#taskId, principal.username)")
@DeleteMapping("/tasks/{taskId}")
public void deleteTask(@PathVariable UUID taskId) {
    taskService.delete(taskId);
}
```

---

## 2. Stateful vs Stateless

### 📦 Autenticación Stateful (Sesiones)

El servidor **almacena** información de la sesión del usuario.

```
┌─────────┐         ┌─────────┐         ┌─────────┐
│ Cliente │         │ Servidor│         │ Sesión  │
│         │ Login   │         │ Crear   │  Store  │
│         │ ──────► │         │ ──────► │         │
│         │         │         │         │ sessionId│
│         │ Cookie  │         │         │ = abc123│
│         │ ◄────── │         │         │ user = X│
│         │ sessionId│        │         │         │
│         │         │         │         │         │
│         │ Request │         │ Buscar  │         │
│         │ ──────► │         │ ──────► │         │
│         │ +Cookie │         │ sesión  │         │
└─────────┘         └─────────┘         └─────────┘
```

**Desventajas para APIs:**
- ❌ Requiere almacenamiento de sesiones
- ❌ Difícil de escalar horizontalmente
- ❌ Problemas con múltiples servidores
- ❌ No es ideal para microservicios

### ⚡ Autenticación Stateless (JWT)

El servidor **NO almacena** información de sesión. El token contiene toda la información necesaria.

```
┌─────────┐         ┌─────────┐
│ Cliente │         │ Servidor│
│         │ Login   │         │
│         │ ──────► │         │ Generar JWT con
│         │         │         │ datos del usuario
│         │  JWT    │         │
│         │ ◄────── │         │
│         │         │         │
│         │ Request │         │ Validar firma
│         │ ──────► │         │ del JWT
│         │ +JWT    │         │ (sin consultar BD)
└─────────┘         └─────────┘
```

**Ventajas para APIs REST:**
- ✅ Escalabilidad horizontal (cualquier servidor puede validar)
- ✅ No requiere almacenamiento de sesiones
- ✅ Ideal para microservicios
- ✅ Mejor rendimiento (sin consultas de sesión)
- ✅ Compatible con SPAs y aplicaciones móviles

---

## 3. ¿Por qué JWT para APIs REST?

JWT (JSON Web Token) es el estándar de facto para autenticación en APIs REST modernas.

### Características de JWT:

| Característica | Descripción |
|----------------|-------------|
| **Autocontenido** | Contiene toda la información del usuario |
| **Firmado** | No se puede falsificar sin la clave secreta |
| **Compacto** | Fácil de transmitir en headers HTTP |
| **Estándar** | RFC 7519, ampliamente soportado |
| **Stateless** | No requiere almacenamiento en servidor |

### Comparación con alternativas:

| Método | Stateless | Escalable | Seguro | Complejidad |
|--------|-----------|-----------|--------|-------------|
| **Sesiones (Cookies)** | ❌ | ❌ | ✅ | Baja |
| **Basic Auth** | ✅ | ✅ | ⚠️ | Muy baja |
| **API Keys** | ✅ | ✅ | ⚠️ | Baja |
| **JWT** | ✅ | ✅ | ✅ | Media |
| **OAuth 2.0** | ✅ | ✅ | ✅ | Alta |

---

## 4. Principios de Seguridad

### 🔑 Defensa en Profundidad

No confiar en una sola capa de seguridad. Implementar múltiples barreras:

```
[Internet] → [Firewall] → [Rate Limiting] → [HTTPS] → [JWT Auth] → [Authorization] → [Validación] → [API]
```

### 🚫 Principio de Mínimo Privilegio

Los usuarios solo deben tener los permisos **estrictamente necesarios**.

```java
// ❌ MAL: Todos los usuarios pueden acceder
@GetMapping("/admin/stats")
public Stats getStats() { ... }

// ✅ BIEN: Solo administradores
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/stats")
public Stats getStats() { ... }
```

### 🔒 Nunca Confiar en el Input

Todo dato del usuario es potencialmente malicioso.

```java
// ❌ MAL: Sin validación
@PostMapping("/users")
public User create(@RequestBody User user) {
    return userRepository.save(user);
}

// ✅ BIEN: Con validación
@PostMapping("/users")
public UserDTO create(@Valid @RequestBody CreateUserRequest request) {
    return userService.create(request);
}
```

### 🔐 Contraseñas Seguras

**NUNCA** almacenar contraseñas en texto plano.

```java
// ❌ NUNCA: Texto plano
user.setPassword("miPassword123");

// ✅ SIEMPRE: Hash con BCrypt
user.setPassword(passwordEncoder.encode("miPassword123"));
```

BCrypt incluye:
- **Salt automático**: Cada hash es único
- **Work factor**: Configurable para ser más lento (dificulta ataques)
- **Resistente**: A ataques de diccionario y rainbow tables

---

## 5. Flujo de Autenticación JWT

```
1. REGISTRO
   Cliente → POST /api/auth/register { username, email, password }
   Servidor → Hashear password con BCrypt
   Servidor → Guardar usuario en BD
   Servidor ← 201 Created { user }

2. LOGIN
   Cliente → POST /api/auth/login { username, password }
   Servidor → Validar credenciales
   Servidor → Generar JWT con claims del usuario
   Servidor ← 200 OK { accessToken, refreshToken }

3. ACCESO A RECURSOS PROTEGIDOS
   Cliente → GET /api/tasks
            Header: Authorization: Bearer <accessToken>
   Servidor → Validar firma del JWT
   Servidor → Verificar que no ha expirado
   Servidor → Extraer usuario del token
   Servidor → Verificar autorización
   Servidor ← 200 OK { tasks }

4. TOKEN EXPIRADO
   Cliente → GET /api/tasks (token expirado)
   Servidor ← 401 Unauthorized

5. REFRESH TOKEN
   Cliente → POST /api/auth/refresh { refreshToken }
   Servidor → Validar refresh token
   Servidor → Generar nuevo accessToken
   Servidor ← 200 OK { accessToken }
```

---

## 6. Headers de Seguridad HTTP

### Authorization Header

```http
GET /api/tasks HTTP/1.1
Host: api.example.com
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Headers de Seguridad Recomendados

```java
@Configuration
public class SecurityHeadersConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                    .policyDirectives("default-src 'self'"))
                .frameOptions(frame -> frame.deny())
                .xssProtection(xss -> xss.disable()) // Browsers modernos
                .contentTypeOptions(Customizer.withDefaults())
            )
            .build();
    }
}
```

---

## 7. Amenazas Comunes

| Amenaza | Descripción | Mitigación |
|---------|-------------|------------|
| **Brute Force** | Intentos masivos de login | Rate limiting, bloqueo temporal |
| **Token Theft** | Robo de JWT | HTTPS, tokens de corta duración |
| **CSRF** | Peticiones falsificadas | Deshabilitado para APIs stateless |
| **XSS** | Scripts maliciosos | Headers de seguridad, sanitización |
| **SQL Injection** | Inyección de SQL | Queries parametrizadas (JPA) |
| **Privilege Escalation** | Acceder a recursos no autorizados | Verificación de autorización |

---

## Resumen

| Concepto | Descripción |
|----------|-------------|
| **Autenticación** | Verificar identidad (¿Quién eres?) |
| **Autorización** | Verificar permisos (¿Qué puedes hacer?) |
| **Stateless** | Sin sesiones en servidor |
| **JWT** | Token autocontenido y firmado |
| **BCrypt** | Hash seguro para contraseñas |
| **Bearer Token** | Formato de envío de JWT en header |

---

## Próximos Pasos

En la siguiente sección aprenderemos a **configurar Spring Security** para proteger nuestra API REST.

→ [02-spring-security-configuracion.md](02-spring-security-configuracion.md)
