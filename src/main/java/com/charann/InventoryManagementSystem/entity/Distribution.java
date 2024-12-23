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
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String orderStatus;

    @Column
    private String orderId;

    @Column
    private long prodId;

    @Column
    private String prodName;

    @Column
    private LocalDate warehouseLeavingDate;

    @Column
    private int quantity;

    @Column
    private String vehicleType;

    @Column
    private String vehicleNo;

    @Column
    private String destinationName;

    @Column
    private String buyerName;

    @Column
    private String buyerId;

    @Column
    private String buyerPhNo;

    @Column
    private String buyerEmail;

}
