package com.SecretShop.services;

import com.SecretShop.entities.PurchaseEntity;
import com.SecretShop.helpers.CheckProductIdHelper;
import com.SecretShop.helpers.CheckUserIdHelper;
import com.SecretShop.repositories.ProductRepository;
import com.SecretShop.repositories.PurchaseRepository;
import com.SecretShop.repositories.UserRepository;

import javax.validation.ValidationException;

public class PurchaseService {

    public void execute(String userId,
                        String productId,
                        UserRepository userRepository,
                        ProductRepository productRepository,
                        PurchaseRepository purchaseRepository)
                        throws ValidationException {

        var parsedUserId = new CheckUserIdHelper().check(userId, userRepository);
        var parsedProductId = new CheckProductIdHelper().check(productId, productRepository);

        var user = userRepository.findById(parsedUserId);
        var product = productRepository.findById(parsedProductId);

        var remainingFunds = user.get().getBalance() - product.get().getPrice();

        if (remainingFunds < 0) {
            throw new ValidationException("Insufficient funds");
        }

        user.get().setBalance(remainingFunds);

        userRepository.save(user.get());
        purchaseRepository.save(new PurchaseEntity(userId, productId));
    }
}
