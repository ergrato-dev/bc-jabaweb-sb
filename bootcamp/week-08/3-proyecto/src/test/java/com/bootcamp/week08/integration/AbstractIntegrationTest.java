package com.bootcamp.week08.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Clase base abstracta para tests de integración con TestContainers.
 * 
 * Proporciona un contenedor PostgreSQL compartido para todos los tests
 * que extiendan esta clase.
 * 
 * El contenedor se inicia una sola vez (singleton pattern) para
 * mejorar el rendimiento de la suite de tests.
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {
    
    /**
     * Contenedor PostgreSQL singleton.
     * Se inicia una vez y se reutiliza en todos los tests.
     */
    static final PostgreSQLContainer<?> postgres;
    
    static {
        postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);  // Reutilizar entre ejecuciones
        postgres.start();
    }
    
    /**
     * Configura las propiedades de conexión dinámicamente
     * basándose en el contenedor PostgreSQL.
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.database-platform", 
            () -> "org.hibernate.dialect.PostgreSQLDialect");
    }
}
