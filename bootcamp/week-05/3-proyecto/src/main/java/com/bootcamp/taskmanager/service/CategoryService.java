package com.bootcamp.taskmanager.service;

import com.bootcamp.taskmanager.dto.CategoryDTO;
import com.bootcamp.taskmanager.dto.request.CreateCategoryRequest;
import com.bootcamp.taskmanager.entity.Category;
import com.bootcamp.taskmanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ============================================================
    // TODO 1: Listar todas las categorías
    // ============================================================
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        // TODO: Implementar
        // Usar simpleFromEntity para no cargar tareas
        return List.of();
    }


    // ============================================================
    // TODO 2: Buscar categoría por ID
    // ============================================================
    @Transactional(readOnly = true)
    public CategoryDTO findById(UUID id) {
        // TODO: Implementar
        return null;
    }


    // ============================================================
    // TODO 3: Buscar categoría con sus tareas
    // ============================================================
    @Transactional(readOnly = true)
    public CategoryDTO findByIdWithTasks(UUID id) {
        // TODO: Usar categoryRepository.findByIdWithTasks(id)
        // Mapear con fromEntity (incluye taskCount)
        return null;
    }


    // ============================================================
    // TODO 4: Crear categoría
    // ============================================================
    @Transactional
    public CategoryDTO create(CreateCategoryRequest request) {
        // TODO: Verificar que nombre no exista
        // Crear Category y guardar
        // Retornar DTO
        return null;
    }


    // ============================================================
    // TODO 5: Actualizar categoría
    // ============================================================
    @Transactional
    public CategoryDTO update(UUID id, CreateCategoryRequest request) {
        // TODO: Buscar existente, actualizar campos, guardar
        return null;
    }


    // ============================================================
    // TODO 6: Eliminar categoría
    // ============================================================
    @Transactional
    public void delete(UUID id) {
        // TODO: Verificar existencia y eliminar
        // Nota: Las relaciones en task_category se eliminan automáticamente
    }
}
