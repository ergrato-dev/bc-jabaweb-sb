package com.bootcamp.security.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para solicitudes de refresh token.
 */
public class RefreshTokenRequest {

    @NotBlank(message = "El refresh token es obligatorio")
    private String refreshToken;

    public RefreshTokenRequest() {}

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
