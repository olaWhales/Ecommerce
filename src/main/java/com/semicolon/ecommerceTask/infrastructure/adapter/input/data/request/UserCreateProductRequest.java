//package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;
//
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//import java.math.BigDecimal;
//
//public class UserCreateProductRequest {
//
//    @NotBlank
//    private String name;
//
//    @NotNull
//    @DecimalMin("0.0")
//    private BigDecimal price;
//
//    @Min(0)
//    private int stockQuantity;
//}
