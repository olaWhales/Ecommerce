package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.ProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUploadDto;
import com.semicolon.ecommerceTask.infrastructure.adapter.input.data.request.ProductUpdateDto;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public interface ManageProductUseCase {
    ProductDomainObject createProduct(ProductUploadDto productDto, MultipartFile imageFile, UUID sellerId);
    ProductDomainObject getProduct(UUID productId);
    List<ProductDomainObject> getAllProductsBySeller(UUID sellerId);
    ProductDomainObject updateProduct(UUID productId, ProductUpdateDto productDto);
    void deleteProduct(UUID productId);
}
