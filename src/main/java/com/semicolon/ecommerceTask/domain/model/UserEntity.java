//package com.semicolon.ecommerceTask.domain.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import java.util.UUID;
//
//@Entity
//@Table(name = "user")
//@Getter
//@Setter
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//    private String keycloakId;
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String password;
//    private String role; // Corresponds to the 'role' field in your schema
//}