# Testing en Spring Boot

## 📚 Conceptos Fundamentales

### Spring Boot Test Starter

Spring Boot proporciona un conjunto completo de herramientas para testing:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

**Incluye automáticamente**:
- JUnit 5
- Mockito
- AssertJ
- Hamcrest
- JSONPath
- Spring Test
- Spring Boot Test

---

## 🏷️ Test Slices - Contextos Parciales

Spring Boot ofrece "slices" para cargar solo las partes necesarias del contexto:

| Anotación | Qué carga | Uso |
|-----------|-----------|-----|
| `@SpringBootTest` | Contexto completo | Tests E2E, integración completa |
| `@WebMvcTest` | Solo capa web | Tests de controllers |
| `@DataJpaTest` | Solo capa de datos | Tests de repositories |
| `@JsonTest` | Solo serialización JSON | Tests de DTOs |
| `@RestClientTest` | Clientes REST | Tests de clientes externos |

---

## 🌐 @WebMvcTest - Testing de Controllers

### Configuración Básica

```java
@WebMvcTest(UserController.class)  // Solo carga este controller
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Cliente HTTP simulado

    @MockBean  // Mock de Spring (diferente a @Mock de Mockito)
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;  // Para serializar JSON
}
```

### Test GET - Listar Usuarios

```java
@Test
@DisplayName("GET /api/users should return list of users")
void getUsers_ReturnsUserList() throws Exception {
    // Given
    List<UserDTO> users = List.of(
        new UserDTO(1L, "john@email.com", "John"),
        new UserDTO(2L, "jane@email.com", "Jane")
    );
    when(userService.findAll()).thenReturn(users);

    // When & Then
    mockMvc.perform(get("/api/users"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].email").value("john@email.com"))
        .andExpect(jsonPath("$[1].name").value("Jane"));
}
```

### Test GET - Usuario por ID

```java
@Test
@DisplayName("GET /api/users/{id} should return user when exists")
void getUserById_ExistingId_ReturnsUser() throws Exception {
    // Given
    UserDTO user = new UserDTO(1L, "john@email.com", "John");
    when(userService.findById(1L)).thenReturn(Optional.of(user));

    // When & Then
    mockMvc.perform(get("/api/users/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.email").value("john@email.com"));
}

@Test
@DisplayName("GET /api/users/{id} should return 404 when not exists")
void getUserById_NonExistingId_Returns404() throws Exception {
    // Given
    when(userService.findById(999L)).thenReturn(Optional.empty());

    // When & Then
    mockMvc.perform(get("/api/users/{id}", 999))
        .andExpect(status().isNotFound());
}
```

### Test POST - Crear Usuario

```java
@Test
@DisplayName("POST /api/users should create user with valid data")
void createUser_ValidData_Returns201() throws Exception {
    // Given
    CreateUserDTO request = new CreateUserDTO("john@email.com", "John", "Pass123!");
    UserDTO response = new UserDTO(1L, "john@email.com", "John");

    when(userService.create(any(CreateUserDTO.class))).thenReturn(response);

    // When & Then
    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.email").value("john@email.com"));

    // Verify service was called
    verify(userService).create(any(CreateUserDTO.class));
}
```

### Test POST - Validación de Datos

```java
@Test
@DisplayName("POST /api/users should return 400 with invalid email")
void createUser_InvalidEmail_Returns400() throws Exception {
    // Given
    String invalidRequest = """
        {
            "email": "invalid-email",
            "name": "John",
            "password": "Pass123!"
        }
        """;

    // When & Then
    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors").exists())
        .andExpect(jsonPath("$.errors.email").value("must be a well-formed email address"));

    // Verify service was NOT called
    verify(userService, never()).create(any());
}

@Test
@DisplayName("POST /api/users should return 400 with blank name")
void createUser_BlankName_Returns400() throws Exception {
    // Given
    String invalidRequest = """
        {
            "email": "john@email.com",
            "name": "",
            "password": "Pass123!"
        }
        """;

    // When & Then
    mockMvc.perform(post("/api/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidRequest))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.name").exists());
}
```

### Test PUT - Actualizar Usuario

```java
@Test
@DisplayName("PUT /api/users/{id} should update user")
void updateUser_ValidData_Returns200() throws Exception {
    // Given
    UpdateUserDTO request = new UpdateUserDTO("John Updated");
    UserDTO response = new UserDTO(1L, "john@email.com", "John Updated");

    when(userService.update(eq(1L), any(UpdateUserDTO.class))).thenReturn(response);

    // When & Then
    mockMvc.perform(put("/api/users/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John Updated"));
}
```

### Test DELETE

```java
@Test
@DisplayName("DELETE /api/users/{id} should delete user")
void deleteUser_ExistingId_Returns204() throws Exception {
    // Given
    doNothing().when(userService).delete(1L);

    // When & Then
    mockMvc.perform(delete("/api/users/{id}", 1))
        .andExpect(status().isNoContent());

    verify(userService).delete(1L);
}
```

---

## 🗄️ @DataJpaTest - Testing de Repositories

### Configuración

```java
@DataJpaTest  // Solo carga JPA, usa H2 por defecto
@AutoConfigureTestDatabase(replace = Replace.NONE)  // Usar BD configurada
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;  // Para setup de datos
}
```

### Tests de Repository

