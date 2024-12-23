package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRepo extends JpaRepository<OrderStatus, Long> {

    OrderStatus getByOrderId(String orderId);
}
