//package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "seller")
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class SellerEntity {
//
//    @Id
//    @UuidGenerator
//    @Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false)
//    private UUID id;
//
//    @Column(unique = true, nullable = false)
//    private String keycloakId;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    private String details;
//}
