package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.MonthlySales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MonthlySalesReportRepo extends JpaRepository<MonthlySales, Long> {

    Boolean existsByMonth(String month);

    MonthlySales findByMonth(String month);
}
