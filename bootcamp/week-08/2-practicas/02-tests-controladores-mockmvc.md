# Práctica 02: Tests de Controllers con MockMvc

## 🎯 Objetivos

- Escribir tests de integración para controllers
- Usar MockMvc para simular peticiones HTTP
- Probar respuestas JSON con jsonPath
- Testear validaciones de entrada

## ⏱️ Duración: 40 minutos

---

## 📋 Prerrequisitos

- Práctica 01 completada
- Controller REST implementado
- DTOs con validaciones (@Valid)

---

## 🔧 Paso 1: Configuración del Test

### 1.1 Crear ProductControllerTest

```java
package com.bootcamp.integration.controller;

import com.bootcamp.product.controller.ProductController;
import com.bootcamp.product.dto.CreateProductDTO;
import com.bootcamp.product.dto.ProductDTO;
import com.bootcamp.product.dto.UpdateProductDTO;
import com.bootcamp.product.service.ProductService;
import com.bootcamp.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController Integration Tests")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private ProductDTO sampleProductDTO;
    private CreateProductDTO validCreateDTO;

    @BeforeEach
    void setUp() {
        sampleProductDTO = new ProductDTO(
            1L,
            "Test Product",
            "Test Description",
            99.99,
            100
        );

        validCreateDTO = new CreateProductDTO(
            "New Product",
            "Description",
            49.99,
            50
        );
    }

    // TODO: Implementar tests
}
```

---

## 📝 Paso 2: Tests GET - Listar Productos

```java
@Nested
@DisplayName("GET /api/products")
class GetAllProductsTests {

    @Test
    @DisplayName("should return 200 and list of products")
    void getAllProducts_ProductsExist_Returns200WithList() throws Exception {
        // TODO: Implementar
        // Given
        List<ProductDTO> products = List.of(
            new ProductDTO(1L, "Product 1", "Desc 1", 10.0, 5),
            new ProductDTO(2L, "Product 2", "Desc 2", 20.0, 10)
        );
        when(productService.findAll()).thenReturn(products);

        // When & Then
        mockMvc.perform(get("/api/products"))
            .andDo(print())  // Imprime request/response para debug
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Product 1"))
            .andExpect(jsonPath("$[1].name").value("Product 2"));

        verify(productService).findAll();
    }

    @Test
    @DisplayName("should return 200 and empty list when no products")
    void getAllProducts_NoProducts_Returns200WithEmptyList() throws Exception {
        // TODO: Implementar
        // Given
        when(productService.findAll()).thenReturn(List.of());

        // When & Then
        // mockMvc.perform(get("/api/products"))
        //     .andExpect(status().isOk())
        //     .andExpect(jsonPath("$", hasSize(0)));
    }
}
```

---

## 📝 Paso 3: Tests GET - Producto por ID

```java
@Nested
@DisplayName("GET /api/products/{id}")
class GetProductByIdTests {

    @Test
    @DisplayName("should return 200 and product when ID exists")
    void getProductById_ExistingId_Returns200WithProduct() throws Exception {
        // TODO: Implementar
        // Given
        when(productService.findById(1L)).thenReturn(sampleProductDTO);

        // When & Then
        mockMvc.perform(get("/api/products/{id}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Test Product"))
            .andExpect(jsonPath("$.price").value(99.99));
    }

    @Test
    @DisplayName("should return 404 when ID does not exist")
    void getProductById_NonExistingId_Returns404() throws Exception {
        // TODO: Implementar
        // Given
        when(productService.findById(999L))
            .thenThrow(new ResourceNotFoundException("Product not found with id: 999"));

        // When & Then
        mockMvc.perform(get("/api/products/{id}", 999))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value(containsString("not found")));
    }

    @Test
    @DisplayName("should return 400 when ID is invalid")
    void getProductById_InvalidId_Returns400() throws Exception {
        // TODO: Implementar
        // When & Then
        mockMvc.perform(get("/api/products/{id}", "abc"))
            .andExpect(status().isBadRequest());
    }
}
```

