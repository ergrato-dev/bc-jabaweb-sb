package com.bootcamp.apidocs.dto;

// TODO 1: Importar la anotación @Schema de io.swagger.v3.oas.annotations.media
import jakarta.validation.constraints.*;

/**
 * DTO para crear un nuevo usuario.
 *
 * TODO 2: Añadir @Schema a nivel de clase con:
 *   - name = "CreateUserRequest"
 *   - description = "Datos necesarios para crear un usuario"
 */
public class CreateUserRequest {

    // TODO 3: Añadir @Schema con description, example, requiredMode.REQUIRED, minLength, maxLength
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "El nombre de usuario solo puede contener letras, números y guiones bajos")
    private String username;

    // TODO 4: Añadir @Schema con description, example, requiredMode.REQUIRED
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters and Setters
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
}
