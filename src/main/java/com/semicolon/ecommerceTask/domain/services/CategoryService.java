package com.semicolon.ecommerceTask.domain.services;

import com.semicolon.ecommerceTask.application.port.input.CategoryUseCase;
import com.semicolon.ecommerceTask.application.port.output.persistence.CategoryPersistenceOutPort;
import com.semicolon.ecommerceTask.domain.model.CategoryDomainObject;
import com.semicolon.ecommerceTask.infrastructure.adapter.utilities.MessageUtil;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService implements CategoryUseCase {
    private final CategoryPersistenceOutPort categoryPersistenceOutPort;

    @Override
    @Transactional
    public CategoryDomainObject createCategory(CategoryDomainObject categoryDomainObject) {
        if (categoryPersistenceOutPort.existsByName(categoryDomainObject.getName())) {throw new RuntimeException(MessageUtil.CATEGORY_ALREADY_EXISTS);}
        return categoryPersistenceOutPort.save(categoryDomainObject);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDomainObject getCategoryById(UUID id) {
        return categoryPersistenceOutPort.findById(id)
            .orElseThrow(() -> new RuntimeException(MessageUtil.CATEGORY_NOT_FOUND));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDomainObject> getAllCategories() {
        return categoryPersistenceOutPort.findAll();
    }
}
