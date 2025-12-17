package com.bootcamp.apidocs.dto;

// TODO 1: Importar la anotación @Schema de io.swagger.v3.oas.annotations.media
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para transferir datos de User.
 *
 * TODO 2: Añadir @Schema a nivel de clase con:
 *   - name = "User"
 *   - description = "Representación de un usuario"
 */
public class UserDTO {

    // TODO 3: Añadir @Schema con description, example (UUID), accessMode.READ_ONLY
    private UUID id;

    // TODO 4: Añadir @Schema con description, example, requiredMode.REQUIRED
    private String username;

    // TODO 5: Añadir @Schema con description, example, requiredMode.REQUIRED
    private String email;

    // TODO 6: Añadir @Schema con description, accessMode.READ_ONLY
    private LocalDateTime createdAt;

    // TODO 7: Añadir @Schema con description, example, accessMode.READ_ONLY
    private Integer taskCount;

    public UserDTO() {
    }

    public UserDTO(UUID id, String username, String email, LocalDateTime createdAt, Integer taskCount) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.taskCount = taskCount;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }
}
