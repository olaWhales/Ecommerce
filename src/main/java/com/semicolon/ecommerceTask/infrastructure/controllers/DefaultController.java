package com.semicolon.ecommerceTask.infrastructure.controllers;

import com.semicolon.ecommerceTask.application.port.input.CustomerUseCase;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.DefaultRegistrationRequest;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.UserDomainObjectResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.CustomerMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
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
    private final CustomerMapper customerMapper;


    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody DefaultRegistrationRequest dto) {
        UserDomainObjectResponse response = customerUseCase.defaultUserRegistration(dto);
        String fullName = response.getFirstName() + " " + response.getLastName();
        String message = MessageUtil.HI + fullName + MessageUtil.YOUR_REGISTRATION_HAS_SUCCESSFULLY_REGISTERED;
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

//    @PostMapping("/register")
//    public ResponseEntity<UserDomainObjectResponse> registerCustomer(
//            @Valid @RequestBody DefaultRegistrationRequest request) {
//
//        CustomerDomainObject customer = customerMapper.toDomainObject(request);
//        CustomerDomainObject registered = customerUseCase.registerCustomer(customer, request.getPassword());
//        return ResponseEntity.ok(customerMapper.toResponseDto(registered));
//    }
}
