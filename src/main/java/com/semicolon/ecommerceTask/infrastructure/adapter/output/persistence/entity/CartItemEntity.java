//package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;
//
//import com.semicolon.ecommerceTask.domain.model.CartDomainObject;
//import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.UUID;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class CartItemEntity {
//    @Id
//    @GeneratedValue
//    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
//    private UUID id;
//
//    @NotNull
//    @ManyToOne
//    private CartDomainObject cartEntity;
//
//    @NotNull
//    @ManyToOne
//    private ProductDomainObject productEntity;
//
//    @Min(1)
//    private int quantity;
//}
