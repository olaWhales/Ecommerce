package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.AdminResponse;

import java.util.List;

public interface AdminUseCase {
    String initiateAdminCreation(String adminEmail);
    AdminDomainObject completeAdminRegistration(AdminDomainObject admin, UserDomainObject user, String rawPassword);
    String sendRegistrationEmail(String email, String token);
    String deleteAdmin(String email);
    AdminResponse updateAdmin(String email, String firstName, String lastName);
    List<AdminResponse> getAllAdmins();
}
