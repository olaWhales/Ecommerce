package com.semicolon.ecommerceTask;

import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.SellerDomainObject;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.SellerResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SellerFormSubmissionTest {
    @BeforeEach
    void setUp(){
    }

    @Mock
    private SellerFormSubmissionPersistenceOutPort sellerPersistenceOutPort;
    private SellerResponseDto sellerResponseDto;
    private SellerDomainObject sellerDomainObject;

//    @InjectMocks

    @Test
    public void testThatACustomerCanSubmitAForm(){

    }
}
