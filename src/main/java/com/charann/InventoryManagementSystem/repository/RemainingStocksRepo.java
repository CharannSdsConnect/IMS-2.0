package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.RemainingStocks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemainingStocksRepo extends JpaRepository<RemainingStocks, Long> {

    RemainingStocks findByName(String name);
}
