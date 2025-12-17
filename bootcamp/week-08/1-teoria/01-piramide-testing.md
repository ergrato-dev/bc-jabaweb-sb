# Pirámide de Testing

## 📚 Conceptos Fundamentales

### ¿Qué es la Pirámide de Testing?

La **pirámide de testing** es un modelo conceptual que define la proporción óptima de tests en una aplicación. Fue popularizada por Mike Cohn y establece que debes tener:

- **Muchos tests unitarios** (base) - Rápidos y baratos
- **Algunos tests de integración** (medio) - Balance costo/valor
- **Pocos tests E2E** (cima) - Lentos y costosos

```
        ╱╲
       ╱E2E╲         10% - UI/Sistema completo
      ╱──────╲
     ╱        ╲
    ╱Integration╲    20% - Componentes juntos
   ╱────────────╲
  ╱              ╲
 ╱   Unit Tests   ╲  70% - Unidades aisladas
╱────────────────────╲
```

---

## 🔍 Tipos de Tests

### 1. Tests Unitarios (Unit Tests)

**Objetivo**: Probar una unidad de código en **aislamiento total**.

**Características**:
- ⚡ Muy rápidos (milisegundos)
- 🎯 Prueban una sola clase/método
- 🔒 Dependencias mockeadas
- 📊 Mayor cobertura, menor costo

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should return user when found by ID")
    void findById_ExistingId_ReturnsUser() {
        // Given
        User user = new User(1L, "john@email.com", "John");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // When
        Optional<User> result = userService.findById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("john@email.com");
        verify(userRepository).findById(1L);
    }
}
```

**¿Qué probar?**
- Lógica de negocio en Services
- Validaciones y transformaciones
- Casos edge y excepciones
- Cálculos y algoritmos

---

### 2. Tests de Integración (Integration Tests)

**Objetivo**: Probar **interacción entre componentes**.

**Características**:
- ⏱️ Moderadamente lentos (segundos)
- 🔗 Prueban flujos entre capas
- 🗄️ Pueden usar BD real o en memoria
- 🌐 Verifican configuración Spring

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /api/users should create user")
    void createUser_ValidData_ReturnsCreated() throws Exception {
        // Given
        String requestBody = """
            {
                "email": "test@email.com",
                "name": "Test User",
                "password": "SecurePass123!"
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.email").value("test@email.com"))
            .andExpect(jsonPath("$.id").exists());
    }
}
```

**¿Qué probar?**
- Controllers con MockMvc
- Repositories con BD
- Flujos completos de API
- Configuración de Spring

---

### 3. Tests End-to-End (E2E)

**Objetivo**: Probar el **sistema completo** como un usuario real.

**Características**:
- 🐢 Muy lentos (minutos)
- 🖥️ Incluyen UI y backend
- 💰 Costosos de mantener
- 🔄 Frágiles ante cambios

```java
// Usando TestContainers + RestAssured
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserE2ETest {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:16-alpine");

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Full user registration flow")
    void userRegistrationFlow() {
        // Register
        given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(new RegisterRequest("user@test.com", "password123"))
        .when()
            .post("/api/auth/register")
        .then()
            .statusCode(201);

        // Login
        String token = given()
            .port(port)
            .contentType(ContentType.JSON)
            .body(new LoginRequest("user@test.com", "password123"))
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(200)
            .extract()
            .path("token");

        // Access protected resource
        given()
            .port(port)
            .header("Authorization", "Bearer " + token)
        .when()
            .get("/api/users/me")
        .then()
            .statusCode(200)
            .body("email", equalTo("user@test.com"));
    }
}
```

---

## 📊 Comparativa de Tipos de Tests

| Aspecto | Unitarios | Integración | E2E |
|---------|-----------|-------------|-----|
| **Velocidad** | ~1ms | ~100ms-1s | ~10s-1min |
| **Costo** | Bajo | Medio | Alto |
| **Confiabilidad** | Alta | Media | Baja |
| **Mantenimiento** | Fácil | Moderado | Difícil |
| **Cobertura** | Profunda | Amplia | Superficial |
| **Dependencias** | Mocks | Algunas reales | Todas reales |
| **Feedback** | Inmediato | Rápido | Lento |

---

## 🎯 Distribución Recomendada

### Para APIs REST (Spring Boot)

```
Tests E2E (10%):
├── Flujos críticos de negocio
├── Happy paths principales
└── Smoke tests

Tests de Integración (20%):
├── Controllers con MockMvc
├── Repositories con H2/TestContainers
├── Configuración de seguridad
└── Validación de DTOs

Tests Unitarios (70%):
├── Services (lógica de negocio)
├── Mappers (DTOs ↔ Entities)
├── Validadores custom
├── Utilidades
└── Excepciones
```

