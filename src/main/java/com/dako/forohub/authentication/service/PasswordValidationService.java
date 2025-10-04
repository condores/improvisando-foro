package com.dako.forohub.authentication.service;

import org.springframework.stereotype.Service;

import com.dako.forohub.infra.exceptions.PasswordValidationException;

@Service
public class PasswordValidationService {

    public void validatePassword(String password) {
        if (password.length() < 8 || password.length() > 11 ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*\\d.*")) {
            throw new PasswordValidationException(
                    "Password must be between 8 and 11 characters long, contain at least one uppercase letter and one number");
        }
    }

    // Ejemplo: método para verificar si la contraseña contiene un carácter especial
    public boolean containsSpecialCharacter(String password) {
        return password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
}