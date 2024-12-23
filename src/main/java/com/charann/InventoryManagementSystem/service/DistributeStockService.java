package com.charann.InventoryManagementSystem.service;

import com.charann.InventoryManagementSystem.entity.OrderStatus;
import com.charann.InventoryManagementSystem.entity.RemainingStock;
import com.charann.InventoryManagementSystem.repository.DistributionRepo;
import com.charann.InventoryManagementSystem.repository.OrderStatusRepo;
import com.charann.InventoryManagementSystem.repository.RemainingStockRepo;
import com.charann.InventoryManagementSystem.repository.UserRepository;
import com.charann.InventoryManagementSystem.entity.Distribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributeStockService {

    @Autowired
    RemainingStockRepo remainingStockRepo;

    @Autowired
    DistributionRepo distributionRepo;

    @Autowired
    OrderStatusRepo orderStatusRepo;

    @Autowired
    UserRepository userRepository;

    public String distributeProduct(Distribution distribution) {

        RemainingStock remainingStock = remainingStockRepo.getByProdId(distribution.getProdId());
        System.out.println(remainingStock.getQuantity() - distribution.getQuantity());
        System.out.println(userRepository.findByEmail(distribution.getBuyerEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + distribution.getBuyerEmail())).isLoggedInStatus());
        if(userRepository.findByEmail(distribution.getBuyerEmail())
                .orElseThrow(
                        () -> new RuntimeException("User not found with this email: " + distribution.getBuyerEmail())).isLoggedInStatus()) {

            if (remainingStock.getQuantity() - distribution.getQuantity() >= 0) {
                Distribution distribution1 = new Distribution();
                distribution1.setOrderId(distribution.getOrderId());
                distribution1.setOrderStatus(distribution.getOrderStatus());
                distribution1.setProdId(distribution.getProdId());
                distribution1.setProdName(distribution.getProdName());
                distribution1.setQuantity(distribution.getQuantity());
                distribution1.setWarehouseLeavingDate(distribution.getWarehouseLeavingDate());
                distribution1.setVehicleType(distribution.getVehicleType());
                distribution1.setVehicleNo(distribution.getVehicleNo());
                distribution1.setBuyerName(distribution.getBuyerName());
                distribution1.setBuyerId(distribution.getBuyerId());
                distribution1.setBuyerPhNo(distribution.getBuyerPhNo());
                distributionRepo.save(distribution1);
            } else {
                return "Out-of-Stock";
            }

            System.out.println(distributionRepo.existsByProdId(distribution.getProdId()));
            if (distributionRepo.existsByProdId(distribution.getProdId())) {
                RemainingStock remainingStock1 = remainingStockRepo.getByProdId(distribution.getProdId());
                if (remainingStock1.getQuantity() - distribution.getQuantity() < 0) return "Out-of-Stock";
                remainingStock1.setQuantity(remainingStock1.getQuantity() - distribution.getQuantity());
                remainingStockRepo.save(remainingStock1);
            } else if (!distributionRepo.existsByProdId(distribution.getProdId())) {
                return "Item does not exist";
            }

            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setOrderId(distribution.getOrderId());
            orderStatus.setOrderStatus(distribution.getOrderStatus());
            orderStatusRepo.save(orderStatus);

            return "Distributed successfully";
        }
        else {
            return "Invalid user-email! Please login!";
        }
    }

    public String getOrderStatus(String orderId) {
        OrderStatus order = orderStatusRepo.getByOrderId(orderId);

        return order.getOrderStatus();
    }
}
