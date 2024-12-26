package com.dako.forohub.infra.exceptions;

public class TokenCreationException extends RuntimeException {
    public TokenCreationException(String message) {
        super(message);
    }

    public TokenCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}