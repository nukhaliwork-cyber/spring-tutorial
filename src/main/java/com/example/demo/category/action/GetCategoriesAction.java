package com.example.demo.category.action;


import com.example.demo.category.entity.Category;
import com.example.demo.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class GetCategoriesAction {

    private final CategoryRepository categoryRepository;

    public Page<Category> execute(int page, int limit) {
        int pageNumber = Math.max(0, page -1);

        Pageable pageable = PageRequest.of(
            pageNumber,
            limit,
            Sort.by("rank")
            .ascending()
            .and(Sort.by("id").descending())
        );

        return categoryRepository.findAll(pageable);
    }
}