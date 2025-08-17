package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.CategoryPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.productManagentsMapper.ProductPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ManageProductService implements ManageProductUseCase {

    private final ProductPersistenceOutPort productPersistenceOutPort;
    private final FileStorageOutPort fileStorageOutPort;
    private final SellerFormSubmissionPersistenceOutPort sellerFormSubmissionPersistenceOutPort;
    private final CategoryPersistenceOutPort categoryPersistenceOutPort;
    private final ProductPersistenceMapper mapper;

    @Transactional
    @Override
    public ManageProductDomainObject createProduct(
            ManageProductDomainObject domain,
            MultipartFile imageFile,
            String sellerId) throws IOException {
        sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId).orElseThrow(() -> new ValidationException(MessageUtil.SELLER_NOT_FOUND_WITH_ID + sellerId));
        domain.setSellerId(sellerId);
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileStorageOutPort.storeFile(imageFile);
            domain.setImageUrl(imageUrl);
        }
        categoryPersistenceOutPort.validateCategoryExists(domain.getCategoryDomainObject().getName());
        return productPersistenceOutPort.save(domain);
    }

    @Transactional
    @Override
    public ManageProductDomainObject updateProduct(UUID productId, ManageProductDomainObject manageProductDomainObject, UUID sellerId) {
        ManageProductDomainObject existingProduct = productPersistenceOutPort.findById(productId).orElseThrow(() -> new RuntimeException(MessageUtil.PRODUCT_NOT_FOUND));
        if (!existingProduct.getSellerId().equals(sellerId.toString())) {throw new RuntimeException(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_UPDATE_THIS_PRODUCT);}
        ManageProductDomainObject updated = ManageProductDomainObject.builder()
            .id(existingProduct.getId())
            .name(manageProductDomainObject.getName())
            .description(manageProductDomainObject.getDescription())
            .price(manageProductDomainObject.getPrice())
            .inStockQuantity(manageProductDomainObject.getInStockQuantity())
            .sellerId(sellerId.toString())
            .imageUrl(existingProduct.getImageUrl())
            .build();
        return productPersistenceOutPort.save(updated);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId, UUID sellerId) {
        ManageProductDomainObject product = productPersistenceOutPort.findById(productId).orElseThrow(() -> new RuntimeException(MessageUtil.PRODUCT_NOT_FOUND));
        throw new RuntimeException(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_DELETE_THIS_PRODUCT);
    }

//    private String extractSellerId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof Jwt jwt) {
//            String sellerId = jwt.getSubject();
//            if (sellerId == null || sellerId.isEmpty()) {
//                log.error("Seller ID (sub) is missing in JWT for principal: {}", principal);
//                throw new ValidationException(MessageUtil.AUTHENTICATED_USER_ID_MISSING);
//            }
//            log.debug("Extracted seller ID from JWT: {}", sellerId);
//            return sellerId;
//        }
//        log.error("Authentication principal is not a JWT: {}", principal);
//        throw new ValidationException(MessageUtil.INVALID_AUTHENTICATION_PRINCIPAL);
//    }
}