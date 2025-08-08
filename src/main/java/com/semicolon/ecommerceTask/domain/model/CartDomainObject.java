//package com.semicolon.ecommerceTask.domain.model;
//
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.CartStatus;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class CartDomainObject {
//    @Id
//    private UUID id;
//
//    @NotNull
//    @ManyToOne
//    private UserEntity user;
//
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private CartStatus cartStatus;
//}
