package com.semicolon.ecommerceTask.infrastructure.adapter.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "keycloak.admin")
public class KeycloakAdminProperties {
    private String serverUrl;
    private String realm;
    private String clientId;
    private String username;
    private String password;
}


// this configuration is inside the application.properties