---

## 📝 Paso 4: Tests POST - Crear Producto

```java
@Nested
@DisplayName("POST /api/products")
class CreateProductTests {

    @Test
    @DisplayName("should return 201 and created product when data is valid")
    void createProduct_ValidData_Returns201() throws Exception {
        // TODO: Implementar
        // Given
        ProductDTO createdProduct = new ProductDTO(
            1L, "New Product", "Description", 49.99, 50);
        when(productService.create(any(CreateProductDTO.class)))
            .thenReturn(createdProduct);

        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCreateDTO)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("New Product"));

        verify(productService).create(any(CreateProductDTO.class));
    }

    @Test
    @DisplayName("should return 400 when name is blank")
    void createProduct_BlankName_Returns400() throws Exception {
        // TODO: Implementar
        // Given
        String invalidRequest = """
            {
                "name": "",
                "description": "Description",
                "price": 49.99,
                "stock": 50
            }
            """;

        // When & Then
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors.name").exists());

        // Verificar que el service NO fue llamado
        verify(productService, never()).create(any());
    }

    @Test
    @DisplayName("should return 400 when price is negative")
    void createProduct_NegativePrice_Returns400() throws Exception {
        // TODO: Implementar
        String invalidRequest = """
            {
                "name": "Product",
                "description": "Description",
                "price": -10.0,
                "stock": 50
            }
            """;

        // When & Then
        // mockMvc.perform(...)
        //     .andExpect(status().isBadRequest())
        //     .andExpect(jsonPath("$.errors.price").exists());
    }

    @Test
    @DisplayName("should return 400 when required fields are missing")
    void createProduct_MissingFields_Returns400() throws Exception {
        // TODO: Implementar
        String invalidRequest = """
            {
                "description": "Only description"
            }
            """;

        // When & Then
        // Verificar que retorna errores para name y price
    }

    @Test
    @DisplayName("should return 409 when product name already exists")
    void createProduct_DuplicateName_Returns409() throws Exception {
        // TODO: Implementar
        // Given
        // when(productService.create(any()))
        //     .thenThrow(new DuplicateResourceException("Product name already exists"));

        // When & Then
        // mockMvc.perform(...)
        //     .andExpect(status().isConflict());
    }
}
```

---

## 📝 Paso 5: Tests PUT - Actualizar Producto

```java
@Nested
@DisplayName("PUT /api/products/{id}")
class UpdateProductTests {

    @Test
    @DisplayName("should return 200 and updated product")
    void updateProduct_ValidData_Returns200() throws Exception {
        // TODO: Implementar
        // Given
        UpdateProductDTO updateDTO = new UpdateProductDTO(
            "Updated Name", null, 199.99, null);
        ProductDTO updatedProduct = new ProductDTO(
            1L, "Updated Name", "Test Description", 199.99, 100);

        when(productService.update(eq(1L), any(UpdateProductDTO.class)))
            .thenReturn(updatedProduct);

        // When & Then
        mockMvc.perform(put("/api/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Name"))
            .andExpect(jsonPath("$.price").value(199.99));
    }

    @Test
    @DisplayName("should return 404 when product not found")
    void updateProduct_NonExistingId_Returns404() throws Exception {
        // TODO: Implementar
    }

    @Test
    @DisplayName("should return 400 when validation fails")
    void updateProduct_InvalidData_Returns400() throws Exception {
        // TODO: Implementar
    }
}
```

---

## 📝 Paso 6: Tests DELETE

```java
@Nested
@DisplayName("DELETE /api/products/{id}")
class DeleteProductTests {

    @Test
    @DisplayName("should return 204 when product deleted successfully")
    void deleteProduct_ExistingId_Returns204() throws Exception {
        // TODO: Implementar
        // Given
        doNothing().when(productService).delete(1L);

        // When & Then
        mockMvc.perform(delete("/api/products/{id}", 1))
            .andExpect(status().isNoContent())
            .andExpect(content().string(""));  // No content

        verify(productService).delete(1L);
    }

    @Test
    @DisplayName("should return 404 when product not found")
    void deleteProduct_NonExistingId_Returns404() throws Exception {
        // TODO: Implementar
        // Given
        doThrow(new ResourceNotFoundException("Product not found"))
            .when(productService).delete(999L);

        // When & Then
        // mockMvc.perform(delete("/api/products/{id}", 999))
        //     .andExpect(status().isNotFound());
    }
}
```

