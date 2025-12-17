package com.bootcamp.taskmanager.repository;

import com.bootcamp.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio JPA para la entidad Task.
 *
 * ⚠️ ARCHIVO DE PRÁCTICA: Completa los TODOs para agregar Query Methods.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    // ═══════════════════════════════════════════════════════════
    // Métodos heredados de JpaRepository (ya disponibles):
    // - save(Task task)
    // - findById(UUID id) -> Optional<Task>
    // - findAll() -> List<Task>
    // - deleteById(UUID id)
    // - existsById(UUID id)
    // - count()
    // ═══════════════════════════════════════════════════════════

    // ═══════════════════════════════════════════════════════════
    // TODO 1: Crear Query Method para buscar tareas por estado
    // Nombre: findByCompleted
    // Parámetro: boolean completed
    // Retorno: List<Task>
    // SQL generado: SELECT * FROM tasks WHERE completed = ?
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // TODO 2: Crear Query Method para buscar por título (parcial)
    // Nombre: findByTitleContainingIgnoreCase
    // Parámetro: String title
    // Retorno: List<Task>
    // SQL generado: SELECT * FROM tasks WHERE LOWER(title) LIKE LOWER('%?%')
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // TODO 3: Crear Query Method para contar por estado
    // Nombre: countByCompleted
    // Parámetro: boolean completed
    // Retorno: long
    // SQL generado: SELECT COUNT(*) FROM tasks WHERE completed = ?
    // ═══════════════════════════════════════════════════════════


    // ═══════════════════════════════════════════════════════════
    // TODO 4: Crear Query Method para verificar existencia por título
    // Nombre: existsByTitle
    // Parámetro: String title
    // Retorno: boolean
    // SQL generado: SELECT EXISTS(SELECT 1 FROM tasks WHERE title = ?)
    // ═══════════════════════════════════════════════════════════

}
