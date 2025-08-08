package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.UserAdapterMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRepositoryAdaptor implements UserPersistenceOutPort {
    private final JpaUserRepository jpaUserRepository ;
    private final UserAdapterMapper userAdapterMapper;

    @Override
    public void saveLocalUser(String keycloakId, UserDomainObject userDomainObject) {
        UserEntity userEntity = UserEntity.builder()
                .name(userDomainObject.getName())
                .email(userDomainObject.getEmail())
                .password(userDomainObject.getPassword())
                .roles(userDomainObject.getRoles())
                .build();
        jpaUserRepository.save(userEntity);
    }

    @Override
    public void deleteLocalUser(String keycloakId) {
        jpaUserRepository.findByKeycloakId(keycloakId);
    }

    @Override
    public UserDomainObject saveUser(UserDomainObject userDomainObject) {
        if (userDomainObject == null) {throw new IllegalArgumentException("cannot be empty");}
        UserEntity usersEntity = userAdapterMapper.mapToUserEntity(userDomainObject);
        jpaUserRepository.save(usersEntity);
        return userAdapterMapper.mapToUserDomainObject(usersEntity);
    }

    @Override
    public boolean existsByEmail(String mail) {
        return jpaUserRepository.existsByEmail(mail);
    }
}

