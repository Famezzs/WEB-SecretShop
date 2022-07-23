package com.SecretShop.helpers;

import com.SecretShop.entities.ProductEntity;
import com.SecretShop.repositories.ProductRepository;

import javax.validation.ValidationException;
import java.util.Optional;

public class CheckProductIdHelper extends CheckIdHelper {
    public Long check(String productId, ProductRepository productRepository) {

        Optional<ProductEntity> product;

        try {
            super.check(productId);
        } catch (ValidationException exception) {
            throw new ValidationException(String.join(" ", "Product ID", exception.getMessage()));
        }

        product = productRepository.findById(Long.parseLong(productId));

        if (product.isEmpty()) {
            throw new ValidationException("No product with such ID exists");
        }

        return product.get().getId();
    }
}