```java
@Test
@DisplayName("Should find user by email")
void findByEmail_ExistingEmail_ReturnsUser() {
    // Given
    User user = new User("john@email.com", "John", "encodedPass");
    entityManager.persistAndFlush(user);

    // When
    Optional<User> found = userRepository.findByEmail("john@email.com");

    // Then
    assertThat(found).isPresent();
    assertThat(found.get().getName()).isEqualTo("John");
}

@Test
@DisplayName("Should return empty when email not found")
void findByEmail_NonExistingEmail_ReturnsEmpty() {
    // When
    Optional<User> found = userRepository.findByEmail("notfound@email.com");

    // Then
    assertThat(found).isEmpty();
}

@Test
@DisplayName("Should check if email exists")
void existsByEmail_ExistingEmail_ReturnsTrue() {
    // Given
    User user = new User("john@email.com", "John", "pass");
    entityManager.persistAndFlush(user);

    // When & Then
    assertThat(userRepository.existsByEmail("john@email.com")).isTrue();
    assertThat(userRepository.existsByEmail("other@email.com")).isFalse();
}

@Test
@DisplayName("Should find users by name containing")
void findByNameContaining_MatchingName_ReturnsUsers() {
    // Given
    entityManager.persist(new User("john@email.com", "John Doe", "pass"));
    entityManager.persist(new User("jane@email.com", "Jane Doe", "pass"));
    entityManager.persist(new User("bob@email.com", "Bob Smith", "pass"));
    entityManager.flush();

    // When
    List<User> result = userRepository.findByNameContainingIgnoreCase("doe");

    // Then
    assertThat(result).hasSize(2);
    assertThat(result).extracting(User::getName)
        .containsExactlyInAnyOrder("John Doe", "Jane Doe");
}
```

---

## 🔐 Testing con Spring Security

### @WithMockUser - Usuario Simulado

```java
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)  // Importar configuración de seguridad
class SecuredUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Authenticated user can access /api/users")
    void getUsers_AuthenticatedUser_Returns200() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Unauthenticated user gets 401")
    void getUsers_UnauthenticatedUser_Returns401() throws Exception {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Admin can delete users")
    void deleteUser_Admin_Returns204() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1))
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("Regular user cannot delete users")
    void deleteUser_RegularUser_Returns403() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1))
            .andExpect(status().isForbidden());
    }
}
```

### @WithUserDetails - Usuario de BD

```java
@Test
@WithUserDetails(value = "admin@email.com", userDetailsServiceBeanName = "customUserDetailsService")
void adminAction_WithAdminUser_Succeeds() throws Exception {
    mockMvc.perform(get("/api/admin/dashboard"))
        .andExpect(status().isOk());
}
```

### Custom Security Context

```java
@Test
void customSecurityContext() throws Exception {
    // Crear autenticación custom
    UsernamePasswordAuthenticationToken auth =
        new UsernamePasswordAuthenticationToken(
            "user@email.com",
            "password",
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

    mockMvc.perform(get("/api/users")
            .with(authentication(auth)))
        .andExpect(status().isOk());
}
```

---

## 🧪 @SpringBootTest - Integración Completa

### Configuración

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

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
}
```

### Test de Flujo Completo

```java
@Test
@DisplayName("Full user CRUD flow")
void fullUserCrudFlow() {
    String baseUrl = "http://localhost:" + port + "/api/users";

    // CREATE
    CreateUserDTO createDto = new CreateUserDTO("test@email.com", "Test", "Pass123!");
    ResponseEntity<UserDTO> createResponse = restTemplate.postForEntity(
        baseUrl, createDto, UserDTO.class);

    assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Long userId = createResponse.getBody().getId();

    // READ
    ResponseEntity<UserDTO> getResponse = restTemplate.getForEntity(
        baseUrl + "/" + userId, UserDTO.class);

    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(getResponse.getBody().getEmail()).isEqualTo("test@email.com");

    // UPDATE
    UpdateUserDTO updateDto = new UpdateUserDTO("Updated Name");
    restTemplate.put(baseUrl + "/" + userId, updateDto);

    UserDTO updated = restTemplate.getForObject(baseUrl + "/" + userId, UserDTO.class);
    assertThat(updated.getName()).isEqualTo("Updated Name");

    // DELETE
    restTemplate.delete(baseUrl + "/" + userId);

    ResponseEntity<UserDTO> deletedResponse = restTemplate.getForEntity(
        baseUrl + "/" + userId, UserDTO.class);
    assertThat(deletedResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
}
```

---

## 📁 Organización de Tests

### Estructura Recomendada

```
src/test/java/com/bootcamp/
├── unit/                          # Tests unitarios
│   ├── service/
│   │   ├── UserServiceTest.java
│   │   └── ProductServiceTest.java
│   └── mapper/
│       └── UserMapperTest.java
├── integration/                   # Tests de integración
│   ├── controller/
│   │   ├── UserControllerTest.java
│   │   └── ProductControllerTest.java
│   └── repository/
│       └── UserRepositoryTest.java
├── e2e/                          # Tests end-to-end
│   └── UserE2ETest.java
└── config/                       # Configuración de tests
    └── TestSecurityConfig.java
```

### application-test.properties

```properties
# Base de datos en memoria para tests
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop

# Desactivar logs innecesarios
logging.level.org.hibernate.SQL=WARN
spring.jpa.show-sql=false

# Seguridad simplificada para tests
spring.security.user.name=testuser
spring.security.user.password=testpass
```

---

## 🔗 Recursos

- [Spring Boot Testing Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
- [MockMvc Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html#spring-mvc-test-framework)
- [Testing Spring Security](https://docs.spring.io/spring-security/reference/servlet/test/index.html)
