package com.SecretShop.repositories;

import com.SecretShop.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
    public List<PurchaseEntity> findByUserId(Long userId);
    public List<PurchaseEntity> findByProductId(Long productId);
}
