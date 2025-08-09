package com.semicolon.ecommerceTask.application.port.output;

import com.semicolon.ecommerceTask.domain.model.User;

public interface AuthOutPort {
    String authenticate(String username, String password);
    User getUserDetails(String token);
}

