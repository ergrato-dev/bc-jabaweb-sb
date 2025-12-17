package com.bootcamp.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Task Manager.
 *
 * @SpringBootApplication combina tres anotaciones:
 *   - @Configuration: Permite definir beans
 *   - @EnableAutoConfiguration: Configura automáticamente Spring Boot
 *   - @ComponentScan: Escanea este paquete y subpaquetes buscando @Component, @Service, etc.
 *
 * 📚 Documentación:
 * https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.using-the-springbootapplication-annotation
 */
@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}
