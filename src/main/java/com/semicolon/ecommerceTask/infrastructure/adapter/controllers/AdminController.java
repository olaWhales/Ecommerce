package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.CreateAdminUseCase;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.AdminInitiationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.AdminRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.AdminUpdateDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.AdminResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CreateAdminUseCase createAdminUseCase;

    @PostMapping("/initiate")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String initiateAdminCreation(@RequestBody AdminInitiationDto dto) {
        createAdminUseCase.initiateAdminCreation(dto.getAdminEmail());
        return "Admin registration initiated. An email has been sent to " + dto.getAdminEmail();
    }

    @GetMapping("/register")
    public String showRegistrationForm(@RequestParam String token, @RequestParam String email) {
        return """
                <html>
                    <body>
                        <h2>Complete Your Admin Registration</h2>
                        <form action="/admin/register" method="post">
                            <input type="hidden" name="token" value="%s">
                            <input type="hidden" name="email" value="%s">
                            <label>First Name:</label><input type="text" name="firstName" required><br>
                            <label>Last Name:</label><input type="text" name="lastName" required><br>
                            <label>Password:</label><input type="password" name="password" required><br>
                            <button type="submit">Register</button>
                        </form>
                    </body>
                </html>
                """.formatted(token, email);
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

    @PutMapping("/update")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public String updateAdmin(@RequestBody AdminUpdateDto dto) {
        createAdminUseCase.updateAdmin(dto.getEmail(), dto.getFirstName(), dto.getLastName());
        return "Admin with email " + dto.getEmail() + " updated.";
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('SUPERADMIN')")
    public List<AdminResponseDto> getAllAdmins() {
        return createAdminUseCase.getAllAdmins().stream()
                .map(admin -> AdminResponseDto.builder()
                        .id(admin.getId())
                        .email(admin.getEmail())
                        .firstName(admin.getFirstName())
                        .lastName(admin.getLastName())
                        .roles(admin.getRoles())
                        .build())
                .collect(Collectors.toList());
    }
}