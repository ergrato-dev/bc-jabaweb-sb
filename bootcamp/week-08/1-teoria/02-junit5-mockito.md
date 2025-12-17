# JUnit 5 y Mockito

## 📚 JUnit 5 - Fundamentos

### ¿Qué es JUnit 5?

**JUnit 5** es el framework de testing más utilizado en Java. Se compone de tres módulos:

- **JUnit Platform**: Base para lanzar frameworks de testing
- **JUnit Jupiter**: Modelo de programación y extensiones
- **JUnit Vintage**: Compatibilidad con JUnit 3/4

### Dependencia Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

> Spring Boot Starter Test incluye: JUnit 5, Mockito, AssertJ, Hamcrest, JSONPath

---

## 🏷️ Anotaciones Principales de JUnit 5

### Anotaciones de Ciclo de Vida

```java
class LifecycleTest {

    @BeforeAll
    static void initAll() {
        // Se ejecuta UNA vez antes de TODOS los tests
        // Debe ser static
        System.out.println("🚀 Iniciando suite de tests");
    }

    @BeforeEach
    void init() {
        // Se ejecuta antes de CADA test
        System.out.println("📋 Preparando test");
    }

    @Test
    void testOne() {
        System.out.println("✅ Test 1 ejecutado");
    }

    @Test
    void testTwo() {
        System.out.println("✅ Test 2 ejecutado");
    }

    @AfterEach
    void tearDown() {
        // Se ejecuta después de CADA test
        System.out.println("🧹 Limpiando después del test");
    }

    @AfterAll
    static void tearDownAll() {
        // Se ejecuta UNA vez después de TODOS los tests
        System.out.println("🏁 Finalizando suite de tests");
    }
}
```

**Salida**:
```
🚀 Iniciando suite de tests
📋 Preparando test
✅ Test 1 ejecutado
🧹 Limpiando después del test
📋 Preparando test
✅ Test 2 ejecutado
🧹 Limpiando después del test
🏁 Finalizando suite de tests
```

---

### Anotaciones de Test

```java
class TestAnnotationsDemo {

    @Test
    @DisplayName("Should calculate sum correctly")
    void basicTest() {
        assertEquals(4, 2 + 2);
    }

    @Test
    @Disabled("Deshabilitado hasta fix de bug #123")
    void disabledTest() {
        // No se ejecuta
    }

    @RepeatedTest(5)
    @DisplayName("Repeated test")
    void repeatedTest(RepetitionInfo info) {
        System.out.println("Repetición: " + info.getCurrentRepetition());
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "world", "junit"})
    @DisplayName("Parameterized test with strings")
    void parameterizedTest(String word) {
        assertNotNull(word);
        assertTrue(word.length() > 0);
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1, 2",
        "2, 3, 5",
        "10, 20, 30"
    })
    @DisplayName("Sum parameterized test")
    void sumTest(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }

    @Nested
    @DisplayName("When user is admin")
    class WhenUserIsAdmin {

        @Test
        @DisplayName("should have all permissions")
        void hasAllPermissions() {
            // Test específico para admin
        }
    }
}
```

---

### Anotaciones de Orden y Tags

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTests {

    @Test
    @Order(1)
    @Tag("fast")
    void firstTest() { }

    @Test
    @Order(2)
    @Tag("slow")
    void secondTest() { }

    @Test
    @Order(3)
    @Tag("integration")
    void thirdTest() { }
}

// Ejecutar solo tests con tag específico:
// mvn test -Dgroups="fast"
// mvn test -DexcludedGroups="slow"
```

---

## ✅ Assertions en JUnit 5

### Assertions Básicas

```java
import static org.junit.jupiter.api.Assertions.*;

@Test
void basicAssertions() {
    // Igualdad
    assertEquals(4, 2 + 2);
    assertEquals("hello", "hello");
    assertEquals(3.14, Math.PI, 0.01); // Con delta para doubles

    // Booleanos
    assertTrue(5 > 3);
    assertFalse(5 < 3);

    // Nulos
    assertNull(null);
    assertNotNull("not null");

    // Referencias
    String a = "test";
    String b = a;
    assertSame(a, b);

    // Arrays
    assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
}
```

### Assertions de Excepciones

```java
@Test
void exceptionAssertions() {
    // Verificar que se lanza excepción
    Exception exception = assertThrows(
        IllegalArgumentException.class,
        () -> validator.validate(null)
    );

    // Verificar mensaje
    assertEquals("Input cannot be null", exception.getMessage());

    // Verificar que NO se lanza excepción
    assertDoesNotThrow(() -> validator.validate("valid"));
}
```

### Assertions Agrupadas

```java
@Test
void groupedAssertions() {
    User user = new User("John", "john@email.com", 25);

    // Todas las assertions se ejecutan aunque alguna falle
    assertAll("user properties",
        () -> assertEquals("John", user.getName()),
        () -> assertEquals("john@email.com", user.getEmail()),
        () -> assertTrue(user.getAge() > 18)
    );
}
```

### Assertions con Timeout

```java
@Test
void timeoutAssertions() {
    // Falla si tarda más de 1 segundo
    assertTimeout(Duration.ofSeconds(1), () -> {
        // Código que debe ejecutar en menos de 1s
        Thread.sleep(500);
    });

    // Aborta inmediatamente si excede timeout
    assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
        // Se cancela si tarda más de 100ms
    });
}
```

---

## 🎭 AssertJ - Assertions Fluent

AssertJ proporciona assertions más legibles y fluent:

```java
import static org.assertj.core.api.Assertions.*;

