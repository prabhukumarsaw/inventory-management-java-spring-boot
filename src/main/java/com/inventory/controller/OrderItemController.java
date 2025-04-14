package com.inventory.controller;

import com.inventory.model.OrderItem;
import com.inventory.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    // 🔹 Get all order items
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    // 🔹 Get a specific order item by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem item = orderItemService.getOrderItemById(id);
        return item != null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    // 🔹 Get all items for a given order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getItemsByOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.getItemsByOrderId(orderId));
    }

    // 🔹 Get all items that include a specific product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<OrderItem>> getItemsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(orderItemService.getItemsByProductId(productId));
    }

    // 🔹 Create or save a new order item
    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        return ResponseEntity.ok(orderItemService.saveOrderItem(orderItem));
    }

    // 🔹 Bulk insert (optional, for batch saving)
    @PostMapping("/bulk")
    public ResponseEntity<List<OrderItem>> saveAll(@RequestBody List<OrderItem> items) {
        return ResponseEntity.ok(orderItemService.saveAllOrderItems(items));
    }

    // 🔹 Delete an order item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
