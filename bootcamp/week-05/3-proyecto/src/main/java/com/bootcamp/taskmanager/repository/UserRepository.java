package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // ============================================================
    // TODO 1: Buscar usuario por username
    // ============================================================
    // Instrucciones:
    // - Crear método findByUsername que retorne Optional<User>
    // - Spring Data genera la query automáticamente

    // Optional<User> findByUsername(String username);


    // ============================================================
    // TODO 2: Buscar usuario por email
    // ============================================================
    // Instrucciones:
    // - Crear método findByEmail que retorne Optional<User>

    // Optional<User> findByEmail(String email);


    // ============================================================
    // TODO 3: Buscar usuario con sus tareas (JOIN FETCH)
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id
    // - Esto evita el problema N+1 al cargar tareas

    // @Query("...")
    // Optional<User> findByIdWithTasks(@Param("id") UUID id);


    // ============================================================
    // TODO 4: Listar todos los usuarios con sus tareas
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.tasks
    // - DISTINCT evita duplicados por el JOIN

    // @Query("...")
    // List<User> findAllWithTasks();


    // ============================================================
    // TODO 5: Verificar si existe por username
    // ============================================================
    // Instrucciones:
    // - Crear método existsByUsername que retorne boolean

    // boolean existsByUsername(String username);


    // ============================================================
    // TODO 6: Verificar si existe por email
    // ============================================================
    // Instrucciones:
    // - Crear método existsByEmail que retorne boolean

    // boolean existsByEmail(String email);
}
