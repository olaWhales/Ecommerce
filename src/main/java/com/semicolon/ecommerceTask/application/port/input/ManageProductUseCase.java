package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ManageProductUseCase {
    ManageProductDomainObject createProduct(ManageProductDomainObject manageProductDomainObject, MultipartFile imageFile, String sellerId) throws IOException;
    ManageProductDomainObject updateProduct(UUID productId, ManageProductDomainObject manageProductDomainObject, String sellerId);
    void deleteProduct(UUID productId, String sellerId);
}

