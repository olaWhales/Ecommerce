package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.manageProductDto;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.CategoryRegRequest;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private int inStockQuantity;

//    @NotBlank(message = "Image URL is required")
//    private String imageUrl;


    @NotNull(message = "Category is required")
    private CategoryRegRequest category;
}
