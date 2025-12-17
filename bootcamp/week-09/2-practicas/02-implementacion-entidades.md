# Práctica 02: Implementación de Entidades y Persistencia

## 📋 Objetivos

- Implementar las entidades JPA del proyecto
- Configurar las relaciones entre entidades
- Crear los repositorios con queries personalizadas
- Configurar PostgreSQL con Docker

---

## Parte 1: Setup Inicial del Proyecto

### 📝 Ejercicio 1.1: Crear Proyecto Spring Boot

Usa Spring Initializr o copia la estructura base:

**pom.xml** (dependencias esenciales):

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>

    <groupId>com.bootcamp</groupId>
    <artifactId>final-project</artifactId>
    <version>1.0.0</version>
    <name>Final Project</name>

    <properties>
        <java.version>21</java.version>
    </properties>

    <dependencies>
        <!-- Web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- JPA -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <!-- PostgreSQL -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!-- Validation -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!-- JWT -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.12.3</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.12.3</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.12.3</version>
            <scope>runtime</scope>
        </dependency>

        <!-- OpenAPI/Swagger -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>2.3.0</version>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>postgresql</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

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

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
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
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

### 📝 Ejercicio 1.2: Configurar Docker

**Dockerfile**:

```dockerfile
# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Copiar archivos de Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Descargar dependencias (cache layer)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src src

# Construir aplicación
RUN ./mvnw package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Crear usuario no-root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copiar JAR desde builder
COPY --from=builder /app/target/*.jar app.jar

# Cambiar a usuario no-root
USER appuser

# Exponer puerto
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**docker-compose.yml**:

```yaml
version: '3.8'

services:
  app:
    build: .
    container_name: final-project-app
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - DB_HOST=db
      - DB_PORT=5432
      - DB_NAME=${DB_NAME:-finalproject}
      - DB_USER=${DB_USER:-dev}
      - DB_PASSWORD=${DB_PASSWORD:-dev123}
      - JWT_SECRET=${JWT_SECRET:-my-super-secret-key-that-should-be-at-least-256-bits-long}
    depends_on:
      db:
        condition: service_healthy
    networks:
      - backend
    restart: unless-stopped

  db:
    image: postgres:16-alpine
    container_name: final-project-db
    environment:
      - POSTGRES_DB=${DB_NAME:-finalproject}
      - POSTGRES_USER=${DB_USER:-dev}
      - POSTGRES_PASSWORD=${DB_PASSWORD:-dev123}
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${DB_USER:-dev} -d ${DB_NAME:-finalproject}"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - backend

  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: final-project-pgadmin
    environment:
      - PGADMIN_DEFAULT_EMAIL=${PGADMIN_EMAIL:-admin@admin.com}
      - PGADMIN_DEFAULT_PASSWORD=${PGADMIN_PASSWORD:-admin}
    ports:
      - "5050:80"
    depends_on:
      - db
    networks:
      - backend

networks:
  backend:
    driver: bridge

volumes:
  postgres_data:
```

**.env.example**:

```bash
# Database
DB_NAME=finalproject
DB_USER=dev
DB_PASSWORD=dev123

# JWT
JWT_SECRET=my-super-secret-key-that-should-be-at-least-256-bits-long-for-security

# PgAdmin
PGADMIN_EMAIL=admin@admin.com
PGADMIN_PASSWORD=admin
```

### 📝 Ejercicio 1.3: Configurar application.properties

**src/main/resources/application.properties**:

```properties
# Application
spring.application.name=final-project

# Server
server.port=8080

# Active profile
spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}
```

**src/main/resources/application-dev.properties**:

```properties
# Database
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:finalproject}
spring.datasource.username=${DB_USER:dev}
spring.datasource.password=${DB_PASSWORD:dev123}
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT
jwt.secret=${JWT_SECRET:default-secret-key-for-development-only-change-in-production}
jwt.expiration=900000

# OpenAPI
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html

# Logging
logging.level.org.springframework.security=DEBUG
logging.level.com.bootcamp=DEBUG
```

---

## Parte 2: Entidad Base Auditable

### 📝 Ejercicio 2.1: Crear AuditableEntity

```java
// src/main/java/com/bootcamp/finalproject/common/audit/AuditableEntity.java
package com.bootcamp.finalproject.common.audit;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AuditableEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
```

---

## Parte 3: Implementar Entidades

### 📝 Ejercicio 3.1: Entidad User

```java
// src/main/java/com/bootcamp/finalproject/user/entity/Role.java
package com.bootcamp.finalproject.user.entity;

public enum Role {
    USER,
    ADMIN
}
```

```java
// src/main/java/com/bootcamp/finalproject/user/entity/User.java
package com.bootcamp.finalproject.user.entity;

import com.bootcamp.finalproject.common.audit.AuditableEntity;
// TODO: Importar entidad Order cuando esté creada
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    // TODO: Descomentar cuando exista la entidad Order
    // @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    // private List<Order> orders = new ArrayList<>();

    // Constructors
    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
