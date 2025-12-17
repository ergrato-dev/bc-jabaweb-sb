package com.bootcamp.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal del proyecto final.
 *
 * Esta API REST integra todos los conceptos del bootcamp:
 * - Spring Boot 3.2 con Java 21
 * - Spring Security con JWT
 * - JPA/Hibernate con PostgreSQL
 * - Docker Compose
 * - OpenAPI/Swagger
 * - Testing con JUnit 5 y TestContainers
 *
 * @author Bootcamp Java Web
 * @version 1.0.0
 */
@SpringBootApplication
public class FinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }
}
