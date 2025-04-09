package com.inventory.repository;

import com.inventory.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find orders by status (e.g., "PENDING", "COMPLETED")
    List<Order> findByStatus(String status);

    // Find orders by customer/supplier name
    List<Order> findByContactNameContainingIgnoreCase(String name);
}