```

### 📝 Ejercicio 3.2: Entidad Category

```java
// src/main/java/com/bootcamp/finalproject/category/entity/Category.java
package com.bootcamp.finalproject.category.entity;

import com.bootcamp.finalproject.common.audit.AuditableEntity;
// TODO: Importar Product cuando esté creado
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // TODO: Descomentar cuando exista Product
    // @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    // private List<Product> products = new ArrayList<>();

    // Constructors
    public Category() {}

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
```

### 📝 Ejercicio 3.3: Entidad Product

```java
// src/main/java/com/bootcamp/finalproject/product/entity/Product.java
package com.bootcamp.finalproject.product.entity;

import com.bootcamp.finalproject.category.entity.Category;
import com.bootcamp.finalproject.common.audit.AuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Constructors
    public Product() {}

    public Product(String name, String description, Double price, Integer stock, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
```

### 📝 Ejercicio 3.4: Tus Entidades Adicionales

**Instrucciones**: Crea las entidades adicionales de tu proyecto siguiendo el mismo patrón. Por ejemplo, para un e-commerce necesitarías Order y OrderItem.

```java
// TODO: Crear Order.java
// - Relación ManyToOne con User
// - Relación OneToMany con OrderItem
// - Campo status (enum OrderStatus: PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
// - Campo totalAmount

// TODO: Crear OrderItem.java
// - Relación ManyToOne con Order
// - Relación ManyToOne con Product
// - Campo quantity
// - Campo unitPrice (precio al momento de la compra)
```

---

## Parte 4: Crear Repositories

### 📝 Ejercicio 4.1: UserRepository

```java
// src/main/java/com/bootcamp/finalproject/user/repository/UserRepository.java
package com.bootcamp.finalproject.user.repository;

import com.bootcamp.finalproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
```

### 📝 Ejercicio 4.2: CategoryRepository

```java
// src/main/java/com/bootcamp/finalproject/category/repository/CategoryRepository.java
package com.bootcamp.finalproject.category.repository;

import com.bootcamp.finalproject.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
```

### 📝 Ejercicio 4.3: ProductRepository con Queries Custom

```java
// src/main/java/com/bootcamp/finalproject/product/repository/ProductRepository.java
package com.bootcamp.finalproject.product.repository;

import com.bootcamp.finalproject.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Query method básico
    List<Product> findByCategoryId(Long categoryId);

    // Query method con paginación
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // Buscar por nombre (contiene, case insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Productos con stock > 0
    List<Product> findByStockGreaterThan(Integer minStock);

    // Query JPQL custom
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice);

    // Query JPQL con JOIN
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.category.name = :categoryName")
    List<Product> findByCategoryNameWithCategory(@Param("categoryName") String categoryName);

    // Verificar stock
    @Query("SELECT CASE WHEN p.stock >= :quantity THEN true ELSE false END " +
           "FROM Product p WHERE p.id = :productId")
    boolean hasEnoughStock(@Param("productId") Long productId, @Param("quantity") Integer quantity);
}
```

### 📝 Ejercicio 4.4: Tus Repositories Adicionales

**Instrucciones**: Crea los repositories para tus otras entidades:

```java
// TODO: Crear OrderRepository.java
// - findByUserId(Long userId)
// - findByUserIdAndStatus(Long userId, OrderStatus status)
// - findByStatus(OrderStatus status)

// TODO: Crear OrderItemRepository.java
// - findByOrderId(Long orderId)
```

---

## Parte 5: Verificar Setup

### 📝 Ejercicio 5.1: Clase Principal

```java
// src/main/java/com/bootcamp/finalproject/FinalProjectApplication.java
package com.bootcamp.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }
}
```

### 📝 Ejercicio 5.2: Verificar que Funciona

```bash
# Copiar .env
cp .env.example .env

# Levantar con Docker Compose
docker-compose up --build

# Verificar logs
docker-compose logs -f app

# Deberías ver:
# - Hibernate creando las tablas
# - "Started FinalProjectApplication"
```

### 📝 Ejercicio 5.3: Verificar Tablas en PostgreSQL

```bash
# Conectar a PostgreSQL
docker-compose exec db psql -U dev -d finalproject

# Listar tablas
\dt

# Ver estructura de una tabla
\d users
\d products
\d categories

# Salir
\q
```

---

## ✅ Checklist de Verificación

Antes de continuar, verifica:

- [ ] `docker-compose up` ejecuta sin errores
- [ ] La aplicación conecta a PostgreSQL
- [ ] Las tablas se crean automáticamente
- [ ] Tienes al menos 3 entidades con relaciones
- [ ] Los repositories están creados
- [ ] AuditableEntity funciona (createdAt, updatedAt)

---

## 🚀 Siguiente Paso

Una vez que las entidades y repositories funcionan, continúa con:

→ [Práctica 03: Integración de Componentes](./03-integracion-componentes.md)

---

> **💡 Tip**: Si tienes errores de conexión a la BD, verifica que el servicio `db` esté healthy antes de que `app` intente conectar. El `depends_on` con `condition: service_healthy` debería manejarlo.
