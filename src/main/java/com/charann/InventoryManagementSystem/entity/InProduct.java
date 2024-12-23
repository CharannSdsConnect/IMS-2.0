package com.charann.InventoryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String prodName;

    @Column
    private long prodId;

    @Column
    private int serialNo;

    @Column
    private String lotNo;

    // yyyy-mm-dd  -> format for LocalDate
    @Column
    private LocalDate manufactureDate;

    @Column
    private LocalDate expiryDate;

    @Column
    private LocalDate warehouseArrivingDate;

    @Column
    private int quantity;

    @Column
    private double pricePerUnit;
}
