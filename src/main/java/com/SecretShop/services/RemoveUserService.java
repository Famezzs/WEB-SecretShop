package com.SecretShop.services;

import com.SecretShop.entities.PurchaseEntity;
import com.SecretShop.helpers.CheckUserIdHelper;
import com.SecretShop.repositories.PurchaseRepository;
import com.SecretShop.repositories.UserRepository;
import org.hibernate.Criteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;

import javax.validation.ValidationException;

public class RemoveUserService implements Runnable {

    private Long userId;
    private UserRepository userRepository;
    private PurchaseRepository purchaseRepository;

    public RemoveUserService(String userId,
                             UserRepository userRepository,
                             PurchaseRepository purchaseRepository)
                             throws ValidationException {

        this.userId = new CheckUserIdHelper().check(userId, userRepository);
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void run() {

        userRepository.deleteById(userId);

        var userPurchases = purchaseRepository.findByUserId(userId);
        purchaseRepository.deleteAll(userPurchases);
    }
}
