# Práctica 04: TestContainers con PostgreSQL

## 🎯 Objetivos

- Configurar TestContainers para tests de integración
- Ejecutar tests con PostgreSQL real en contenedor
- Crear clase base reutilizable
- Escribir tests de repository con BD real

## ⏱️ Duración: 35 minutos

---

## 📋 Prerrequisitos

- Docker instalado y corriendo
- Dependencias de TestContainers en pom.xml
- Repositories JPA implementados

---

## 🔧 Paso 1: Configurar Dependencias

### 1.1 Verificar pom.xml

```xml
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
    <!-- TestContainers Core -->
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

    <!-- PostgreSQL TestContainers -->
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>postgresql</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 📝 Paso 2: Crear Clase Base Abstracta

### 2.1 AbstractIntegrationTest

```java
package com.bootcamp.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
public abstract class AbstractIntegrationTest {

    // Contenedor singleton compartido por todos los tests
    static final PostgreSQLContainer<?> postgres;

    static {
        postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);  // Reutilizar entre ejecuciones
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.properties.hibernate.dialect",
            () -> "org.hibernate.dialect.PostgreSQLDialect");
    }
}
```

### 2.2 Habilitar Reuse (Opcional)

Crear archivo `~/.testcontainers.properties`:

```properties
testcontainers.reuse.enable=true
```

---

## 📝 Paso 3: Tests de Repository

### 3.1 ProductRepositoryIT

```java
package com.bootcamp.integration.repository;

import com.bootcamp.integration.AbstractIntegrationTest;
import com.bootcamp.product.entity.Product;
import com.bootcamp.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ProductRepository Integration Tests")
class ProductRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Limpiar datos entre tests
        productRepository.deleteAll();
    }

    @Nested
    @DisplayName("CRUD Operations")
    class CrudOperations {

        @Test
        @DisplayName("should save and retrieve product")
        void saveAndFind_ValidProduct_Success() {
            // TODO: Implementar
            // Given
            Product product = new Product();
            product.setName("Test Product");
            product.setDescription("Test Description");
            product.setPrice(99.99);
            product.setStock(100);

            // When
            Product saved = productRepository.save(product);

            // Then
            assertThat(saved.getId()).isNotNull();

            Optional<Product> found = productRepository.findById(saved.getId());
            assertThat(found).isPresent();
            assertThat(found.get().getName()).isEqualTo("Test Product");
        }

        @Test
        @DisplayName("should update existing product")
        void update_ExistingProduct_Success() {
            // TODO: Implementar
            // Given: Producto guardado
            Product product = new Product();
            product.setName("Original Name");
            product.setPrice(50.0);
            Product saved = productRepository.save(product);

            // When: Actualizar
            saved.setName("Updated Name");
            saved.setPrice(75.0);
            productRepository.save(saved);

            // Then: Verificar cambios
            // Product updated = productRepository.findById(saved.getId()).orElseThrow();
            // assertThat(updated.getName()).isEqualTo("Updated Name");
            // assertThat(updated.getPrice()).isEqualTo(75.0);
        }

        @Test
        @DisplayName("should delete product by id")
        void deleteById_ExistingProduct_Success() {
            // TODO: Implementar
            // Given
            Product product = new Product();
            product.setName("To Delete");
            Product saved = productRepository.save(product);
            Long id = saved.getId();

            // When
            productRepository.deleteById(id);

            // Then
            // assertThat(productRepository.findById(id)).isEmpty();
        }
    }

    @Nested
    @DisplayName("Custom Queries")
    class CustomQueries {

        @Test
        @DisplayName("should find products by category")
        void findByCategory_ExistingCategory_ReturnsProducts() {
            // TODO: Implementar
            // Given
            Product electronics = createProduct("Laptop", "Electronics", 999.0);
            Product furniture = createProduct("Chair", "Furniture", 199.0);
            productRepository.saveAll(List.of(electronics, furniture));

            // When
            // List<Product> result = productRepository.findByCategory("Electronics");

            // Then
            // assertThat(result).hasSize(1);
            // assertThat(result.get(0).getName()).isEqualTo("Laptop");
        }

        @Test
        @DisplayName("should find products by price range")
        void findByPriceBetween_ValidRange_ReturnsProducts() {
            // TODO: Implementar
            // Given
            productRepository.saveAll(List.of(
                createProduct("Cheap", null, 10.0),
                createProduct("Medium", null, 50.0),
                createProduct("Expensive", null, 100.0)
            ));

            // When
            // List<Product> result = productRepository.findByPriceBetween(20.0, 80.0);

            // Then
            // assertThat(result).hasSize(1);
            // assertThat(result.get(0).getName()).isEqualTo("Medium");
        }

        @Test
        @DisplayName("should find products by name containing (case insensitive)")
        void findByNameContaining_ValidPattern_ReturnsProducts() {
            // TODO: Implementar
            // Given
            productRepository.saveAll(List.of(
                createProduct("Apple iPhone", null, 999.0),
                createProduct("Samsung Galaxy", null, 899.0),
                createProduct("Apple MacBook", null, 1999.0)
            ));

            // When
            // List<Product> result = productRepository.findByNameContainingIgnoreCase("apple");

            // Then
            // assertThat(result).hasSize(2);
        }

        @Test
        @DisplayName("should check if product exists by name")
        void existsByName_ExistingName_ReturnsTrue() {
            // TODO: Implementar
            // Given
            productRepository.save(createProduct("Unique Product", null, 50.0));

            // When & Then
            // assertThat(productRepository.existsByName("Unique Product")).isTrue();
            // assertThat(productRepository.existsByName("Non Existing")).isFalse();
        }
    }

    @Nested
    @DisplayName("Pagination and Sorting")
    class PaginationAndSorting {

        @BeforeEach
        void setUpData() {
            // Crear 25 productos para pruebas de paginación
            for (int i = 1; i <= 25; i++) {
                productRepository.save(
                    createProduct("Product " + i, "Category", i * 10.0)
                );
            }
        }

        @Test
        @DisplayName("should return paginated results")
        void findAll_WithPagination_ReturnsPaginatedResults() {
            // TODO: Implementar
            // Given
            PageRequest pageRequest = PageRequest.of(0, 10);

            // When
            Page<Product> page = productRepository.findAll(pageRequest);

            // Then
            assertThat(page.getContent()).hasSize(10);
            assertThat(page.getTotalElements()).isEqualTo(25);
            assertThat(page.getTotalPages()).isEqualTo(3);
            assertThat(page.getNumber()).isEqualTo(0);
        }

        @Test
        @DisplayName("should return sorted results")
        void findAll_WithSorting_ReturnsSortedResults() {
            // TODO: Implementar
            // Given
            PageRequest pageRequest = PageRequest.of(
                0, 5, Sort.by(Sort.Direction.DESC, "price")
            );

            // When
            Page<Product> page = productRepository.findAll(pageRequest);

            // Then
            // assertThat(page.getContent().get(0).getPrice()).isEqualTo(250.0);
        }

        @Test
        @DisplayName("should navigate to different pages")
        void findAll_DifferentPages_ReturnsCorrectData() {
            // TODO: Implementar
            // Given
            PageRequest page0 = PageRequest.of(0, 10);
            PageRequest page1 = PageRequest.of(1, 10);
            PageRequest page2 = PageRequest.of(2, 10);

            // When & Then
            // assertThat(productRepository.findAll(page0).getContent()).hasSize(10);
            // assertThat(productRepository.findAll(page1).getContent()).hasSize(10);
            // assertThat(productRepository.findAll(page2).getContent()).hasSize(5);
        }
    }

    // Helper method
    private Product createProduct(String name, String category, Double price) {
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setStock(10);
        return product;
    }
}
```

---

## 📝 Paso 4: Tests de Relaciones

```java
@Nested
@DisplayName("Relationship Tests")
class RelationshipTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("should save order with products")
    void saveOrder_WithProducts_Success() {
        // TODO: Implementar
        // Given: Usuario y productos
        // User user = userRepository.save(new User("test@email.com", "Test"));
        // Product product1 = productRepository.save(createProduct("P1", null, 100.0));
        // Product product2 = productRepository.save(createProduct("P2", null, 200.0));

        // When: Crear orden
        // Order order = new Order();
        // order.setUser(user);
        // order.addItem(new OrderItem(product1, 2));
        // order.addItem(new OrderItem(product2, 1));
        // Order saved = orderRepository.save(order);

        // Then: Verificar relaciones
        // Order found = orderRepository.findById(saved.getId()).orElseThrow();
        // assertThat(found.getItems()).hasSize(2);
        // assertThat(found.getUser().getEmail()).isEqualTo("test@email.com");
    }

    @Test
    @DisplayName("should fetch lazy relationships")
    void findWithItems_LazyFetch_Success() {
        // TODO: Implementar
        // Verificar que LAZY loading funciona con @EntityGraph o JOIN FETCH
    }
}
```

---

## 📝 Paso 5: Tests de Transacciones

```java
@Nested
@DisplayName("Transaction Tests")
class TransactionTests {

