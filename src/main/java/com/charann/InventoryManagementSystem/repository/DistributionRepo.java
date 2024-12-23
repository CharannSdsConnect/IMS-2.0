package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistributionRepo extends JpaRepository<Distribution, Long> {

    Boolean existsByProdId(long id);

    Distribution getByProdId(long id);

}
