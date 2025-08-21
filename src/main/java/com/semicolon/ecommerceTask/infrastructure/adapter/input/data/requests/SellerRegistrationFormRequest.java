package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegistrationFormRequest {

    @NotBlank(message = "Name is required")
    private String businessName;

    @NotBlank(message = "Details are required")
    private String details;

    private String userId;



}


