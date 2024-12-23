package com.charann.InventoryManagementSystem.repository;

import com.charann.InventoryManagementSystem.entity.ProductBought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductBoughtRepo extends JpaRepository<ProductBought, Long> {

    Optional<ProductBought> findById(Long id);

//    @Query(value = "SELECT name, sum(quantity) FROM product_bought group by name;", nativeQuery = true)
//    List<ProductBought> findCategorizedCount();

    Boolean existsByName(String name);

    ProductBought findFirstByName(String name);

    Boolean existsBySku(String sku);

//    List<ProductBought> findAll();
    ProductBought findBySku(String sku);

}
