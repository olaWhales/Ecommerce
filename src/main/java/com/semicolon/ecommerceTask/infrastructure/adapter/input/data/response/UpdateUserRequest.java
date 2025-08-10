package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String username;
    private String email;
    private List<UserRole> roles;
}