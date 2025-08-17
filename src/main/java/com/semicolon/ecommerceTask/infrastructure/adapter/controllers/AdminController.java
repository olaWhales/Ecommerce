package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.AdminActionOnSellerUseCase;
import com.semicolon.ecommerceTask.application.port.input.AdminUseCase;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminInitiationRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.ResponseMessageDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminUseCase adminUseCase;
    private final AdminActionOnSellerUseCase adminActionOnSellerUseCase;

    @PostMapping("/initiate")
//    @PreAuthorize("hasRole('SUPERADMIN')")
    public String initiateAdminCreation(@RequestBody AdminInitiationRequest dto) {
        adminUseCase.initiateAdminCreation(dto.getAdminEmail());
        return MessageUtil.ADMIN_REGISTRATION_INITIATED_AN_EMAIL_HAS_BEEN_SENT_TO + dto.getAdminEmail();
    }

    @GetMapping("/register")
    public ResponseMessageDto showRegistrationForm(@RequestParam String token, @RequestParam String email) {
        return new ResponseMessageDto(MessageUtil.PROCEED_TO_COMPLETE_YOUR_REGISTRATION);
    }

    @PostMapping("/register")
    public String completeAdminRegistration(@RequestBody AdminRegistrationRequest dto) {
        adminUseCase.completeAdminRegistration(dto);
        return MessageUtil.REGISTRATION_COMPLETED_YOU_CAN_NOW_LOGIN;
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String deleteAdmin(@RequestParam String email) {
        adminUseCase.deleteAdmin(email);
        return MessageUtil.ADMIN_WITH_EMAIL + email + MessageUtil.DELETED;
    }

}