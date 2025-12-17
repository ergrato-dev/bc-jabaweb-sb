# TestContainers

## 📚 ¿Qué es TestContainers?

**TestContainers** es una biblioteca Java que permite ejecutar contenedores Docker durante los tests de integración. Proporciona instancias desechables de bases de datos, message brokers, y otros servicios.

### Ventajas

- ✅ **Tests con BD real**: No más H2 que se comporta diferente a PostgreSQL
- ✅ **Aislamiento**: Cada test suite tiene su contenedor limpio
- ✅ **Reproducibilidad**: Mismo entorno en local y CI/CD
- ✅ **Sin configuración**: No necesitas instalar servicios localmente

### Desventajas

- ⚠️ Requiere Docker instalado
- ⚠️ Tests más lentos que con BD en memoria
- ⚠️ Consume más recursos

---

## 🔧 Configuración

### Dependencias Maven

```xml
<!-- TestContainers BOM -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>testcontainers-bom</artifactId>
            <version>1.19.3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- Core TestContainers -->
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>testcontainers</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- JUnit 5 Integration -->
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- PostgreSQL Module -->
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>postgresql</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🐘 PostgreSQL Container

### Configuración Básica

```java
@Testcontainers
@SpringBootTest
class UserRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndRetrieveUser() {
        // Given
        User user = new User("john@email.com", "John", "encodedPass");

        // When
        User saved = userRepository.save(user);

        // Then
        assertThat(saved.getId()).isNotNull();

        Optional<User> found = userRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("john@email.com");
    }
}
```

### Explicación

| Anotación/Método | Propósito |
|------------------|-----------|
| `@Testcontainers` | Habilita la integración con JUnit 5 |
| `@Container` | Marca el contenedor a gestionar |
| `static` | Contenedor compartido entre tests de la clase |
| `@DynamicPropertySource` | Inyecta propiedades dinámicas en Spring |

---

## 🔄 Ciclo de Vida

### Contenedor por Clase (Recomendado)

```java
@Testcontainers
@SpringBootTest
class SharedContainerTest {

    // static = contenedor compartido por todos los tests de esta clase
    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeEach
    void setUp() {
        // Limpiar datos entre tests
        userRepository.deleteAll();
    }

    @Test
    void test1() { /* usa el mismo contenedor */ }

    @Test
    void test2() { /* usa el mismo contenedor */ }
}
```

### Contenedor por Test (Más Aislado, Más Lento)

```java
@Testcontainers
@SpringBootTest
class IsolatedContainerTest {

    // Sin static = nuevo contenedor para cada test
    @Container
    PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:16-alpine");

    @Test
    void test1() { /* nuevo contenedor */ }

    @Test
    void test2() { /* otro contenedor nuevo */ }
}
```

### Contenedor Singleton (Más Rápido)

```java
// Clase abstracta base
abstract class AbstractIntegrationTest {

    static final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withReuse(true);  // Reutilizar entre ejecuciones
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}

