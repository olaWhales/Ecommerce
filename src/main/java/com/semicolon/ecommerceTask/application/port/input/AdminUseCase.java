package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.AdminResponse;

import java.util.List;

public interface AdminUseCase {
    String initiateAdminCreation(String adminEmail);
    // Method signature changed to use the DTO
    AdminResponse completeAdminRegistration(AdminRegistrationRequest requestDto);
    String sendRegistrationEmail(String email, String token);
    String deleteAdmin(String email);
    AdminResponse updateAdmin(String email, String firstName, String lastName);
    List<AdminResponse> getAllAdmins();
}
