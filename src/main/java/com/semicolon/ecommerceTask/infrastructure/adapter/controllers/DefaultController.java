package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.CustomerUseCase;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.DefaultRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserDomainObjectResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class DefaultController {

    private final CustomerUseCase customerUseCase;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody DefaultRegistrationRequest dto) {
        UserDomainObjectResponse response = customerUseCase.registerCustomer(dto);
        String fullName = response.getFirstName() + " " + response.getLastName();
        String message = MessageUtil.HI + fullName + MessageUtil.YOUR_REGISTRATION_HAS_SUCCESSFULLY_REGISTERED;
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
