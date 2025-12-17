# Guía de Presentación y Defensa del Proyecto

## 📋 Índice

1. [Objetivo de la Presentación](#objetivo-de-la-presentación)
2. [Estructura de la Presentación](#estructura-de-la-presentación)
3. [Demo en Vivo](#demo-en-vivo)
4. [Preguntas Frecuentes](#preguntas-frecuentes)
5. [Consejos para la Defensa](#consejos-para-la-defensa)
6. [Criterios de Evaluación](#criterios-de-evaluación)
7. [Checklist Pre-Presentación](#checklist-pre-presentación)

---

## Objetivo de la Presentación

La presentación tiene como objetivo evaluar:

1. **Conocimiento técnico**: Que entiendas lo que has implementado
2. **Capacidad de comunicación**: Explicar conceptos técnicos claramente
3. **Pensamiento crítico**: Justificar decisiones de diseño
4. **Dominio del proyecto**: Conocer cada parte del código

### Duración

| Componente | Tiempo |
|------------|--------|
| Presentación + Demo | 5-7 minutos |
| Preguntas técnicas | 3-5 minutos |
| **Total** | **8-12 minutos** |

---

## Estructura de la Presentación

### 1. Introducción (1 minuto)

**Qué incluir:**
- Nombre del proyecto
- Propósito/problema que resuelve
- Stack tecnológico principal

**Ejemplo:**
> "Mi proyecto es una API REST para un sistema de gestión de pedidos de un e-commerce. Permite a usuarios registrarse, autenticarse, ver productos y realizar pedidos. Está construido con Spring Boot 3.2, PostgreSQL y Docker."

### 2. Arquitectura (1-2 minutos)

**Qué incluir:**
- Diagrama de arquitectura (Docker Compose)
- Arquitectura interna (capas)
- Modelo de datos (entidades y relaciones)

**Ejemplo de explicación:**
> "La arquitectura usa Docker Compose con 3 servicios: la aplicación Spring Boot, PostgreSQL y pgAdmin. Internamente sigo una arquitectura en capas: Controllers para recibir requests, Services para lógica de negocio, y Repositories para acceso a datos. El modelo tiene 5 entidades relacionadas: User, Product, Category, Order y OrderItem."

### 3. Demo en Vivo (2-3 minutos)

**Qué mostrar:**
1. Ejecutar `docker-compose up`
2. Swagger UI
3. Flujo de registro y login
4. Obtener JWT y usarlo
5. CRUD de una entidad
6. Crear un pedido

### 4. Testing (1 minuto)

**Qué incluir:**
- Tipos de tests implementados
- Cobertura alcanzada
- Herramientas usadas

**Ejemplo:**
> "Implementé tests unitarios con Mockito para los servicios, tests de integración con MockMvc para los controllers, y tests con TestContainers para los repositorios. La cobertura es del 75%."

### 5. Conclusión (30 segundos)

**Qué incluir:**
- Resumen de logros
- Desafíos superados
- Posibles mejoras futuras

---

## Demo en Vivo

### Preparación

```bash
# Antes de la presentación, verifica que todo funciona
docker-compose down -v
docker-compose up --build -d
docker-compose logs -f app  # En otra terminal
```

### Script de Demo Sugerido

#### Paso 1: Mostrar que está corriendo

```bash
# Terminal
docker-compose ps
```

#### Paso 2: Swagger UI

```
Abrir navegador: http://localhost:8080/swagger-ui.html
```

Mostrar:
- Lista de endpoints organizados
- Schemas de datos
- Autenticación configurada

#### Paso 3: Registro de Usuario

```bash
# En Swagger UI o curl
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Demo User",
    "email": "demo@example.com",
    "password": "password123"
  }'
```

#### Paso 4: Login y obtener JWT

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "demo@example.com",
    "password": "password123"
  }'
```

Copiar el token de la respuesta.

#### Paso 5: Usar JWT en Swagger

1. Click en "Authorize" 🔓
2. Pegar: `Bearer eyJ...`
3. Click "Authorize"

#### Paso 6: Operaciones CRUD

```bash
# GET productos
curl http://localhost:8080/api/products

# POST crear producto (si es admin)
curl -X POST http://localhost:8080/api/products \
  -H "Authorization: Bearer eyJ..." \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Producto Demo",
    "price": 29.99,
    "stock": 100,
    "categoryId": 1
  }'
```

#### Paso 7: Crear Pedido

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Authorization: Bearer eyJ..." \
  -H "Content-Type: application/json" \
  -d '{
    "items": [
      {"productId": 1, "quantity": 2}
    ]
  }'
```

### Plan B (Si algo falla)

Tener preparado:
1. Screenshots del funcionamiento
2. Video grabado de la demo
3. Postman collection exportada

```bash
# Si Docker falla, mostrar los tests
./mvnw test
```

---

## Preguntas Frecuentes

### Sobre Arquitectura

**P: ¿Por qué elegiste arquitectura en capas?**
> "Elegí arquitectura en capas porque separa claramente las responsabilidades. Los controllers solo manejan HTTP, los services contienen la lógica de negocio, y los repositories acceden a datos. Esto hace el código más testeable y mantenible."

**P: ¿Por qué usas DTOs en lugar de devolver entidades directamente?**
> "Los DTOs me permiten controlar exactamente qué datos expongo en la API. Las entidades JPA pueden tener relaciones circulares que causan problemas de serialización, y además no quiero exponer campos como passwords o datos internos."

**P: ¿Cómo fluye una petición desde el cliente hasta la base de datos?**
> "La petición llega al controller que valida el input, luego pasa al service que aplica la lógica de negocio, el service usa repositories para acceder a la base de datos, y la respuesta vuelve por el mismo camino pero convirtiendo entidades a DTOs."

### Sobre Seguridad

**P: ¿Cómo funciona la autenticación JWT?**
> "Cuando el usuario hace login, verifico sus credenciales contra la base de datos. Si son correctas, genero un JWT con el email del usuario y su rol. Este token tiene firma digital y expiración. En cada request subsecuente, el JwtFilter extrae el token del header, valida la firma y la expiración, y establece el SecurityContext con los datos del usuario."

**P: ¿Por qué usas JWT en lugar de sesiones?**
> "JWT es stateless, lo que significa que el servidor no necesita guardar estado. Esto permite escalar horizontalmente añadiendo más instancias de la aplicación sin preocuparse por sincronizar sesiones. Además, es un estándar de la industria para APIs REST."

**P: ¿Dónde guardas el JWT secret?**
> "El secret está en variables de entorno, definidas en el archivo .env que no se sube a Git. En producción se usaría un servicio de secrets management como HashiCorp Vault o AWS Secrets Manager."

**P: ¿Cómo previenes SQL Injection?**
> "Spring Data JPA usa prepared statements automáticamente, lo que previene SQL injection. Nunca concateno strings para formar queries SQL."

### Sobre Base de Datos

**P: ¿Por qué PostgreSQL?**
> "PostgreSQL es una base de datos robusta, con soporte completo para transacciones ACID, buen rendimiento, y es ampliamente usada en producción. Además, tiene excelente integración con Spring Data JPA."

**P: ¿Cómo manejas las relaciones entre entidades?**
> "Uso anotaciones JPA como @ManyToOne y @OneToMany. Por ejemplo, un Order tiene muchos OrderItems (@OneToMany), y cada OrderItem pertenece a un Product (@ManyToOne). Configuro fetch LAZY para evitar cargar datos innecesarios."

**P: ¿Qué pasa si se cae el contenedor de PostgreSQL?**
> "Los datos persisten gracias al volumen de Docker. Cuando el contenedor se reinicia, la base de datos carga los datos del volumen. En producción usaría backups automáticos adicionales."

### Sobre Docker

**P: ¿Por qué multi-stage build en el Dockerfile?**
> "El multi-stage build me permite tener una imagen de build con todas las herramientas de Maven, y una imagen final solo con el JRE y el JAR compilado. Esto reduce el tamaño de la imagen final de ~500MB a ~200MB."

**P: ¿Cómo se comunican los contenedores?**
> "Docker Compose crea una red interna donde los contenedores se pueden comunicar usando sus nombres de servicio como hostname. Por ejemplo, la app se conecta a PostgreSQL usando 'db:5432' donde 'db' es el nombre del servicio."

**P: ¿Qué son los healthchecks?**
> "Los healthchecks verifican que un servicio está realmente funcionando, no solo que el contenedor está corriendo. Por ejemplo, el healthcheck de PostgreSQL hace un `pg_isready` para verificar que la base de datos está lista para aceptar conexiones."

### Sobre Testing

**P: ¿Qué diferencia hay entre tests unitarios y de integración?**
> "Los tests unitarios prueban una clase aislada, usando mocks para sus dependencias. Los tests de integración prueban múltiples componentes trabajando juntos. Por ejemplo, un test de controller verifica que la request HTTP llega, se procesa, y retorna la respuesta correcta."

**P: ¿Qué es TestContainers y por qué lo usas?**
> "TestContainers levanta contenedores Docker reales durante los tests. Lo uso para tests de repositories porque prueban contra PostgreSQL real en lugar de una base de datos en memoria, lo que es más cercano a producción."

**P: ¿Cómo llegaste al 70% de cobertura?**
> "Me enfoqué en testear la lógica de negocio en los services, los flujos de autenticación, y los endpoints principales. JaCoCo me muestra qué líneas faltan por cubrir."

---

## Consejos para la Defensa

### Antes de la Presentación

1. **Practica la demo** al menos 3 veces
2. **Prepara respuestas** para preguntas comunes
3. **Conoce tu código** - no solo lo que hace, sino POR QUÉ
4. **Ten un plan B** si algo falla

### Durante la Presentación

1. **Habla con confianza** - conoces tu proyecto
2. **No leas** - usa puntos clave como guía
3. **Mantén contacto visual**
4. **Si no sabes algo**, di "No estoy seguro, pero investigaré"
5. **No tengas miedo de los errores** - explica cómo los resolverías

### Errores Comunes a Evitar

| ❌ Evitar | ✅ Mejor |
|-----------|---------|
| Leer código línea por línea | Explicar conceptos y flujos |
| Decir "no sé" sin más | "No estoy seguro, pero creo que..." |
| Justificar errores | Reconocer y explicar cómo mejorar |
| Hablar muy técnico | Usar analogías cuando sea apropiado |
| Memorizar sin entender | Entender los "por qué" |

---

## Criterios de Evaluación

### Rúbrica de Presentación

| Criterio | Excelente (5) | Bueno (4) | Suficiente (3) | Insuficiente (≤2) |
|----------|--------------|-----------|----------------|-------------------|
| **Claridad** | Explicación muy clara y estructurada | Clara con algunos detalles menores | Comprensible pero desorganizada | Confusa o incompleta |
| **Demo** | Fluida, sin errores, bien preparada | Funcional con errores menores | Funciona parcialmente | No funciona o no preparada |
| **Conocimiento** | Domina todos los aspectos | Conoce la mayoría | Conocimiento básico | Desconoce partes importantes |
| **Respuestas** | Responde todo correctamente | Responde la mayoría | Responde lo básico | No puede responder |
| **Tiempo** | Ajustado perfectamente | Ligeramente corto/largo | Significativamente desajustado | Muy corto o muy largo |

### Peso en la Nota Final

| Componente | Peso |
|------------|------|
| Presentación oral | 10% |
| Demo funcional | 10% |
| Respuestas técnicas | 10% |
| Defensa general | 10% |
| **Total Presentación** | **40%** |

---

## Checklist Pre-Presentación

### 24 Horas Antes

- [ ] Proyecto funciona con `docker-compose up`
- [ ] Swagger UI accesible
- [ ] Flujo de auth funciona
- [ ] Tests pasan
- [ ] README actualizado

### 1 Hora Antes

- [ ] Equipo cargado / conectado a corriente
- [ ] Docker Desktop corriendo
- [ ] Contenedores construidos y listos
- [ ] Navegador abierto en Swagger UI
- [ ] Terminal lista con comandos frecuentes

### 5 Minutos Antes

- [ ] Cerrar aplicaciones innecesarias
- [ ] Silenciar notificaciones
- [ ] Verificar que la demo sigue funcionando
- [ ] Respirar profundo 😊

### Comandos Rápidos para Tener a Mano

```bash
# Levantar todo
docker-compose up -d

# Ver logs
docker-compose logs -f app

# Verificar estado
docker-compose ps

# Restart rápido
docker-compose restart app

# Nuclear option
docker-compose down && docker-compose up --build -d
```

---

## Ejemplo de Guión de Presentación

> "Buenos días, soy [nombre] y voy a presentar mi proyecto final: [nombre del proyecto].
>
> **El problema** que resuelve es [breve descripción]. Por ejemplo, [caso de uso].
>
> **La arquitectura** está compuesta por [describir Docker Compose]. Internamente uso arquitectura en capas con [describir].
>
> **El modelo de datos** tiene [X] entidades: [listar]. Las relaciones principales son [describir].
>
> Ahora voy a hacer una **demo en vivo**. Primero verificamos que todo está corriendo... [demo].
>
> En cuanto a **testing**, implementé [tipos de tests] con una cobertura del [X]%.
>
> Los **principales desafíos** que encontré fueron [1-2 desafíos] y los resolví [cómo].
>
> Para **mejorar el proyecto** en el futuro, añadiría [mejoras].
>
> Estoy listo para responder preguntas."

---

> **💡 Recuerda**: Lo más importante es demostrar que ENTIENDES tu código, no solo que funciona. Los evaluadores buscan pensamiento crítico y capacidad de explicar decisiones técnicas.
