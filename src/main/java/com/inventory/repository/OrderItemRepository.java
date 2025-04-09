package com.inventory.repository;

import com.inventory.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Find all items in an order
    List<OrderItem> findByOrderId(Long orderId);

    // Find all order items for a product
    List<OrderItem> findByProductId(Long productId);
}