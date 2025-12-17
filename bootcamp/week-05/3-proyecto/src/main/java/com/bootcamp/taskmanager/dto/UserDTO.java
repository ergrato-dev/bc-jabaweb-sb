package com.bootcamp.taskmanager.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
    UUID id,
    String username,
    String email,
    LocalDateTime createdAt,
    int taskCount
) {
    // ============================================================
    // TODO 1: Crear método estático fromEntity(User user)
    // ============================================================
    // Instrucciones:
    // - Mapear campos básicos: id, username, email, createdAt
    // - Calcular taskCount:
    //   - Si user.getTasks() != null, usar user.getTasks().size()
    //   - Si es null, usar 0
    // - Retornar new UserDTO(...)

    // public static UserDTO fromEntity(User user) {
    //     return new UserDTO(
    //         user.getId(),
    //         user.getUsername(),
    //         user.getEmail(),
    //         user.getCreatedAt(),
    //         // TODO: calcular taskCount
    //     );
    // }
}
