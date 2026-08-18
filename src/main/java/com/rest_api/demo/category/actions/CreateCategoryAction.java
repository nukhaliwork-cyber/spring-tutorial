package com.rest_api.demo.category.actions;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.dto.CreateCategoryRequest;
import com.rest_api.demo.category.entities.Category;
import com.rest_api.demo.category.mappers.CategoryMapper;
import com.rest_api.demo.category.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCategoryAction {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse execute(CreateCategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }
}
