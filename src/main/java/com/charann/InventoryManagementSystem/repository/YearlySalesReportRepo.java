package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.YearlySalesReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YearlySalesReportRepo extends JpaRepository<YearlySalesReport, Long> {

    Boolean existsByYear(String year);

    YearlySalesReport findByYear(String year);
}
