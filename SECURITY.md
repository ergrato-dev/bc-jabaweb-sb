# Política de Seguridad

## 🔐 Compromiso con la Seguridad

La seguridad es una prioridad fundamental en este proyecto. Seguimos el principio de **"Security First"**: diseñamos pensando en que ya fuimos atacados, no en si nos podrían atacar.

## 📋 Versiones Soportadas

| Versión | Soportada |
|---------|-----------|
| Rama `main` | ✅ Sí |
| Ramas de feature | ⚠️ Solo durante desarrollo activo |
| Releases etiquetados | ✅ Última versión |

## 🚨 Reportar una Vulnerabilidad

Si descubres una vulnerabilidad de seguridad, por favor repórtala de manera responsable.

### ❌ NO hacer

- No abras un issue público con detalles de la vulnerabilidad
- No publiques la vulnerabilidad en redes sociales
- No explotes la vulnerabilidad

### ✅ SÍ hacer

1. **Envía un email privado** a: [tu-email@ejemplo.com](mailto:tu-email@ejemplo.com)
2. **Incluye en tu reporte**:
   - Descripción de la vulnerabilidad
   - Pasos para reproducir
   - Impacto potencial
   - Sugerencia de solución (si la tienes)
3. **Espera confirmación** - Responderemos en máximo 48 horas

### Proceso de Divulgación

1. **Recepción**: Confirmamos recepción del reporte
2. **Investigación**: Evaluamos la vulnerabilidad (1-5 días)
3. **Corrección**: Desarrollamos y probamos la solución
4. **Divulgación coordinada**: Publicamos el fix y créditos al reportante
5. **Comunicación**: Notificamos a la comunidad si es necesario

## 🛡️ Buenas Prácticas de Seguridad

Este bootcamp enseña y aplica:

### Validación de Inputs

```java
// ✅ SIEMPRE validar
@PostMapping("/users")
public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserRequest request) {
    // ...
}
```

### Manejo Seguro de Errores

```java
// ❌ NUNCA exponer stack traces
return ResponseEntity.status(500).body(exception.getMessage());

// ✅ Respuestas genéricas
return ResponseEntity.status(500).body(new ErrorResponse("Error interno", "ERR-500"));
```

### Credenciales

```properties
# ❌ NUNCA hardcodear
spring.datasource.password=mi_password

# ✅ Variables de entorno
spring.datasource.password=${DB_PASSWORD}
```

### Archivos Sensibles

El `.gitignore` del proyecto excluye:

- `.env` y variantes
- Claves privadas (`*.pem`, `*.key`)
- Keystores (`*.jks`, `*.p12`)
- Carpetas `secrets/` y `credentials/`

## 📚 Recursos de Seguridad

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [Docker Security Best Practices](https://docs.docker.com/develop/security-best-practices/)
- [CWE/SANS Top 25](https://cwe.mitre.org/top25/)

## 🏆 Hall of Fame

Agradecemos a quienes han reportado vulnerabilidades de manera responsable:

*Aún no hay reportes - ¡sé el primero en contribuir a la seguridad del proyecto!*

---

> **Recuerda**: La seguridad es responsabilidad de todos. Si ves algo, di algo. 🔐
