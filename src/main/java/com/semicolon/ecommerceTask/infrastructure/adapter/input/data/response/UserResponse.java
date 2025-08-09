package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response;

import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
//    private String userId;
    private String name ;
    private String email ;
    private String keycloakId ;
    private String message ;

//    public UserRegistrationResponse(String message){
//        this.message = message ;
//    }
}
