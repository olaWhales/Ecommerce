package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ManageProductUseCase {
    ManageProductDomainObject createProduct(ManageProductDomainObject manageProductDomainObject, MultipartFile imageFile, String sellerId) throws IOException;
    ManageProductDomainObject updateProduct(UUID productId, ManageProductDomainObject manageProductDomainObject, String sellerId);
    void deleteProduct(UUID productId, String sellerId);
//    Optional<ManageProductDomainObject> getProductById(UUID productId);
    @Transactional(readOnly = true)
    ManageProductDomainObject getProductById(UUID productId, String sellerId);

    List<ManageProductDomainObject> getAllProductsBySellerId(String sellerId);
}



