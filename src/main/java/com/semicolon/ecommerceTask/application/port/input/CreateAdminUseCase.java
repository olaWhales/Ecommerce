package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;

import java.util.List;

public interface CreateAdminUseCase {
    void initiateAdminCreation(String adminEmail);
    void completeAdminRegistration(String email, String firstName, String lastName, String password);
    void sendRegistrationEmail(String email, String token);
    void deleteAdmin(String email);
    void updateAdmin(String email, String firstName, String lastName);
    List<AdminDomainObject> getAllAdmins();
}