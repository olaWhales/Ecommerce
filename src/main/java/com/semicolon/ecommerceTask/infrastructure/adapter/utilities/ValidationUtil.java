package com.semicolon.ecommerceTask.infrastructure.adapter.utilities;

import com.semicolon.ecommerceTask.domain.exception.NameNotFoundException;

public class ValidationUtil {

    public static void validateEmail(String email) {
        if (email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new NameNotFoundException(MessageUtil.INVALID_EMAIL);
        }
    }

    public static void validatePassword(String password) {
        if (password == null || !password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?])(?=\\S+$).{8,}$")) {
            throw new NameNotFoundException(MessageUtil.INVALID_PASSWORD);
        }
    }

    public static void validateFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new NameNotFoundException(MessageUtil.FIRST_NAME_REQUIRED);
        }
    }

    public static void validateLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new NameNotFoundException(MessageUtil.LAST_NAME_REQUIRED);
        }
    }

    public static void validateRegistrationInput(String email, String firstName, String lastName, String password) {
        validateEmail(email);
        validateFirstName(firstName);
        validateLastName(lastName);
        validatePassword(password);
    }

    public static void validateUpdateInput(String email, String firstName, String lastName) {
        validateEmail(email);
        validateFirstName(firstName);
        validateLastName(lastName);
    }
}
