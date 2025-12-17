package com.bootcamp.finalproject.order;

/**
 * Estados posibles de un pedido.
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
