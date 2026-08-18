package com.rest_api.demo.category.actions;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.mappers.CategoryMapper;
import com.rest_api.demo.category.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListCategoriesAction {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ListCategoriesAction(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponse> execute() {
        return categoryRepository.findByParentIsNullOrderByRankAsc()
                .stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}
