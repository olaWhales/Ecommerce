package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entities.UserEntity;

import java.util.Optional;

public interface UserPersistenceOutPort {

    void saveLocalUser(String keycloakId, UserDomainObject userDomainObject);
    UserDomainObject saveUser(UserDomainObject userDomainObject);
    UserDomainObject save(UserDomainObject user);
    boolean existsByEmail(String mail);
    Optional<UserEntity> findByEmail(String id);
    UserDomainObject findById(String userId);
    UserDomainObject findUserByEmail(String email);// <-- Add this method

}


