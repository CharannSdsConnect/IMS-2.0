package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.CustomerMonthlyReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerMontlyReportRepo extends JpaRepository<CustomerMonthlyReport, Long> {

    Boolean existsByCustIdAndCustName(long custId, String custName);

    CustomerMonthlyReport findByCustIdAndCustName(long id, String custName);
}
