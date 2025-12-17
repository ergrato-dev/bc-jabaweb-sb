package com.bootcamp.finalproject.category;

import com.bootcamp.finalproject.category.dto.CategoryRequest;
import com.bootcamp.finalproject.category.dto.CategoryResponse;
import com.bootcamp.finalproject.common.exception.BadRequestException;
import com.bootcamp.finalproject.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestión de categorías.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllActive() {
        return categoryRepository.findByActiveTrue().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        return categoryRepository.findById(id)
            .map(this::toResponse)
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new BadRequestException("Ya existe una categoría con ese nombre");
        }

        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        category.setActive(true);

        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));

        // Verificar nombre único (excepto la actual)
        categoryRepository.findByName(request.name())
            .filter(c -> !c.getId().equals(id))
            .ifPresent(c -> {
                throw new BadRequestException("Ya existe una categoría con ese nombre");
            });

        category.setName(request.name());
        category.setDescription(request.description());

        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));

        // Soft delete
        category.setActive(false);
        categoryRepository.save(category);
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getDescription(),
            category.getActive(),
            category.getProducts() != null ? category.getProducts().size() : 0,
            category.getCreatedAt()
        );
    }
}
