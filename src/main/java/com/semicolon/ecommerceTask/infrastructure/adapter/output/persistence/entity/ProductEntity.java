//package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;
//
//import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.DecimalMin;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class ProductEntity {
//    @Id
//    @GeneratedValue
//    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
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
