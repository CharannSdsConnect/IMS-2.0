package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.ExpiryReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiryReportRepo extends JpaRepository<ExpiryReport, Long> {
}
