package com.bootcamp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // TODO: Configurar OpenAPI con soporte JWT
        // 1. Crear Info con título, versión y descripción
        // 2. Agregar SecurityRequirement para "bearerAuth"
        // 3. Agregar SecurityScheme tipo HTTP, scheme bearer, formato JWT

        return new OpenAPI()
            .info(new Info()
                .title("API Bootcamp - Seguridad")
                .version("1.0.0")
                .description("API REST con Spring Security y JWT")
                .contact(new Contact()
                    .name("Bootcamp")
                    .email("bootcamp@example.com")))
            // TODO: Agregar SecurityRequirement
            // TODO: Agregar Components con SecurityScheme
            ;
    }
}
