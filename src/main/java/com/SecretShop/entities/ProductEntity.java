package com.SecretShop.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.ValidationException;

@Entity
@Table(name="PRODUCTS")
public class ProductEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String productName;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) throws ValidationException {
        if (productName.isBlank() || productName.isEmpty()) {
            throw new ValidationException("Product's name cannot be blank or empty");
        }

        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(String price) throws ValidationException {
        if (price.isBlank() || price.isEmpty()) {
            throw new ValidationException("Price cannot be blank or empty");
        }

        Double parsedPrice;

        try {
            parsedPrice = Double.parseDouble(price);
        }
        catch (Exception exception) {
            throw new ValidationException("Price value provided is of a wrong type");
        }

        if (parsedPrice < 0) {
            throw new ValidationException("Price cannot be less than zero");
        }

        this.price = parsedPrice;
    }

    public ProductEntity() {

    }

    public ProductEntity(String productName, String price)
                  throws ValidationException {

        setProductName(productName);
        setPrice(price);
    }
}
