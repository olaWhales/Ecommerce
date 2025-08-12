//package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;
//
//import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
//import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUploadDto;
//import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUpdateDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/seller/products")
//@RequiredArgsConstructor
//public class SellerProductController {
//
//    private final ManageProductUseCase manageProductUseCase;
//    private UUID getAuthenticatedSellerId() {
//        return UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
//    }
//
//    // CREATE
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<String> createProduct(
//            @RequestPart("name") String name,
//            @RequestPart("description") String description,
//            @RequestPart("price") String price,
//            @RequestPart("inStockQuantity") String inStockQuantity,
//            @RequestPart("imageFile") MultipartFile imageFile) {
//
//        ProductUploadDto productDto = new ProductUploadDto();
//        productDto.setName(name);
//        productDto.setDescription(description);
//        productDto.setPrice(new BigDecimal(price));
//        productDto.setInStockQuantity(Integer.parseInt(inStockQuantity));
//        productDto.setSellerId(getAuthenticatedSellerId());
//
//        try {
//            manageProductUseCase.createProduct(productDto, imageFile);
//            return new ResponseEntity<>("Product uploaded successfully", HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Product upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // READ (Single Product)
//    @GetMapping("/{productId}")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<ProductDomainObject> getProduct(@PathVariable UUID productId) {
//        ProductDomainObject product = manageProductUseCase.getProduct(productId);
//        // You should check if the product belongs to the authenticated seller here
//        if (!product.getSellerId().equals(getAuthenticatedSellerId())) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        return new ResponseEntity<>(product, HttpStatus.OK);
//    }
//
//    // READ (All Products by Seller)
//    @GetMapping
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<List<ProductDomainObject>> getAllProducts() {
//        List<ProductDomainObject> products = manageProductUseCase.getAllProductsBySeller(getAuthenticatedSellerId());
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
//
//    // UPDATE
//    @PutMapping("/{productId}")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<ProductDomainObject> updateProduct(
//            @PathVariable UUID productId,
//            @RequestBody ProductUpdateDto productDto) {
//
//        ProductDomainObject existingProduct = manageProductUseCase.getProduct(productId);
//        if (!existingProduct.getSellerId().equals(getAuthenticatedSellerId())) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        ProductDomainObject updatedProduct = manageProductUseCase.updateProduct(productId, productDto);
//        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
//    }
//
//    // DELETE
//    @DeleteMapping("/{productId}")
//    @PreAuthorize("hasRole('SELLER')")
//    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
//        ProductDomainObject product = manageProductUseCase.getProduct(productId);
//        if (!product.getSellerId().equals(getAuthenticatedSellerId())) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//
//        manageProductUseCase.deleteProduct(productId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//}