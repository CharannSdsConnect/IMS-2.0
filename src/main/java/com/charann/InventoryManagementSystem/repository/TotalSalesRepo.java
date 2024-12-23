package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.TotalSales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalSalesRepo extends JpaRepository<TotalSales, Long> {

    Boolean existsByName(String name);

    TotalSales findByName(String name);

    // Optional<Product> findTopByOrderByPriceDesc();
    Optional<TotalSales> findTopByOrderByPriceDesc();
}
