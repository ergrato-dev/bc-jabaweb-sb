package com.bootcamp.security.service;

import com.bootcamp.security.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Servicio para verificación de propiedad de recursos de usuario.
 * Se usa en @PreAuthorize para autorización basada en propiedad.
 */
@Service("userSecurityService")
public class UserSecurityService {

    private final UserRepository userRepository;

    public UserSecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Verifica si el usuario autenticado es propietario del recurso.
     *
     * @param userId ID del usuario a verificar
     * @param username Username del usuario autenticado
     * @return true si el usuario es propietario
     */
    public boolean isOwner(UUID userId, String username) {
        // TODO: Implementar verificación de propiedad
        // 1. Buscar usuario por ID con userRepository.findById()
        // 2. Si existe, comparar username con el parámetro
        // 3. Retornar true si coinciden, false en caso contrario

        return false; // TODO: Reemplazar
    }
}
