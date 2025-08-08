//package com.semicolon.ecommerceTask.domain.model;
//
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class ProductDomainObject {
//    @Id
//    private UUID id;
//
//    @NotNull
//    @ManyToOne
//    private CategoryDomainObject categoryEntity;
//
//    @NotNull
//    @ManyToOne
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
//}
//
