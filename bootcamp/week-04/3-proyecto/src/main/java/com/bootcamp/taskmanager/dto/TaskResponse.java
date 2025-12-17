package com.bootcamp.taskmanager.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para respuestas de tareas.
 */
public record TaskResponse(
    UUID id,
    String title,
    String description,
    boolean completed,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
}
