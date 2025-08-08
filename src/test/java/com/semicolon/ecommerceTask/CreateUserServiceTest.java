//package com.semicolon.ecommerceTask;
//
//import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
//import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
//import com.semicolon.ecommerceTask.domain.service.ManageUserService;
//
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserRole;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class CreateUserServiceTest {
//
//    private UserPersistenceOutPort userPersistenceOutPort;
//    private ManageUserService createUserService;
//
//    @BeforeEach
//    void setUp() {
//        userPersistenceOutPort = mock(UserPersistenceOutPort.class);
//        createUserService = new ManageUserService(userPersistenceOutPort);
//    }
//
//    @Test
//    void testThatUserCanRegisterSuccessfully() {
//        UserDomainObject user = UserDomainObject.builder()
//            .name("Olawale")
//            .email("Olawale@gmail.com")
//            .role(UserRole.BUYER)
//            .password("1111")
//            .build();
//        when(userPersistenceOutPort.saveUser(any(UserDomainObject.class))).thenReturn(user);
//        createUserService.createUser(user);
//        ArgumentCaptor<UserDomainObject> userCaptor = ArgumentCaptor.forClass(UserDomainObject.class);
//        verify(userPersistenceOutPort, times(1)).saveUser(userCaptor.capture());
//        UserDomainObject capturedUser = userCaptor.getValue();
//        assertEquals("Olawale", capturedUser.getName());
//        assertEquals("Olawale@gmail.com", capturedUser.getEmail());
//        assertEquals("BUYER", capturedUser.getRole().name());
//    }
////
////    @Test
////    void testThatUserCan
//
//}
