package com.bootcamp.security.service;

import com.bootcamp.security.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación de UserDetailsService para cargar usuarios de la BD.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Buscar usuario por username
        // 1. Usar userRepository.findByUsername(username)
        // 2. Si no existe, lanzar UsernameNotFoundException con mensaje descriptivo
        // 3. Si existe, retornar el User (que implementa UserDetails)

        return null; // TODO: Reemplazar
    }
}
