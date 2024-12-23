package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.RemainingStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemainingStockRepo extends JpaRepository<RemainingStock, Long> {

    RemainingStock getByProdId(long prodId);
}
