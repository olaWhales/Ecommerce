package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;

public interface UserPersistenceOutPortPort {
    void saveUser(UserDomainObject userDomainObject);
}
