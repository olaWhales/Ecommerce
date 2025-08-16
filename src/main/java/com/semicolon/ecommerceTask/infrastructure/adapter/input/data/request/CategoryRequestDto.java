package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Allows Spring to instantiate
public class CategoryRequestDto {
    @NotBlank(message = "Category name is required")
    private String name;
}