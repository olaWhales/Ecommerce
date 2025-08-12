package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/seller/products")
@RequiredArgsConstructor
public class ProductController {

    private final ManageProductUseCase manageProductUseCase;

    /**
     * Retrieves the seller's UUID from the security context.
     * @return The UUID of the currently authenticated seller.
     */
    private UUID getAuthenticatedSellerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Assuming the principal is a JWT with a 'sub' claim containing the UUID.
        // You may need to adjust this depending on your JWT token structure.
        return UUID.fromString(authentication.getName());
    }

    // CREATE
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<String> createProduct(
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart("price") String price,
            @RequestPart("inStockQuantity") String inStockQuantity,
            @RequestPart("imageFile") MultipartFile imageFile) {

        try {
            // Get the authenticated seller's ID dynamically
            UUID sellerId = getAuthenticatedSellerId();

            ProductUploadDto productDto = ProductUploadDto.builder()
                    .name(name)
                    .description(description)
                    .price(new BigDecimal(price))
                    .inStockQuantity(Integer.parseInt(inStockQuantity))
                    .build();

            manageProductUseCase.createProduct(productDto, imageFile, sellerId);
            return new ResponseEntity<>("Product uploaded successfully", HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            log.error("Invalid number format for price or stock quantity", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid number format for price or stock quantity");
        } catch (Exception e) {
            log.error("Product upload failed for seller: {}", getAuthenticatedSellerId(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Product upload failed: " + e.getMessage());
        }
    }

    // READ (Single Product)
    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductDomainObject> getProduct(@PathVariable UUID productId) {
        UUID sellerId = getAuthenticatedSellerId();
        ProductDomainObject product = manageProductUseCase.getProduct(productId);
        if (!product.getSellerId().equals(sellerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to this product.");
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // READ (All Products by Seller)
    @GetMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<ProductDomainObject>> getAllProducts() {
        UUID sellerId = getAuthenticatedSellerId();
        List<ProductDomainObject> products = manageProductUseCase.getAllProductsBySeller(sellerId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductDomainObject> updateProduct(
            @PathVariable UUID productId,
            @RequestBody ProductUpdateDto productDto) {
        UUID sellerId = getAuthenticatedSellerId();
        ProductDomainObject existingProduct = manageProductUseCase.getProduct(productId);
        if (!existingProduct.getSellerId().equals(sellerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to update this product.");
        }
        ProductDomainObject updatedProduct = manageProductUseCase.updateProduct(productId, productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        UUID sellerId = getAuthenticatedSellerId();
        ProductDomainObject product = manageProductUseCase.getProduct(productId);
        if (!product.getSellerId().equals(sellerId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have access to delete this product.");
        }
        manageProductUseCase.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