// Tests heredan de la clase base
@SpringBootTest
class UserRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void testSomething() {
        // Usa el contenedor singleton
    }
}
```

---

## 📝 Ejemplos Prácticos

### Test de Repository con PostgreSQL

```java
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductRepositoryIT {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should find products by category")
    void findByCategory_ExistingCategory_ReturnsProducts() {
        // Given
        Product electronics1 = new Product("Laptop", "Electronics", 999.99);
        Product electronics2 = new Product("Phone", "Electronics", 599.99);
        Product furniture = new Product("Chair", "Furniture", 199.99);

        entityManager.persist(electronics1);
        entityManager.persist(electronics2);
        entityManager.persist(furniture);
        entityManager.flush();

        // When
        List<Product> result = productRepository.findByCategory("Electronics");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Product::getName)
            .containsExactlyInAnyOrder("Laptop", "Phone");
    }

    @Test
    @DisplayName("Should find products with price less than")
    void findByPriceLessThan_ValidPrice_ReturnsMatchingProducts() {
        // Given
        entityManager.persist(new Product("Cheap", "Cat1", 50.0));
        entityManager.persist(new Product("Medium", "Cat1", 150.0));
        entityManager.persist(new Product("Expensive", "Cat1", 500.0));
        entityManager.flush();

        // When
        List<Product> result = productRepository.findByPriceLessThan(200.0);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Product::getName)
            .containsExactlyInAnyOrder("Cheap", "Medium");
    }
}
```

### Test de Service con BD Real

```java
@Testcontainers
@SpringBootTest
@Transactional
class OrderServiceIT {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("Should create order with multiple products")
    void createOrder_MultipleProducts_CalculatesTotalCorrectly() {
        // Given
        Product product1 = productRepository.save(
            new Product("Product 1", "Cat", 100.0));
        Product product2 = productRepository.save(
            new Product("Product 2", "Cat", 200.0));

        CreateOrderDTO orderDto = new CreateOrderDTO(
            List.of(
                new OrderItemDTO(product1.getId(), 2),  // 2 x 100 = 200
                new OrderItemDTO(product2.getId(), 1)   // 1 x 200 = 200
            )
        );

        // When
        OrderDTO result = orderService.createOrder(orderDto);

        // Then
        assertThat(result.getTotal()).isEqualTo(400.0);
        assertThat(result.getItems()).hasSize(2);

        // Verify persistence
        Order savedOrder = orderRepository.findById(result.getId()).orElseThrow();
        assertThat(savedOrder.getTotal()).isEqualTo(400.0);
    }
}
```

### Test E2E con TestContainers

```java
@Testcontainers
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthenticationE2ETest {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Full authentication flow: register -> login -> access protected resource")
    void fullAuthenticationFlow() {
        String baseUrl = "http://localhost:" + port;

        // 1. Register
        RegisterRequest registerRequest = new RegisterRequest(
            "test@email.com", "Test User", "SecurePass123!");

        ResponseEntity<AuthResponse> registerResponse = restTemplate.postForEntity(
            baseUrl + "/api/auth/register",
            registerRequest,
            AuthResponse.class
        );

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(registerResponse.getBody().getToken()).isNotEmpty();

        // 2. Login
        LoginRequest loginRequest = new LoginRequest("test@email.com", "SecurePass123!");

        ResponseEntity<AuthResponse> loginResponse = restTemplate.postForEntity(
            baseUrl + "/api/auth/login",
            loginRequest,
            AuthResponse.class
        );

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = loginResponse.getBody().getToken();
        assertThat(token).isNotEmpty();

        // 3. Access protected resource with token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDTO> protectedResponse = restTemplate.exchange(
            baseUrl + "/api/users/me",
            HttpMethod.GET,
            entity,
            UserDTO.class
        );

        assertThat(protectedResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(protectedResponse.getBody().getEmail()).isEqualTo("test@email.com");

        // 4. Verify access denied without token
        ResponseEntity<String> unauthorizedResponse = restTemplate.getForEntity(
            baseUrl + "/api/users/me",
            String.class
        );

        assertThat(unauthorizedResponse.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
```

---

## 🐳 Otros Contenedores Disponibles

### Redis

```java
@Container
static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
    .withExposedPorts(6379);

@DynamicPropertySource
static void configureRedis(DynamicPropertyRegistry registry) {
    registry.add("spring.redis.host", redis::getHost);
    registry.add("spring.redis.port", redis::getFirstMappedPort);
}
```

### MySQL

```java
@Container
static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8")
    .withDatabaseName("testdb")
    .withUsername("test")
    .withPassword("test");
```

### MongoDB

```java
@Container
static MongoDBContainer mongo = new MongoDBContainer("mongo:7");

@DynamicPropertySource
static void configureMongo(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
}
```

### Kafka

```java
@Container
static KafkaContainer kafka = new KafkaContainer(
    DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));

@DynamicPropertySource
static void configureKafka(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
}
```

---

## ⚡ Optimización

### Reutilización de Contenedores

```java
// En ~/.testcontainers.properties
testcontainers.reuse.enable=true
```

```java
@Container
static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
    .withReuse(true);  // Mantener contenedor entre ejecuciones
```

### Parallel Tests

```java
// En src/test/resources/junit-platform.properties
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent
junit.jupiter.execution.parallel.mode.classes.default=concurrent
```

### Wait Strategies

```java
@Container
static GenericContainer<?> customService = new GenericContainer<>("custom:latest")
    .withExposedPorts(8080)
    .waitingFor(Wait.forHttp("/health")
        .forStatusCode(200)
        .withStartupTimeout(Duration.ofSeconds(60)));
```

---

## 🔗 Recursos

- [TestContainers Documentation](https://www.testcontainers.org/)
- [TestContainers for Java](https://java.testcontainers.org/)
- [Spring Boot + TestContainers](https://spring.io/blog/2023/06/23/improved-testcontainers-support-in-spring-boot-3-1)
