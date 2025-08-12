package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUploadDto {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private int inStockQuantity;

    @NotNull(message = "Seller ID is required")
    private UUID sellerId;

    @NotNull(message = "Image file is required")
    private MultipartFile imageFile;
}
