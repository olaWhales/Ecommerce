package com.semicolon.ecommerceTask.infrastructure.adapter.output.keycloack;

import com.semicolon.ecommerceTask.application.port.output.AuthOutPort;
import com.semicolon.ecommerceTask.domain.model.User;
import com.semicolon.ecommerceTask.domain.exception.AuthenticationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil.*;

@Component
public class KeycloakAuthAdapter implements AuthOutPort {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakAuthAdapter.class);

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
        String url = keycloakUrl + REALMS + realm + PROTOCOL_OPENID_CONNECT_TOKEN;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", username);
        body.add("password", password);
        body.add("scope", "openid profile email");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
//            logger.debug("Attempting to authenticate user: {} with URL: {} and body: {}", username, url, body);
            String response = restTemplate.postForObject(url, request, String.class);
//            logger.debug("Authentication response: {}", response);
            Map<String, Object> jsonResponse = objectMapper.readValue(response, Map.class);
            return (String) jsonResponse.get(ACCESS_TOKEN);
        } catch (Exception e) {
//            logger.error("Authentication failed for user: {} - Error: {}", username, e.getMessage(), e);
            throw new IllegalArgumentException(KEYCLOAK_CREATION_FAILED + e.getMessage());
        }
    }

    @Override
    public User getUserDetails(String token) {
        String url = keycloakUrl + REALMS + realm + PROTOCOL_OPENID_CONNECT_USER_INFO;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    String.class
            );
            String responseBody = response.getBody();
//            logger.debug("Userinfo response: {}", responseBody);

            Map<String, Object> userInfo = objectMapper.readValue(responseBody, Map.class);
            String username = (String) userInfo.get(PREFERRED_USERNAME);
            String email = (String) userInfo.get(EMAIL);

            @SuppressWarnings("unchecked")
            Map<String, Object> realmAccess = (Map<String, Object>) userInfo.get(REALM_ACCESS);
            List<String> roles = (realmAccess != null) ? (List<String>) realmAccess.get("roles") : List.of(USER);
            String role = (!roles.isEmpty()) ? roles.get(0) : "USER";

            return new User(username, email, role);
        } catch (Exception e) {
            logger.error(FAILED_TO_RETRIEVE_USER_DETAILS, e.getMessage(), e);
            throw new IllegalArgumentException(MessageUtil.ADMIN_NOT_FOUND);
        }
    }
}