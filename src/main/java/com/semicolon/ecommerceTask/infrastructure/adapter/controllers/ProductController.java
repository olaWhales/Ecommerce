package com.semicolon.ecommerceTask.infrastructure.adapter.controllers;

import com.nimbusds.jwt.JWT;
import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.domain.model.User;
import com.semicolon.ecommerceTask.domain.model.UserDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.manageProductDto.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.manageProductDto.ProductUpdateDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.response.ProductResponseDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.ProductEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.entity.UserEntity;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.ProductPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import jakarta.mail.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ManageProductUseCase manageProductUseCase;
    private final ProductPersistenceMapper mapper;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ProductResponseDto> createProduct(@ModelAttribute ProductUploadDto dto, @AuthenticationPrincipal Jwt principal) throws IOException {
        if (principal == null) {throw new SecurityException(MessageUtil.AUTHENTICATED_USER_NOT_FOUND);}
        String keycloakUserId = principal.getClaimAsString("sub");
        ManageProductDomainObject domain = mapper.toDomain(mapper.toEntity(dto));
        ManageProductDomainObject savedProduct = manageProductUseCase.createProduct(domain, dto.getImageFile(), keycloakUserId);
        return ResponseEntity.ok(mapper.toResponse(savedProduct));
    }
//        @PreAuthorize("hasRole('SELLER')")
//        @PostMapping("/upload")
//        public ResponseEntity<ProductResponseDto> createProduct(@ModelAttribute ProductUploadDto dto, @AuthenticationPrincipal UserDomainObject user) throws IOException {
//            log.info("User Email ======>>>>> {}", user != null ? user.getEmail() : "null");
//            if (user == null) {
//                throw new SecurityException("Authenticated user not found");
//            }
//            ManageProductDomainObject domain = mapper.toDomain(mapper.toEntity(dto));
//            ManageProductDomainObject savedProduct = manageProductUseCase.createProduct(domain, dto.getImageFile(), user.getId());
//            log.info("Saved product =====> {}", savedProduct);
//            return ResponseEntity.ok(mapper.toResponse(savedProduct));
//        }
//        public ResponseEntity<ProductResponseDto> createProduct(@ModelAttribute ProductUploadDto dto, @AuthenticationPrincipal UserEntity user) throws IOException {
//            log.info("User Email ======>>>>> {}",user);
//            ManageProductDomainObject domain = mapper.toDomain(mapper.toEntity(dto));
//            ManageProductDomainObject savedProduct = manageProductUseCase.createProduct(domain, dto.getImageFile(), UUID.fromString(user.getEmail()));
//            log.info("Saved product =====> {}",savedProduct);
//            return ResponseEntity.ok(mapper.toResponse(savedProduct));
//        }

//            ManageProductDomainObject domain = mapper.toDomain(mapper.toEntity(dto));
//            ManageProductDomainObject savedProduct = manageProductUseCase.createProduct(domain, dto.getImageFile(), jwt);
//            return ResponseEntity.ok(mapper.toResponse(savedProduct));
//        }

        @PreAuthorize("hasRole('SELLER')")
        @PutMapping("/{productId}")
        public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID productId, @RequestBody ProductUpdateDto dto) {
            UUID sellerId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
            ManageProductDomainObject domain = mapper.toDomain(mapper.toEntity(dto, new ProductEntity()));
            ManageProductDomainObject updatedProduct = manageProductUseCase.updateProduct(productId, domain, UUID.fromString(String.valueOf(sellerId)));
            return ResponseEntity.ok(mapper.toResponse(updatedProduct));
        }

        @PreAuthorize("hasRole('SELLER')")
        @DeleteMapping("/{productId}")
        public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
            UUID sellerId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
            manageProductUseCase.deleteProduct(productId, sellerId);
            return ResponseEntity.noContent().build();
        }
    }