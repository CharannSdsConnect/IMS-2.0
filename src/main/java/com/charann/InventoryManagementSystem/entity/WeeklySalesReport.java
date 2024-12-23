package com.charann.InventoryManagementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeeklySalesReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

}
