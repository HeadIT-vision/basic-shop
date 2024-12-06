package com.vision.shoppingmall.category.service;

import com.vision.shoppingmall.category.model.entity.Category;
import com.vision.shoppingmall.category.model.exception.CategoryNameDuplicationException;
import com.vision.shoppingmall.category.model.exception.CategoryNotFoundException;
import com.vision.shoppingmall.category.model.request.CreateCategoryRequest;
import com.vision.shoppingmall.category.model.response.CategoryCreateResponse;
import com.vision.shoppingmall.category.model.response.CategoryListResponse;
import com.vision.shoppingmall.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  public Page<CategoryListResponse> getCategories(int page) {
    PageRequest request = PageRequest.of(page, 10);
    Page<Category> categories = categoryRepository.findAllByOrderById(request);

    return categories
        .map(category ->
            new CategoryListResponse(category.getId(), category.getCategoryName())
        );
  }
  
  public CategoryListResponse getCategoryById(Long id) {
    //1. 해당 카테고리가 존재하지 않으면 오류 발생
    //2. 존재하면 response 객체로 반환
    Category category
        = categoryRepository.findById(id)
        .orElseThrow(CategoryNotFoundException::new);
    return new CategoryListResponse(category.getId(), category.getCategoryName());
  }
}
