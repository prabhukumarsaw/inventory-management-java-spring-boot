package com.inventory.service;

import com.inventory.model.OrderItem;
import com.inventory.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    // Basic CRUD operations
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    // Order relationship queries
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    // Product relationship queries
    public List<OrderItem> getItemsByProductId(Long productId) {
        return orderItemRepository.findByProductId(productId);
    }

    // Bulk operations
    public List<OrderItem> saveAllOrderItems(List<OrderItem> items) {
        return orderItemRepository.saveAll(items);
    }
}