package com.charann.InventoryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String customer;

    @Column
    private long custId;

    @Column
    private double amount;

    @Column
    private int points = 0;

}
