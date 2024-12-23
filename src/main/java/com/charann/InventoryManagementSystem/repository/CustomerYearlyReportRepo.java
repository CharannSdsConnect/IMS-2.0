package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.CustomerYearlyReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerYearlyReportRepo extends JpaRepository<CustomerYearlyReport, Long> {

    Boolean existsByCustIdAndCustName(long custId, String custName);

    CustomerYearlyReport findByCustIdAndCustName(long id, String custName);
}
