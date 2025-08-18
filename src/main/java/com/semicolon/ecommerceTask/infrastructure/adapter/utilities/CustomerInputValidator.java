package com.semicolon.ecommerceTask.infrastructure.adapter.utilities;

import com.semicolon.ecommerceTask.domain.exception.NameNotFoundException;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.DefaultRegistrationRequest;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CustomerInputValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*?])(?=\\S+$).{8,}$");

    public void validate(DefaultRegistrationRequest dto) {
        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            throw new NameNotFoundException(MessageUtil.FIRST_NAME_REQUIRED);
        }
        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            throw new NameNotFoundException(MessageUtil.LAST_NAME_REQUIRED);
        }
        if (dto.getEmail() == null || !EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            throw new NameNotFoundException(MessageUtil.INVALID_EMAIL);
        }
        if (dto.getPassword() == null || !PASSWORD_PATTERN.matcher(dto.getPassword()).matches()) {
            throw new NameNotFoundException(MessageUtil.INVALID_PASSWORD);
        }
    }
}
