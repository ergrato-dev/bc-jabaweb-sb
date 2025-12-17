package com.bootcamp.apidocs.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para la documentación de la API.
 *
 * Esta clase configura la información que aparece en Swagger UI:
 * - Título, versión y descripción de la API
 * - Información de contacto
 * - Licencia
 * - Servidores disponibles
 */
@Configuration
public class OpenApiConfig {

    /**
     * TODO 1: Crear Bean OpenAPI
     *
     * Implementa este método para configurar la documentación de la API.
     *
     * Requisitos:
     * - Info con:
     *   - title: "Task Manager API"
     *   - version: "1.0.0"
     *   - description: "API REST para gestión de tareas - Bootcamp Java Web"
     *   - contact con name y email
     *   - license MIT
     * - Servers:
     *   - http://localhost:8080 (Desarrollo)
     *   - https://api.bootcamp.com (Producción)
     *
     * Hint: Usa new OpenAPI().info(...).servers(...)
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // TODO: Implementar configuración OpenAPI
        //
        // return new OpenAPI()
        //     .info(new Info()
        //         .title("...")
        //         .version("...")
        //         .description("...")
        //         .contact(new Contact()
        //             .name("...")
        //             .email("..."))
        //         .license(new License()
        //             .name("MIT")
        //             .url("https://opensource.org/licenses/MIT")))
        //     .servers(List.of(
        //         new Server().url("...").description("...")
        //     ));

        return new OpenAPI(); // Placeholder - reemplazar con implementación
    }
}
