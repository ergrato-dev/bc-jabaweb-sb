package com.bootcamp.finalproject.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad OrderItem.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
