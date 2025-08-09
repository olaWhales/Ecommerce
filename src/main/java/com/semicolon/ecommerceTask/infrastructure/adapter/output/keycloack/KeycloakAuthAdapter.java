package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.AuthOutPort;
import com.semicolon.ecommerceTask.domain.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class KeycloakAuthAdapter implements AuthOutPort {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String authenticate(String username, String password) {
        String url = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            String response = restTemplate.postForObject(url, request, String.class);
            Map<String, Object> jsonResponse = objectMapper.readValue(response, Map.class);
            return (String) jsonResponse.get("access_token");
        } catch (Exception e) {
            throw new IllegalArgumentException(MessageUtil.KEYCLOAK_CREATION_FAILED);
        }
    }

    @Override
    public User getUserDetails(String token) {
        String url = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            String response = restTemplate.postForObject(url, request, String.class);
            Map<String, Object> userInfo = objectMapper.readValue(response, Map.class);
            String username = (String) userInfo.get("preferred_username");
            String email = (String) userInfo.get("email");
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) userInfo.get("realm_roles"); // Adjust based on Keycloak config
            return new User(username, email, roles.isEmpty() ? "USER" : roles.get(0));
        } catch (Exception e) {
            throw new IllegalArgumentException(MessageUtil.ADMIN_NOT_FOUND);
        }
    }
}