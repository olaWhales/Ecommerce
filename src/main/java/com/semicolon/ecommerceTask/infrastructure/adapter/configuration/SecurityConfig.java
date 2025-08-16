package com.semicolon.ecommerceTask.infrastructure.adapter.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@Slf4j
@EnableMethodSecurity // This enables @PreAuthorize, @PostAuthorize, @Secured, etc.
public class SecurityConfig {

    private static final String KEYCLOAK_ISSUER_URI = "http://localhost:8080/realms/ecommerce-realm";
    private static final String CLIENT_ID = "ecommerce-app";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for stateless APIs
                .csrf(AbstractHttpConfigurer::disable)
                // Set session management to stateless as we're using JWTs
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configure authorization for HTTP requests
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints that do not require any authentication
                        .requestMatchers(
                                "/auth/login",
                                "/api/v1/customer/register",
                                "/admin/register",
                                "/admin/register?*",
                                "/admin/initiate" // Permit the admin initiation endpoint.
                        ).permitAll()
                        // Require authentication for all other endpoints
                        .anyRequest().authenticated()
                )
                // Configure OAuth2 Resource Server to handle JWTs
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                // Use the custom JWT decoder
                                .decoder(jwtDecoder())
                                // Use the custom converter to map JWT claims to roles
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                        )
                );
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        log.info("Initializing JWT Decoder with issuer URI: {}", KEYCLOAK_ISSUER_URI);

        // Build the URI for the Keycloak JWKS endpoint
        String jwkSetUri = KEYCLOAK_ISSUER_URI + "/protocol/openid-connect/certs";

        NimbusJwtDecoder decoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();

        // Validate the JWT's issuer to prevent tokens from other realms
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(KEYCLOAK_ISSUER_URI));

        return decoder;
    }

    /**
     * This converter is crucial for mapping Keycloak roles from the JWT to Spring Security authorities.
     * It extracts the `resource_access` claim and then finds the roles for your specific client ID.
     * The roles are prefixed with "ROLE_" as per Spring Security convention.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        log.info("Initializing JWT Authentication Converter");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            // Get the "resource_access" claim from the JWT
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null || !realmAccess.containsKey("roles")) {
                log.warn("No 'realm_access' claim found in JWT. User will have no roles.");
                return List.of();// No roles found
            }

            // Get the client's resource access roles
//            @SuppressWarnings("unchecked")
            @SuppressWarnings("unchecked")
//            Map<String, Object> clientAccess = (Map<String, Object>) realmAccess.get(CLIENT_ID);
//            Object rolesObj = clientAccess.get("roles");
//
//            List<String> roles = new ArrayList<>();
//            if (rolesObj instanceof List<?>) {
//                for (Object role : (List<?>) rolesObj) {
//                    if (role instanceof String) {
//                        roles.add((String) role);
//                    }
//                }
//            }
            List<String> roles = (List<String>) realmAccess.get("roles");
            // Convert roles to GrantedAuthority objects with the "ROLE_" prefix
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        });
        return converter;
    }
}