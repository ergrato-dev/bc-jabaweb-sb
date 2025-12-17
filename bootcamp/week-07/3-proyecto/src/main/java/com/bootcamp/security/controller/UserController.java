package com.bootcamp.security.controller;

import com.bootcamp.security.dto.UserDTO;
import com.bootcamp.security.entity.User;
import com.bootcamp.security.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador para gestión de usuarios.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos los usuarios (solo ADMIN)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuarios"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado")
    })
    public List<UserDTO> getAllUsers() {
        // TODO: Retornar lista de todos los usuarios como DTOs
        // 1. userRepository.findAll()
        // 2. Convertir cada User a UserDTO
        // 3. Retornar lista

        return null; // TODO: Reemplazar
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Obtener perfil del usuario actual")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Perfil del usuario"),
        @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    public UserDTO getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // TODO: Buscar y retornar el usuario actual
        // 1. userRepository.findByUsername(userDetails.getUsername())
        // 2. Convertir a DTO
        // 3. Manejar caso de no encontrado

        return null; // TODO: Reemplazar
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurityService.isOwner(#id, principal.username)")
    @Operation(summary = "Obtener usuario por ID (propietario o ADMIN)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Datos del usuario"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        // TODO: Buscar usuario por ID y retornar
        // 1. userRepository.findById(id)
        // 2. Si existe, convertir a DTO y retornar 200
        // 3. Si no existe, retornar 404

        return null; // TODO: Reemplazar
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar usuario (solo ADMIN)")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuario eliminado"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        // TODO: Eliminar usuario
        // 1. Verificar si existe con userRepository.existsById(id)
        // 2. Si no existe, retornar 404
        // 3. Si existe, eliminar y retornar 204

        return null; // TODO: Reemplazar
    }

    /**
     * Convierte User a UserDTO.
     */
    private UserDTO toDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole().name(),
            user.getCreatedAt()
        );
    }
}
