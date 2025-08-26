package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.LoginUseCase;
import com.semicolon.ecommerceTask.application.port.output.AuthOutPort;
import com.semicolon.ecommerceTask.domain.exception.AuthenticationException;
import com.semicolon.ecommerceTask.domain.model.User;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {
    private final AuthOutPort authOutPort;

    @Override
    public String login(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            throw new AuthenticationException(MessageUtil.INVALID_EMAIL);}
        String token = authOutPort.authenticate(username, password);
        if (token == null) {throw new AuthenticationException(MessageUtil.INVALID_CREDENTIALS);}
        User user = authOutPort.getUserDetails(token);
        if (user == null) {throw new AuthenticationException(MessageUtil.USER_NOT_FOUND);}
        return token;
    }
}

