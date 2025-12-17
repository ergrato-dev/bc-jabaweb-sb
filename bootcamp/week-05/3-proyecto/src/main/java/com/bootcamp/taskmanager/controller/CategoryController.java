package com.bootcamp.taskmanager.controller;

import com.bootcamp.taskmanager.dto.CategoryDTO;
import com.bootcamp.taskmanager.dto.request.CreateCategoryRequest;
import com.bootcamp.taskmanager.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ============================================================
    // TODO 1: GET /api/categories - Listar todas las categorías
    // ============================================================
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        // TODO: Implementar
        return ResponseEntity.ok(List.of());
    }


    // ============================================================
    // TODO 2: GET /api/categories/{id} - Obtener categoría por ID
    // ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable UUID id) {
        // TODO: Implementar
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 3: GET /api/categories/{id}/tasks - Categoría con sus tareas
    // ============================================================
    @GetMapping("/{id}/tasks")
    public ResponseEntity<CategoryDTO> findByIdWithTasks(@PathVariable UUID id) {
        // TODO: Implementar
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 4: POST /api/categories - Crear categoría
    // ============================================================
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CreateCategoryRequest request) {
        // TODO: Implementar
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // ============================================================
    // TODO 5: PUT /api/categories/{id} - Actualizar categoría
    // ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody CreateCategoryRequest request) {
        // TODO: Implementar
        return ResponseEntity.notFound().build();
    }


    // ============================================================
    // TODO 6: DELETE /api/categories/{id} - Eliminar categoría
    // ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // TODO: Implementar
        return ResponseEntity.noContent().build();
    }
}
