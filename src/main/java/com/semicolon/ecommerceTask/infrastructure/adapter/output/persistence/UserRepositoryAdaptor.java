package com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence;

import ch.qos.logback.core.util.StringUtil;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.repository.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserRepositoryAdaptor implements UserPersistenceOutPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public void saveLocalUser(String keycloakId, UserDomainObject userDomainObject) {
        UserEntity userEntity = UserEntity.builder()
                .firstName(userDomainObject.getFirstName())
                .lastName(userDomainObject.getLastName())
                .email(userDomainObject.getEmail())
                .roles(userDomainObject.getRoles())
                .build();
        jpaUserRepository.save(userEntity);
    }

    @Override
    public UserDomainObject saveUser(UserDomainObject userDomainObject) {
        UserEntity userEntity = UserEntity.builder()
                .id(userDomainObject.getId())
                .firstName(userDomainObject.getFirstName())
                .lastName(userDomainObject.getLastName())
                .email(userDomainObject.getEmail())
                .roles(userDomainObject.getRoles())
                .build();
      return userPersistenceMapper.toDomain(jpaUserRepository.save(userEntity));
    }

    @Override
    public UserDomainObject save(UserDomainObject user) {
        UserEntity userEntity = userPersistenceMapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(userEntity);
        return userPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);

    }

    @Override
    public UserDomainObject findById(String userId) {
        return userPersistenceMapper.toDomain(jpaUserRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not Found")));
    }
//
//    @Override
//    public Optional<UserDomainObject> findById(UUID id) {
//        return jpaUserRepository.findById(id)
//                .map(userPersistenceMapper::toDomain);
//    }

    @Override
    public UserDomainObject findUserByEmail(String email) {
        if(StringUtil.isNullOrEmpty(email)){
            throw new IllegalArgumentException("invalid email");
        }
        UserEntity userEntity = jpaUserRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("user not found"));
        return userPersistenceMapper.toDomain(userEntity);
    }

}