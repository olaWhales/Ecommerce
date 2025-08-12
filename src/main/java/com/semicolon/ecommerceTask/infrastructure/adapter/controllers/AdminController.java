package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.ApproveSellerUseCase;
import com.semicolon.ecommerceTask.application.port.input.CreateAdminUseCase;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminInitiationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.adminRequestDto.AdminRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.ResponseMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CreateAdminUseCase createAdminUseCase;
    private final ApproveSellerUseCase approveSellerUseCase;


    @PostMapping("/initiate")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String initiateAdminCreation(@RequestBody AdminInitiationDto dto) {
        createAdminUseCase.initiateAdminCreation(dto.getAdminEmail());
        return "Admin registration initiated. An email has been sent to " + dto.getAdminEmail();
    }

    @GetMapping("/register")
    public ResponseMessageDto showRegistrationForm(@RequestParam String token, @RequestParam String email) {
        return new ResponseMessageDto(
                "Proceed to complete your registration.",
                token,
                email);
    }

    @PostMapping("/register")
    public String completeAdminRegistration(@RequestBody AdminRegistrationDto dto) {
        createAdminUseCase.completeAdminRegistration(dto.getEmail(), dto.getFirstName(), dto.getLastName(), dto.getPassword());
        return "Registration complete. You can now log in.";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String deleteAdmin(@RequestParam String email) {
        createAdminUseCase.deleteAdmin(email);
        return "Admin with email " + email + " deleted.";
    }


//    @PostMapping("/actions-seller-registration/{registrationId}")
//    @PreAuthorize("hasRole('ADMIN'/SUPERADMIN)")
//    public ResponseEntity<UserDomainObject> handleSellerRegistration(
//            @PathVariable UUID registrationId,
//            @RequestBody ApprovalRequest request) {
//
//        UserDomainObject result = approveSellerUseCase.approveSeller(registrationId, request);
//
//        if (result != null) {
//            return ResponseEntity.ok(result);
//        } else {
//            // Return an empty body with a successful status code for rejection
//            return ResponseEntity.noContent().build();
//        }


//    @PutMapping("/update")
//    @PreAuthorize("hasRole('SUPERADMIN')")
//    public String updateAdmin(@RequestBody AdminUpdateDto dto) {
//        createAdminUseCase.updateAdmin(dto.getEmail(), dto.getFirstName(), dto.getLastName());
//        return "Admin with email " + dto.getEmail() + " updated.";
//    }
//
//    @GetMapping("/all")
//    @PreAuthorize("hasRole('SUPERADMIN')")
//    public List<AdminResponseDto> getAllAdmins() {
//        return createAdminUseCase.getAllAdmins().stream()
//                .map(admin -> AdminResponseDto.builder()
//                        .id(admin.getId())
//                        .email(admin.getEmail())
//                        .firstName(admin.getFirstName())
//                        .lastName(admin.getLastName())
//                        .roles(admin.getRoles())
//                        .build())
//                .collect(Collectors.toList());
//    }
//    }
}