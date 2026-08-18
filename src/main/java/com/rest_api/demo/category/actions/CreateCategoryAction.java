package com.rest_api.demo.category.actions;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.dto.CreateCategoryRequest;
import com.rest_api.demo.category.entities.Category;
import com.rest_api.demo.category.mappers.CategoryMapper;
import com.rest_api.demo.category.repositories.CategoryRepository;
import com.rest_api.demo.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryAction {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CreateCategoryAction(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponse execute(CreateCategoryRequest request) {
        Category parent = null;
        if (request.getParentId() != null && request.getParentId() > 0) {
            parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Valideyn kateqoriya tapılmadı: ID " + request.getParentId()));
        }

        Category category = categoryMapper.toEntity(request, parent);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }
}
