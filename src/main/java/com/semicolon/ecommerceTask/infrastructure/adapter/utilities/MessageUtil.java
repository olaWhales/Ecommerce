package com.semicolon.ecommerceTask.infrastructure.adapter.utilities;

public class MessageUtil {
    public static final String AUTHENTICATED_USER_EMAIL_MISSING = "Authentication user email missing ";
    public static final String INVALID_AUTHENTICATION_PRINCIPAL = "Invalid authentication principal";
    public static final String AUTHENTICATED_USER_ID_MISSING = "Authentication user id is missing";
    public static final String AUTHENTICATED_USER_NOT_FOUND = "Authenticated user not found ";
    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String PRODUCT_UPLOAD_SUCCESSFULLY = "Product uploaded successfully";
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exist";
    public static final String SELLER_NOT_FOUND = "Seller not found ";
    public static final String ADMIN_REGISTRATION_INITIATED_AN_EMAIL_HAS_BEEN_SENT_TO = "Admin registration initiated. An email has been sent to ";
    public static final String PROCEED_TO_COMPLETE_YOUR_REGISTRATION = "Proceed to complete your registration.";
    public static final String REGISTRATION_COMPLETED_YOU_CAN_NOW_LOGIN = "Registration complete. You can now log in.";
    public static final String ADMIN_WITH_EMAIL = "Admin with email ";
    public static final String DELETED = " deleted.";
    public static final String SELLER_NOT_FOUND_WITH_ID = "Seller not found with id ";
    public static final String CATEGORY_CREATED_SUCCESSFULLY = "Category created successfully";
    public static final String PRODUCT_CREATED_SUCCESSFULLY = "Product created successfully";
    public static final String YOUR_REGISTRATION_HAS_SUCCESSFULLY_REGISTERED = ", your registration has successfully registered";
    public static final String ADMIN_ALREADY_EXISTS_IN_KEYCLOAK = "Admin with email '%s' already exists in Keycloak";
    public static final String ADMIN_ALREADY_EXISTS = "Admin with email %s already exists";
    public static final String NO_PENDING_REGISTRATION = "No pending registration for email %s";
    public static final String TOKEN_EXPIRED = "Registration token has expired";
    public static final String KEYCLOAK_CREATION_FAILED = "Failed to create admin in Keycloak";
    public static final String FAILED_TO_CREATE_SUPERADMIN_IN_KEYCLOAK = "Failed to create superAdmin in Keycloak";
    public static final String SUPERADMIN_CONFIGURATION_PROPERTY_ARE_MISSING_IN_APPLICATION_PROPERTIES = "Superadmin configuration properties are missing in application.properties";
    public static final String ADMIN_NOT_FOUND = "Admin with email %s not found";
    public static final String ADMIN_INITIATION_SUCCESSFUL_VERIFICATION_EMAIL_SENT_TO_ = "Admin initiation successful. Verification email sent to %s";
    public static final String INVALID_EMAIL = "Invalid email address format";
    public static final String REGISTRATION_EMAIL_SENT_TO = "Registration email sent to %s";
    public static final String ADMIN_WITH_EMAIL_DELETED_SUCCESSFUL = "Admin with email %s deleted successfully";
    public static final String FIRST_NAME_REQUIRED = "First name is required";
    public static final String LAST_NAME_REQUIRED = "Last name is required";
    public static final String INVALID_PASSWORD = "Password must be at least 8 characters long, with at least one uppercase letter, one lowercase letter, one number, and one special character";
    public static final String EMAIL_SUBJECT = "Complete Your Admin Registration";
    public static final String EMAIL_BODY = """
            Click the link to complete your registration:\s
            http://localhost:8081/admin/register?token=%s&email=%s
            This link expires in 24 hours.
           \s""";
    public static final String PRODUCT_NOT_FOUND = "Product not found with ID: ";
    public static final String USER_IS_NOT_AUTHORIZED_TO_ACCESS_THIS_PRODUCT = "User is not authorized to access this product";
    public static final String USER_IS_NOT_AUTHORIZED_TO_UPDATE_THIS_PRODUCT = "User is not authorized to update this product";
    public static final String USER_IS_NOT_AUTHORIZED_TO_DELETE_THIS_PRODUCT = "User is not authorized to delete this product";
    public static final String ADMIN_REGISTRATION_SUCCESSFUL = "Admin registration successful";
    public static final String PRODUCT_UPDATED_SUCCESSFULLY = "Product updated successfully";
    public static final String PRODUCT_DELETED_SUCCESSFULLY = "Product deleted successfully";
    public static final String YOU_HAVE_NO_PRODUCTS_YET = "You have no product yet ";
    public static final String USER_IS_NOT_AUTHORIZED_TO_VIEW_THIS_PRODUCT = "";
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
    public static String DOT = ".";
    public static final String FAILED_TO_ASSIGN_ROLE_USER = "Failed to assign role {} to user {}: {}";
    public static final String EXCEPTION_DURING_USER_CREATION = "Exception during user creation";
    public static final String KEYCLOAK_USER_CREATION_FAILED = "Keycloak user creation failed: ";
    public static String ONLY_IMAGE_FILES_ARE_ALLOWED = "Only image files are allowed";
    public static String COULD_NOT_UPLOAD_FILE = "Could not upload file: ";
    public static final String PENDING_REGISTRATION_NOT_FOUND_FOR_UPDATE = "Pending registration not found for update";

    public static String USER_DATA_CANNOT_BE_NULL = "User data cannot be null";
    public static final String USER_ALREADY_EXIST_IN_KEYCLOAK = "User already exit in keycloak";
    public static String CUSTOMER_NOT_FOUND = "Customer not found ";
    public static String FAILED_TO_EMPTY_FILE = "Failed to store empty file";
    public static String A_SELLER_REGISTRATION_REQUEST_IS_ALREADY_PENDING = "A seller registration request is already pending for %s";
    public static String SELLER_REGISTRATION_REQUEST_SUBMITTED_SUCCESSFULLY = "Seller registration request submitted successfully for %s. It is now pending admin approval.";
    public static String FAILED_TO_UPLOAD_IMAGE = "Failed to upload image";
    public static final String SELLER_FORM_NOT_FOUND = "Seller form not found";
    public static final String HI = "Hi ";
    public static final String PRODUCT_OWNERSHIP_MISMATCH = "Product ownership mismatch";
    public static final String YOUR_FORM_TO_BECOME_A_SELLER_HAS_BEEN_REJECTED = ", your form to become a seller has been rejected";
    public static final String YOUR_FORM_TO_BECOME_A_SELLER_HAS_BEEN_SUCCESSFULLY_APPROVE = ", your form to become a seller has been successfully approved";



}