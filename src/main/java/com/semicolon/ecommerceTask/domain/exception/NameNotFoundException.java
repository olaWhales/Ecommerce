package com.semicolon.ecommerceTask.domain.exception;

public class NameNotFoundException extends RuntimeException {
    public NameNotFoundException(String message) {
        super(message);
    }
}
