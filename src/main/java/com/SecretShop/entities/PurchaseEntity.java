package com.SecretShop.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.ValidationException;

@Entity
@Table(name="PURCHASES")
public class PurchaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long productId;

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) throws ValidationException{

        if (userId.isBlank() || userId.isEmpty()) {
            throw new ValidationException("User ID cannot be blank or empty");
        }

        var parsedUserId = 0L;

        try {
            parsedUserId = Long.parseLong(userId);
        }
        catch (Exception exception) {
            throw new ValidationException("User ID provided is of a wrong type");
        }

        if (parsedUserId < 0) {
            throw new ValidationException("User ID cannot be negative");
        }

        this.userId = parsedUserId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(String productId) throws ValidationException{

        if (productId.isBlank() || productId.isEmpty()) {
            throw new ValidationException("Product ID cannot be blank or empty");
        }

        var parsedProductId = 0L;

        try {
            parsedProductId = Long.parseLong(productId);
        }
        catch (Exception exception) {
            throw new ValidationException("Product ID provided is of a wrong type");
        }

        if (parsedProductId < 0) {
            throw new ValidationException("Product ID cannot be negative");
        }

        this.productId = parsedProductId;
    }

    public PurchaseEntity() {

    }

    public PurchaseEntity(String userId, String productId) throws ValidationException {

        setUserId(userId);
        setProductId(productId);
    }
}
