package com.bootcamp.taskmanager.dto;

import java.util.UUID;

public record CategoryDTO(
    UUID id,
    String name,
    String color,
    String description,
    int taskCount
) {
    // ============================================================
    // TODO 1: Crear método estático fromEntity(Category category)
    // ============================================================
    // Instrucciones:
    // - Mapear campos básicos
    // - Calcular taskCount desde category.getTasks().size()
    //   - Verificar null antes de acceder

    // public static CategoryDTO fromEntity(Category category) {
    //     return new CategoryDTO(
    //         category.getId(),
    //         category.getName(),
    //         category.getColor(),
    //         category.getDescription(),
    //         // TODO: calcular taskCount
    //     );
    // }


    // ============================================================
    // TODO 2: Versión simple sin taskCount (evita cargar relación)
    // ============================================================
    // Instrucciones:
    // - Igual que fromEntity pero taskCount = 0
    // - Útil cuando solo necesitas datos básicos de categoría

    // public static CategoryDTO simpleFromEntity(Category category) {
    //     return new CategoryDTO(
    //         category.getId(),
    //         category.getName(),
    //         category.getColor(),
    //         category.getDescription(),
    //         0  // No cargamos la relación
    //     );
    // }
}
