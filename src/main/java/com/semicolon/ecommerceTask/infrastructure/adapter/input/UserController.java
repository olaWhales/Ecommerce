//package com.semicolon.ecommerceTask.infrastructure.adapter.input;
//
//import com.semicolon.ecommerceTask.application.port.input.CreateUserUsecase;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.UserRegistrationRequest;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserRegistrationResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/users")
//@AllArgsConstructor
//public class UserController {
//    private final CreateUserUsecase createUserUseCase;
//
//    @PostMapping("/register")
//    public UserRegistrationResponse register(@RequestBody UserRegistrationRequest request) {
//        createUserUseCase.registerUser(request.toCommand());
//        return new UserRegistrationResponse("User registered successfully");
//    }
//}
//
//
