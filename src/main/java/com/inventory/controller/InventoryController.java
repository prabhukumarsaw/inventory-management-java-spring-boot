package com.inventory.controller;

import com.inventory.model.Inventory;
import com.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // 📦 Create or update inventory manually
    @PostMapping
    public ResponseEntity<Inventory> saveInventory(@RequestBody Inventory inventory) {
        Inventory saved = inventoryService.saveInventory(inventory);
        return ResponseEntity.ok(saved);
    }

    // 📋 Get all inventory records
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    // 🔍 Get inventory by ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        return inventory != null ? ResponseEntity.ok(inventory) : ResponseEntity.notFound().build();
    }

    // 🧹 Delete inventory record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    // 📦 Get inventory by Product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getByProductId(@PathVariable Long productId) {
        Inventory inventory = inventoryService.getInventoryByProductId(productId);
        return inventory != null ? ResponseEntity.ok(inventory) : ResponseEntity.notFound().build();
    }

    // 🔼 Add/Remove stock for product
    @PutMapping("/update-stock")
    public ResponseEntity<Inventory> updateStock(
            @RequestParam Long productId,
            @RequestParam int quantityChange
    ) {
        Inventory updated = inventoryService.updateStockQuantity(productId, quantityChange);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // ⚠️ Get low stock items
    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory>> getLowStock(@RequestParam int threshold) {
        return ResponseEntity.ok(inventoryService.getLowStockItems(threshold));
    }

    // ✅ Check if in stock
    @GetMapping("/check-stock")
    public ResponseEntity<Boolean> isInStock(
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        boolean available = inventoryService.isInStock(productId, quantity);
        return ResponseEntity.ok(available);
    }
}
