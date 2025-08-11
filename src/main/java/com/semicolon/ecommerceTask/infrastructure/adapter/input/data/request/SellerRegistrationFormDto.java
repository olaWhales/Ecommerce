package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SellerRegistrationFormDto {
    private String name;
    private String details;
}