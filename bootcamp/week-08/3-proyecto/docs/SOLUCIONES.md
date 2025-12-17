# Soluciones para Instructores

> ⚠️ **CONFIDENCIAL**: Este archivo es solo para instructores.
> No compartir con estudiantes.

## 📋 Índice

1. [ProductServiceTest](#productservicetest)
2. [ProductControllerTest](#productcontrollertest)
3. [ProductRepositoryIT](#productrepositoryit)

---

## ProductServiceTest

### findById_ExistingId_ReturnsProduct

```java
@Test
@DisplayName("should return product when found")
void findById_ExistingId_ReturnsProduct() {
    // Given
    when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

    // When
    ProductDTO result = productService.findById(1L);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(1L);
    assertThat(result.name()).isEqualTo("Test Product");

    verify(productRepository, times(1)).findById(1L);
}
```

### findById_NonExistingId_ThrowsException

```java
@Test
@DisplayName("should throw exception when not found")
void findById_NonExistingId_ThrowsException() {
    // Given
    when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

    // When & Then
    assertThatThrownBy(() -> productService.findById(999L))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessageContaining("999");

    verify(productRepository).findById(999L);
}
```

### findAll_HasProducts_ReturnsAll

```java
@Test
@DisplayName("should return all products")
void findAll_HasProducts_ReturnsAll() {
    // Given
    Product product2 = new Product("Second", "Desc", 50.0, 10);
    product2.setId(2L);

    when(productRepository.findAll())
        .thenReturn(Arrays.asList(sampleProduct, product2));

    // When
    List<ProductDTO> result = productService.findAll();

    // Then
    assertThat(result).hasSize(2);
    assertThat(result.get(0).name()).isEqualTo("Test Product");
}
```

### create_ValidRequest_ReturnsCreatedProduct

```java
@Test
@DisplayName("should create product successfully")
void create_ValidRequest_ReturnsCreatedProduct() {
    // Given
    CreateProductRequest request = new CreateProductRequest(
        "New Product", "Description", "Category", 199.99, 50
    );

    when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

    // When
    ProductDTO result = productService.create(request);

    // Then
    assertThat(result).isNotNull();
    verify(productRepository).save(productCaptor.capture());
    Product captured = productCaptor.getValue();
    assertThat(captured.getName()).isEqualTo("New Product");
}
```

### update_ExistingProduct_ReturnsUpdated

```java
@Test
@DisplayName("should update existing product")
void update_ExistingProduct_ReturnsUpdated() {
    // Given
    CreateProductRequest request = new CreateProductRequest(
        "Updated Name", "Updated Desc", "NewCat", 299.99, 75
    );

    when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
    when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

    // When
    ProductDTO result = productService.update(1L, request);

    // Then
    verify(productRepository).findById(1L);
    verify(productRepository).save(productCaptor.capture());
    Product captured = productCaptor.getValue();
    assertThat(captured.getName()).isEqualTo("Updated Name");
}
```

### delete_ExistingProduct_DeletesSuccessfully

```java
@Test
@DisplayName("should delete existing product")
void delete_ExistingProduct_DeletesSuccessfully() {
    // Given
    when(productRepository.existsById(1L)).thenReturn(true);
    doNothing().when(productRepository).deleteById(1L);

    // When
    productService.delete(1L);

    // Then
    verify(productRepository).existsById(1L);
    verify(productRepository).deleteById(1L);
}
```

---

## ProductControllerTest

### getById_ExistingId_ReturnsProduct

```java
@Test
@DisplayName("should return product when found")
void getById_ExistingId_ReturnsProduct() throws Exception {
    when(productService.findById(1L)).thenReturn(sampleProduct);

    mockMvc.perform(get("/api/products/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Test Product"))
        .andExpect(jsonPath("$.price").value(99.99));
}
```

### create_ValidRequest_Returns201

```java
@Test
@DisplayName("should create product with valid data")
void create_ValidRequest_Returns201() throws Exception {
    CreateProductRequest request = new CreateProductRequest(
        "New Product", "Description", "Category", 199.99, 50
    );

    when(productService.create(any())).thenReturn(sampleProduct);

    mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists());
}
```

### create_BlankName_Returns400

```java
@Test
@DisplayName("should return 400 when name is blank")
void create_BlankName_Returns400() throws Exception {
    CreateProductRequest request = new CreateProductRequest(
        "", "Description", "Category", 99.99, 10
    );

    mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors.name").exists());
}
```

### delete_ExistingProduct_Returns204

```java
@Test
@DisplayName("should delete product and return 204")
void delete_ExistingProduct_Returns204() throws Exception {
    doNothing().when(productService).delete(1L);

    mockMvc.perform(delete("/api/products/1"))
        .andExpect(status().isNoContent());

    verify(productService).delete(1L);
}
```

---

## ProductRepositoryIT

### findByCategory_ExistingCategory_ReturnsProducts

```java
@Test
@DisplayName("should find products by category")
void findByCategory_ExistingCategory_ReturnsProducts() {
    // Given
    productRepository.saveAll(List.of(
        createProduct("Laptop", "Electronics", 999.0),
        createProduct("Phone", "Electronics", 699.0),
        createProduct("Chair", "Furniture", 199.0)
    ));

    // When
    List<Product> result = productRepository.findByCategory("Electronics");

    // Then
    assertThat(result).hasSize(2);
    assertThat(result).extracting(Product::getName)
        .containsExactlyInAnyOrder("Laptop", "Phone");
}
```

### findByPriceBetween_ValidRange_ReturnsProducts

```java
@Test
@DisplayName("should find products by price range")
void findByPriceBetween_ValidRange_ReturnsProducts() {
    // Given
    productRepository.saveAll(List.of(
        createProduct("Cheap", null, 10.0),
        createProduct("Medium", null, 50.0),
        createProduct("Expensive", null, 100.0)
    ));

    // When
    List<Product> result = productRepository.findByPriceBetween(20.0, 80.0);

    // Then
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getName()).isEqualTo("Medium");
}
```

### existsByName_ExistingName_ReturnsTrue

```java
@Test
@DisplayName("should check existence by name")
void existsByName_ExistingName_ReturnsTrue() {
    // Given
    productRepository.save(createProduct("Unique Product", null, 50.0));

    // When & Then
    assertThat(productRepository.existsByName("Unique Product")).isTrue();
    assertThat(productRepository.existsByName("Non Existing")).isFalse();
}
```

### findAll_WithSorting_ReturnsSortedResults

```java
@Test
@DisplayName("should return sorted results")
void findAll_WithSorting_ReturnsSortedResults() {
    // Given
    PageRequest pageRequest = PageRequest.of(
        0, 5, Sort.by(Sort.Direction.DESC, "price")
    );

    // When
    Page<Product> page = productRepository.findAll(pageRequest);

    // Then
    assertThat(page.getContent().get(0).getPrice()).isEqualTo(250.0);
}
```

---

## 📝 Notas Adicionales

### Errores Comunes de Estudiantes

1. **Olvidar `@ExtendWith(MockitoExtension.class)`**
   - Síntoma: Mocks son null
   - Solución: Agregar anotación

2. **No usar `@MockBean` en `@WebMvcTest`**
   - Síntoma: Error de inyección de dependencias
   - Solución: Usar `@MockBean` en lugar de `@Mock`

3. **Olvidar `@BeforeEach` para limpiar datos**
   - Síntoma: Tests fallan aleatoriamente
   - Solución: Agregar `repository.deleteAll()`

4. **Contenedor Docker no está corriendo**
   - Síntoma: TestContainers falla al iniciar
   - Solución: `docker info` para verificar

### Tips de Evaluación

- Verificar que los tests realmente prueban algo (no solo `assertTrue(true)`)
- Los tests deben tener assertions significativas
- Cobertura mínima 70%
- Tests deben ser independientes entre sí
