package com.semicolon.ecommerceTask.application.port.output.persistence;

import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductPersistenceOutPort {
    ProductDomainObject saveProduct(ProductDomainObject product);
    Optional<ProductDomainObject> findProductById(UUID productId);
    List<ProductDomainObject> findAllProductsBySellerId(UUID sellerId);
    void deleteProductById(UUID productId);
}