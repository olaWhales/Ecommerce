package com.semicolon.ecommerceTask.domain.model;

import com.semicolon.ecommerceTask.application.port.input.ManageUserUsecase;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.UpdateUserRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserResponse;

public class ManageUserService implements ManageUserUsecase {
    @Override
    public UserResponse createUser(UserDomainObject userDomainObject) {
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
