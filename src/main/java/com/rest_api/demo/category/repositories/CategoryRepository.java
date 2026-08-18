package com.rest_api.demo.category.repositories;

import com.rest_api.demo.category.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Parent ID-yə və rank-a görə sıralama ilə tapmaq üçün nümunə metod
    List<Category> findAllByOrderByRankAsc();
    
    List<Category> findByParentIdOrderByRankAsc(Long parentId);
}
