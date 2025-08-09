//package com.semicolon.ecommerceTask.domain.service;
//
//import com.semicolon.ecommerceTask.application.port.output.KeycloakUserPort;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.superAdmin.KeycloakUserDto;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@AllArgsConstructor
//public class SuperAdminService {
//
//    private final KeycloakUserPort keycloakUserPort;
//
//    public void registerUser(UserEntity user) {
//        // Convert domain User to Keycloak DTO
//        KeycloakUserDto dto = new KeycloakUserDto(
//                user.getName(),
//                user.getEmail(),
//                user.getPassword(),
//                user.getKeycloakId()
//        );
//
////        // Delegate to Keycloak adapter
////        keycloakUserPort.createUserWithRole(dto);
////
////        // Optionally persist user in your own DB
////        // userRepository.save(user);
//    }
//}
