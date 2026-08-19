package com.example.demo.category.service;


import com.example.demo.category.action.GetCategoriesAction;
import com.example.demo.category.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CategoryService {
    
    private final GetCategoriesAction getCategoriesAction;

    public Page<Category> getCategories(int page, int limit){
        return getCategoriesAction.execute(page, limit);
    }
    
}