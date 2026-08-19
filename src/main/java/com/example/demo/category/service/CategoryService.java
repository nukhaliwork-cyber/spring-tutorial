package com.example.demo.category.service;

import com.example.demo.category.action.CreateCategoryAction;
import com.example.demo.category.action.GetCategoriesAction;
import com.example.demo.category.dto.CreateCategoryRequest;
import com.example.demo.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final GetCategoriesAction getCategoriesAction;
    private final CreateCategoryAction createCategoryAction;

    public Page<Category> getCategories(int page, int limit) {
        return getCategoriesAction.execute(page, limit);
    }

    public Category createCategory(CreateCategoryRequest request) {
        return createCategoryAction.execute(request);
    }
}