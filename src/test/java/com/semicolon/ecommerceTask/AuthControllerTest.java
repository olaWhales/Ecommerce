//package com.semicolon.ecommerceTask;
//
//import com.semicolon.ecommerceTask.application.port.input.LoginUseCase;
//import com.semicolon.ecommerceTask.infrastructure.adapter.controllers.AuthController;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.LoginRequestDto;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.LoginResponseDto;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class AuthControllerTest {
//
//    private AuthController authController;
//
//    private LoginUseCase loginUseCase;
//
//    @BeforeEach
//    void setUp() {
//        loginUseCase = mock(LoginUseCase.class);
//        authController = new AuthController(loginUseCase);
//    }
//
//    @Test
//    void login_SuccessfulLogin_ReturnsTokenWithMessage() {
//        // Arrange
//        LoginRequestDto request = new LoginRequestDto();
//        request.setUsername("superadmin");
//        request.setPassword("Superadmin123!");
//        String expectedToken = "mock-jwt-token";
//        when(loginUseCase.login("superadmin", "Superadmin123!")).thenReturn(expectedToken);
//
//        // Act
//        ResponseEntity<LoginResponseDto> response = authController.login(request);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(200, response.getStatusCodeValue());
//        LoginResponseDto responseBody = response.getBody();
//        assertNotNull(responseBody);
//        assertEquals("Welcome back superadmin!", responseBody.getMessage());
//        assertEquals(expectedToken, responseBody.getToken());
//        verify(loginUseCase).login("superadmin", "Superadmin123!");
//        verifyNoMoreInteractions(loginUseCase);
//    }
//
//    @Test
//    void login_NullRequest_ThrowsException() {
//        // Act & Assert
//        assertThrows(NullPointerException.class, () -> authController.login(null));
//        verifyNoInteractions(loginUseCase);
//    }
//}