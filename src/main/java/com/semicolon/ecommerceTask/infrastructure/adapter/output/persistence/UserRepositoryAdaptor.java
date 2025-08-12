package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRepositoryAdaptor implements UserPersistenceOutPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceMapper userPersistenceMapper; // <-- The new mapper is injected here

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
    public UserDomainObject save(UserDomainObject user) {
        // Map the Domain Object to a JPA Entity using the mapper
        UserEntity userEntity = userPersistenceMapper.toEntity(user);

        // Save the Entity to the database
        UserEntity savedEntity = jpaUserRepository.save(userEntity);

        // Map the saved Entity back to a Domain Object and return it
        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}