package com.semicolon.ecommerceTask.domain.service;

import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.UserPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.output.persistence.mapper.ProductPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ManageProductService implements ManageProductUseCase {
    private final ProductPersistenceOutPort productPersistenceOutPort;
    private final FileStorageOutPort fileStorageOutPort;
    private final ProductPersistenceMapper mapper;
    private final UserPersistenceOutPort userPersistenceOutPort;

    @Transactional
    @Override
    public ManageProductDomainObject createProduct(ManageProductDomainObject manageProductDomainObject, MultipartFile imageFile, String sellerId) throws IOException {
//        ManageProductDomainObject existingProduct = productPersistenceOutPort.findById(manageProductDomainObject.getId())
//            .filter(p -> p.getSellerId().equals(sellerId))
//            .orElse(null);
//        if (existingProduct != null) {throw new RuntimeException(MessageUtil.PRODUCT_OWNERSHIP_MISMATCH);}
        String imageUrl = fileStorageOutPort.storeFile(imageFile);
        manageProductDomainObject.setImageUrl(imageUrl);
        manageProductDomainObject.setSellerId(sellerId);
        return productPersistenceOutPort.save(manageProductDomainObject);
    }
    @Transactional
    @Override
    public ManageProductDomainObject updateProduct(UUID productId, ManageProductDomainObject manageProductDomainObject, UUID sellerId) {
        ManageProductDomainObject existingProduct = productPersistenceOutPort.findById(productId)
                .orElseThrow(() -> new RuntimeException(MessageUtil.PRODUCT_NOT_FOUND));
        throw new RuntimeException(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_UPDATE_THIS_PRODUCT);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId, UUID sellerId) {
        // Verify seller and ownership
        ManageProductDomainObject product = productPersistenceOutPort.findById(productId)
                .orElseThrow(() -> new RuntimeException(MessageUtil.PRODUCT_NOT_FOUND));
        throw new RuntimeException(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_DELETE_THIS_PRODUCT);
    }
}