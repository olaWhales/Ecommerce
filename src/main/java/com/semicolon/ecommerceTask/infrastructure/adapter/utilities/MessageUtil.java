package com.semicolon.ecommerceTask.infrastructure.adapter.utilities;

public class MessageUtil {
    public static String ADMIN_ALREADY_EXISTS_IN_KEYCLOAK = "Admin already exist in keycloak";
    public static String ADMIN_ALREADY_EXISTS = "Admin with email %s already exists";
    public static String NO_PENDING_REGISTRATION = "No pending registration for email %s";
    public static String TOKEN_EXPIRED = "Registration token has expired";
    public static String KEYCLOAK_CREATION_FAILED = "Failed to create admin in Keycloak";
    public static String ADMIN_NOT_FOUND = "Admin with email %s not found";
    public static String INVALID_EMAIL = "Invalid email address format";
    public static String FIRST_NAME_REQUIRED = "First name is required";
    public static String LAST_NAME_REQUIRED = "Last name is required";
    public static String INVALID_PASSWORD = "Password must be at least 8 characters long, with at least one uppercase letter, one lowercase letter, one number, and one special character";
    public static String EMAIL_SUBJECT = "Complete Your Admin Registration";
    public static String EMAIL_BODY = """
            Click the link to complete your registration:\s
            http://localhost:8081/admin/register?token=%s&email=%s
            This link expires in 24 hours.
           \s""";
    public static String INVALID_CREDENTIALS = "Invalid username or password";
    public static String USER_NOT_FOUND = "User not found";
    public static String LOGIN_SUCCESS = "Welcome back ";
    public static String FAILED_TO_RETRIEVE_USER_DETAILS ="Failed to retrieve user details for token. Error: {}";
    public static String ACCESS_TOKEN = "access_token";
    public static String PREFERRED_USERNAME = "preferred_username";
    public static String EMAIL = "email";
    public static String FAILED_TO_ASSIGN_ROLE_IN_KEYCLOAK = "Failed to assign role in Keycloak: ";
    public static String REALM_ACCESS = "realm_access";
    public static String FAILED_TO_CREATE_USER_IN_KEYCLOAK = "Failed to create user in Keycloak";
    public static String AN_ERROR_OCCURRED_WHILE_CREATING_USER_IN_KEYCLOAK = "An error occurred while creating user in Keycloak: {}";
    public static String USER = "USER";
    public static String REALMS = "/realms/";
    public static String PROTOCOL_OPENID_CONNECT_USER_INFO = "/protocol/openid-connect/userinfo";
    public static String PROTOCOL_OPENID_CONNECT_TOKEN = "/protocol/openid-connect/token";
    public static String FIRSTNAME = "firstName";
    public static String LASTNAME = "lastName";
    public static String ROLE = "roles";
    public static String FAILED_TO_ASSIGN_ROLE_USER = "Failed to assign role {} to user {}: {}";
    public static String EXCEPTION_DURING_USER_CREATION = "Exception during user creation";
    public static String KEYCLOAK_USER_CREATION_FAILED = "Keycloak user creation failed: ";
    public static String USER_ALREADY_EXIST_IN_KEYCLOAK = "User or email already exists in Keycloak";

    public static String USER_DATA_CANNOT_BE_NULL = "User data cannot be null";

}