package com.semicolon.ecommerceTask.infrastructure.adapter.configurations.superAdminProperties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix = "superadmin")
@Getter
@Setter
public class SuperadminProperties {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
}
