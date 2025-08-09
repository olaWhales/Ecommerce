package com.semicolon.ecommerceTask.infrastructure.adapter.configuration.keyCloakProperties;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Bean
    public Keycloak keycloak(KeycloakAdminProperties properties) {
        if (properties.getRealm() == null || properties.getClientId() == null || properties.getClientSecret() == null) {
            throw new IllegalStateException("Keycloak properties (realm, clientId, clientSecret) must not be null");
        }

        return KeycloakBuilder.builder()
            .serverUrl("http://localhost:8080")
            .realm(properties.getRealm())
            .clientId(properties.getClientId())
            .clientSecret(properties.getClientSecret())
            .grantType("client_credentials")
            .build();
    }
}

