package com.charann.InventoryManagementSystem.service;

import com.charann.InventoryManagementSystem.entity.MonthlyProducts;
import com.charann.InventoryManagementSystem.entity.ProductSold;
import com.charann.InventoryManagementSystem.entity.YearlyProducts;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductSoldService {
    Optional<ProductSold> getProductById(Long id);

    ProductSold addSoldProduct(ProductSold productSold);

    String monthlyReport();

//    TotalSales getTotalSales();

    String mostSold();

    String yearlyReport();

    String dailyReport();

    ProductSold updateProductSold(String sku);

    void generateMonthlyReport(HttpServletResponse response) throws IOException;

    void generateYearlyReport(HttpServletResponse response) throws IOException;

    void generateDailyReport(HttpServletResponse response) throws IOException;

    List<MonthlyProducts> getMonthlyProducts(String month);

    List<YearlyProducts> getYearlyProducts(String year);

    void generateEachMonthReport(HttpServletResponse response) throws IOException;

    void generateEachYearReport(HttpServletResponse response) throws IOException;

    String getCustomerReport();

    void generateCustomerReport(HttpServletResponse response) throws IOException;

    String getCustomerMonthlyReport(String month);

    void generateCustomerMonthlyReport(HttpServletResponse response) throws IOException;

    String getCustomerYearlyReport(String year);

    void generateCustomerYearlyReport(HttpServletResponse response) throws IOException;

    String getExpiryReport();

    void generateExpiryReport(HttpServletResponse response)throws IOException;
}

