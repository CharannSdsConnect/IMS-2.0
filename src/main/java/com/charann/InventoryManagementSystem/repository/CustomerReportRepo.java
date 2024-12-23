package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.CustomerReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerReportRepo extends JpaRepository<CustomerReport, Long> {

    Boolean existsByCustId(long custId);

    CustomerReport getByCustId(long custId);

    List<CustomerReport> findAllByOrderByAmountDesc();
}
