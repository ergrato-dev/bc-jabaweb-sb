# Glosario - Semana 8: Testing y Docker Avanzado

## Testing

### Assertion
**Aserción** - Verificación que comprueba que el resultado de una operación es el esperado. Si falla, el test falla. Ejemplo: `assertThat(result).isEqualTo(expected)`.

### Code Coverage
**Cobertura de código** - Métrica que indica qué porcentaje del código fuente es ejecutado durante los tests. Se mide con herramientas como JaCoCo.

### Fixture
**Fixture** - Datos o estado fijo usado como baseline para ejecutar tests. Configurado en métodos `@BeforeEach` o `@BeforeAll`.

### Given-When-Then
**Dado-Cuando-Entonces** - Patrón de organización de tests: Given (preparación), When (ejecución), Then (verificación). También conocido como AAA (Arrange-Act-Assert).

### Integration Test
**Test de integración** - Test que verifica la interacción entre múltiples componentes o sistemas. Usa bases de datos reales, APIs externas, etc.

### JaCoCo
**Java Code Coverage** - Herramienta de cobertura de código para Java. Genera reportes HTML y puede fallar el build si no se alcanza un mínimo.

### JUnit 5
**JUnit 5** - Framework de testing más usado en Java. Compuesto por JUnit Platform, JUnit Jupiter y JUnit Vintage.

### Mock
**Mock/Simulacro** - Objeto simulado que reemplaza dependencias reales en tests unitarios. Permite controlar comportamiento y verificar interacciones.

### MockBean
**@MockBean** - Anotación de Spring Boot Test que crea un mock y lo registra en el contexto de Spring, reemplazando el bean real.

### MockMvc
**MockMvc** - Utilidad de Spring Test para probar controladores sin levantar un servidor HTTP real. Simula peticiones y valida respuestas.

### Mockito
**Mockito** - Framework de mocking para Java. Permite crear mocks, stubs y spies. Usado con `@Mock`, `when()`, `verify()`.

### Parameterized Test
**Test parametrizado** - Test que se ejecuta múltiples veces con diferentes conjuntos de datos. Usa `@ParameterizedTest` con `@ValueSource`, `@CsvSource`, etc.

### Spy
**Spy/Espía** - Variante de mock que mantiene el comportamiento real del objeto pero permite verificar llamadas y sobrescribir métodos específicos.

### Stub
**Stub** - Configuración de un mock para retornar valores específicos. Ejemplo: `when(repository.findById(1L)).thenReturn(product)`.

### Test Pyramid
**Pirámide de testing** - Modelo que sugiere muchos tests unitarios (base), menos de integración (medio) y pocos E2E (cima). Distribución típica: 70/20/10.

### Test Slice
**Test Slice** - Configuración de Spring Boot Test que carga solo parte del contexto. Ejemplos: `@WebMvcTest`, `@DataJpaTest`.

### TestContainers
**TestContainers** - Librería que permite usar contenedores Docker en tests de integración. Inicia PostgreSQL, Redis, etc. automáticamente.

### TDD
**Test-Driven Development** - Metodología donde se escriben tests antes que el código. Ciclo: Red (test falla) → Green (código pasa) → Refactor.

### Unit Test
**Test unitario** - Test que verifica una unidad de código (método, clase) de forma aislada. Usa mocks para dependencias.

### Verify
**Verify** - Verificación de que un método mock fue llamado con ciertos argumentos. Ejemplo: `verify(repository).save(product)`.

---

## Docker

### Build Context
**Contexto de build** - Directorio enviado al daemon de Docker durante `docker build`. Definido por el PATH en el comando o en docker-compose.

### Container
**Contenedor** - Instancia ejecutable de una imagen Docker. Proceso aislado con su propio filesystem, red y recursos.

### Depends_on
**depends_on** - Configuración en docker-compose que define el orden de inicio de servicios. Con `condition: service_healthy` espera healthcheck.

### Docker Compose
**Docker Compose** - Herramienta para definir y ejecutar aplicaciones multi-contenedor con un archivo YAML.

### Healthcheck
**Healthcheck** - Comando que Docker ejecuta periódicamente para verificar si un contenedor está "healthy" (saludable).

### Image
**Imagen** - Template de solo lectura usado para crear contenedores. Se construye a partir de un Dockerfile.

### Multi-stage Build
**Build multi-etapa** - Técnica de Dockerfile que usa múltiples `FROM` para optimizar imágenes. Separa build de runtime.

### Network
**Red** - Red virtual de Docker que permite comunicación entre contenedores. Incluye DNS interno automático.

### Override File
**Archivo override** - `docker-compose.override.yml` se fusiona automáticamente con docker-compose.yml. Usado para configuración de desarrollo.

### Profile
**Perfil** - Configuración en docker-compose que permite agrupar servicios opcionales. Se activa con `--profile`.

### Service
**Servicio** - Definición de contenedor en docker-compose. Incluye imagen, puertos, volúmenes, etc.

### Volume
**Volumen** - Mecanismo de persistencia de datos en Docker. Los datos sobreviven al reinicio del contenedor.

---

## Spring Boot Test

### @DataJpaTest
**@DataJpaTest** - Test slice que carga solo componentes JPA (entities, repositories). Usa H2 por defecto.

### @MockBean
**@MockBean** - Crea un mock de un bean y lo registra en el contexto de Spring, reemplazando cualquier bean existente del mismo tipo.

### @SpringBootTest
**@SpringBootTest** - Carga el contexto completo de Spring Boot para tests de integración.

### @WebMvcTest
**@WebMvcTest** - Test slice que carga solo la capa web (controllers, filters). Requiere @MockBean para servicios.

### @WithMockUser
**@WithMockUser** - Configura un usuario mock para tests de seguridad. Permite especificar roles y authorities.

### TestRestTemplate
**TestRestTemplate** - Cliente HTTP para tests de integración que hace peticiones reales al servidor embedded.

---

## Abreviaturas Comunes

| Sigla | Significado |
|-------|-------------|
| TDD | Test-Driven Development |
| E2E | End-to-End (testing) |
| SUT | System Under Test |
| CI | Continuous Integration |
| CD | Continuous Deployment |
| AAA | Arrange-Act-Assert |
| BDD | Behavior-Driven Development |
