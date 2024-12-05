package com.vision.shoppingmall.category.repository;

import com.vision.shoppingmall.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  boolean existsByCategoryName(String categoryName);
}
