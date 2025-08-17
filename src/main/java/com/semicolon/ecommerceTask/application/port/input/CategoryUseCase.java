package com.semicolon.ecommerceTask.application.port.input;

import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;

import java.util.List;
import java.util.UUID;

public interface CategoryUseCase {
    CategoryDomainObject createCategory(CategoryDomainObject categoryDomainObject);
    CategoryDomainObject getCategoryById(UUID id);
    List<CategoryDomainObject> getAllCategories();
}
