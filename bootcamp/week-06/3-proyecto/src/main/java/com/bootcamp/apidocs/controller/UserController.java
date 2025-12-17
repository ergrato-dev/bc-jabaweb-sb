package com.bootcamp.apidocs.controller;

import com.bootcamp.apidocs.dto.CreateUserRequest;
import com.bootcamp.apidocs.dto.ErrorResponse;
import com.bootcamp.apidocs.dto.UserDTO;
import com.bootcamp.apidocs.service.UserService;
// TODO 1: Importar las anotaciones de io.swagger.v3.oas.annotations:
//   - @Tag (para el controlador)
//   - @Operation, @ApiResponse, @ApiResponses (para los métodos)
//   - @Parameter (para parámetros)
// TODO 2: Importar @Content y @Schema de io.swagger.v3.oas.annotations.media
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para la gestión de usuarios.
 *
 * TODO 3: Añadir @Tag con:
 *   - name = "Usuarios"
 *   - description = "Operaciones CRUD para la gestión de usuarios"
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * TODO 4: Añadir @Operation con:
     *   - summary = "Obtener todos los usuarios"
     *   - description = "Retorna una lista con todos los usuarios registrados"
     *
     * TODO 5: Añadir @ApiResponse con:
     *   - responseCode = "200"
     *   - description = "Lista de usuarios obtenida exitosamente"
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * TODO 6: Añadir @Operation con:
     *   - summary = "Obtener usuario por ID"
     *   - description = "Busca y retorna un usuario específico por su ID"
     *
     * TODO 7: Añadir @ApiResponses con:
     *   - @ApiResponse(responseCode = "200", description = "Usuario encontrado")
     *   - @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
     *       content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
     *
     * TODO 8: Añadir @Parameter al parámetro id con:
     *   - description = "ID único del usuario (UUID)"
     *   - required = true
     *   - example = "550e8400-e29b-41d4-a716-446655440000"
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * TODO 9: Añadir @Operation con:
     *   - summary = "Buscar usuario por username"
     *   - description = "Busca un usuario por su nombre de usuario único"
     *
     * TODO 10: Añadir @ApiResponses con respuestas 200 y 404
     *
     * TODO 11: Añadir @Parameter al parámetro username
     */
    @GetMapping("/search")
    public ResponseEntity<UserDTO> getUserByUsername(
            @RequestParam String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    /**
     * TODO 12: Añadir @Operation con:
     *   - summary = "Crear nuevo usuario"
     *   - description = "Crea un nuevo usuario en el sistema"
     *
     * TODO 13: Añadir @ApiResponses con:
     *   - @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente")
     *   - @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
     *   - @ApiResponse(responseCode = "409", description = "El usuario o email ya existe")
     *
     * TODO 14: Añadir @io.swagger.v3.oas.annotations.parameters.RequestBody con:
     *   - description = "Datos del nuevo usuario"
     *   - required = true
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        UserDTO created = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * TODO 15: Añadir @Operation con:
     *   - summary = "Eliminar usuario"
     *   - description = "Elimina un usuario y todas sus tareas asociadas"
     *
     * TODO 16: Añadir @ApiResponses con respuestas 204 y 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
