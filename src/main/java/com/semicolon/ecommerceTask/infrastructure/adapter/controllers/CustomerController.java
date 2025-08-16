package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.CreateCustomerUseCase;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.CustomerRegistrationDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.UserDomainObjectResponse;
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
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody CustomerRegistrationDto dto) {
        UserDomainObjectResponse response = createCustomerUseCase.registerCustomer(dto);
        String fullName = response.getFirstName() + " " + response.getLastName();
        String message = "Hi " + fullName + ", your registration has successfully registered";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
