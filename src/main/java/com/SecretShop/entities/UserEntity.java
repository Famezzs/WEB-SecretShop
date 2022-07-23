package com.SecretShop.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.ValidationException;

@Entity
@Table(name="USERS")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws ValidationException {
        if (firstName.isBlank() || firstName.isEmpty()) {
            throw new ValidationException("First name cannot be blank or empty");
        }

        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws ValidationException {
        if (lastName.isBlank() || lastName.isEmpty()) {
            throw new ValidationException("Last name cannot be blank or empty");
        }

        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ValidationException {
        if (email.isBlank() || email.isEmpty()) {
            throw new ValidationException("Email cannot be blank or empty");
        }

        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(String balance) throws ValidationException {
        if (balance.isBlank() || balance.isEmpty()) {
            throw new ValidationException("Balance cannot be blank or empty");
        }

        Double parsedBalance;

        try {
            parsedBalance = Double.parseDouble(balance);
        }
        catch (Exception exception) {
            throw new ValidationException("Balance value provided is of a wrong type");
        }

        if (parsedBalance < 0) {
            throw new ValidationException("Balance cannot be less than zero");
        }

        this.balance = parsedBalance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public UserEntity() {

    }

    public UserEntity(String firstName, String lastName,
                      String email, String balance)
                throws ValidationException {

        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setBalance(balance);
    }
}
