package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.BoughtPieChart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoughPieChartRepo extends JpaRepository<BoughtPieChart, Long> {

    Boolean existsByName(String name);

    BoughtPieChart findByName(String name);

}
