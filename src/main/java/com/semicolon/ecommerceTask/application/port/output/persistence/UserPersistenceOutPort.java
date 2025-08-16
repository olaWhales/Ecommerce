package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;

import java.util.Optional;

public interface UserPersistenceOutPort {

    void saveLocalUser(String keycloakId, UserDomainObject userDomainObject);
    UserDomainObject saveUser(UserDomainObject userDomainObject);
    UserDomainObject save(UserDomainObject user);
//    void deleteLocalUser(String keycloakId);

//    UserDomainObject saveUser(UserDomainObject userDomainObject);

    boolean existsByEmail(String mail);
    Optional<UserEntity> findByEmail(String id);
    UserDomainObject findById(String userId);
    UserDomainObject findUserByEmail(String email);// <-- Add this method




////    boolean existsByEmail(String email);
//    AdminDomainObject saveAdmin(AdminDomainObject admin);
//    void deleteAdmin(String email);
//    Optional<AdminDomainObject> findByEmail(String email);
//    List<AdminDomainObject> findAllAdmins();
//    // New and updated methods for pending registrations
//    void createPendingRegistration(String email, String token, LocalDateTime expiration);
//    Optional<PendingRegistrationDomainObject> findPendingRegistrationByEmail(String email);
//    void updatePendingRegistration(String email, String token, LocalDateTime expiration);
//    void deletePendingRegistration(String email); // Re-added this method

}


