package com.charann.InventoryManagementSystem.service;

import com.charann.InventoryManagementSystem.entity.InProduct;
import com.charann.InventoryManagementSystem.entity.RemainingStock;
import com.charann.InventoryManagementSystem.repository.InProductRepo;
import com.charann.InventoryManagementSystem.repository.RemainingStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InProductService {

    @Autowired
    InProductRepo inProductRepo;

    @Autowired
    RemainingStockRepo remainingStockRepo;

    public InProduct input(InProduct inProduct) {

        if(inProductRepo.existsByProdId(inProduct.getProdId())) {
            RemainingStock remainingStock = remainingStockRepo.getByProdId(inProduct.getProdId());
            remainingStock.setQuantity(remainingStock.getQuantity() + inProduct.getQuantity());

            remainingStockRepo.save(remainingStock);
        } else {
            RemainingStock remainingStock = new RemainingStock();
            remainingStock.setProdId(inProduct.getProdId());
            remainingStock.setProdName(inProduct.getProdName());
            remainingStock.setQuantity(inProduct.getQuantity());

            remainingStockRepo.save(remainingStock);
        }

        InProduct inProduct1 = new InProduct();

        inProduct1.setProdName(inProduct.getProdName());
        inProduct1.setProdId(inProduct.getProdId());
        inProduct1.setSerialNo(inProduct.getSerialNo());
        inProduct1.setLotNo(inProduct.getLotNo());
        inProduct1.setManufactureDate(inProduct.getManufactureDate());
        inProduct1.setExpiryDate(inProduct.getExpiryDate());
        inProduct1.setWarehouseArrivingDate(inProduct.getWarehouseArrivingDate());
        inProduct1.setQuantity(inProduct.getQuantity());
        inProduct1.setPricePerUnit(inProduct.getPricePerUnit());

        return inProductRepo.save(inProduct1);
    }
}
