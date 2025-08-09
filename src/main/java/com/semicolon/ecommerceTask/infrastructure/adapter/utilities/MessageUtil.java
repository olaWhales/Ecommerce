package com.semicolon.ecommerceTask.infrastructure.adapter.utilities;

public class MessageUtil {
    public static final String ADMIN_ALREADY_EXISTS = "Admin with email %s already exists";
    public static final String NO_PENDING_REGISTRATION = "No pending registration for email %s";
    public static final String TOKEN_EXPIRED = "Registration token has expired";
    public static final String KEYCLOAK_CREATION_FAILED = "Failed to create admin in Keycloak";
    public static final String ADMIN_NOT_FOUND = "Admin with email %s not found";
    public static final String INVALID_EMAIL = "Invalid email address format";
    public static final String FIRST_NAME_REQUIRED = "First name is required";
    public static final String LAST_NAME_REQUIRED = "Last name is required";
    public static final String INVALID_PASSWORD = "Password must be at least 8 characters long, with at least one uppercase letter, one lowercase letter, one number, and one special character";
    public static final String EMAIL_SUBJECT = "Complete Your Admin Registration";
    public static final String EMAIL_BODY = """
            Click the link to complete your registration: 
            http://localhost:8081/admin/register?token=%s&email=%s
            This link expires in 24 hours.
            """;
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String LOGIN_SUCCESS = "Welcome back!";
}