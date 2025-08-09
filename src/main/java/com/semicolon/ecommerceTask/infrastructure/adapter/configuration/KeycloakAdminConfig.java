//package com.semicolon.ecommerceTask.infrastructure.adapter.configuration;
//
//import com.semicolon.ecommerceTask.infrastructure.adapter.configuration.keyCloakProperties.KeycloakAdminProperties;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class KeycloakAdminConfig {
//    private static final Logger logger = LoggerFactory.getLogger(KeycloakAdminConfig.class);
//
//    private final KeycloakAdminProperties properties;
//
//    public KeycloakAdminConfig(KeycloakAdminProperties properties) {
//        this.properties = properties;
//    }
//
//    @Bean
//    public Keycloak keycloak() {
//        logger.info("Initializing Keycloak Admin Client with serverUrl={}, realm={}, clientId={}, clientSecret={}",
//                properties.getServerUrl(), properties.getRealm(), properties.getClientId(), properties.getClientSecret());
//
//        KeycloakBuilder builder = KeycloakBuilder.builder()
//                .serverUrl(properties.getServerUrl())
//                .realm(properties.getRealm()) // Authenticate in ecommerce-realm
//                .clientId(properties.getClientId())
//                .clientSecret(properties.getClientSecret()) // Use client secret for ecommerce-app
//                .grantType("client_credentials"); // Use client credentials grant type
//
//        Keycloak keycloak = builder.build();
//
//        // Test authentication
//        try {
//            keycloak.tokenManager().getAccessToken();
//            logger.info("Keycloak Admin Client authenticated successfully");
//        } catch (Exception e) {
//            logger.error("Failed to authenticate Keycloak Admin Client", e);
//            throw new RuntimeException("Keycloak Admin Client authentication failed: " + e.getMessage());
//        }
//
//        return keycloak;
//    }
//}