////package com.semicolon.ecommerceTask;
////
////import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
////import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
////import com.semicolon.ecommerceTask.infrastructure.adapter.controllers.ProductController;
////import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.manageProductDto.ProductUpdateDto;
////import org.junit.jupiter.api.AfterEach;
////import org.junit.jupiter.api.BeforeEach;
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.mockito.InjectMocks;
////import org.mockito.Mock;
////import org.mockito.MockedStatic;
////import org.mockito.junit.jupiter.MockitoExtension;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.context.SecurityContext;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.web.server.ResponseStatusException;
////
////import java.math.BigDecimal;
////import java.util.Collections;
////import java.util.List;
////import java.util.UUID;
////
////import static org.junit.jupiter.api.Assertions.*;
////import static org.mockito.ArgumentMatchers.any;
////import static org.mockito.Mockito.*;
////
////@ExtendWith(MockitoExtension.class)
////class ProductControllerPureUnitTest {
////
////    @Mock
////    private ManageProductUseCase manageProductUseCase;
////
////    @Mock
////    private SecurityContext securityContext;
////
////    @Mock
////    private Authentication authentication;
////
////    @InjectMocks
////    private ProductController productController;
////
////    private final UUID sellerId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
////    private final UUID otherSellerId = UUID.fromString("234e5678-e89b-12d3-a456-426614174000");
////    private final UUID productId = UUID.fromString("345e6789-e89b-12d3-a456-426614174000");
////
////    private ProductDomainObject mockProduct;
////    private MockedStatic<SecurityContextHolder> securityContextHolderMockedStatic;
////
////    @BeforeEach
////    void setUp() {
////        mockProduct = ProductDomainObject.builder()
////                .id(productId)
////                .name("Test Product")
////                .description("A great product for testing.")
////                .price(new BigDecimal("10.50"))
////                .inStockQuantity(100)
////                .sellerId(sellerId)
////                .imageUrl("http://example.com/image.jpg")
////                .build();
////
////        // Mock the SecurityContextHolder's static methods
////        securityContextHolderMockedStatic = mockStatic(SecurityContextHolder.class);
////        securityContextHolderMockedStatic.when(SecurityContextHolder::getContext).thenReturn(securityContext);
////        when(securityContext.getAuthentication()).thenReturn(authentication);
////        when(authentication.getName()).thenReturn(sellerId.toString());
////    }
////
////    @AfterEach
////    void tearDown() {
////        // Close the static mock after each test
////        securityContextHolderMockedStatic.close();
////    }
////
////    // CREATE
////    @Test
////    void createProduct_validProduct_returnsCreatedStatus() throws Exception {
////        // Arrange
////        when(manageProductUseCase.createProduct(any(), eq(sellerId))).thenReturn(mockProduct);
////
////        // Act
////        // We simulate the request by directly calling the controller method with mocked parameters
////        ResponseEntity<String> response = productController.createProduct(null);
////
////        // Assert
////        assertEquals(HttpStatus.CREATED, response.getStatusCode());
////        assertEquals("Product uploaded successfully", response.getBody());
////        verify(manageProductUseCase, times(1)).createProduct(any(), eq(sellerId));
////    }
////
////    @Test
////    void createProduct_uploadFails_throwsException() throws Exception {
////        // Arrange
////        // Mock the use case to throw the same exception the controller expects to catch
////        doThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Upload failed"))
////                .when(manageProductUseCase).createProduct(any(), any());
////
////        // Act & Assert
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
////                () -> productController.createProduct(null));
////
////        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
////        assertTrue(exception.getReason().contains("Upload failed"));
////    }
////
////    // READ (Single Product)
////    @Test
////    void getProduct_productBelongsToSeller_returnsProduct() {
////        // Arrange
////        when(manageProductUseCase.getProduct(productId)).thenReturn(mockProduct);
////
////        // Act
////        ResponseEntity<ProductDomainObject> response = productController.getProduct(productId);
////
////        // Assert
////        assertEquals(HttpStatus.OK, response.getStatusCode());
////        assertEquals(mockProduct, response.getBody());
////    }
////
////    @Test
////    void getProduct_productDoesNotBelongToSeller_returnsForbidden() {
////        // Arrange
////        // Create a product that belongs to another seller
////        ProductDomainObject otherSellerProduct = mockProduct.toBuilder().sellerId(otherSellerId).build();
////        when(manageProductUseCase.getProduct(productId)).thenReturn(otherSellerProduct);
////
////        // Act & Assert
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
////                () -> productController.getProduct(productId));
////
////        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
////    }
////
////    @Test
////    void getProduct_productNotFound_throwsException() {
////        // Arrange
////        when(manageProductUseCase.getProduct(productId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
////
////        // Act & Assert
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
////                () -> productController.getProduct(productId));
////
////        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
////    }
////
////    // READ (All Products by Seller)
////    @Test
////    void getAllProducts_returnsListOfProducts() {
////        // Arrange
////        List<ProductDomainObject> products = Collections.singletonList(mockProduct);
////        when(manageProductUseCase.getAllProductsBySeller(sellerId)).thenReturn(products);
////
////        // Act
////        ResponseEntity<List<ProductDomainObject>> response = productController.getAllProducts();
////
////        // Assert
////        assertEquals(HttpStatus.OK, response.getStatusCode());
////        assertEquals(1, response.getBody().size());
////        assertEquals(mockProduct, response.getBody().get(0));
////    }
////
////    // UPDATE
////    @Test
////    void updateProduct_productBelongsToSeller_returnsUpdatedProduct() {
////        // Arrange
////        ProductUpdateDto updateDto = ProductUpdateDto.builder().name("Updated Name").build();
////        ProductDomainObject updatedProduct = mockProduct.toBuilder().name("Updated Name").build();
////
////        when(manageProductUseCase.getProduct(productId)).thenReturn(mockProduct);
////        when(manageProductUseCase.updateProduct(any(), any(ProductUpdateDto.class))).thenReturn(updatedProduct);
////
////        // Act
////        ResponseEntity<ProductDomainObject> response = productController.updateProduct(productId, updateDto);
////
////        // Assert
////        assertEquals(HttpStatus.OK, response.getStatusCode());
////        assertEquals("Updated Name", response.getBody().getName());
////    }
////
////    @Test
////    void updateProduct_productDoesNotBelongToSeller_returnsForbidden() {
////        // Arrange
////        ProductDomainObject otherSellerProduct = mockProduct.toBuilder().sellerId(otherSellerId).build();
////        when(manageProductUseCase.getProduct(productId)).thenReturn(otherSellerProduct);
////        ProductUpdateDto updateDto = ProductUpdateDto.builder().name("Updated Name").build();
////
////        // Act & Assert
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
////                () -> productController.updateProduct(productId, updateDto));
////
////        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
////    }
////
////    // DELETE
////    @Test
////    void deleteProduct_productBelongsToSeller_returnsNoContent() {
////        // Arrange
////        when(manageProductUseCase.getProduct(productId)).thenReturn(mockProduct);
////        doNothing().when(manageProductUseCase).deleteProduct(productId);
////
////        // Act
////        ResponseEntity<Void> response = productController.deleteProduct(productId);
////
////        // Assert
////        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
////        verify(manageProductUseCase, times(1)).deleteProduct(productId);
////    }
////
////    @Test
////    void deleteProduct_productDoesNotBelongToSeller_returnsForbidden() {
////        // Arrange
////        ProductDomainObject otherSellerProduct = mockProduct.toBuilder().sellerId(otherSellerId).build();
////        when(manageProductUseCase.getProduct(productId)).thenReturn(otherSellerProduct);
////
////        // Act & Assert
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
////                () -> productController.deleteProduct(productId));
////
////        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
////    }
////}
//
//
//package com.semicolon.ecommerceTask;
//
//import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
//import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.manageProductDto.ProductUploadDto;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.manageProductDto.ProductUpdateDto;
//import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.ProductPersistenceMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//import jakarta.validation.Valid;
//import java.util.List;
//import java.util.UUID;
//
//@Slf4j
//@RestController
//@RequestMapping("/seller/products")
//@RequiredArgsConstructor
//public class ProductController {
//
//    private final ManageProductUseCase manageProductUseCase;
//    private final ProductPersistenceMapper productDtoMapper;
//
//    // CREATE
//    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<String> createProduct(@Valid @ModelAttribute ProductUploadDto productDto) {
//        try {
//            // Map the DTO to a domain object here, in the controller.
//            ProductDomainObject productDomainObject = productDtoMapper.toDomain(productDto);
//            manageProductUseCase.createProduct(productDomainObject, productDto.getImageFile());
//            return new ResponseEntity<>("Product uploaded successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            log.error("Product upload failed: ", e);
//            // The service layer throws more specific exceptions now, but this is a good catch-all.
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Product upload failed: " + e.getMessage());
//        }
//    }
//
//    // READ (Single Product)
//    @GetMapping("/{productId}")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<ProductDomainObject> getProduct(@PathVariable UUID productId) {
//        ProductDomainObject product = manageProductUseCase.getProduct(productId);
//        return new ResponseEntity<>(product, HttpStatus.OK);
//    }
//
//    // READ (All Products by Seller)
//    @GetMapping
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<List<ProductDomainObject>> getAllProducts() {
//        List<ProductDomainObject> products = manageProductUseCase.getAllProductsBySeller();
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
//
//    // UPDATE
//    @PutMapping("/{productId}")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<ProductDomainObject> updateProduct(
//            @PathVariable UUID productId,
//            @RequestBody @Valid ProductUpdateDto productDto) {
//        // Map the DTO to a domain object here.
//        ProductDomainObject productDomainObject = productDtoMapper.toDomain(productDto);
//        ProductDomainObject updatedProduct = manageProductUseCase.updateProduct(productId, productDomainObject);
//        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//    }
//
//    // DELETE
//    @DeleteMapping("/{productId}")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
//        manageProductUseCase.deleteProduct(productId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}
