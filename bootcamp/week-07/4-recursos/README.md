# 📚 Recursos - Semana 07: Seguridad con Spring Security y JWT

## Documentación Oficial

### Spring Security
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Security Architecture](https://docs.spring.io/spring-security/reference/servlet/architecture.html)
- [Method Security](https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html)

### JWT
- [RFC 7519 - JSON Web Token](https://datatracker.ietf.org/doc/html/rfc7519)
- [JWT Introduction - jwt.io](https://jwt.io/introduction)
- [JJWT GitHub](https://github.com/jwtk/jjwt)

## Tutoriales Recomendados

### Baeldung (Español/Inglés)
- [Spring Security with JWT](https://www.baeldung.com/spring-security-oauth-jwt)
- [Spring Security Basic Authentication](https://www.baeldung.com/spring-security-basic-authentication)
- [Method Security Expressions](https://www.baeldung.com/spring-security-method-security)
- [@PreAuthorize and @PostAuthorize](https://www.baeldung.com/spring-security-preauthorize-postauthorize)

### Spring Official Guides
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [Spring Security and Angular](https://spring.io/guides/tutorials/spring-security-and-angular-js/)

## Videos

### YouTube
- [Spring Security 6 Full Course - Amigoscode](https://www.youtube.com/watch?v=b9O9NI-RJ3o)
- [JWT Authentication Spring Boot - Dan Vega](https://www.youtube.com/watch?v=KYNR5js2cXE)
- [Spring Security Architecture Explained - Java Brains](https://www.youtube.com/watch?v=caCJAJC41Rk)

### Playlists
- [Spring Security Tutorial Series - Java Techie](https://www.youtube.com/playlist?list=PLVz2XdJiJQxwpfSeuJXdjsCQfPLGaICDg)

## Herramientas

### Debugging JWT
- [jwt.io](https://jwt.io/) - Decoder y debugger de JWT
- [JWT Debugger Chrome Extension](https://chrome.google.com/webstore/detail/jwt-debugger/ppmmlchacdbknfphdeafcbmklcghghmd)

### Testing APIs
- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)
- [HTTPie](https://httpie.io/)

### Generación de Secrets
```bash
# Generar secret key para JWT (256 bits en Base64)
openssl rand -base64 32

# Generar password seguro
openssl rand -base64 16
```

## Libros Gratuitos

- [Spring Security in Action (Manning)](https://www.manning.com/books/spring-security-in-action) - Capítulos gratuitos
- [OAuth 2.0 Simplified](https://oauth.net/books/) - Aaron Parecki

## Artículos Adicionales

### Seguridad en APIs
- [OWASP API Security Top 10](https://owasp.org/www-project-api-security/)
- [Best Practices for REST API Security](https://blog.restcase.com/restful-api-authentication-basics/)

### JWT Security
- [Critical vulnerabilities in JSON Web Token libraries](https://auth0.com/blog/critical-vulnerabilities-in-json-web-token-libraries/)
- [JWT Security Best Practices](https://curity.io/resources/learn/jwt-best-practices/)

## Cheat Sheets

### Spring Security
```java
// Anotaciones principales
@EnableWebSecurity      // Habilitar seguridad web
@EnableMethodSecurity   // Habilitar @PreAuthorize, @PostAuthorize

// Expresiones SpEL
hasRole('ADMIN')                    // Tiene rol ADMIN
hasAnyRole('USER', 'ADMIN')         // Tiene cualquier rol
isAuthenticated()                   // Está autenticado
isAnonymous()                       // Es anónimo
principal.username                  // Username del usuario actual
#paramName                          // Parámetro del método
@beanName.method()                  // Llamar método de bean
```

### JWT Claims Estándar
```json
{
  "iss": "issuer",           // Emisor
  "sub": "subject",          // Sujeto (usuario)
  "aud": "audience",         // Audiencia
  "exp": 1234567890,         // Expiración (Unix timestamp)
  "nbf": 1234567890,         // No válido antes de
  "iat": 1234567890,         // Emitido en
  "jti": "unique-id"         // ID único del token
}
```

### HTTP Status Codes para Auth
```
200 OK              - Login exitoso, refresh exitoso
201 Created         - Usuario registrado
400 Bad Request     - Datos de entrada inválidos
401 Unauthorized    - No autenticado, token inválido/expirado
403 Forbidden       - Autenticado pero sin permisos
409 Conflict        - Username/email ya existe
```

## Preguntas Frecuentes (FAQ)

### ¿Access Token vs Refresh Token?
- **Access Token**: Corta duración (15min-1h), acceso a recursos
- **Refresh Token**: Larga duración (7-30 días), renovar access tokens

### ¿Por qué stateless en APIs REST?
- Escalabilidad horizontal (cualquier servidor puede validar)
- Sin estado en servidor = mejor rendimiento
- Ideal para microservicios

### ¿JWT es seguro?
- Sí, si se usa correctamente:
  - HTTPS siempre
  - Tokens cortos
  - Secret key fuerte (256+ bits)
  - No almacenar datos sensibles en payload
