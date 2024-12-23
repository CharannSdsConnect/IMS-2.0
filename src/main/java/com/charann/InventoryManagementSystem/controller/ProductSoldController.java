package com.charann.InventoryManagementSystem.controller;

import com.charann.InventoryManagementSystem.entity.OrderStatus;
import com.charann.InventoryManagementSystem.entity.ProductSold;
import com.charann.InventoryManagementSystem.entity.YearlyProducts;
import com.charann.InventoryManagementSystem.repository.OrderStatusRepo;
import com.charann.InventoryManagementSystem.service.DistributeStockService;
import com.charann.InventoryManagementSystem.entity.MonthlyProducts;
import com.charann.InventoryManagementSystem.service.ProductSoldService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ProductSoldController {

    @Autowired
    ProductSoldService productSoldService;

    @Autowired
    DistributeStockService distributeStockService;

    @Autowired
    OrderStatusRepo orderStatusRepo;

    @PostMapping("/distribute")
    public ResponseEntity<ProductSold> buyProduct(@RequestBody ProductSold productSold) {
        return new ResponseEntity<>(
                productSoldService.addSoldProduct(productSold),
                HttpStatus.CREATED);
    }

    @GetMapping("/monthly-sales-report")
    public ResponseEntity<String> monthlyReport() {
        return new ResponseEntity<>(
                productSoldService.monthlyReport(),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/most-sold-product")
    public ResponseEntity<String> mostSoldProduct() {
        return new ResponseEntity<>(
                productSoldService.mostSold(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/yearly-sales-report")
    public ResponseEntity<String> yearlyReport() {
        return new ResponseEntity<>(
            productSoldService.yearlyReport(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/daily-sales-report")
    public ResponseEntity<String> dailyReport() {
        return new ResponseEntity<>(
                productSoldService.dailyReport(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/download-daily-report")
    public void generateDailyExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=daily_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateDailyReport(response);
    }

    @GetMapping("/download-monthly-report")
    public void generateMonthlyExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=monthly_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateMonthlyReport(response);
    }

    @GetMapping("/download-yearly-report")
    public void generateYearlyExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=yearly_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateYearlyReport(response);
    }

    @GetMapping("/get-month-report/{month}")
    public ResponseEntity<List<MonthlyProducts>> monthlySale(@PathVariable String month) {
        return new ResponseEntity<>(
                productSoldService.getMonthlyProducts(month),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-yearly-report/{year}")
    public ResponseEntity<List<YearlyProducts>> yearlySale(@PathVariable String year) {
        return new ResponseEntity<>(
                productSoldService.getYearlyProducts(year),
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/download-each-month-report")
    public void generateEacMonthExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=single_month_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateEachMonthReport(response);
    }

    @GetMapping("/download-each-year-report")
    public void generateEacYearExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=single_year_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateEachYearReport(response);
    }

    @GetMapping("/get-customer-report")
    public ResponseEntity<String> getCustomerReport() {
        return new ResponseEntity<>(
                productSoldService.getCustomerReport(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/download-customer-excel-report")
    public void generateCustomerExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=customer_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateCustomerReport(response);
    }

    @GetMapping("/get-customer-report/{month}")
    public ResponseEntity<String> getCustomerMonthlyReport(@PathVariable String month) {
        return new ResponseEntity<>(
                productSoldService.getCustomerMonthlyReport(month),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/download-monthly-customer-report")
    public void generateCustomerMonthlyReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=month_customer_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateCustomerMonthlyReport(response);
    }

    @GetMapping("/get-yearly-customer-report/{year}")
    public ResponseEntity<String> getCustomerYearlyReport(@PathVariable String year) {
        return new ResponseEntity<>(
                productSoldService.getCustomerYearlyReport(year),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/download-yearly-customer-report")
    public void generateCustomerYearlyReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=month_customer_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateCustomerYearlyReport(response);
    }

    @GetMapping("/get-expiry-report")
    public ResponseEntity<String> getExpiryReport(LocalDate localDate) {
        return new ResponseEntity<>(
                productSoldService.getExpiryReport(),
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping("/download-expiry-report")
    public void generateExpiryReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerkey = "Content-Disposition";
        String headerValue = "attachment;filename=expiry_report.xls";

        response.setHeader(headerkey, headerValue);

        productSoldService.generateCustomerYearlyReport(response);
    }

    @GetMapping("/order-status/{id}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String id) {
        return new ResponseEntity<>(
                distributeStockService.getOrderStatus(id),
                HttpStatus.ACCEPTED
        );
    }

    @PutMapping("/update-status/{id}")
    public String changeOrderStatus(@PathVariable String id, @RequestBody String status) {
        OrderStatus orderStatus = orderStatusRepo.getByOrderId(id);
        orderStatus.setOrderStatus(status);
        orderStatusRepo.save(orderStatus);

        return "Status updated";
    }

}
