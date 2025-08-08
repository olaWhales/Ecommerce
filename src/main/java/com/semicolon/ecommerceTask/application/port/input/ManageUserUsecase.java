package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.UpdateUserRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserResponse;

public interface ManageUserUsecase {
    UserResponse createUser(UserDomainObject userDomainObject);
    void deleteUser(String userId);
    UserResponse updateUser(String userId, UpdateUserRequest updateUserRequest);
}
