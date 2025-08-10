package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;


@Component
public class UserAdapterMapper {

    public UserEntity toEntity(UserDomainObject domainObject) {
        return UserEntity.builder()
            .firstName(domainObject.getFirstName())
            .lastName(domainObject.getLastName())
            .email(domainObject.getEmail())
            .password(domainObject.getPassword())
            .build();
    }

    public UserDomainObject toDomain(UserEntity entity) {
        return UserDomainObject.builder()
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .email(entity.getEmail())
            .password(entity.getPassword())
            .roles(entity.getRoles() != null ? entity.getRoles() : null)
            .keycloakId(entity.getKeycloakId())
            .build();
    }
}