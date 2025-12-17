# Práctica 01: Tests Unitarios de Services

## 🎯 Objetivos

- Escribir tests unitarios para la capa de servicio
- Usar Mockito para simular dependencias
- Aplicar el patrón Given-When-Then
- Alcanzar alta cobertura de código

## ⏱️ Duración: 40 minutos

---

## 📋 Prerrequisitos

- Proyecto Spring Boot con estructura en capas
- Dependencia `spring-boot-starter-test` en pom.xml
- Service y Repository implementados

---

## 🔧 Paso 1: Estructura de Test

Crear la estructura de carpetas para tests:

```
src/test/java/com/bootcamp/
├── unit/
│   └── service/
│       └── ProductServiceTest.java
└── integration/
    └── (para después)
```

---

## 📝 Paso 2: Test Básico de Service

### 2.1 Crear ProductServiceTest

```java
package com.bootcamp.unit.service;

import com.bootcamp.product.dto.CreateProductDTO;
import com.bootcamp.product.dto.ProductDTO;
import com.bootcamp.product.entity.Product;
import com.bootcamp.product.repository.ProductRepository;
import com.bootcamp.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Unit Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    // Datos de prueba reutilizables
    private Product sampleProduct;
    private CreateProductDTO validCreateDTO;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Test Product");
        sampleProduct.setDescription("Test Description");
        sampleProduct.setPrice(99.99);
        sampleProduct.setStock(100);

        validCreateDTO = new CreateProductDTO(
            "New Product",
            "New Description",
            49.99,
            50
        );
    }

    // TODO: Implementar los tests siguientes
}
```

---

## 📝 Paso 3: Tests de Búsqueda

### 3.1 Test findAll

```java
@Nested
@DisplayName("findAll()")
class FindAllTests {

    @Test
    @DisplayName("should return list of products when products exist")
    void findAll_ProductsExist_ReturnsList() {
        // TODO: Implementar
        // Given: Mock del repository retornando lista de productos
        // When: Llamar a productService.findAll()
        // Then: Verificar que retorna lista con DTOs correctos

        // Hints:
        // - when(productRepository.findAll()).thenReturn(List.of(product1, product2));
        // - assertThat(result).hasSize(2);
        // - assertThat(result).extracting(ProductDTO::getName)
        //       .containsExactlyInAnyOrder("Product1", "Product2");
    }

    @Test
    @DisplayName("should return empty list when no products exist")
    void findAll_NoProducts_ReturnsEmptyList() {
        // TODO: Implementar
        // Given: Mock del repository retornando lista vacía
        // When: Llamar a productService.findAll()
        // Then: Verificar que retorna lista vacía
    }
}
```

### 3.2 Test findById

```java
@Nested
@DisplayName("findById()")
class FindByIdTests {

    @Test
    @DisplayName("should return product when ID exists")
    void findById_ExistingId_ReturnsProduct() {
        // TODO: Implementar
        // Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        // When
        // ProductDTO result = productService.findById(1L);

        // Then
        // assertThat(result).isNotNull();
        // assertThat(result.getId()).isEqualTo(1L);
        // assertThat(result.getName()).isEqualTo("Test Product");
        // verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("should throw exception when ID does not exist")
    void findById_NonExistingId_ThrowsException() {
        // TODO: Implementar
        // Given
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        // assertThatThrownBy(() -> productService.findById(999L))
        //     .isInstanceOf(ResourceNotFoundException.class)
        //     .hasMessageContaining("Product not found");
    }
}
```

---

## 📝 Paso 4: Tests de Creación

```java
@Nested
@DisplayName("create()")
class CreateTests {

    @Test
    @DisplayName("should create product with valid data")
    void create_ValidData_ReturnsCreatedProduct() {
        // TODO: Implementar
        // Given: Mock del repository save
        // when(productRepository.save(any(Product.class)))
        //     .thenAnswer(invocation -> {
        //         Product p = invocation.getArgument(0);
        //         p.setId(1L);
        //         return p;
        //     });

        // When: Llamar a productService.create(validCreateDTO)

        // Then:
        // - Verificar que el producto retornado tiene ID
        // - Verificar que los datos son correctos
        // - Capturar el producto guardado y verificar sus campos
        // verify(productRepository).save(productCaptor.capture());
        // Product saved = productCaptor.getValue();
        // assertThat(saved.getName()).isEqualTo("New Product");
    }

    @Test
    @DisplayName("should throw exception when name is duplicate")
    void create_DuplicateName_ThrowsException() {
        // TODO: Implementar
        // Given: Mock que indica que el nombre ya existe
        // when(productRepository.existsByName(anyString())).thenReturn(true);

        // When & Then: Verificar que lanza excepción
        // verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("should set default stock to 0 when not provided")
    void create_NoStock_SetsDefaultStock() {
        // TODO: Implementar si aplica
    }
}
```

