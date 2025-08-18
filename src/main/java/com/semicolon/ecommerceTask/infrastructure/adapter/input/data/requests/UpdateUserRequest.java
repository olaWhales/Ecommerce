//package com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request;
//
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.enumPackage.UserRole;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import lombok.*;
//
//import java.util.List;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class UpdateUserRequest {
//
//    @NotBlank(message = "Username is required")
//    private String username;
//
//    @Email(message = "Invalid email")
//    @NotBlank(message = "Email is required")
//    private String email;
//
//    @NotEmpty(message = "At least one role is required")
//    private List<UserRole> roles;
//}
