package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.ProductSold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSoldRepo extends JpaRepository<ProductSold, Long> {

    Boolean existsByMonth(String month);

    Boolean existsBySku(String sku);
}
