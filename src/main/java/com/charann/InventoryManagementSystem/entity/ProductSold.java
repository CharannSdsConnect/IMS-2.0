package com.charann.InventoryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSold {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private double quantity;    // no.of products sold

//    @Column
//    private double price;   // price of one product

    @Column(name = "product_name")
    private String name;

    @Column(name = "sold_month")
    private String month;

    @Column
    private String year;

    @Column
    private String date;

    @Column
    private LocalDate buyingDate;

    @Column(name = "total_price")
    private double price;

    @Column
    private String sku;

    @Column
    private String orderStatus;

    @Column
    private String orderId;

    @Column
    private LocalDate warehouseLeavingDate;

    @Column
    private String vehicleType;

    @Column
    private String vehicleNo;

    @Column
    private String destinationName;

    @Column
    private String buyerName;

    @Column
    private long buyerId;

    @Column
    private String buyerPhNo;

    @Column
    private String buyerEmail;

}