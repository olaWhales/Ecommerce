package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.ManageUserUsecase;

import com.semicolon.ecommerceTask.application.port.output.KeycloakUserPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;

import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.UpdateUserRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ManageUserService implements ManageUserUsecase {
    private final KeycloakUserPort keycloakUserPort ;
    private final UserPersistenceOutPort userPersistenceOutPort;

    @Override
    public UserResponse createUser(UserDomainObject userDomainObject) {
        userPersistenceOutPort.saveUser(userDomainObject);
        return null;
    }

    @Override
    public void deleteUser(String userId) {
    }

    @Override
    public UserResponse updateUser(String userId, UpdateUserRequest updateUserRequest) {
        return null;
    }
}
