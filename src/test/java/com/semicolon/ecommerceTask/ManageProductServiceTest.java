//package com.semicolon.ecommerceTask;
//
//import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
//import com.semicolon.ecommerceTask.application.port.output.persistence.CategoryPersistenceOutPort;
//import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
//import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
//import com.semicolon.ecommerceTask.domain.exception.ValidationException;
//import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
//import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
//import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
//import com.semicolon.ecommerceTask.domain.service.ManageProductService;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.productManagentsMapper.ProductPersistenceMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class ManageProductServiceTest {
//
//    @Mock
//    private ProductPersistenceOutPort productPersistenceOutPort;
//
//    @Mock
//    private FileStorageOutPort fileStorageOutPort;
//
//    @Mock
//    private SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;
//
//    @Mock
//    private CategoryPersistenceOutPort categoryPersistenceOutPort;
//
//    @Mock
//    private ProductPersistenceMapper mapper;
//
//    @InjectMocks
//    private ManageProductService manageProductService;
//
//    private ManageProductDomainObject product;
//    private MultipartFile imageFile;
//    private final String sellerId = "seller-123";
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        CategoryDomainObject category = new CategoryDomainObject(UUID.randomUUID(), "Electronics");
//
//        product = new ManageProductDomainObject();
//        product.setId(UUID.randomUUID());
//        product.setName("iPhone 15");
//        product.setPrice(BigDecimal.valueOf(1500));
//        product.setInStockQuantity(5);
//        product.setCategoryDomainObject(category);
//        imageFile = mock(MultipartFile.class);
//    }
//
//    @Test
//    void testCreateProduct_SuccessWithoutImage() throws IOException {
//        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId))
//                .thenReturn((Optional<SellerFormSubmissionDomain>) Optional.of("dummySeller"));
//        doNothing().when(categoryPersistenceOutPort)
//                .validateCategoryExists("Electronics");
//        when(productPersistenceOutPort.save(any())).thenReturn(product);
//        ManageProductDomainObject result =
//                manageProductService.createProduct(product, null, sellerId);
//        assertNotNull(result);
//        assertEquals("iPhone 15", result.getName());
//        assertEquals(sellerId, result.getSellerId());
//        verify(productPersistenceOutPort, times(1)).save(any());
//        verify(fileStorageOutPort, never()).storeFile(any());
//    }
//
//    @Test
//    void testCreateProduct_SuccessWithImage() throws IOException {
//        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId))
//                .thenReturn((Optional<SellerFormSubmissionDomain>) Optional.of("dummySeller"));
//        when(imageFile.isEmpty()).thenReturn(false);
//        when(fileStorageOutPort.storeFile(imageFile)).thenReturn("http://cdn.com/iphone.png");
//        doNothing().when(categoryPersistenceOutPort)
//                .validateCategoryExists("Electronics");
//        when(productPersistenceOutPort.save(any())).thenReturn(product);
//        ManageProductDomainObject result =
//                manageProductService.createProduct(product, imageFile, sellerId);
//        assertNotNull(result);
//        assertEquals("iPhone 15", result.getName());
//        assertEquals("http://cdn.com/iphone.png", result.getImageUrl());
//        verify(fileStorageOutPort, times(1)).storeFile(imageFile);
//    }
//
//    @Test
//    void testCreateProduct_ThrowsWhenSellerNotFound() {
//        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId))
//                .thenReturn(Optional.empty());
//        ValidationException ex = assertThrows(
//                ValidationException.class,
//                () -> manageProductService.createProduct(product, null, sellerId)
//        );
//        assertTrue(ex.getMessage().contains("Seller not found"));
//        verify(productPersistenceOutPort, never()).save(any());
//    }
//
//    @Test
//    void testCreateProduct_ThrowsWhenCategoryNotFound() {
//        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId))
//                .thenReturn((Optional<SellerFormSubmissionDomain>) Optional.of("dummySeller"));
//        doThrow(new ValidationException("Category not found"))
//                .when(categoryPersistenceOutPort).validateCategoryExists("Electronics");
//        ValidationException ex = assertThrows(
//                ValidationException.class,
//                () -> manageProductService.createProduct(product, null, sellerId)
//        );
//        assertEquals("Category not found", ex.getMessage());
//        verify(productPersistenceOutPort, never()).save(any());
//    }
//
//    @Test
//    void testCreateProduct_FileStorageThrowsIOException() throws IOException {
//        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId))
//                .thenReturn((Optional<SellerFormSubmissionDomain>) Optional.of("dummySeller"));
//        when(imageFile.isEmpty()).thenReturn(false);
//        when(fileStorageOutPort.storeFile(imageFile))
//                .thenThrow(new IOException("Upload failed"));
//        assertThrows(IOException.class,
//                () -> manageProductService.createProduct(product, imageFile, sellerId));
//        verify(productPersistenceOutPort, never()).save(any());
//    }
//}
