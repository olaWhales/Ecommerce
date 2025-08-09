package com.semicolon.ecommerceTask.infrastructure.adapter.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()
//                .requestMatchers("/api/public").permitAll()

                .requestMatchers("/superAdmin/**").hasRole("SUPERADMIN")
                .requestMatchers("/admin/initiate").hasRole("SUPERADMIN")
                .requestMatchers("/admin/delete").hasRole("SUPERADMIN")
                .requestMatchers("/admin/update").hasRole("SUPERADMIN")
                .requestMatchers("/admin/all").hasRole("SUPERADMIN")
                .requestMatchers("/admin/register").permitAll()


                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
            );
        return http.build();
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = "http://localhost:8080/realms/ecommerce-realm";
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withIssuerLocation(issuerUri).build();
        decoder.setJwtValidator(JwtValidators.createDefaultWithIssuer(issuerUri));
        System.out.println("JwtDecoder initialized with issuer: " + issuerUri);
        return decoder;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        System.out.println("JwtAuthenticationConverter initialized");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess == null || !resourceAccess.containsKey("ecommerce-app")) {return List.of();}

            @SuppressWarnings("unchecked")
            Map<String, Object> ecommerceAppAccess = (Map<String, Object>) resourceAccess.get("ecommerce-app");
            Object rolesObj = ecommerceAppAccess.get("roles");

            List<String> roles = new ArrayList<>();
            if (rolesObj instanceof List<?>) {
                for (Object role : (List<?>) rolesObj) {
                    if (role instanceof String) {
                        roles.add((String) role);
                    }
                }
            }

        return roles.stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
            .collect(Collectors.toList());
        });
        return converter;
    }
}