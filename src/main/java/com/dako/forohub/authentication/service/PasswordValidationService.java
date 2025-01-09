package com.dako.forohub.authentication.service;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidationService {

    public void validatePassword(String password) {
        if (password.length() < 8 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*\\d.*")) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long, contain at least one uppercase letter and one number");
        }
    }
}