### Cobertura por Capa

| Capa | Cobertura Objetivo | Tipo de Test Principal |
|------|-------------------|----------------------|
| Controllers | 70-80% | Integración (MockMvc) |
| Services | 85-95% | Unitarios (Mockito) |
| Repositories | 60-70% | Integración (TestContainers) |
| DTOs/Entities | 50-60% | Unitarios |
| Utils | 90%+ | Unitarios |

---

## 🔄 Anti-patrones a Evitar

### ❌ Pirámide Invertida (Ice Cream Cone)

```
     ╱──────────────╲
    ╱      E2E       ╲     ← Muchos tests E2E
   ╱──────────────────╲
  ╱    Integration     ╲   ← Pocos de integración
 ╱────────────────────╲
╱        Unit          ╲   ← Casi ningún unitario
```

**Problemas**:
- Tests lentos
- Feedback tardío
- Difícil localizar errores
- Alto costo de mantenimiento

### ❌ Hourglass (Reloj de Arena)

```
     ╱────────╲
    ╱   E2E    ╲         ← Muchos E2E
   ╱────────────╲
        │
        │                ← Pocos de integración
        │
   ╱────────────╲
  ╱    Unit      ╲       ← Muchos unitarios
 ╱────────────────╲
```

**Problemas**:
- Gap en capa de integración
- No valida interacciones
- Falsa confianza

---

## ✅ Mejores Prácticas

### 1. Nomenclatura de Tests

```java
// Patrón: methodName_condition_expectedResult
@Test
void findById_ExistingUser_ReturnsUser() { }

@Test
void findById_NonExistingUser_ReturnsEmpty() { }

@Test
void createUser_DuplicateEmail_ThrowsException() { }
```

### 2. Estructura AAA / Given-When-Then

```java
@Test
void calculateTotal_WithDiscount_ReturnsDiscountedPrice() {
    // Arrange (Given)
    Order order = new Order();
    order.addItem(new Item("Product", 100.0));
    order.setDiscountPercentage(10);

    // Act (When)
    double total = orderService.calculateTotal(order);

    // Assert (Then)
    assertThat(total).isEqualTo(90.0);
}
```

### 3. Tests Independientes

```java
@BeforeEach
void setUp() {
    // Limpiar estado antes de cada test
    userRepository.deleteAll();
    // Configurar datos de prueba
    testUser = createTestUser();
}

@AfterEach
void tearDown() {
    // Limpiar recursos si es necesario
}
```

### 4. Un Assert Lógico por Test

```java
// ✅ Correcto: Un concepto por test
@Test
void createUser_ValidData_ReturnsUserWithCorrectData() {
    User result = userService.create(validDto);

    assertThat(result)
        .isNotNull()
        .satisfies(user -> {
            assertThat(user.getId()).isNotNull();
            assertThat(user.getEmail()).isEqualTo(validDto.getEmail());
            assertThat(user.getName()).isEqualTo(validDto.getName());
        });
}

// ❌ Incorrecto: Múltiples conceptos
@Test
void createUser_MultipleAssertions() {
    // Crea usuario
    User user = userService.create(dto);
    assertThat(user).isNotNull();

    // Verifica que se guardó
    Optional<User> found = userRepository.findById(user.getId());
    assertThat(found).isPresent();

    // Verifica email enviado
    verify(emailService).sendWelcome(user.getEmail());
}
```

---

## 📈 Métricas de Cobertura

### JaCoCo Configuration

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
        <execution>
            <id>check</id>
            <goals>
                <goal>check</goal>
            </goals>
            <configuration>
                <rules>
                    <rule>
                        <element>BUNDLE</element>
                        <limits>
                            <limit>
                                <counter>LINE</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.70</minimum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

### Interpretar Cobertura

| Métrica | Descripción | Objetivo |
|---------|-------------|----------|
| Line Coverage | Líneas ejecutadas | >70% |
| Branch Coverage | Ramas if/else | >60% |
| Method Coverage | Métodos llamados | >80% |
| Class Coverage | Clases testeadas | >90% |

> ⚠️ **Nota**: Alta cobertura ≠ Buenos tests. Un test sin assertions tiene cobertura pero no valor.

---

## 🔗 Recursos Adicionales

- [Martin Fowler - Test Pyramid](https://martinfowler.com/bliki/TestPyramid.html)
- [Google Testing Blog](https://testing.googleblog.com/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
