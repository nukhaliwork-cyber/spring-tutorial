package com.rest_api.demo.category.services;

import com.rest_api.demo.category.actions.*;
import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.dto.CreateCategoryRequest;
import com.rest_api.demo.category.dto.UpdateCategoryRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final ListCategoriesAction listCategoriesAction;
    private final GetCategoryAction getCategoryAction;
    private final CreateCategoryAction createCategoryAction;
    private final UpdateCategoryAction updateCategoryAction;
    private final DeleteCategoryAction deleteCategoryAction;

    public CategoryService(
            ListCategoriesAction listCategoriesAction,
            GetCategoryAction getCategoryAction,
            CreateCategoryAction createCategoryAction,
            UpdateCategoryAction updateCategoryAction,
            DeleteCategoryAction deleteCategoryAction) {
        this.listCategoriesAction = listCategoriesAction;
        this.getCategoryAction = getCategoryAction;
        this.createCategoryAction = createCategoryAction;
        this.updateCategoryAction = updateCategoryAction;
        this.deleteCategoryAction = deleteCategoryAction;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return listCategoriesAction.execute();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        return getCategoryAction.execute(id);
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        return createCategoryAction.execute(request);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
        return updateCategoryAction.execute(id, request);
    }

    @Transactional
    public void deleteCategory(Long id) {
        deleteCategoryAction.execute(id);
    }
}
