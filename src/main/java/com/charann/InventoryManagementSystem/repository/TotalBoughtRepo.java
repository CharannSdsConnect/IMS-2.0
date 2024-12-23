package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.TotalBought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalBoughtRepo extends JpaRepository<TotalBought, Long> {

    Boolean existsByName(String name);

    TotalBought findOneByName(String name);


}
