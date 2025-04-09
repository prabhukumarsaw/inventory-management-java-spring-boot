package com.inventory.repository;

import com.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Find inventory by product ID
    Inventory findByProductId(Long productId);

    // Find low stock items (quantity < threshold)
    List<Inventory> findByQuantityAvailableLessThan(int threshold);
}