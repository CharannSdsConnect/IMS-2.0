package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.MonthlyProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyProductRepo extends JpaRepository<MonthlyProducts, Long> {

    Boolean existsByName(String name);
    MonthlyProducts findByName(String name);

    //findTopByOrderByPriceDesc
    List<MonthlyProducts> findAllByOrderByPriceDesc();
}