@Test
void assertJExamples() {
    // Strings
    assertThat("Hello World")
        .isNotNull()
        .startsWith("Hello")
        .endsWith("World")
        .contains("lo Wo")
        .hasSize(11);

    // Numbers
    assertThat(42)
        .isPositive()
        .isGreaterThan(40)
        .isLessThanOrEqualTo(42)
        .isBetween(40, 50);

    // Collections
    List<String> fruits = List.of("apple", "banana", "cherry");
    assertThat(fruits)
        .isNotEmpty()
        .hasSize(3)
        .contains("banana")
        .doesNotContain("orange")
        .containsExactly("apple", "banana", "cherry");

    // Objects
    User user = new User("John", "john@email.com");
    assertThat(user)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", "John")
        .extracting(User::getEmail)
        .isEqualTo("john@email.com");

    // Exceptions
    assertThatThrownBy(() -> {
        throw new IllegalArgumentException("Invalid input");
    })
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid input")
        .hasMessageContaining("Invalid");
}
```

---

## 🎭 Mockito - Fundamentos

### ¿Qué es Mockito?

**Mockito** es un framework para crear objetos simulados (mocks) que reemplazan dependencias reales en tests unitarios.

### Conceptos Clave

| Concepto | Descripción |
|----------|-------------|
| **Mock** | Objeto simulado que reemplaza una dependencia |
| **Stub** | Mock con comportamiento predefinido (when/thenReturn) |
| **Spy** | Objeto real parcialmente mockeado |
| **Verify** | Verificar que se llamó un método |
| **Capture** | Capturar argumentos pasados a métodos |

---

## 🏷️ Anotaciones de Mockito

```java
@ExtendWith(MockitoExtension.class)  // Habilita Mockito
class MockitoAnnotationsTest {

    @Mock  // Crea un mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks  // Inyecta los mocks en esta clase
    private UserService userService;

    @Spy  // Objeto real parcialmente mockeado
    private List<String> spyList = new ArrayList<>();

