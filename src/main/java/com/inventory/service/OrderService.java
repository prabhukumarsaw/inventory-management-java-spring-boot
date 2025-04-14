package com.inventory.service;

import com.inventory.model.*;
import com.inventory.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    // Basic CRUD operations
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Create order and update inventory
    @Transactional
    public Order createOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("COMPLETED");

        // Set 'order' reference in all OrderItems
        for (OrderItem item : order.getOrderItems()) {
            item.setOrder(order);
        }

        Order savedOrder = orderRepository.save(order);

        updateInventory(savedOrder);
        return savedOrder;
    }

    // Inventory update for PURCHASE or SALE
    private void updateInventory(Order order) {
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            Inventory inventory = inventoryRepository.findByProductId(product.getId());

            if (inventory != null) {
                int quantity = inventory.getQuantityAvailable();
                int change = item.getQuantity();

                // If SALE: reduce stock, If PURCHASE: increase stock
                if (order.getOrderType().equalsIgnoreCase("SALE")) {
                    inventory.setQuantityAvailable(quantity - change);
                } else if (order.getOrderType().equalsIgnoreCase("PURCHASE")) {
                    inventory.setQuantityAvailable(quantity + change);
                }

                inventoryRepository.save(inventory);
            }
        }
    }

    // Status-based queries
    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus("PENDING");
    }

    public List<Order> getCompletedOrders() {
        return orderRepository.findByStatus("COMPLETED");
    }

    public List<Order> getOrdersByContactName(String name) {
        return orderRepository.findByContactNameContainingIgnoreCase(name);
    }
}
