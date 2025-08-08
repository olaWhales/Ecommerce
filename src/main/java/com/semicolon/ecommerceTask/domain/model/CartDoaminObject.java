//package com.semicolon.ecommerceTask.domain.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//import java.util.UUID;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class CartDoaminObject {
//    @Id
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
