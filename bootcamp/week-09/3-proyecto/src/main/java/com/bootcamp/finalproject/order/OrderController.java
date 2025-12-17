package com.bootcamp.finalproject.order;

import com.bootcamp.finalproject.order.dto.CreateOrderRequest;
import com.bootcamp.finalproject.order.dto.OrderResponse;
import com.bootcamp.finalproject.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para órdenes.
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Órdenes", description = "Gestión de pedidos")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @Operation(summary = "Listar mis órdenes")
    public ResponseEntity<Page<OrderResponse>> findMyOrders(
        @AuthenticationPrincipal User user,
        Pageable pageable
    ) {
        return ResponseEntity.ok(orderService.findByUser(user.getId(), pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener orden por ID")
    public ResponseEntity<OrderResponse> findById(
        @PathVariable Long id,
        @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(orderService.findById(id, user));
    }

    @PostMapping
    @Operation(summary = "Crear nueva orden")
    public ResponseEntity<OrderResponse> create(
        @Valid @RequestBody CreateOrderRequest request,
        @AuthenticationPrincipal User user
    ) {
        OrderResponse response = orderService.create(request, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar estado de orden (Admin)")
    public ResponseEntity<OrderResponse> updateStatus(
        @PathVariable Long id,
        @RequestParam OrderStatus status
    ) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancelar orden")
    public ResponseEntity<Void> cancel(
        @PathVariable Long id,
        @AuthenticationPrincipal User user
    ) {
        orderService.cancel(id, user);
        return ResponseEntity.noContent().build();
    }
}
