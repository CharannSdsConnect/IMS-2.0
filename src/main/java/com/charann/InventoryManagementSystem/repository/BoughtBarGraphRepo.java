package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.BoughtBarGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoughtBarGraphRepo extends JpaRepository<BoughtBarGraph, Long> {

    Boolean existsByMonth(String month);
    BoughtBarGraph findByMonth(String month);


}
