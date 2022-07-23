package com.SecretShop.services;

import com.SecretShop.helpers.CheckProductIdHelper;
import com.SecretShop.helpers.CheckUserIdHelper;
import com.SecretShop.repositories.ProductRepository;
import com.SecretShop.repositories.PurchaseRepository;
import com.SecretShop.repositories.UserRepository;

import javax.validation.ValidationException;

public class RemoveProductService implements Runnable {

    private Long productId;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;

    public RemoveProductService(String productId,
                                ProductRepository productRepository,
                                PurchaseRepository purchaseRepository)
                                throws ValidationException {

        this.productId = new CheckProductIdHelper().check(productId, productRepository);
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void run() {

        productRepository.deleteById(productId);

        var productPurchases = purchaseRepository.findByProductId(productId);
        purchaseRepository.deleteAll(productPurchases);
    }
}