---

## 📝 Paso 7: Tests de Query Parameters

```java
@Nested
@DisplayName("GET /api/products with filters")
class GetProductsWithFiltersTests {

    @Test
    @DisplayName("should filter by category")
    void getProducts_WithCategory_ReturnsFilteredProducts() throws Exception {
        // TODO: Implementar
        // Given
        List<ProductDTO> electronics = List.of(
            new ProductDTO(1L, "Laptop", "Desc", 999.0, 10),
            new ProductDTO(2L, "Phone", "Desc", 599.0, 20)
        );
        when(productService.findByCategory("electronics")).thenReturn(electronics);

        // When & Then
        mockMvc.perform(get("/api/products")
                .param("category", "electronics"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @DisplayName("should filter by price range")
    void getProducts_WithPriceRange_ReturnsFilteredProducts() throws Exception {
        // TODO: Implementar
        // mockMvc.perform(get("/api/products")
        //         .param("minPrice", "50")
        //         .param("maxPrice", "100"))
        //     .andExpect(status().isOk());
    }

    @Test
    @DisplayName("should paginate results")
    void getProducts_WithPagination_ReturnsPaginatedResults() throws Exception {
        // TODO: Implementar
        // mockMvc.perform(get("/api/products")
        //         .param("page", "0")
        //         .param("size", "10")
        //         .param("sort", "name,asc"))
        //     .andExpect(status().isOk())
        //     .andExpect(jsonPath("$.content").isArray())
        //     .andExpect(jsonPath("$.totalElements").isNumber());
    }
}
```

---

## ✅ Criterios de Éxito

- [ ] Tests cubren todos los endpoints del controller
- [ ] Se prueban casos de éxito y error
- [ ] Se validan códigos de estado HTTP correctos
- [ ] Se verifica el contenido JSON de las respuestas
- [ ] Se prueban las validaciones de entrada
- [ ] El service no se llama cuando la validación falla

---

## 🚀 Ejecutar Tests

```bash
# Ejecutar tests del controller
mvn test -Dtest=ProductControllerTest

# Ejecutar con detalle
mvn test -Dtest=ProductControllerTest -Dsurefire.useFile=false

# Ver reporte HTML
mvn surefire-report:report
open target/site/surefire-report.html
```

---

## 💡 Tips de MockMvc

### Métodos útiles de request

```java
// GET con parámetros
mockMvc.perform(get("/api/products")
    .param("name", "test")
    .header("X-Custom-Header", "value")
    .accept(MediaType.APPLICATION_JSON))

// POST con JSON
mockMvc.perform(post("/api/products")
    .contentType(MediaType.APPLICATION_JSON)
    .content("{\"name\": \"test\"}"))

// POST con form data
mockMvc.perform(post("/api/form")
    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
    .param("name", "test")
    .param("email", "test@email.com"))
```

### Matchers útiles de jsonPath

```java
.andExpect(jsonPath("$.name").value("test"))           // Valor exacto
.andExpect(jsonPath("$.name").exists())                 // Campo existe
.andExpect(jsonPath("$.name").doesNotExist())           // Campo no existe
.andExpect(jsonPath("$.name").isEmpty())                // Campo vacío
.andExpect(jsonPath("$.items", hasSize(3)))             // Tamaño de array
.andExpect(jsonPath("$.items[0].name").value("first"))  // Primer elemento
.andExpect(jsonPath("$.price", greaterThan(10.0)))      // Comparación
.andExpect(jsonPath("$.name", containsString("test")))  // Contiene
.andExpect(jsonPath("$[*].name", hasItem("test")))      // Array contiene
```
