//package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;
//
//import com.semicolon.ecommerceTask.domain.model.AdminDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.AdminEntity;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AdminEntityMapper {
//
//    public AdminEntity toEntity(AdminDomainObject domainObject) {
//        return AdminEntity.builder()
//                .id(domainObject.getId())
//                .keycloakId(domainObject.getKeycloakId())
//                .email(domainObject.getEmail())
//                .firstName(domainObject.getFirstName())
//                .lastName(domainObject.getLastName())
////                .password(domainObject.getPassword())
//                .roles(domainObject.getRoles())
//                .build();
//    }
//
//    public AdminDomainObject toDomainObject(AdminEntity entity) {
//        return AdminDomainObject.builder()
//                .id(entity.getId())
//                .keycloakId(entity.getKeycloakId())
//                .email(entity.getEmail())
//                .firstName(entity.getFirstName())
//                .lastName(entity.getLastName())
////                .password(entity.getPassword())
//                .roles(entity.getRoles())
//                .build();
//    }
//}