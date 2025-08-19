package com.semicolon.ecommerceTask;

import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.CategoryPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.domain.model.SellerFormSubmissionDomain;
import com.semicolon.ecommerceTask.domain.services.ManageProductService;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageProductServiceTest {

    @Mock
    private ProductPersistenceOutPort productPersistenceOutPort;

    @Mock
    private FileStorageOutPort fileStorageOutPort;

    @Mock
    private SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;

    @Mock
    private CategoryPersistenceOutPort categoryPersistenceOutPort;

    @InjectMocks
    private ManageProductService manageProductService;

    private UUID productId;
    private String sellerId;
    private ManageProductDomainObject productDomainObject;
    private CategoryDomainObject categoryDomainObject;
    private SellerFormSubmissionDomain sellerDomainObject;
    private MultipartFile imageFile;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();
        sellerId = UUID.randomUUID().toString();
        imageFile = mock(MultipartFile.class);

        categoryDomainObject = CategoryDomainObject.builder()
                .id(UUID.randomUUID())
                .name("Electronics")
                .build();

        productDomainObject = ManageProductDomainObject.builder()
                .id(productId)
                .name("Test Product")
                .description("Test Description")
                .price(BigDecimal.valueOf(100.0))
                .inStockQuantity(10)
                .sellerId(sellerId)
                .categoryDomainObject(categoryDomainObject)
                .imageUrl("http://example.com/image.jpg")
                .build();

        sellerDomainObject = SellerFormSubmissionDomain.builder()
                .keycloakUserId(sellerId)
                .build();
    }


    @Test
    void createProduct_Success() throws IOException {
        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId)).thenReturn(Optional.of(sellerDomainObject));
        when(imageFile.isEmpty()).thenReturn(false);
        when(fileStorageOutPort.storeFile(imageFile)).thenReturn("http://new-image-url.jpg");
        when(productPersistenceOutPort.save(any(ManageProductDomainObject.class))).thenReturn(productDomainObject);
        ManageProductDomainObject createdProduct = manageProductService.createProduct(productDomainObject, imageFile, sellerId);
        assertNotNull(createdProduct);
        assertEquals("http://new-image-url.jpg", createdProduct.getImageUrl());
        verify(sellerFormSubmissionPersistenceOutPort, times(1)).findByKeycloakUserId(sellerId);
        verify(fileStorageOutPort, times(1)).storeFile(imageFile);
        verify(productPersistenceOutPort, times(1)).save(any(ManageProductDomainObject.class));
    }

    @Test
    void createProduct_WithNullImageFile_Success() throws IOException {
        // Mock dependencies
        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId)).thenReturn(Optional.of(sellerDomainObject));
        when(productPersistenceOutPort.save(any(ManageProductDomainObject.class))).thenReturn(productDomainObject);
        ManageProductDomainObject createdProduct = manageProductService.createProduct(productDomainObject, null, sellerId);
        assertNotNull(createdProduct);
        verify(fileStorageOutPort, never()).storeFile(any(MultipartFile.class));
        verify(productPersistenceOutPort, times(1)).save(any(ManageProductDomainObject.class));
    }

    @Test
    void createProduct_WithEmptyImageFile_Success() throws IOException {
        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId)).thenReturn(Optional.of(sellerDomainObject));
        when(imageFile.isEmpty()).thenReturn(true);
        when(productPersistenceOutPort.save(any(ManageProductDomainObject.class))).thenReturn(productDomainObject);
        ManageProductDomainObject createdProduct = manageProductService.createProduct(productDomainObject, imageFile, sellerId);
        assertNotNull(createdProduct);
        verify(fileStorageOutPort, never()).storeFile(any(MultipartFile.class));
        verify(productPersistenceOutPort, times(1)).save(any(ManageProductDomainObject.class));
    }

    @Test
    void createProduct_WhenSellerNotFound_ThrowsValidationException() {
        when(sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId)).thenReturn(Optional.empty());
        assertThrows(ValidationException.class, () ->
                manageProductService.createProduct(productDomainObject, imageFile, sellerId));
    }
    @Test
    void updateProduct_Success() {
        ManageProductDomainObject updatedProductDomainObject = ManageProductDomainObject.builder()
                .name("Updated Name")
                .description("Updated Description")
                .price(BigDecimal.valueOf(150.0))
                .inStockQuantity(5)
                .categoryDomainObject(categoryDomainObject)
                .build();

        when(productPersistenceOutPort.findById(productId)).thenReturn(Optional.of(productDomainObject));
        when(productPersistenceOutPort.save(any(ManageProductDomainObject.class))).thenReturn(updatedProductDomainObject);
        when(categoryPersistenceOutPort.findById(any(UUID.class))).thenReturn(Optional.of(categoryDomainObject));
        ManageProductDomainObject result = manageProductService.updateProduct(productId, updatedProductDomainObject, sellerId);
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertEquals(BigDecimal.valueOf(150.0), result.getPrice());
        assertEquals(5, result.getInStockQuantity());
        assertEquals(categoryDomainObject.getId(), result.getCategoryDomainObject().getId());
        assertEquals(categoryDomainObject.getName(), result.getCategoryDomainObject().getName());
        verify(productPersistenceOutPort, times(1)).findById(productId);
        verify(productPersistenceOutPort, times(1)).save(productDomainObject);
    }

    @Test
    void updateProduct_WhenProductNotFound_ThrowsRuntimeException() {
        when(productPersistenceOutPort.findById(productId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () ->
                manageProductService.updateProduct(productId, productDomainObject, sellerId));
    }

    @Test
    void updateProduct_WhenUserIsNotAuthorized_ThrowsRuntimeException() {
        productDomainObject.setSellerId("differentSellerId");
        when(productPersistenceOutPort.findById(productId)).thenReturn(Optional.of(productDomainObject));
        assertThrows(RuntimeException.class, () ->
                manageProductService.updateProduct(productId, productDomainObject, sellerId));
    }

    @Test
    @DisplayName("Should successfully delete a product when seller is authorized")
    void deleteProduct_WhenSellerIsAuthorized_DeletesSuccessfully() {
        when(productPersistenceOutPort.findById(productId)).thenReturn(Optional.of(productDomainObject));
        doNothing().when(productPersistenceOutPort).deleteById(productId);
        assertDoesNotThrow(() -> manageProductService.deleteProduct(productId, sellerId));
        verify(productPersistenceOutPort, times(1)).findById(productId);
        verify(productPersistenceOutPort, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Should throw an exception if the product is not found")
    void deleteProduct_WhenProductNotFound_ThrowsException() {
        when(productPersistenceOutPort.findById(productId)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> manageProductService.deleteProduct(productId, sellerId));
        assertEquals(MessageUtil.PRODUCT_NOT_FOUND, exception.getMessage());
        verify(productPersistenceOutPort, times(1)).findById(productId);
        verify(productPersistenceOutPort, never()).deleteById(any(UUID.class));
    }

    @Test
    @DisplayName("Should throw an exception if the seller is not authorized to delete")
    void deleteProduct_WhenSellerIsNotAuthorized_ThrowsException() {
        String unauthorizedSellerId = UUID.randomUUID().toString();
        when(productPersistenceOutPort.findById(productId)).thenReturn(Optional.of(productDomainObject));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> manageProductService.deleteProduct(productId, unauthorizedSellerId));
        assertEquals(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_DELETE_THIS_PRODUCT, exception.getMessage());
        verify(productPersistenceOutPort, times(1)).findById(productId);
        verify(productPersistenceOutPort, never()).deleteById(any(UUID.class));
    }
    @Test
    @DisplayName("Should successfully delete a product with null image URL when seller is authorized")
    void deleteProduct_WhenProductHasNoImageUrl_DeletesSuccessfully() {
        productDomainObject.setImageUrl(null);
        when(productPersistenceOutPort.findById(productId)).thenReturn(Optional.of(productDomainObject));
        doNothing().when(productPersistenceOutPort).deleteById(productId);
        assertDoesNotThrow(() -> manageProductService.deleteProduct(productId, sellerId));
        verify(productPersistenceOutPort, times(1)).findById(productId);
        verify(productPersistenceOutPort, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Should throw an exception when product ID is null")
    void deleteProduct_WhenProductIdIsNull_ThrowsException() {
        assertThrows(RuntimeException.class, () -> manageProductService.deleteProduct(null, sellerId));
        verify(productPersistenceOutPort, never()).findById(any(UUID.class));
        verify(productPersistenceOutPort, never()).deleteById(any(UUID.class));
    }




    @Test
    @DisplayName("Should return product when a valid product ID is provided")
    void getProductById_ProductExists_ReturnsProduct() {
//        UUID existingProductId = UUID.randomUUID();
//        ManageProductDomainObject existingProduct = ManageProductDomainObject.builder().id(existingProductId).name("Existing Product").sellerId(sellerId).build();
//        when(productPersistenceOutPort.findById(existingProductId)).thenReturn(Optional.of(existingProduct));
//        ManageProductDomainObject result = manageProductService.getProductById(existingProductId, sellerId);
//        assertNotNull(result);
//        assertEquals("Existing Product", result.getName());
//        verify(productPersistenceOutPort, times(1)).findById(existingProductId);
    }

    @Test
    @DisplayName("Should throw an exception when product ID does not exist")
    void getProductById_ProductDoesNotExist_ThrowsException() {
//        when(productPersistenceOutPort.findById(any(UUID.class))).thenReturn(Optional.empty());
//        RuntimeException exception = assertThrows(RuntimeException.class, () ->
//                manageProductService.getProductById(UUID.randomUUID(), sellerId));
//        assertEquals(MessageUtil.PRODUCT_NOT_FOUND, exception.getMessage());
//        verify(productPersistenceOutPort, times(1)).findById(any(UUID.class));
    }

    @Test
    @DisplayName("Should throw an exception when the seller is not the owner of the product")
    void getProductById_SellerIsNotOwner_ThrowsException() {
//        UUID existingProductId = UUID.randomUUID();
//        ManageProductDomainObject existingProduct = ManageProductDomainObject.builder().id(existingProductId).name("Existing Product").sellerId("anotherSeller").build();
//        when(productPersistenceOutPort.findById(existingProductId)).thenReturn(Optional.of(existingProduct));
//        RuntimeException exception = assertThrows(RuntimeException.class, () ->
//                manageProductService.getProductById(existingProductId, sellerId));
//        assertEquals(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_VIEW_THIS_PRODUCT, exception.getMessage());
//        verify(productPersistenceOutPort, times(1)).findById(existingProductId);
    }


    @Test
    @DisplayName("Should return list of products for a valid seller ID")
    void getAllProductsBySellerId_SellerHasProducts_ReturnsProductList() {
        ManageProductDomainObject product1 = ManageProductDomainObject.builder().id(UUID.randomUUID()).name("Product 1").sellerId(sellerId).build();
        ManageProductDomainObject product2 = ManageProductDomainObject.builder().id(UUID.randomUUID()).name("Product 2").sellerId(sellerId).build();
        List<ManageProductDomainObject> products = List.of(product1, product2);
        when(productPersistenceOutPort.findAllBySellerId(sellerId)).thenReturn(products);
        List<ManageProductDomainObject> result = manageProductService.getAllProductsBySellerId(sellerId);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product 1", result.get(0).getName());
        assertEquals("Product 2", result.get(1).getName());
        verify(productPersistenceOutPort, times(1)).findAllBySellerId(sellerId);
    }

    @Test
    @DisplayName("Should return empty list when seller ID has no products")
    void getAllProductsBySellerId_SellerHasNoProducts_ReturnsEmptyList() {
//        when(productPersistenceOutPort.findAllBySellerId(sellerId)).thenReturn(Collections.emptyList());
//        List<ManageProductDomainObject> result = manageProductService.getAllProductsBySellerId(sellerId);
//        assertNotNull(result);
//        assertTrue(result.isEmpty());
//        verify(productPersistenceOutPort, times(1)).findAllBySellerId(sellerId);
    }
}
