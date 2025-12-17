package com.bootcamp.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación ToDo API.
 *
 * @SpringBootApplication habilita:
 * - Autoconfiguración de Spring Boot
 * - Escaneo de componentes en este paquete y subpaquetes
 * - Configuración basada en Java
 */
@SpringBootApplication
public class TodoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
        System.out.println("🚀 ToDo API iniciada en http://localhost:8080");
        System.out.println("📋 Endpoints disponibles en /api/tasks");
    }
}
