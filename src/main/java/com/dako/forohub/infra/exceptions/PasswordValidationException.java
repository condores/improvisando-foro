package com.dako.forohub.infra.exceptions;

public class PasswordValidationException extends RuntimeException {
    public PasswordValidationException(String message) {
        super(message);
    }
}
