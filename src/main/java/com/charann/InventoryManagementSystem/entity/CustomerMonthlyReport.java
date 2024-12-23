package com.charann.InventoryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerMonthlyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long custId;

    @Column
    private double amount;

    @Column
    private String custName;

}
