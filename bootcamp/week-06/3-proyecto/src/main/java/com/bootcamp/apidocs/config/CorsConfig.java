package com.bootcamp.apidocs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS (Cross-Origin Resource Sharing).
 *
 * Permite que aplicaciones frontend en diferentes orígenes
 * puedan consumir esta API.
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${cors.allowed-origins:http://localhost:3000}")
    private String[] allowedOrigins;

    /**
     * TODO 1: Implementar configuración CORS
     *
     * Configura CORS para permitir:
     * - Mapping: /api/**
     * - Orígenes: los definidos en allowedOrigins
     * - Métodos: GET, POST, PUT, DELETE, PATCH, OPTIONS
     * - Headers: todos (*)
     * - Exposed Headers: Authorization, X-Total-Count
     * - Credentials: true
     * - Max Age: 3600 segundos
     *
     * Hint: registry.addMapping("/api/**").allowedOrigins(...)
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // TODO: Implementar configuración CORS
        //
        // registry.addMapping("/api/**")
        //     .allowedOrigins(allowedOrigins)
        //     .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
        //     .allowedHeaders("*")
        //     .exposedHeaders("Authorization", "X-Total-Count")
        //     .allowCredentials(true)
        //     .maxAge(3600);
    }
}
