package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRepositoryAdaptor implements UserPersistenceOutPort {
    private final JpaUserRepository jpaUserRepository;

    @Override
    public void saveLocalUser(String keycloakId, UserDomainObject userDomainObject) {
        UserEntity userEntity = UserEntity.builder()
            .keycloakId(keycloakId) // Set Keycloak ID
            .firstName(userDomainObject.getFirstName())
            .lastName(userDomainObject.getLastName())
            .email(userDomainObject.getEmail())
            .password(userDomainObject.getPassword())
            .roles(userDomainObject.getRoles())
            .build();
        jpaUserRepository.save(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}