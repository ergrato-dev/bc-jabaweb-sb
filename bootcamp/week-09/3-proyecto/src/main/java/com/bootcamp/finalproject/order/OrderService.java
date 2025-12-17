package com.bootcamp.finalproject.order;

import com.bootcamp.finalproject.common.exception.BadRequestException;
import com.bootcamp.finalproject.common.exception.ResourceNotFoundException;
import com.bootcamp.finalproject.order.dto.*;
import com.bootcamp.finalproject.product.Product;
import com.bootcamp.finalproject.product.ProductRepository;
import com.bootcamp.finalproject.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para gestión de órdenes.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> findByUser(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
            .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public OrderResponse findById(Long id, User currentUser) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Orden", id));

        // Verificar que la orden pertenece al usuario (o es admin)
        if (!order.getUser().getId().equals(currentUser.getId())
            && !currentUser.getRole().name().equals("ADMIN")) {
            throw new AccessDeniedException("No tienes permiso para ver esta orden");
        }

        return toResponse(order);
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(request.shippingAddress());
        order.setNotes(request.notes());
        order.setStatus(OrderStatus.PENDING);

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto", itemRequest.productId()));

            if (!product.hasStock(itemRequest.quantity())) {
                throw new BadRequestException(
                    "Stock insuficiente para el producto: " + product.getName()
                );
            }

            // Reducir stock
            product.reduceStock(itemRequest.quantity());
            productRepository.save(product);

            // Crear item
            OrderItem item = new OrderItem(product, itemRequest.quantity());
            order.addItem(item);
        }

        order.recalculateTotal();
        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Orden", id));

        order.setStatus(status);
        return toResponse(orderRepository.save(order));
    }

    @Transactional
    public void cancel(Long id, User currentUser) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Orden", id));

        // Verificar permisos
        if (!order.getUser().getId().equals(currentUser.getId())
            && !currentUser.getRole().name().equals("ADMIN")) {
            throw new AccessDeniedException("No tienes permiso para cancelar esta orden");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Solo se pueden cancelar órdenes pendientes");
        }

        // Restaurar stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
            .map(item -> new OrderItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSubtotal()
            ))
            .toList();

        return new OrderResponse(
            order.getId(),
            order.getUser().getId(),
            order.getUser().getName(),
            order.getStatus(),
            order.getTotal(),
            order.getShippingAddress(),
            order.getNotes(),
            items,
            order.getCreatedAt()
        );
    }
}
