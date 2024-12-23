package com.charann.InventoryManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
public class ExpiryReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private long prodId;

    @Column
    private String prodName;

    @Column
    private LocalDate expiredOn;
}
