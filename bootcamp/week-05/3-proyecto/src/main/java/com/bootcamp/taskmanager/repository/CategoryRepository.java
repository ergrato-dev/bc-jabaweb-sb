package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // ============================================================
    // TODO 1: Buscar categoría por nombre
    // ============================================================
    // Instrucciones:
    // - Crear método findByName que retorne Optional<Category>

    // Optional<Category> findByName(String name);


    // ============================================================
    // TODO 2: Buscar categorías por nombre (contiene, ignore case)
    // ============================================================
    // Instrucciones:
    // - Crear método findByNameContainingIgnoreCase
    // - Retorna List<Category>

    // List<Category> findByNameContainingIgnoreCase(String name);


    // ============================================================
    // TODO 3: Buscar categoría con sus tareas (JOIN FETCH)
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT c FROM Category c LEFT JOIN FETCH c.tasks WHERE c.id = :id

    // @Query("...")
    // Optional<Category> findByIdWithTasks(@Param("id") UUID id);


    // ============================================================
    // TODO 4: Categorías que tienen al menos una tarea
    // ============================================================
    // Instrucciones:
    // - Usar @Query con JPQL
    // - SELECT DISTINCT c FROM Category c JOIN c.tasks
    // - JOIN (no LEFT JOIN) filtra las que no tienen tareas

    // @Query("...")
    // List<Category> findCategoriesWithTasks();


    // ============================================================
    // TODO 5: Verificar si existe por nombre
    // ============================================================
    // Instrucciones:
    // - Crear método existsByName que retorne boolean

    // boolean existsByName(String name);
}
