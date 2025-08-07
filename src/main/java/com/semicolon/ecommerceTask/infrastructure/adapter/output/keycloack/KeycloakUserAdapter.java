package com.semicolon.ecommerceTask.infrastructure.adapter.output;

import com.semicolon.ecommerceTask.application.port.output.KeycloakUserPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.superAdmin.KeycloakUserDto;
import org.springframework.stereotype.Service;

@Service
public class KeycloakUserPortImpl implements KeycloakUserPort {

//    @Override
//    public void createUserWithRole(KeycloakUserDto userDto) {
//    }

    @Override
    public String createUser(UserDomainObject userDomainObject) {
        return "";
    }

    @Override
    public void deleteUser(String userDomainObjectId) {

    }

    @Override
    public void update(String userId, UserDomainObject userDomainObject) {

    }
}
