package com.bootcamp.security.repository;

import com.bootcamp.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para operaciones con la entidad User.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Busca un usuario por su username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su email.
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica si existe un usuario con el username dado.
     */
    boolean existsByUsername(String username);

    /**
     * Verifica si existe un usuario con el email dado.
     */
    boolean existsByEmail(String email);
}
