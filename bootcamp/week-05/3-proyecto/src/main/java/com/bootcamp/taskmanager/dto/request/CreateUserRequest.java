package com.bootcamp.taskmanager.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "El username es requerido")
    @Size(min = 3, max = 50, message = "Username debe tener entre 3 y 50 caracteres")
    String username,

    @NotBlank(message = "El email es requerido")
    @Email(message = "Email debe ser válido")
    String email
) {}
