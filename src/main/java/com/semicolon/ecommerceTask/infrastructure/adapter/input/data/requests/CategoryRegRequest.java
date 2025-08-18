package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRegRequest {
    @NotBlank(message = "Category name is required")
    private String name;
}