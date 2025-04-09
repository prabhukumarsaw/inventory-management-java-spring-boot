package com.inventory.service;

import com.inventory.model.Inventory;
import com.inventory.model.Product;
import com.inventory.repository.InventoryRepository;
import com.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    // Basic CRUD
//    public Inventory saveInventory(Inventory inventory) {
//        return inventoryRepository.save(inventory);
//    }

    public Inventory saveInventory(Inventory inventory) {
        // Load full product from DB using ID
        Long productId = inventory.getProduct().getId();
        Product product = productRepository.findById(productId).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product with ID " + productId + " not found");
        }

        inventory.setProduct(product);
        inventory.setLastUpdated(LocalDateTime.now());

        return inventoryRepository.save(inventory);
    }


    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }

    // Product-specific operations
    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    // Stock management
    public Inventory updateStockQuantity(Long productId, int quantityChange) {
        Inventory inventory = getInventoryByProductId(productId);
        if (inventory != null) {
            inventory.setQuantityAvailable(
                    inventory.getQuantityAvailable() + quantityChange
            );
            return inventoryRepository.save(inventory);
        }
        return null;
    }

    // Status checks
    public List<Inventory> getLowStockItems(int threshold) {
        return inventoryRepository.findByQuantityAvailableLessThan(threshold);
    }

    public boolean isInStock(Long productId, int requestedQuantity) {
        Inventory inventory = getInventoryByProductId(productId);
        return inventory != null && inventory.getQuantityAvailable() >= requestedQuantity;
    }
}