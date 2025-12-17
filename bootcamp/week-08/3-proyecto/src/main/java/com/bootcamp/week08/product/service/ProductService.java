package com.bootcamp.week08.product.service;

import com.bootcamp.week08.product.dto.CreateProductRequest;
import com.bootcamp.week08.product.dto.ProductDTO;
import com.bootcamp.week08.product.entity.Product;
import com.bootcamp.week08.product.repository.ProductRepository;
import com.bootcamp.week08.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para lógica de negocio de Product.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Obtiene todos los productos.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
            .stream()
            .map(ProductDTO::fromEntity)
            .toList();
    }

    /**
     * Obtiene productos paginados.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(ProductDTO::fromEntity);
    }

    /**
     * Obtiene un producto por ID.
     */
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return ProductDTO.fromEntity(product);
    }

    /**
     * Crea un nuevo producto.
     */
    public ProductDTO create(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setCategory(request.category());
        product.setPrice(request.price());
        product.setStock(request.stock());

        Product saved = productRepository.save(product);
        return ProductDTO.fromEntity(saved);
    }

    /**
     * Actualiza un producto existente.
     */
    public ProductDTO update(Long id, CreateProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setCategory(request.category());
        product.setPrice(request.price());
        product.setStock(request.stock());

        Product updated = productRepository.save(product);
        return ProductDTO.fromEntity(updated);
    }

    /**
     * Elimina un producto.
     */
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Busca productos por categoría.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByCategory(String category) {
        return productRepository.findByCategory(category)
            .stream()
            .map(ProductDTO::fromEntity)
            .toList();
    }

    /**
     * Busca productos por rango de precio.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice)
            .stream()
            .map(ProductDTO::fromEntity)
            .toList();
    }
}
