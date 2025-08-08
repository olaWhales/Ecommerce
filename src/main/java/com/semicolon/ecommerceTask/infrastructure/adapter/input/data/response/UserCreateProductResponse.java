//package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;
//
//import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//
//import java.math.BigDecimal;
//
//public class UserCreateProductResponse {
//
//    @NotNull
//    private CategoryDomainObject categoryEntity;
//
//    @NotNull
//    private UserEntity seller;
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
//
//}
