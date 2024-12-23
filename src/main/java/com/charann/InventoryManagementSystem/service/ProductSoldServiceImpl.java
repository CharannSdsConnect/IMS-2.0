package com.charann.InventoryManagementSystem.service;

import com.charann.InventoryManagementSystem.entity.*;
import com.charann.InventoryManagementSystem.repository.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSoldServiceImpl implements ProductSoldService {

    @Autowired
    ProductSoldRepo productSoldRepo;

    @Autowired
    TotalBoughtRepo totalBoughtRepo;

    @Autowired
    MonthlySalesReportRepo monthlySalesReportRepo;

    @Autowired
    ProductBoughtRepo productBoughtRepo;

    @Autowired
    TotalSalesRepo totalSalesRepo;

    @Autowired
    RemainingStocksRepo remainingStocksRepo;

    @Autowired
    YearlySalesReportRepo yearlySalesReportRepo;

    @Autowired
    DailySalesRepo dailySalesRepo;

    @Autowired
    MonthlyProductRepo monthlyProductRepo;

    @Autowired
    YearlyProductRepo yearlyProductRepo;

    @Autowired
    CustomerReportRepo customerReportRepo;

    @Autowired
    CustomerMontlyReportRepo customerMontlyReportRepo;

    @Autowired
    CustomerYearlyReportRepo customerYearlyReportRepo;

    @Autowired
    ExpiryReportRepo expiryReportRepo;

    @Autowired
    UserRepository userRepository;

    int iterationDailyReport = 1;
    int iterationMonthlyReport = 1;
    int iterationYearlyReport = 1;
    int iterationCustomerReport = 1;
    int iterationCustomerMonthlyReport = 1;
    int iterationCustomerYearlyReport = 1;
    int iterationExpiryReport = 1;

    @Override
    public Optional<ProductSold> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductSold addSoldProduct(ProductSold productSold) {

        if (userRepository.findByEmail(productSold.getBuyerEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + productSold.getBuyerEmail())).isLoggedInStatus()) {

            //Update stocks in bought repo
            RemainingStocks remainingStocks = remainingStocksRepo.findByName(productSold.getName());

            if (remainingStocks.getQuantity() - productSold.getQuantity() >= 0) {
                remainingStocks.setQuantity(remainingStocks.getQuantity() - productSold.getQuantity());

                ProductSold productSold1 = new ProductSold();
                ProductBought productBought = productBoughtRepo.findFirstByName(productSold.getName());
                System.out.println(productBought);

                productSold1.setName(productSold.getName());
                productSold1.setQuantity(productSold.getQuantity());
                productSold1.setDate(productSold.getDate());
                productSold1.setMonth(productSold.getMonth());
                productSold1.setYear(productSold.getYear());
                productSold1.setBuyingDate(productSold.getBuyingDate());
                productSold1.setPrice(productSold.getPrice());
                productSold1.setSku(productSold.getSku());
                productSold1.setOrderStatus(productSold.getOrderStatus());
                productSold1.setOrderId(productSold.getOrderId());
                productSold1.setWarehouseLeavingDate(productSold.getWarehouseLeavingDate());
                productSold1.setVehicleNo(productSold.getVehicleNo());
                productSold1.setVehicleType(productSold.getVehicleType());
                productSold1.setDestinationName(productSold.getDestinationName());
                productSold1.setBuyerName(productSold.getBuyerName());
                productSold1.setBuyerId(productSold.getBuyerId());
                productSold1.setBuyerPhNo(productSold.getBuyerPhNo());
                productSold1.setBuyerEmail(productSold.getBuyerEmail());

                System.out.println(totalSalesRepo.existsByName(productSold.getName()));
                if (totalSalesRepo.existsByName(productSold.getName())) {
                    TotalSales totalSales1 = totalSalesRepo.findByName(productSold.getName());
                    totalSales1.setQuantity(totalSales1.getQuantity() + productSold.getQuantity());
                    totalSales1.setPrice(totalSales1.getPrice() + productSold1.getPrice());
                    totalSalesRepo.save(totalSales1);
                } else {
                    TotalSales totalSales = new TotalSales();
                    totalSales.setName(productSold.getName());
                    totalSales.setQuantity(productSold.getQuantity());
                    totalSales.setPrice(productSold1.getPrice() * productSold.getQuantity());

                    totalSalesRepo.save(totalSales);
                }
//        TotalBought totalBought = totalBoughtRepo.findByName(productSold.getName());
//        totalBought.setTotal_quantity(totalBought.getTotal_quantity() - productSold.getQuantity());
//        totalBought.setTotal_price(totalBought.getTotal_price() - productSold1.getPrice());
//        totalBoughtRepo.save(totalBought);

                return productSoldRepo.save(productSold1);
            }
        }

        return null;
//        return productSold;
    }

    @Override
    public String monthlyReport() {

//        monthlySalesReportRepo.deleteAll();

        if(iterationMonthlyReport>1) {
            monthlySalesReportRepo.findAll().forEach(product-> product.setSales_amount(0));
        }

        List<ProductSold> productSold = productSoldRepo.findAll();


        productSold.forEach((sale)-> {
            if(!monthlySalesReportRepo.existsByMonth(sale.getMonth())) {
                MonthlySales monthlySales1 = new MonthlySales();
                monthlySales1.setMonth(sale.getMonth());
                monthlySales1.setSales_amount(sale.getPrice()* sale.getQuantity());
                monthlySalesReportRepo.save(monthlySales1);
            }
            else {
                MonthlySales monthlySales = monthlySalesReportRepo.findByMonth(sale.getMonth());
                monthlySales.setSales_amount(monthlySales.getSales_amount()+ (sale.getPrice()* sale.getQuantity()));
                monthlySalesReportRepo.save(monthlySales);
            }
            iterationMonthlyReport++;
        });

        return "Successfully generated";
    }

    @Override
    public String mostSold() {
        Optional<TotalSales> totalSales = totalSalesRepo.findTopByOrderByPriceDesc();
        return totalSales.get().getName();
    }

    @Override
    public String yearlyReport() {

        if(iterationYearlyReport>1) {
            yearlyProductRepo.findAll().forEach(product-> product.setPrice(0));
        }

        List<ProductSold> productSold = productSoldRepo.findAll();
//        productSold.forEach(product -> {
//            product.setPrice(0);
//        });
        productSold.forEach((product)-> {
            if (!yearlySalesReportRepo.existsByYear(product.getYear())) {
                YearlySalesReport yearlySalesReport = new YearlySalesReport();
                yearlySalesReport.setYear(product.getYear());
                yearlySalesReport.setPrice(product.getPrice()*product.getQuantity());
                yearlySalesReportRepo.save(yearlySalesReport);
            }
            else {
                YearlySalesReport yearlySalesReport = yearlySalesReportRepo.findByYear(product.getYear());
                yearlySalesReport.setPrice((product.getPrice()* product.getQuantity()) + yearlySalesReport.getPrice());
                yearlySalesReportRepo.save(yearlySalesReport);
            }
        });
        iterationYearlyReport++;
        return "Yearly Report Generated";
    }

    @Override
    public String dailyReport() {

        if(iterationDailyReport>1) {
            dailySalesRepo.findAll().forEach(product-> product.setSales(0));
        }

        List<ProductSold> productSold = productSoldRepo.findAll();

//        productSold.forEach(product -> {
//            product.setPrice(0);
//        });
//        dailySalesRepo.deleteAll();

        productSold.forEach((product) -> {

            Boolean exists = dailySalesRepo.existsByYearAndMonthAndDate(
                    product.getYear(), product.getMonth(), product.getDate()
            );
            if (exists) {
                DailySalesReport dailySalesReport = dailySalesRepo.findByYearAndMonthAndDate(
                        product.getYear(), product.getMonth(), product.getDate()
                );

                dailySalesReport.setSales((product.getPrice()* product.getQuantity()) + dailySalesReport.getSales());
                dailySalesRepo.save(dailySalesReport);
            }

            else {
                DailySalesReport dailySalesReport = new DailySalesReport();
                dailySalesReport.setYear(product.getYear());
                dailySalesReport.setMonth(product.getMonth());
                dailySalesReport.setDate(product.getDate());
                dailySalesReport.setSales(product.getPrice()* product.getQuantity());

                dailySalesRepo.save(dailySalesReport);
            }
        });

        iterationDailyReport++;
        return "Daily Report Generated";
    }

    @Override
    public ProductSold updateProductSold(String sku) {


        return null;
    }

    @Override
    public void generateMonthlyReport(HttpServletResponse response) throws IOException {
        List<MonthlySales> monthlySalesList = monthlySalesReportRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Month");
        row.createCell(2).setCellValue("Amount");

        int dataRowIndex = 1;
        for(MonthlySales sales:monthlySalesList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sales.getId());
            dataRow.createCell(1).setCellValue(sales.getMonth());
            dataRow.createCell(2).setCellValue(sales.getSales_amount());

            dataRowIndex++;

        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public void generateYearlyReport(HttpServletResponse response) throws IOException {
        List<YearlySalesReport> monthlySalesList = yearlySalesReportRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Year");
        row.createCell(2).setCellValue("Amount");

        int dataRowIndex = 1;
        for(YearlySalesReport sales:monthlySalesList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sales.getId());
            dataRow.createCell(1).setCellValue(sales.getYear());
            dataRow.createCell(2).setCellValue(sales.getPrice());

            dataRowIndex++;

        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public void generateDailyReport(HttpServletResponse response) throws IOException {
        List<DailySalesReport> dailySalesReports = dailySalesRepo.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Date");
        row.createCell(2).setCellValue("Month");
        row.createCell(3).setCellValue("Year");
        row.createCell(4).setCellValue("Amount");

        int dataRowIndex = 1;
        for(DailySalesReport sales:dailySalesReports) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sales.getId());
            dataRow.createCell(1).setCellValue(sales.getDate());
            dataRow.createCell(2).setCellValue(sales.getMonth());
            dataRow.createCell(3).setCellValue(sales.getYear());
            dataRow.createCell(4).setCellValue(sales.getSales());

            dataRowIndex++;

        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public List<MonthlyProducts> getMonthlyProducts(String month) {


        List<ProductSold> soldProducts = productSoldRepo.findAll();
//        soldProducts.forEach((productSold -> {
//            productSold.setPrice(0);
//            productSold.setQuantity(0);
//        }));
        soldProducts.forEach(product -> {
            if(product.getMonth().equals(month)) {

                if(monthlyProductRepo.existsByName(product.getName())) {
                    MonthlyProducts monthlyProducts = monthlyProductRepo.findByName(product.getName());
                    monthlyProducts.setPrice(monthlyProducts.getPrice() + (product.getPrice()*product.getQuantity()));
                    monthlyProducts.setQuantity(monthlyProducts.getQuantity()+ product.getQuantity());
                    monthlyProductRepo.save(monthlyProducts);
                }

                else {
                    MonthlyProducts monthlyProducts = new MonthlyProducts();
                    monthlyProducts.setName(product.getName());
                    monthlyProducts.setPrice(product.getPrice()*product.getQuantity());
                    monthlyProducts.setQuantity(product.getQuantity());
                    monthlyProductRepo.save(monthlyProducts);
                }
            }
        });

        return monthlyProductRepo.findAll();
    }

    @Override
    public List<YearlyProducts> getYearlyProducts(String year) {
//        yearlySalesReportRepo.deleteAll();

        List<ProductSold> soldProducts = productSoldRepo.findAll();
//        soldProducts.forEach((productSold -> {
//            productSold.setPrice(0);
//            productSold.setQuantity(0);
//        }));

        soldProducts.forEach(product -> {
            if(product.getYear().equals(year)) {
                if(yearlyProductRepo.existsByName(product.getName())) {
                    YearlyProducts yearlyProducts = yearlyProductRepo.findByName(product.getName());
                    yearlyProducts.setPrice(yearlyProducts.getPrice() + (product.getPrice()* product.getQuantity()));
                    yearlyProducts.setQuantity(yearlyProducts.getQuantity() + product.getQuantity());
                    yearlyProductRepo.save(yearlyProducts);
                }

                else {
                    YearlyProducts yearlyProducts = new YearlyProducts();
                    yearlyProducts.setName(product.getName());
                    yearlyProducts.setPrice(product.getPrice()* product.getQuantity());
                    yearlyProducts.setQuantity(product.getQuantity());
                    yearlyProductRepo.save(yearlyProducts);
                }
            }
        });

        return yearlyProductRepo.findAll();
    }

    @Override
    public void generateEachMonthReport(HttpServletResponse response) throws IOException {
        List<MonthlyProducts> monthlyProductsList = monthlyProductRepo.findAllByOrderByPriceDesc();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Monthly report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Price");
        row.createCell(3).setCellValue("Quantity");

        int dataRowIndex = 1;
        for(MonthlyProducts sales:monthlyProductsList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sales.getId());
            dataRow.createCell(1).setCellValue(sales.getName());
            dataRow.createCell(2).setCellValue(sales.getPrice());
            dataRow.createCell(3).setCellValue(sales.getQuantity());

            dataRowIndex++;

        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public void generateEachYearReport(HttpServletResponse response) throws IOException {
        List<YearlyProducts> yearlyProductsList = yearlyProductRepo.findAllByOrderByPriceDesc();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Yearly report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Price");
        row.createCell(3).setCellValue("Quantity");

        int dataRowIndex = 1;
        for(YearlyProducts sales:yearlyProductsList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(sales.getId());
            dataRow.createCell(1).setCellValue(sales.getName());
            dataRow.createCell(2).setCellValue(sales.getPrice());
            dataRow.createCell(3).setCellValue(sales.getQuantity());

            dataRowIndex++;

        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public String getCustomerReport() {
        List<ProductSold> productSoldList = productSoldRepo.findAll();

        if(iterationCustomerReport >1) {
            customerReportRepo.findAll().forEach(p -> {
                p.setPoints(0);
                p.setAmount(0);
            });
        }

        productSoldList.forEach(product-> {
            if(customerReportRepo.existsByCustId(product.getBuyerId())) {
                CustomerReport customerReport = customerReportRepo.getByCustId(product.getBuyerId());
                customerReport.setAmount(customerReport.getAmount() + (product.getPrice() * product.getQuantity()));
                customerReport.setPoints((int)(customerReport.getPoints() + (customerReport.getAmount()/100)));
                customerReportRepo.save(customerReport);
            }

            else{
                CustomerReport customerReport = new CustomerReport();
                customerReport.setCustomer(product.getBuyerName());
                customerReport.setCustId(product.getBuyerId());
                customerReport.setAmount(product.getPrice() * product.getQuantity());
                // 100rs = 1 point
                customerReport.setPoints((int)(customerReport.getAmount()/100));
                customerReportRepo.save(customerReport);
            }

        });
        iterationCustomerReport++;
        return "Customer Report generated";
    }

    @Override
    public void generateCustomerReport(HttpServletResponse response) throws IOException {
        List<CustomerReport> customerReportList = customerReportRepo.findAllByOrderByAmountDesc();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Customer report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Customer Id");
        row.createCell(3).setCellValue("Amount");
        row.createCell(4).setCellValue("Points");

        int dataRowIndex = 1;
        for(CustomerReport customer:customerReportList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(customer.getId());
            dataRow.createCell(1).setCellValue(customer.getCustomer());
            dataRow.createCell(2).setCellValue(customer.getCustId());
            dataRow.createCell(3).setCellValue(customer.getAmount());
            dataRow.createCell(4).setCellValue(customer.getPoints());

            dataRowIndex++;
        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public String getCustomerMonthlyReport(String month) {
        List<ProductSold> soldProducts = productSoldRepo.findAll();

        if(iterationCustomerMonthlyReport >1) {
            customerMontlyReportRepo.findAll().forEach(p -> {
                p.setAmount(0);
            });
        }

        soldProducts.forEach(p -> {
            if (p.getMonth().equals(month)) {
                if (customerMontlyReportRepo.existsByCustIdAndCustName(p.getBuyerId(), p.getBuyerName())) {
                    CustomerMonthlyReport customerMonthlyReport = customerMontlyReportRepo.
                            findByCustIdAndCustName(p.getBuyerId(), p.getBuyerName());

                    customerMonthlyReport.setAmount(customerMonthlyReport.getAmount() + (p.getQuantity() * p.getPrice()));
                    customerMontlyReportRepo.save(customerMonthlyReport);
                } else {
                    CustomerMonthlyReport customerMonthlyReport = new CustomerMonthlyReport();
                    customerMonthlyReport.setCustId(p.getBuyerId());
                    customerMonthlyReport.setCustName(p.getBuyerName());
                    customerMonthlyReport.setAmount(p.getQuantity() * p.getPrice());

                    customerMontlyReportRepo.save(customerMonthlyReport);
                }
            }
        });
        return "Generated successfully";
    }

    @Override
    public void generateCustomerMonthlyReport(HttpServletResponse response) throws IOException {

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Customer Monthly Report");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("Id");
            row.createCell(1).setCellValue("Customer Id");
            row.createCell(2).setCellValue("Customer Name");
            row.createCell(3).setCellValue("Amount");

            List<CustomerMonthlyReport> customerMonthlyReportList = customerMontlyReportRepo.findAll();
            int dataRowIndex = 1;
            for (CustomerMonthlyReport customerMonthlyReport: customerMonthlyReportList) {
                HSSFRow dataRow = sheet.createRow(dataRowIndex);
                dataRow.createCell(0).setCellValue(customerMonthlyReport.getId());
                dataRow.createCell(1).setCellValue(customerMonthlyReport.getCustId());
                dataRow.createCell(2).setCellValue(customerMonthlyReport.getCustName());
                dataRow.createCell(3).setCellValue(customerMonthlyReport.getAmount());

                dataRowIndex++;
            }
            ServletOutputStream ops= response.getOutputStream();
            workbook.write(ops);
            workbook.close();
            ops.close();
    }

    @Override
    public String getCustomerYearlyReport(String year) {
        List<ProductSold> soldProducts = productSoldRepo.findAll();

        if(iterationCustomerYearlyReport > 1) {
            customerYearlyReportRepo.findAll().forEach(p-> {
                p.setAmount(0);
            });
        }

        soldProducts.forEach(p -> {
            if(p.getYear().equals(year)) {
                if(customerYearlyReportRepo.existsByCustIdAndCustName(p.getBuyerId(), p.getBuyerName())) {
                    CustomerYearlyReport customerYearlyReport = customerYearlyReportRepo
                            .findByCustIdAndCustName(p.getBuyerId(), p.getBuyerName());
                    customerYearlyReport.setAmount(customerYearlyReport.getAmount() + (p.getPrice()*p.getQuantity()));
                    customerYearlyReportRepo.save(customerYearlyReport);
                }
                else {
                    CustomerYearlyReport customerYearlyReport = new CustomerYearlyReport();
                    customerYearlyReport.setCustId(p.getBuyerId());
                    customerYearlyReport.setCustName(p.getBuyerName());
                    customerYearlyReport.setAmount(p.getPrice()*p.getQuantity());
                    customerYearlyReportRepo.save(customerYearlyReport);
                }
            }
        });

        return "Customer Yearly Report Generated!";
    }

    @Override
    public void generateCustomerYearlyReport(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Customer Yearly Report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Customer Id");
        row.createCell(2).setCellValue("Customer Name");
        row.createCell(3).setCellValue("Amount");

        List<CustomerYearlyReport> customerYearlyReportList = customerYearlyReportRepo.findAll();
        int dataRowIndex = 1;
        for (CustomerYearlyReport customerYearlyReport: customerYearlyReportList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(customerYearlyReport.getId());
            dataRow.createCell(1).setCellValue(customerYearlyReport.getCustId());
            dataRow.createCell(2).setCellValue(customerYearlyReport.getCustName());
            dataRow.createCell(3).setCellValue(customerYearlyReport.getAmount());

            dataRowIndex++;
        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public String getExpiryReport() {
        List<ProductBought> productBought = productBoughtRepo.findAll();

        productBought.forEach(product -> {
            LocalDate l1 = product.getExpirydate();
            if(LocalDate.now().isAfter(l1)) {
                ExpiryReport expiryReport = new ExpiryReport();
                expiryReport.setProdId(product.getProdId());
                expiryReport.setProdName(product.getName());
                expiryReport.setExpiredOn(product.getExpirydate());
                expiryReportRepo.save(expiryReport);
            }
        });
        return "Expiry Report Created";
    }

    @Override
    public void generateExpiryReport(HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Customer Yearly Report");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Id");
        row.createCell(1).setCellValue("Product Id");
        row.createCell(2).setCellValue("Product Name");
        row.createCell(3).setCellValue("Expired on");

        List<ExpiryReport> expiryReportList = expiryReportRepo.findAll();
        int dataRowIndex = 1;
        for (ExpiryReport expiryReport: expiryReportList) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            dataRow.createCell(0).setCellValue(expiryReport.getId());
            dataRow.createCell(1).setCellValue(expiryReport.getProdId());
            dataRow.createCell(2).setCellValue(expiryReport.getProdName());
            dataRow.createCell(3).setCellValue(expiryReport.getExpiredOn());

            dataRowIndex++;
        }
        ServletOutputStream ops= response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }
}


