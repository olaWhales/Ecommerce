package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreationDto {
    @NotBlank(message = "Category name is required")
    private String name;
}