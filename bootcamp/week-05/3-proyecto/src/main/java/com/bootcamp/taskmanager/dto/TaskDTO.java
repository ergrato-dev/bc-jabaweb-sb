package com.bootcamp.taskmanager.dto;

import com.bootcamp.taskmanager.entity.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TaskDTO(
    UUID id,
    String title,
    String description,
    Boolean completed,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    UUID userId,
    String username,
    List<CategoryDTO> categories
) {
    // ============================================================
    // TODO 1: Crear método estático fromEntity(Task task)
    // ============================================================
    // Instrucciones:
    // - Mapear campos básicos de task
    // - Obtener userId y username desde task.getUser()
    //   - Verificar que user no sea null antes de acceder
    // - Mapear categorías usando CategoryDTO.simpleFromEntity()
    //   - Si categories es null o vacío, usar List.of()
    //   - Si tiene categorías: task.getCategories().stream()
    //       .map(CategoryDTO::simpleFromEntity)
    //       .toList()

    // public static TaskDTO fromEntity(Task task) {
    //     return new TaskDTO(
    //         task.getId(),
    //         task.getTitle(),
    //         task.getDescription(),
    //         task.getCompleted(),
    //         task.getCreatedAt(),
    //         task.getUpdatedAt(),
    //         // TODO: userId desde user
    //         // TODO: username desde user
    //         // TODO: categorías mapeadas
    //     );
    // }


    // ============================================================
    // TODO 2: Versión simple sin categorías (evita cargar relación)
    // ============================================================
    // Instrucciones:
    // - Similar a fromEntity pero con categories = List.of()
    // - Útil cuando no necesitas las categorías

    // public static TaskDTO simpleFromEntity(Task task) {
    //
    // }
}
