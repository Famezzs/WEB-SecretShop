package com.SecretShop.helpers;

import javax.validation.ValidationException;

public class CheckIdHelper {
    public void check(String id) throws ValidationException {

        if (id.isBlank() || id.isEmpty()) {
            throw new ValidationException("cannot be blank or empty");
        }

        var parsedUserId = 0L;

        try {
            parsedUserId = Long.parseLong(id);
        }
        catch (Exception exception) {
            throw new ValidationException("provided is of a wrong type");
        }

        if (parsedUserId < 0) {
            throw new ValidationException("cannot be negative");
        }
    }
}