    @Captor  // Captura argumentos
    private ArgumentCaptor<User> userCaptor;
}
```

---

## 📝 Stubbing con Mockito

### when().thenReturn()

```java
@Test
void whenThenReturnExample() {
    // Given
    User mockUser = new User(1L, "john@email.com", "John");
    when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

    // When
    Optional<User> result = userService.findById(1L);

    // Then
    assertThat(result).isPresent();
    assertThat(result.get().getName()).isEqualTo("John");
}
```

### when().thenThrow()

```java
@Test
void whenThenThrowExample() {
    // Given
    when(userRepository.findById(999L))
        .thenThrow(new EntityNotFoundException("User not found"));

    // When & Then
    assertThatThrownBy(() -> userService.findById(999L))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("User not found");
}
```

### Respuestas Múltiples

```java
@Test
void multipleReturnsExample() {
    when(userRepository.count())
        .thenReturn(0L)    // Primera llamada
        .thenReturn(1L)    // Segunda llamada
        .thenReturn(5L);   // Tercera y siguientes

    assertEquals(0L, userRepository.count());
    assertEquals(1L, userRepository.count());
    assertEquals(5L, userRepository.count());
    assertEquals(5L, userRepository.count()); // Sigue retornando 5
}
```

### thenAnswer() para Lógica Compleja

```java
@Test
void thenAnswerExample() {
    when(userRepository.save(any(User.class)))
        .thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(100L); // Simula ID generado
            return user;
        });

    User newUser = new User(null, "test@email.com", "Test");
    User saved = userRepository.save(newUser);

    assertThat(saved.getId()).isEqualTo(100L);
}
```

---

## ✅ Verificaciones con Mockito

### verify() Básico

```java
@Test
void verifyExample() {
    // When
    userService.create(new CreateUserDTO("test@email.com", "Test"));

    // Then - Verificar que se llamó save exactamente 1 vez
    verify(userRepository).save(any(User.class));
    verify(userRepository, times(1)).save(any(User.class));

    // Verificar que nunca se llamó delete
    verify(userRepository, never()).delete(any());

    // Verificar número de invocaciones
    verify(emailService, atLeastOnce()).sendWelcome(anyString());
    verify(emailService, atMost(2)).sendWelcome(anyString());
}
```

### Verificar Orden de Llamadas

```java
@Test
void verifyOrderExample() {
    // When
    userService.createAndNotify(dto);

    // Then - Verificar orden
    InOrder inOrder = inOrder(userRepository, emailService);
    inOrder.verify(userRepository).save(any(User.class));
    inOrder.verify(emailService).sendWelcome(anyString());
}
```

### Capturar Argumentos

```java
@Test
void captorExample() {
    // Given
    @Captor
    ArgumentCaptor<User> userCaptor;

    // When
    userService.create(new CreateUserDTO("test@email.com", "Test User"));

    // Then - Capturar el User que se pasó a save()
    verify(userRepository).save(userCaptor.capture());

    User captured = userCaptor.getValue();
    assertThat(captured.getEmail()).isEqualTo("test@email.com");
    assertThat(captured.getName()).isEqualTo("Test User");
}
```

---

## 🎯 Argument Matchers

```java
@Test
void argumentMatchersExample() {
    // any() - Cualquier valor
    when(userRepository.save(any())).thenReturn(new User());
    when(userRepository.save(any(User.class))).thenReturn(new User());

    // eq() - Valor exacto
    when(userRepository.findByEmail(eq("admin@email.com")))
        .thenReturn(Optional.of(adminUser));

    // Strings
    when(service.findByNameContaining(anyString())).thenReturn(List.of());
    when(service.findByNameContaining(contains("John"))).thenReturn(List.of(john));
    when(service.findByNameContaining(startsWith("J"))).thenReturn(List.of(john));

    // Numbers
    when(service.findByAgeGreaterThan(anyInt())).thenReturn(List.of());
    when(service.findByAgeGreaterThan(gt(18))).thenReturn(adults);

    // Collections
    when(service.processAll(anyList())).thenReturn(true);
    when(service.processAll(argThat(list -> list.size() > 5))).thenReturn(false);

    // Null handling
    when(service.process(isNull())).thenThrow(new IllegalArgumentException());
    when(service.process(isNotNull())).thenReturn(true);
}
```

---

## 🕵️ Spy - Mocks Parciales

```java
@Test
void spyExample() {
    // Spy sobre objeto real
    List<String> realList = new ArrayList<>();
    List<String> spyList = spy(realList);

    // Llamadas reales por defecto
    spyList.add("one");
    spyList.add("two");
    assertEquals(2, spyList.size());  // Comportamiento real

    // Pero podemos stubear métodos específicos
    doReturn(100).when(spyList).size();
    assertEquals(100, spyList.size());  // Comportamiento mockeado

    // El contenido sigue siendo real
    assertTrue(spyList.contains("one"));
}
```

### @Spy en Clases

```java
@ExtendWith(MockitoExtension.class)
class SpyServiceTest {

    @Spy
    private UserValidator validator = new UserValidator();

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @Test
    void partialMocking() {
        // Usa validación real pero mockea un método específico
        doReturn(true).when(validator).isEmailUnique(anyString());

        // El resto de métodos de validator se ejecutan realmente
        userService.create(dto);
    }
}
```

---

## 📋 Ejemplo Completo

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private CreateUserDTO validDto;

    @BeforeEach
    void setUp() {
        validDto = new CreateUserDTO(
            "john@email.com",
            "John Doe",
            "SecurePass123!"
        );
    }

    @Test
    @DisplayName("Should create user with encoded password")
    void createUser_ValidData_EncodesPasswordAndSaves() {
        // Given
        when(passwordEncoder.encode("SecurePass123!"))
            .thenReturn("$2a$10$encodedPassword");
        when(userRepository.save(any(User.class)))
            .thenAnswer(inv -> {
                User u = inv.getArgument(0);
                u.setId(1L);
                return u;
            });

        // When
        UserDTO result = userService.create(validDto);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("john@email.com");

        // Verify password was encoded
        verify(passwordEncoder).encode("SecurePass123!");

        // Verify saved user has encoded password
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getPassword()).isEqualTo("$2a$10$encodedPassword");

        // Verify welcome email sent
        verify(emailService).sendWelcome("john@email.com");
    }

    @Test
    @DisplayName("Should throw when email already exists")
    void createUser_DuplicateEmail_ThrowsException() {
        // Given
        when(userRepository.existsByEmail("john@email.com")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.create(validDto))
            .isInstanceOf(DuplicateResourceException.class)
            .hasMessage("Email already registered");

        // Verify save was never called
        verify(userRepository, never()).save(any());
        verify(emailService, never()).sendWelcome(anyString());
    }
}
```

---

## 🔗 Recursos

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://site.mockito.org/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Baeldung - Mockito Tutorial](https://www.baeldung.com/mockito-series)
