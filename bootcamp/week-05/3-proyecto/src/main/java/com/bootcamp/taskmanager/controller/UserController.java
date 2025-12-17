package com.bootcamp.taskmanager.controller;

import com.bootcamp.taskmanager.dto.UserDTO;
import com.bootcamp.taskmanager.dto.request.CreateUserRequest;
import com.bootcamp.taskmanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ============================================================
    // TODO 1: GET /api/users - Listar todos los usuarios
    // ============================================================
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        // TODO: Implementar
        // return ResponseEntity.ok(userService.findAll());
        return ResponseEntity.ok(List.of());
    }


    // ============================================================
    // TODO 2: GET /api/users/{id} - Obtener usuario por ID
    // ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        // TODO: Implementar
        // return ResponseEntity.ok(userService.findById(id));
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 3: GET /api/users/{id}/tasks - Usuario con sus tareas
    // ============================================================
    // Instrucciones:
    // - Usar userService.findByIdWithTasks(id)
    // - El DTO incluirá taskCount calculado

    @GetMapping("/{id}/tasks")
    public ResponseEntity<UserDTO> findByIdWithTasks(@PathVariable UUID id) {
        // TODO: Implementar
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 4: POST /api/users - Crear usuario
    // ============================================================
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserRequest request) {
        // TODO: Implementar
        // UserDTO created = userService.create(request);
        // return ResponseEntity.status(HttpStatus.CREATED).body(created);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // ============================================================
    // TODO 5: DELETE /api/users/{id} - Eliminar usuario
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // TODO: Implementar
        // userService.delete(id);
        // return ResponseEntity.noContent().build();
        return ResponseEntity.noContent().build();
    }
}
