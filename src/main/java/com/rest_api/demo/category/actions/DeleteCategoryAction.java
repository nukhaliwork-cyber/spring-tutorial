package com.rest_api.demo.category.actions;

import com.rest_api.demo.category.entities.Category;
import com.rest_api.demo.category.repositories.CategoryRepository;
import com.rest_api.demo.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DeleteCategoryAction {

    private final CategoryRepository categoryRepository;

    public DeleteCategoryAction(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void execute(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID " + id + " olan kateqoriya tapılmadı"));

        categoryRepository.delete(category);
    }
}
