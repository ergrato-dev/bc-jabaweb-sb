package com.bootcamp.config;

import com.bootcamp.security.entity.Role;
import com.bootcamp.security.entity.User;
import com.bootcamp.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            // TODO: Crear usuario ADMIN si no existe
            // 1. Verificar si existe username "admin"
            // 2. Si no existe, crear User con:
            //    - username: "admin"
            //    - email: "admin@bootcamp.com"
            //    - password: passwordEncoder.encode("Admin123!")
            //    - role: Role.ADMIN
            // 3. Guardar en repository
            // 4. Loguear "Usuario ADMIN creado"

            // TODO: Crear usuario USER de prueba si no existe
            // 1. Verificar si existe username "user"
            // 2. Si no existe, crear User con:
            //    - username: "user"
            //    - email: "user@bootcamp.com"
            //    - password: passwordEncoder.encode("User123!")
            //    - role: Role.USER
            // 3. Guardar en repository
            // 4. Loguear "Usuario USER creado"

            log.info("Inicialización de datos completada");
        };
    }
}
