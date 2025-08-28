package com.semicolon.ecommerceTask;

import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.CategoryPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.controllers.ProductController;
import com.semicolon.ecommerceTask.infrastructure.input.data.requests.manageProductDto.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.input.data.responses.ProductRegistrationResponse;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.productManagentsMapper.ProductDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ManageProductUseCase manageProductUseCase;

    @Mock
    private CategoryPersistenceOutPort categoryPersistenceOutPort;

    @Mock
    private ProductDtoMapper dtoMapper;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private ProductController productController;

    private ProductUploadDto productUploadDto;
    private CategoryDomainObject categoryDomainObject;
    private ManageProductDomainObject domainObject;
    private ManageProductDomainObject savedDomainObject;
    private ProductRegistrationResponse productRegistrationResponse;
    private Jwt principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productUploadDto = new ProductUploadDto();
        productUploadDto.setName("Food");
        productUploadDto.setDescription("Nice meal");
        productUploadDto.setPrice(BigDecimal.valueOf(200));
        productUploadDto.setInStockQuantity(5);
        productUploadDto.setCategoryId(UUID.randomUUID());

        categoryDomainObject = new CategoryDomainObject(UUID.randomUUID(), "food");

        domainObject = new ManageProductDomainObject();
        domainObject.setName("Food");
        domainObject.setPrice(BigDecimal.valueOf(200));
        domainObject.setInStockQuantity(5);

        savedDomainObject = new ManageProductDomainObject();
        savedDomainObject.setId(UUID.randomUUID());
        savedDomainObject.setName("Food");
        savedDomainObject.setPrice(BigDecimal.valueOf(200));
        savedDomainObject.setInStockQuantity(5);

        productRegistrationResponse = new ProductRegistrationResponse();
        productRegistrationResponse.setName("Food");
        productRegistrationResponse.setPrice(BigDecimal.valueOf(200));
        productRegistrationResponse.setInStockQuantity(5);
        productRegistrationResponse.setMessage("Product created successfully");

        principal = mock(Jwt.class);
        when(principal.getClaimAsString("sub")).thenReturn("seller-keycloak-id");
    }

    @Test
    void testCreateProduct_Success() throws IOException {
        when(categoryPersistenceOutPort.findById(productUploadDto.getCategoryId()))
                .thenReturn(Optional.of(categoryDomainObject));
        when(dtoMapper.toDomain(productUploadDto, categoryDomainObject))
                .thenReturn(domainObject);
        when(manageProductUseCase.createProduct(domainObject, null, "seller-keycloak-id"))
                .thenReturn(savedDomainObject);
        when(dtoMapper.toResponse(savedDomainObject)).thenReturn(productRegistrationResponse);
        ResponseEntity<ProductRegistrationResponse> response =
                productController.createProduct(productUploadDto, principal);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Food", response.getBody().getName());
        assertEquals("Product created successfully", response.getBody().getMessage());
    }

    @Test
    void testCreateProduct_MissingPrincipal_ShouldThrowSecurityException() {
        assertThrows(SecurityException.class,
                () -> productController.createProduct(productUploadDto, null));
    }

    @Test
    void testCreateProduct_CategoryNotFound_ShouldThrowRuntimeException() {
        when(categoryPersistenceOutPort.findById(productUploadDto.getCategoryId()))
                .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> productController.createProduct(productUploadDto, principal));
    }

    @Test
    void testCreateProduct_ValidationExceptionFromService() throws IOException {
        when(categoryPersistenceOutPort.findById(productUploadDto.getCategoryId()))
                .thenReturn(Optional.of(categoryDomainObject));
        when(dtoMapper.toDomain(productUploadDto, categoryDomainObject))
                .thenReturn(domainObject);
        when(manageProductUseCase.createProduct(domainObject, null, "seller-keycloak-id"))
                .thenThrow(new ValidationException("Invalid product data"));
        ValidationException ex = assertThrows(ValidationException.class,
                () -> productController.createProduct(productUploadDto, principal));
        assertEquals("Invalid product data", ex.getMessage());
    }
}
