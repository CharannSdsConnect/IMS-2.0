package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.YearlyProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YearlyProductRepo extends JpaRepository<YearlyProducts, Long> {

    Boolean existsByName(String name);

    YearlyProducts findByName(String name);

    List<YearlyProducts> findAllByOrderByPriceDesc();

}
