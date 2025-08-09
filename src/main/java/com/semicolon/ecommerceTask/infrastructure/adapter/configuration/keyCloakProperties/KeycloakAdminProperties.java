package com.semicolon.ecommerceTask.infrastructure.adapter.configuration.keyCloakProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "keycloak.admin")
@Data
public class KeycloakAdminProperties {
    private String clientId;
    private String clientSecret;
    private String realm;
}


