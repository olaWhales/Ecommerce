package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegistrationFormDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Details are required")
    private String details;
}
