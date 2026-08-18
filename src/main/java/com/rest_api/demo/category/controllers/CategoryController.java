package com.rest_api.demo.category.controllers;

import com.rest_api.demo.category.dto.CategoryResponse;
import com.rest_api.demo.category.dto.CreateCategoryRequest;
import com.rest_api.demo.category.dto.UpdateCategoryRequest;
import com.rest_api.demo.category.services.CategoryService;
import com.rest_api.demo.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        
        return ApiResponse.<List<CategoryResponse>>create()
                .success("Kateqoriyalar uğurla gətirildi")
                .data(categories)
                .response();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        CategoryResponse category = categoryService.getCategoryById(id);
        
        return ApiResponse.<CategoryResponse>create()
                .success("Kateqoriya tapıldı")
                .data(category)
                .response();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        CategoryResponse created = categoryService.createCategory(request);
        
        return ApiResponse.<CategoryResponse>create()
                .success("Kateqoriya uğurla yaradıldı", HttpStatus.CREATED.value())
                .data(created)
                .response();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request) {
        CategoryResponse updated = categoryService.updateCategory(id, request);
        
        return ApiResponse.<CategoryResponse>create()
                .success("Kateqoriya uğurla yeniləndi")
                .data(updated)
                .response();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        
        return ApiResponse.<Void>create()
                .success("Kateqoriya uğurla silindi")
                .response();
    }
}