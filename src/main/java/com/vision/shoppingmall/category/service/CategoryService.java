package com.vision.shoppingmall.category.service;

import com.vision.shoppingmall.category.model.entity.Category;
import com.vision.shoppingmall.category.model.exception.CategoryHasProductsException;
import com.vision.shoppingmall.category.model.exception.CategoryNameDuplicationException;
import com.vision.shoppingmall.category.model.exception.CategoryNotFoundException;
import com.vision.shoppingmall.category.model.request.CategoryUpdateRequest;
import com.vision.shoppingmall.category.model.request.CreateCategoryRequest;
import com.vision.shoppingmall.category.model.response.CategoryCreateResponse;
import com.vision.shoppingmall.category.model.response.CategoryListResponse;
import com.vision.shoppingmall.category.repository.CategoryRepository;
import com.vision.shoppingmall.product.model.entity.Product;
import com.vision.shoppingmall.product.model.entity.ProductStatus;
import com.vision.shoppingmall.product.repository.ProductRepository;
import com.vision.shoppingmall.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final ProductService productService;

  public CategoryCreateResponse createCategory(CreateCategoryRequest request) {
    //1. 카테고리 이름 중복검사
    if(categoryRepository.existsByCategoryName(request.getCategoryName()))
      throw new CategoryNameDuplicationException();

    //2. 카테고리 생성
    Category newCategory = Category.create(request);
    Category category = categoryRepository.save(newCategory);
    return new CategoryCreateResponse(category.getId(), category.getCategoryName());
  }

  @Transactional
  public Page<CategoryListResponse> getCategories(int page) {
    PageRequest request = PageRequest.of(page, 10);
    Page<Category> categories = categoryRepository.findAllByOrderById(request);

    return categories
        .map(category ->
            new CategoryListResponse(
                category.getId(),
                category.getCategoryName(),
                category.getProducts().stream().filter(product ->
                  product.getProductStatus() == ProductStatus.ACTIVE
                ).count())
        );
  }
  
  public CategoryListResponse getCategoryById(Long id) {
    //1. 해당 카테고리가 존재하지 않으면 오류 발생
    //2. 존재하면 response 객체로 반환
    Category category
        = categoryRepository.findById(id)
        .orElseThrow(CategoryNotFoundException::new);
    return new CategoryListResponse(category.getId(), category.getCategoryName(), 0);
  }

  public void updateCategory(Long id, CategoryUpdateRequest request) {
    Category category
        = categoryRepository.findById(id)
        .orElseThrow(CategoryNotFoundException::new);
    if(categoryRepository.existsByCategoryNameAndIdNot(request.getCategoryName(), id))
      throw new CategoryNameDuplicationException();

    category.update(request.getCategoryName());
    categoryRepository.save(category);
  }

  @Transactional
  public void deleteCategory(Long id) {
    Category category
        = categoryRepository.findById(id)
        .orElseThrow(CategoryNotFoundException::new);

    boolean hasActiveProducts
        = category.getProducts().stream()
        .anyMatch(product -> product.getProductStatus() == ProductStatus.ACTIVE);
    if(hasActiveProducts)
      throw new CategoryHasProductsException();

    List<Product> inactiveProducts
        = category.getProducts().stream()
            .filter(product -> product.getProductStatus() == ProductStatus.INACTIVE)
            .toList();
    productService.removeCategory(inactiveProducts);
    categoryRepository.delete(category);
  }

  public List<CategoryListResponse> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();

    return  categories.stream().map(
        category ->
            new CategoryListResponse(category.getId(), category.getCategoryName(), 0)
    ).toList();
  }
}
