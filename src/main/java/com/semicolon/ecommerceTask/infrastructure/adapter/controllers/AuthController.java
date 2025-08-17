package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.LoginUseCase;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.LoginRequestDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.LoginResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto request) {
        String token = loginUseCase.login(request.getUsername(), request.getPassword());
        LoginResponse response = LoginResponse.builder()
            .message(MessageUtil.LOGIN_SUCCESS + request.getUsername() + "!")
            .token(token)
            .build();
        return ResponseEntity.ok(response);
    }
}

