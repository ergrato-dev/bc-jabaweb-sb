package com.bootcamp.finalproject.auth.dto;

/**
 * DTO para respuesta de autenticación.
 */
public record AuthResponse(
    String token,
    String type,
    String email,
    String name,
    String role
) {
    public AuthResponse(String token, String email, String name, String role) {
        this(token, "Bearer", email, name, role);
    }
}
