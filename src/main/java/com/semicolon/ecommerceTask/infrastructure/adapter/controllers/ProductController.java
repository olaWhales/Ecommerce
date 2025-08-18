package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.CategoryPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.requests.manageProductDto.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.responses.ProductRegResponse;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.productManagentsMapper.ProductDtoMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ManageProductUseCase manageProductUseCase;
    private final ProductDtoMapper dtoMapper;
    private final CategoryPersistenceOutPort categoryPersistenceOutPort;

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductRegResponse> createProduct(@Valid @ModelAttribute ProductUploadDto dto, @AuthenticationPrincipal Jwt principal) throws IOException {
        if (principal == null || principal.getClaimAsString("sub") == null) {throw new SecurityException(MessageUtil.AUTHENTICATED_USER_NOT_FOUND);}
        String sellerId = principal.getClaimAsString("sub");
        CategoryDomainObject categoryDomainObject = categoryPersistenceOutPort.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException(MessageUtil.CATEGORY_NOT_FOUND));
        ManageProductDomainObject domain = dtoMapper.toDomain(dto, categoryDomainObject);
        ManageProductDomainObject savedProduct = manageProductUseCase.createProduct(domain, dto.getImageFile(), sellerId);
        ProductRegResponse productRegResponse = dtoMapper.toResponse(savedProduct);
        productRegResponse.setMessage(MessageUtil.PRODUCT_CREATED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRegResponse);
    }

    @PreAuthorize("hasRole('SELLER')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        UUID sellerId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        manageProductUseCase.deleteProduct(productId, String.valueOf(sellerId));
        return ResponseEntity.noContent().build();
    }
    }