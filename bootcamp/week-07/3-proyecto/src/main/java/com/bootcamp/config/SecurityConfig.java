package com.bootcamp.config;

import com.bootcamp.security.filter.JwtAuthenticationFilter;
import com.bootcamp.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                         JwtAuthenticationFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // TODO: Configurar la cadena de seguridad
        // 1. Deshabilitar CSRF (no necesario para APIs stateless)
        // 2. Configurar sesión como STATELESS
        // 3. Configurar el AuthenticationProvider
        // 4. Definir rutas públicas:
        //    - /api/auth/** (registro, login, refresh)
        //    - /swagger-ui/**, /api-docs/**, /v3/api-docs/**
        //    - /actuator/health
        // 5. Requerir autenticación para todo lo demás
        // 6. Agregar JwtAuthenticationFilter antes de UsernamePasswordAuthenticationFilter

        return http
            // TODO: Implementar configuración
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // TODO: Configurar DaoAuthenticationProvider
        // 1. Crear instancia de DaoAuthenticationProvider
        // 2. Establecer userDetailsService
        // 3. Establecer passwordEncoder
        // 4. Retornar provider

        return null; // TODO: Reemplazar
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // TODO: Retornar BCryptPasswordEncoder
        return null; // TODO: Reemplazar
    }
}
