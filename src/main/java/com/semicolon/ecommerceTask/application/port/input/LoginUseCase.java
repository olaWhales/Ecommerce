package com.semicolon.ecommerceTask.application.port.input;

import javax.naming.AuthenticationException;

public interface LoginUseCase {
    String login(String username, String password);
}
