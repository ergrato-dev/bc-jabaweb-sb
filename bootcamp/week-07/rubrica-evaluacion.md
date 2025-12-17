# 📋 Rúbrica de Evaluación - Semana 07

## Seguridad con Spring Security y JWT

### Información General

| Aspecto | Detalle |
|---------|---------|
| **Semana** | 07 |
| **Tema** | Seguridad: Spring Security y JWT |
| **Duración** | 5 horas |
| **Peso Total** | 100 puntos |

---

## Distribución de Puntos

| Tipo de Evidencia | Puntos | Porcentaje |
|-------------------|--------|------------|
| Conocimiento | 25 | 25% |
| Desempeño | 35 | 35% |
| Producto | 40 | 40% |

---

## 1. Evidencia de Conocimiento (25 puntos)

### 1.1 Cuestionario Teórico (15 puntos)

| Pregunta | Puntos | Criterio |
|----------|--------|----------|
| Diferencia entre autenticación y autorización | 2 | Explicación clara con ejemplos |
| Ventajas de JWT sobre sesiones tradicionales | 2 | Mínimo 3 ventajas correctas |
| Estructura de un JWT (header, payload, signature) | 3 | Identificar partes y contenido |
| Flujo de autenticación con JWT | 3 | Describir pasos correctamente |
| Propósito de BCrypt en contraseñas | 2 | Explicar hashing vs encriptación |
| Función del SecurityFilterChain | 3 | Explicar rol en Spring Security |

### 1.2 Preguntas de Análisis (10 puntos)

| Pregunta | Puntos | Criterio |
|----------|--------|----------|
| ¿Por qué JWT es stateless? | 2 | Explicar autocontenido del token |
| ¿Qué ocurre si el JWT expira? | 2 | Describir flujo con refresh token |
| ¿Cómo proteger un endpoint para ADMIN? | 3 | Código correcto con @PreAuthorize |
| ¿Dónde almacenar el JWT secret? | 3 | Variables de entorno, no en código |

---

## 2. Evidencia de Desempeño (35 puntos)

### 2.1 Configuración de Spring Security (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (5-6) | Insuficiente (<5) |
|----------|----------------|-------------|------------------|-------------------|
| SecurityConfig | SecurityFilterChain completo, CSRF desactivado, stateless, filtro JWT | Configuración funcional con pequeños errores | Configuración básica incompleta | No funciona |

### 2.2 Implementación de JwtService (15 puntos)

| Criterio | Excelente (15) | Bueno (11-14) | Suficiente (8-10) | Insuficiente (<8) |
|----------|----------------|---------------|-------------------|-------------------|
| Generación de token | Token con claims correctos, expiración, firma | Funcional con claims básicos | Token generado sin expiración | No genera token |
| Validación de token | Valida firma, expiración, extrae claims | Valida pero sin manejo de errores | Validación parcial | No valida |
| Manejo de errores | Excepciones específicas para cada caso | Manejo genérico de errores | Sin manejo de errores | Errores no controlados |

### 2.3 Implementación de AuthController (10 puntos)

| Criterio | Excelente (10) | Bueno (7-9) | Suficiente (5-6) | Insuficiente (<5) |
|----------|----------------|-------------|------------------|-------------------|
| Registro | Validación, hash de password, respuesta correcta | Funcional con validación básica | Registro sin validación | No funciona |
| Login | Autenticación, generación JWT, respuesta correcta | Login funcional | Login parcial | No funciona |

---

## 3. Evidencia de Producto (40 puntos)

### 3.1 Proyecto Funcional (25 puntos)

| Componente | Puntos | Criterio de Evaluación |
|------------|--------|------------------------|
| **SecurityConfig** | 5 | Configuración completa y correcta |
| **JwtService** | 5 | Generación y validación funcional |
| **JwtAuthenticationFilter** | 5 | Filtro integrado en cadena de seguridad |
| **AuthController** | 5 | Register, login y refresh funcionando |
| **Protección de endpoints** | 5 | Roles USER/ADMIN correctamente aplicados |

### 3.2 Calidad del Código (10 puntos)

| Criterio | Puntos | Descripción |
|----------|--------|-------------|
| Arquitectura en capas | 2 | Separación correcta de responsabilidades |
| Manejo de excepciones | 2 | Errores de auth manejados globalmente |
| Validaciones | 2 | DTOs validados correctamente |
| Nomenclatura | 2 | Nombres claros en inglés |
| Documentación Swagger | 2 | Endpoints de auth documentados |

### 3.3 Docker y Ejecución (5 puntos)

