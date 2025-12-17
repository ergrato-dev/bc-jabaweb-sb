package com.bootcamp.security.controller;

import com.bootcamp.security.dto.*;
import com.bootcamp.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para endpoints de autenticación.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de autenticación y registro")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "409", description = "Username o email ya existe")
    })
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterRequest request) {
        // TODO: Llamar a authService.register() y retornar con status CREATED
        return null; // TODO: Reemplazar
    }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // TODO: Llamar a authService.login() y retornar con status OK
        return null; // TODO: Reemplazar
    }

    @PostMapping("/refresh")
    @Operation(summary = "Renovar access token")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token renovado exitosamente"),
        @ApiResponse(responseCode = "401", description = "Refresh token inválido o expirado")
    })
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        // TODO: Llamar a authService.refreshToken() y retornar con status OK
        return null; // TODO: Reemplazar
    }
}
