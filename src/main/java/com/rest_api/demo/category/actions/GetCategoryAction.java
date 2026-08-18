package com.rest_api.demo.category.actions;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.entities.Category;
import com.rest_api.demo.category.mappers.CategoryMapper;
import com.rest_api.demo.category.repositories.CategoryRepository;
import com.rest_api.demo.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GetCategoryAction {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public GetCategoryAction(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public CategoryResponse execute(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID " + id + " olan kateqoriya tapılmadı"));

        return categoryMapper.toResponse(category);
    }
}
