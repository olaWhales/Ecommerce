package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;

public interface UserPersistenceOutPort {

    void saveLocalUser(String keycloakId, UserDomainObject userDomainObject);
    UserDomainObject save(UserDomainObject user);
//    void deleteLocalUser(String keycloakId);

//    UserDomainObject saveUser(UserDomainObject userDomainObject);

    boolean existsByEmail(String mail);

}


