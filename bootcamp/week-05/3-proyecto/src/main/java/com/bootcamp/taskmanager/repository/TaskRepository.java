package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // ============================================================
    // TODO 1: Buscar tareas por userId
    // ============================================================
    // Instrucciones:
    // - Crear método findByUserId que retorne List<Task>
    // - Spring Data navega la relación automáticamente

    // List<Task> findByUserId(UUID userId);


    // ============================================================
    // TODO 2: Buscar tareas por username del usuario
    // ============================================================
    // Instrucciones:
    // - Crear método findByUserUsername que retorne List<Task>
    // - Navega: Task -> user -> username

    // List<Task> findByUserUsername(String username);


    // ============================================================
    // TODO 3: Buscar tarea con su usuario (JOIN FETCH)
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT t FROM Task t JOIN FETCH t.user WHERE t.id = :id

    // @Query("...")
    // Optional<Task> findByIdWithUser(@Param("id") UUID id);


    // ============================================================
    // TODO 4: Buscar tarea con sus categorías (JOIN FETCH)
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT t FROM Task t LEFT JOIN FETCH t.categories WHERE t.id = :id

    // @Query("...")
    // Optional<Task> findByIdWithCategories(@Param("id") UUID id);


    // ============================================================
    // TODO 5: Buscar tarea completa (usuario + categorías)
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT DISTINCT t FROM Task t
    //   LEFT JOIN FETCH t.user
    //   LEFT JOIN FETCH t.categories
    //   WHERE t.id = :id

    // @Query("...")
    // Optional<Task> findByIdComplete(@Param("id") UUID id);


    // ============================================================
    // TODO 6: Buscar tareas de un usuario con categorías
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT DISTINCT t FROM Task t
    //   LEFT JOIN FETCH t.categories
    //   WHERE t.user.id = :userId

    // @Query("...")
    // List<Task> findByUserIdWithCategories(@Param("userId") UUID userId);


    // ============================================================
    // TODO 7: Buscar tareas por categoría
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT t FROM Task t JOIN t.categories c WHERE c.id = :categoryId

    // @Query("...")
    // List<Task> findByCategoryId(@Param("categoryId") UUID categoryId);


    // ============================================================
    // TODO 8: Buscar tareas completadas de un usuario
    // ============================================================
    // Instrucciones:
    // - Crear método findByUserIdAndCompleted
    // - Parámetros: UUID userId, Boolean completed

    // List<Task> findByUserIdAndCompleted(UUID userId, Boolean completed);
}