    @Test
    @DisplayName("should rollback on exception")
    void transaction_OnException_Rollback() {
        // TODO: Implementar
        // Given: Datos iniciales

        // When: Operación que falla a mitad

        // Then: Verificar que no se guardó nada
    }

    @Test
    @Transactional
    @DisplayName("should execute within transaction")
    void transaction_Success_CommitsData() {
        // TODO: Implementar
        // Operaciones dentro de transacción
    }
}
```

---

## ✅ Criterios de Éxito

- [ ] Docker está corriendo para TestContainers
- [ ] Clase base AbstractIntegrationTest funciona
- [ ] Tests de CRUD pasan con PostgreSQL real
- [ ] Tests de queries custom funcionan
- [ ] Tests de paginación y ordenamiento pasan
- [ ] Datos se limpian entre tests (@BeforeEach)

---

## 🚀 Ejecutar Tests

```bash
# Verificar que Docker está corriendo
docker info

# Ejecutar tests de integración
mvn test -Dtest="**/*IT.java"

# Ejecutar con logs de TestContainers
mvn test -Dtest=ProductRepositoryIT -Dtestcontainers.logs=true

# Solo tests de repository
mvn test -Dtest="**/repository/*IT"
```

---

## 💡 Tips

### Verificar conexión al contenedor

```java
@Test
void containerIsRunning() {
    assertThat(postgres.isRunning()).isTrue();
    System.out.println("JDBC URL: " + postgres.getJdbcUrl());
    System.out.println("Username: " + postgres.getUsername());
    System.out.println("Password: " + postgres.getPassword());
}
```

### Debug de queries SQL

```properties
# En application-test.properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Limpiar contenedores huérfanos

```bash
# Si hay contenedores que no se detuvieron
docker ps -a | grep testcontainers | awk '{print $1}' | xargs docker rm -f
```
