//package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.UuidGenerator;
//
//import java.util.UUID;
//
//@Data
//@Entity
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class SellerEntity {
//    @Id
//    @UuidGenerator
//    @Column(columnDefinition = "uuid", updatable = false, nullable = false, unique = true)
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