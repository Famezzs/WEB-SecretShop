package com.SecretShop.services;

import com.SecretShop.entities.UserEntity;
import com.SecretShop.helpers.CheckProductIdHelper;
import com.SecretShop.repositories.ProductRepository;
import com.SecretShop.repositories.PurchaseRepository;
import com.SecretShop.repositories.UserRepository;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FindUsersByProductIdService {

    private Long productId;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;

    public FindUsersByProductIdService(String productId,
                                       UserRepository userRepository,
                                       ProductRepository productRepository,
                                       PurchaseRepository purchaseRepository) {

        this.productId = new CheckProductIdHelper().check(productId, productRepository);
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<UserEntity> execute() {

        var purchases = purchaseRepository.findByProductId(productId);
        var users = userRepository.findAll();

        List<UserEntity> filteredUsers = new ArrayList<>();

        for(var user: users) {
            for (var purchase: purchases) {
                if (purchase.getUserId() == user.getId()) {
                    filteredUsers.add(user);
                }
            }
        }

        return filteredUsers;
    }
}
