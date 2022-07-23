package com.SecretShop.helpers;

import com.SecretShop.repositories.ProductRepository;
import com.SecretShop.repositories.UserRepository;

public class CheckBalanceHelper {

    public boolean check(Long userId, UserRepository userRepository,
                         Long productId, ProductRepository productRepository) {

        var user = userRepository.findById(userId);
        var product = productRepository.findById(productId);

        if (user.isEmpty() || product.isEmpty()) {
            return false;
        }

        if (user.get().getBalance() - product.get().getPrice() >= 0) {
            return true;
        } else {
            return false;
        }
    }
}
