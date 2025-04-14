package com.inventory.controller;

import com.inventory.model.Order;
import com.inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 🔹 Create new order (e.g. SALE or PURCHASE)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.createOrder(order);
        return ResponseEntity.ok(savedOrder);
    }

    // 🔹 Get all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // 🔹 Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    // 🔹 Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Get pending orders
    @GetMapping("/status/pending")
    public ResponseEntity<List<Order>> getPendingOrders() {
        return ResponseEntity.ok(orderService.getPendingOrders());
    }

    // 🔹 Get completed orders
    @GetMapping("/status/completed")
    public ResponseEntity<List<Order>> getCompletedOrders() {
        return ResponseEntity.ok(orderService.getCompletedOrders());
    }

    // 🔹 Search by contact name (customer/supplier)
    @GetMapping("/search")
    public ResponseEntity<List<Order>> searchOrdersByContactName(@RequestParam String name) {
        return ResponseEntity.ok(orderService.getOrdersByContactName(name));
    }
}