| Criterio | Puntos | Descripción |
|----------|--------|-------------|
| docker-compose up | 2 | Aplicación inicia correctamente |
| Variables de entorno | 2 | JWT_SECRET en .env, no hardcodeado |
| Persistencia | 1 | PostgreSQL con volumen |

---

## Niveles de Desempeño

### Excelente (90-100 puntos)
- Implementación completa de autenticación JWT
- Código limpio y bien estructurado
- Manejo robusto de errores de seguridad
- Documentación Swagger completa
- Tests manuales documentados

### Bueno (75-89 puntos)
- Autenticación JWT funcional
- Código organizado con pequeñas mejoras posibles
- Manejo básico de errores
- Documentación parcial

### Suficiente (60-74 puntos)
- Autenticación básica funcional
- Estructura mejorable
- Errores no manejados consistentemente
- Falta documentación

### Insuficiente (<60 puntos)
- Autenticación no funcional o incompleta
- Errores de seguridad graves
- Código desorganizado
- No ejecuta con Docker

---

## Criterios de Seguridad Obligatorios

> ⚠️ **Penalizaciones automáticas** (descuento de puntos):

| Falla de Seguridad | Penalización |
|--------------------|--------------|
| Contraseña en texto plano | -15 puntos |
| JWT secret hardcodeado | -10 puntos |
| Sin validación de inputs | -10 puntos |
| Exponer stack traces en errores | -5 puntos |
| Sin expiración en JWT | -5 puntos |

---

## Rúbrica Detallada por Criterio

### Generación de JWT

| Nivel | Descripción | Puntos |
|-------|-------------|--------|
| **Excelente** | Token con subject, roles, issued at, expiration, firma HS256/512 | 5 |
| **Bueno** | Token con claims básicos y expiración | 4 |
| **Suficiente** | Token generado sin todos los claims | 3 |
| **Insuficiente** | Token no generado correctamente | 0-2 |

### Validación de JWT

| Nivel | Descripción | Puntos |
|-------|-------------|--------|
| **Excelente** | Valida firma, expiración, claims; maneja TokenExpiredException | 5 |
| **Bueno** | Valida token correctamente sin manejo específico de errores | 4 |
| **Suficiente** | Validación parcial | 3 |
| **Insuficiente** | No valida correctamente | 0-2 |

### Protección de Endpoints

| Nivel | Descripción | Puntos |
|-------|-------------|--------|
| **Excelente** | @PreAuthorize con roles, acceso a recursos propios verificado | 5 |
| **Bueno** | Protección por roles funcional | 4 |
| **Suficiente** | Protección básica | 3 |
| **Insuficiente** | Sin protección o incorrecta | 0-2 |

---

## Entregables

1. **Código fuente** en repositorio Git
2. **README.md** con instrucciones de ejecución
3. **Capturas de Postman/curl** mostrando:
   - Registro exitoso
   - Login exitoso con JWT
   - Acceso a endpoint protegido con token
   - Rechazo sin token (401)
   - Rechazo por rol (403)
4. **docker-compose.yml** funcional

---

## Checklist de Evaluación

### Para el Instructor

- [ ] SecurityConfig correctamente configurado
- [ ] JwtService genera tokens válidos
- [ ] JwtService valida tokens correctamente
- [ ] JwtAuthenticationFilter integrado
- [ ] AuthController con register y login
- [ ] Contraseñas hasheadas con BCrypt
- [ ] Endpoints protegidos por roles
- [ ] Errores de auth manejados (401, 403)
- [ ] JWT secret en variable de entorno
- [ ] Docker compose funcional
- [ ] Swagger documenta endpoints de auth

### Pruebas Funcionales

```bash
# 1. Registro
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@example.com","password":"Test123!"}'
# Esperado: 201 Created

# 2. Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"Test123!"}'
# Esperado: 200 OK con accessToken

# 3. Acceso con token
curl -X GET http://localhost:8080/api/users/me \
  -H "Authorization: Bearer <token>"
# Esperado: 200 OK con datos del usuario

# 4. Acceso sin token
curl -X GET http://localhost:8080/api/users/me
# Esperado: 401 Unauthorized

# 5. Acceso sin permisos
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer <token_user>"
# Esperado: 403 Forbidden (si no es ADMIN)
```

---

## Feedback y Mejora Continua

| Aspecto a Evaluar | Comentarios |
|-------------------|-------------|
| Comprensión de JWT | |
| Implementación de seguridad | |
| Calidad del código | |
| Uso de Docker | |
| Áreas de mejora | |
