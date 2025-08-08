package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private String userId;
    private String username ;
    private String email ;
    private List<UserRole> roles ;
    private String message ;

//    public UserRegistrationResponse(String message){
//        this.message = message ;
//    }
}
