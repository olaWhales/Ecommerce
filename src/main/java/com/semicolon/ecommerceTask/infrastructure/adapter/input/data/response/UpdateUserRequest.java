package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdateUserRequest {
    private String username;
    private String email;
    private List<UserRole> roles;
}