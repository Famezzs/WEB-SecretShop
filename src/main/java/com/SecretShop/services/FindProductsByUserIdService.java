package com.SecretShop.services;

import com.SecretShop.entities.ProductEntity;
import com.SecretShop.entities.UserEntity;
import com.SecretShop.helpers.CheckProductIdHelper;
import com.SecretShop.helpers.CheckUserIdHelper;
import com.SecretShop.repositories.ProductRepository;
import com.SecretShop.repositories.PurchaseRepository;
import com.SecretShop.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class FindProductsByUserIdService {

    private Long userId;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;

    public FindProductsByUserIdService(String userId,
                                       UserRepository userRepository,
                                       ProductRepository productRepository,
                                       PurchaseRepository purchaseRepository) {

        this.userId = new CheckUserIdHelper().check(userId, userRepository);
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<ProductEntity> execute() {

        var purchases = purchaseRepository.findByUserId(userId);
        var products = productRepository.findAll();

        List<ProductEntity> filteredProducts = new ArrayList<>();

        for(var product: products) {
            for (var purchase: purchases) {
                if (purchase.getProductId() == product.getId()) {
                    filteredProducts.add(product);
                }
            }
        }

        return filteredProducts;
    }
}
