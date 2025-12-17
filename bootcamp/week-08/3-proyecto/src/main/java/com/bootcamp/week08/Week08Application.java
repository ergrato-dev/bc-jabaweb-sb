package com.bootcamp.week08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal de la Semana 8: Testing y Docker Avanzado.
 *
 * Esta aplicación demuestra:
 * - Tests unitarios con JUnit 5 y Mockito
 * - Tests de integración con TestContainers
 * - Docker Compose multi-servicio
 * - Cobertura de código con JaCoCo
 */
@SpringBootApplication
public class Week08Application {

    public static void main(String[] args) {
        SpringApplication.run(Week08Application.class, args);
    }
}
