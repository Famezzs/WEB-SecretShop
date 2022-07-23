package com.SecretShop.helpers;

import com.SecretShop.entities.UserEntity;
import com.SecretShop.repositories.UserRepository;

import javax.validation.ValidationException;
import java.util.Optional;

public class CheckUserIdHelper extends CheckIdHelper {
    public Long check(String userId, UserRepository userRepository) throws ValidationException {

        Optional<UserEntity> user;

        try {
            super.check(userId);
        } catch (ValidationException exception) {
            throw new ValidationException(String.join(" ", "User ID", exception.getMessage()));
        }

        user = userRepository.findById(Long.parseLong(userId));

        if (user.isEmpty()) {
            throw new ValidationException("No user with such ID exists");
        }

        return user.get().getId();
    }
}
