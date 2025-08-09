package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;

public interface KeycloakUserPort {
//    void createUserWithRole(KeycloakUserDto userDto);
    String createUser(UserDomainObject userDomainObject);
//    void deleteUser(String userDomainObjectId);
//    void update(String userId, UserDomainObject userDomainObject);
}
