package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {

    private String email;
    private List<UserRole> role;

}
