package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.ManageProductUseCase;
import com.semicolon.ecommerceTask.application.port.output.FileStorageOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.CategoryPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.ProductPersistenceOutPort;
import com.semicolon.ecommerceTask.application.port.output.persistence.SellerFormSubmissionPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.domain.model.ManageProductDomainObject;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.productManagentsMapper.ProductDtoMapper;
import com.semicolon.ecommerceTask.infrastructure.output.persistence.mapper.productManagentsMapper.ProductPersistenceMapper;
import com.semicolon.ecommerceTask.infrastructure.utilities.MessageUtil;
import com.semicolon.ecommerceTask.domain.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
    private final ProductDtoMapper dtoMapper;

    @Transactional
    @Override
    public ManageProductDomainObject createProduct(ManageProductDomainObject domain, MultipartFile imageFile, String sellerId) throws IOException {
        sellerFormSubmissionPersistenceOutPort.findByKeycloakUserId(sellerId).orElseThrow(() -> new ValidationException(MessageUtil.SELLER_NOT_FOUND_WITH_ID + sellerId));
        domain.setSellerId(sellerId);
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileStorageOutPort.storeFile(imageFile);
            domain.setImageUrl(imageUrl);
        }
        categoryPersistenceOutPort.validateCategoryExists(domain.getCategoryDomainObject());
        return productPersistenceOutPort.save(domain);
    }

    @Transactional
    @Override
    public ManageProductDomainObject updateProduct(UUID productId, ManageProductDomainObject manageProductDomainObject, String sellerId) {
        ManageProductDomainObject existingProduct = productPersistenceOutPort.findById(productId)
                .orElseThrow(() -> new RuntimeException(MessageUtil.PRODUCT_NOT_FOUND));
        if (!existingProduct.getSellerId().equals(sellerId)) {throw new RuntimeException(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_UPDATE_THIS_PRODUCT);}
        CategoryDomainObject categoryDomainObject = categoryPersistenceOutPort.findById(UUID.fromString(manageProductDomainObject.getCategoryDomainObject()))
                .orElseThrow(() -> new RuntimeException(MessageUtil.CATEGORY_NOT_FOUND));
        existingProduct.setName(manageProductDomainObject.getName());
        existingProduct.setDescription(manageProductDomainObject.getDescription());
        existingProduct.setPrice(manageProductDomainObject.getPrice());
        existingProduct.setInStockQuantity(manageProductDomainObject.getInStockQuantity());
        existingProduct.setCategoryDomainObject(categoryDomainObject.getId().toString());
        return productPersistenceOutPort.save(existingProduct);
    }

    @Transactional
    @Override
    public void deleteProduct(UUID productId, String sellerId) {
        ManageProductDomainObject productToDelete = productPersistenceOutPort.findById(productId).orElseThrow(()-> new IllegalArgumentException(MessageUtil.PRODUCT_NOT_FOUND));
        if(!productToDelete.getSellerId().equals(sellerId)){throw new IllegalArgumentException(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_DELETE_THIS_PRODUCT);}
        productPersistenceOutPort.deleteById(productId);
    }

    @Transactional(readOnly = true)
    @Override
    public ManageProductDomainObject getProductById(UUID productId, String sellerId) {
        ManageProductDomainObject product = productPersistenceOutPort.findById(productId).orElseThrow(() -> new ValidationException(MessageUtil.PRODUCT_NOT_FOUND + productId));
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"));
        if (!isAdmin && !product.getSellerId().equals(sellerId)) {throw new ValidationException(MessageUtil.USER_IS_NOT_AUTHORIZED_TO_ACCESS_THIS_PRODUCT);}
        return product;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ManageProductDomainObject> getAllProductsBySellerId(String sellerId) {
        List<ManageProductDomainObject> allProducts = productPersistenceOutPort.findAllBySellerId(sellerId);
        if(allProducts.isEmpty()){throw new IllegalArgumentException(MessageUtil.YOU_HAVE_NO_PRODUCTS_YET);}
        return allProducts;
    }
}