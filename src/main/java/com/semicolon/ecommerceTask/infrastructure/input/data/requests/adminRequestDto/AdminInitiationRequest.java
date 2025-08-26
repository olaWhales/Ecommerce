package com.semicolon.ecommerceTask.infrastructure.input.data.requests.adminRequestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminInitiationRequest {

    @Email(message = "Invalid email")
    @NotBlank(message = "Admin email is required")
    private String adminEmail;



}