---

## 📝 Paso 5: Tests de Actualización

```java
@Nested
@DisplayName("update()")
class UpdateTests {

    @Test
    @DisplayName("should update product when ID exists")
    void update_ExistingId_ReturnsUpdatedProduct() {
        // TODO: Implementar
        // Given: Producto existente
        // when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        // when(productRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // When: Llamar a update con nuevos datos
        // UpdateProductDTO updateDTO = new UpdateProductDTO("Updated Name", null, 199.99, null);

        // Then: Verificar que solo se actualizaron los campos enviados
    }

    @Test
    @DisplayName("should throw exception when ID does not exist")
    void update_NonExistingId_ThrowsException() {
        // TODO: Implementar
    }

    @Test
    @DisplayName("should only update non-null fields (partial update)")
    void update_PartialData_UpdatesOnlyProvidedFields() {
        // TODO: Implementar
        // Given: Producto existente y DTO con solo algunos campos

        // When: Actualizar

        // Then: Verificar que solo los campos enviados cambiaron
    }
}
```

---

## 📝 Paso 6: Tests de Eliminación

```java
@Nested
@DisplayName("delete()")
class DeleteTests {

    @Test
    @DisplayName("should delete product when ID exists")
    void delete_ExistingId_DeletesProduct() {
        // TODO: Implementar
        // Given
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        // When
        // productService.delete(1L);

        // Then
        // verify(productRepository).deleteById(1L);
    }

    @Test
    @DisplayName("should throw exception when ID does not exist")
    void delete_NonExistingId_ThrowsException() {
        // TODO: Implementar
        // Given
        when(productRepository.existsById(999L)).thenReturn(false);

        // When & Then
        // assertThatThrownBy(() -> productService.delete(999L))
        //     .isInstanceOf(ResourceNotFoundException.class);
        // verify(productRepository, never()).deleteById(anyLong());
    }
}
```

---

## 📝 Paso 7: Tests de Lógica de Negocio

```java
@Nested
@DisplayName("Business Logic")
class BusinessLogicTests {

    @Test
    @DisplayName("should decrease stock when order is placed")
    void decreaseStock_SufficientStock_DecreasesStock() {
        // TODO: Implementar
        // Given: Producto con stock = 100
        // when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        // When: Decrementar 10 unidades
        // productService.decreaseStock(1L, 10);

        // Then: Verificar que el stock es 90
        // verify(productRepository).save(productCaptor.capture());
        // assertThat(productCaptor.getValue().getStock()).isEqualTo(90);
    }

    @Test
    @DisplayName("should throw exception when insufficient stock")
    void decreaseStock_InsufficientStock_ThrowsException() {
        // TODO: Implementar
        // Given: Producto con stock = 5
        sampleProduct.setStock(5);
        // when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        // When & Then: Intentar decrementar 10
        // assertThatThrownBy(() -> productService.decreaseStock(1L, 10))
        //     .isInstanceOf(InsufficientStockException.class);
    }

    @Test
    @DisplayName("should apply discount correctly")
    void applyDiscount_ValidDiscount_CalculatesCorrectly() {
        // TODO: Implementar
        // Given: Producto con precio 100
        // When: Aplicar 20% de descuento
        // Then: Precio final es 80
    }
}
```

---

## ✅ Criterios de Éxito

- [ ] Todos los tests pasan (`mvn test`)
- [ ] Cobertura de ProductService > 80%
- [ ] Tests usan patrón Given-When-Then
- [ ] Cada test verifica UN concepto
- [ ] Nombres de tests son descriptivos
- [ ] Se usa @Nested para agrupar tests relacionados

---

## 🚀 Ejecutar Tests

```bash
# Ejecutar todos los tests unitarios
mvn test -Dtest="**/unit/**"

# Ejecutar solo ProductServiceTest
mvn test -Dtest=ProductServiceTest

# Ejecutar con cobertura
mvn test jacoco:report

# Ver reporte en target/site/jacoco/index.html
```

---

## 📊 Verificar Cobertura

Después de ejecutar los tests, revisa el reporte de JaCoCo:

```bash
open target/site/jacoco/index.html
```

Busca la clase `ProductService` y verifica:
- Line Coverage > 80%
- Branch Coverage > 70%

---

## 💡 Tips

1. **Un assert por concepto**: Un test puede tener múltiples `assertThat` si verifican el mismo concepto
2. **No mockear lo que estás testeando**: El `@InjectMocks` es la clase bajo test, no se mockea
3. **Verificar interacciones**: Usa `verify()` para asegurar que se llamaron los métodos correctos
4. **Usar ArgumentCaptor**: Para verificar los argumentos exactos pasados a los mocks
5. **Tests independientes**: Cada test debe poder ejecutarse solo, sin depender de otros
