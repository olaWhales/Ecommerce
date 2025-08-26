package com.semicolon.ecommerceTask;

import com.semicolon.ecommerceTask.application.port.output.AuthOutPort;
import com.semicolon.ecommerceTask.domain.model.User;
import com.semicolon.ecommerceTask.domain.exception.AuthenticationException;
import com.semicolon.ecommerceTask.domain.services.LoginService;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private AuthOutPort authOutPort;

    @InjectMocks
    private LoginService loginService;

    private static final String VALID_USERNAME = "superadmin";
    private static final String VALID_PASSWORD = "Superadmin123!";
    private static final String VALID_TOKEN = "mock-jwt-token";
    private static final User VALID_USER = new User(VALID_USERNAME, "superadmin@example.com", "SUPERADMIN");

    @BeforeEach
    void setUp() {
        reset(authOutPort);
    }

    @Test
    void login_SuccessfulAuthentication_ReturnsToken() {
        when(authOutPort.authenticate(VALID_USERNAME, VALID_PASSWORD)).thenReturn(VALID_TOKEN);
        when(authOutPort.getUserDetails(VALID_TOKEN)).thenReturn(VALID_USER);

        String result = loginService.login(VALID_USERNAME, VALID_PASSWORD);

        assertEquals(VALID_TOKEN, result);
        verify(authOutPort).authenticate(VALID_USERNAME, VALID_PASSWORD);
        verify(authOutPort).getUserDetails(VALID_TOKEN);
        verifyNoMoreInteractions(authOutPort);
    }

    @Test
    void login_NullUsername_ThrowsAuthenticationException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                loginService.login(null, VALID_PASSWORD));
        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(authOutPort);
    }

    @Test
    void login_EmptyUsername_ThrowsAuthenticationException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                loginService.login("", VALID_PASSWORD));
        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(authOutPort);
    }

    @Test
    void login_NullPassword_ThrowsAuthenticationException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                loginService.login(VALID_USERNAME, null));
        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(authOutPort);
    }

    @Test
    void login_EmptyPassword_ThrowsAuthenticationException() {
        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                loginService.login(VALID_USERNAME, ""));
        assertEquals(MessageUtil.INVALID_EMAIL, exception.getMessage());
        verifyNoInteractions(authOutPort);
    }

    @Test
    void login_InvalidCredentials_ReturnsNullToken_ThrowsAuthenticationException() {
        when(authOutPort.authenticate(VALID_USERNAME, VALID_PASSWORD)).thenReturn(null);

        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                loginService.login(VALID_USERNAME, VALID_PASSWORD));
        assertEquals(MessageUtil.INVALID_CREDENTIALS, exception.getMessage());
        verify(authOutPort).authenticate(VALID_USERNAME, VALID_PASSWORD);
        verifyNoMoreInteractions(authOutPort);
    }

    @Test
    void login_UserDetailsNull_ThrowsAuthenticationException() {
        when(authOutPort.authenticate(VALID_USERNAME, VALID_PASSWORD)).thenReturn(VALID_TOKEN);
        when(authOutPort.getUserDetails(VALID_TOKEN)).thenReturn(null);

        AuthenticationException exception = assertThrows(AuthenticationException.class, () ->
                loginService.login(VALID_USERNAME, VALID_PASSWORD));
        assertEquals(MessageUtil.USER_NOT_FOUND, exception.getMessage());
        verify(authOutPort).authenticate(VALID_USERNAME, VALID_PASSWORD);
        verify(authOutPort).getUserDetails(VALID_TOKEN);
        verifyNoMoreInteractions(authOutPort);
    }
}