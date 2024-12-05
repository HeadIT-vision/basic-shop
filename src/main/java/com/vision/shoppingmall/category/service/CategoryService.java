package com.vision.shoppingmall.category.service;

import com.vision.shoppingmall.category.model.entity.Category;
import com.vision.shoppingmall.category.model.exception.CategoryNameDuplicationException;
import com.vision.shoppingmall.category.model.request.CreateCategoryRequest;
import com.vision.shoppingmall.category.model.response.CategoryCreateResponse;
import com.vision.shoppingmall.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryCreateResponse createCategory(CreateCategoryRequest request) {
    //1. 카테고리 이름 중복검사
    if(categoryRepository.existsByCategoryName(request.getCategoryName()))
      throw new CategoryNameDuplicationException();

    //2. 카테고리 생성
    Category newCategory = Category.create(request);
    Category category = categoryRepository.save(newCategory);
    return new CategoryCreateResponse(category.getId(), category.getCategoryName());
  }
}
