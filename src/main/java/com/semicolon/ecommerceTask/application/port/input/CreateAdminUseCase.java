package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.AdminResponseDto;

import java.util.List;

public interface CreateAdminUseCase {
    String initiateAdminCreation(String adminEmail);
    AdminResponseDto completeAdminRegistration(String email, String firstName, String lastName, String password);
    String sendRegistrationEmail(String email, String token);
    String deleteAdmin(String email);
    AdminResponseDto updateAdmin(String email, String firstName, String lastName);
    List<AdminResponseDto> getAllAdmins();
}
