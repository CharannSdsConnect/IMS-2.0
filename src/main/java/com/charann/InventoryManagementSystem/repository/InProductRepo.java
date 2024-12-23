package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.InProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InProductRepo extends JpaRepository<InProduct, Long> {

    Boolean existsByProdId(long prod_id);
}
