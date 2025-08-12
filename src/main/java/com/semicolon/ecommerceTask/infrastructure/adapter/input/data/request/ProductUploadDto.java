package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductUploadDto {
    private String name;
    private String description;
    private BigDecimal price;
    private int inStockQuantity;
    private UUID sellerId;
    private MultipartFile imageFile;
}